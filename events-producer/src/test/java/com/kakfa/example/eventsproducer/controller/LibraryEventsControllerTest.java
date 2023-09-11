package com.kakfa.example.eventsproducer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakfa.example.eventsproducer.domain.Book;
import com.kakfa.example.eventsproducer.domain.LibraryEvent;
import com.kakfa.example.eventsproducer.domain.LibraryEventType;
import com.kakfa.example.eventsproducer.producer.LibraryEventsProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(LibraryEventsController.class)
public class LibraryEventsControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    LibraryEventsProducer libraryEventsProducer;


    @Test
    void postLibraryEvent() throws Exception {
        //given
        LibraryEvent libraryEvent = new LibraryEvent(null, LibraryEventType.NEW,
                new Book(123, "Dilip","Kafka Using Spring Boot" ));

        String json = objectMapper.writeValueAsString(libraryEvent);

        // when
        when(libraryEventsProducer.sendEvent(isA(LibraryEvent.class))).thenReturn(null);

        // then
        mockMvc.perform(post("/v1/libraryevent")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void postLibraryEvent_InvalidInput() throws Exception {
        //given

        // Invalid Input - No Book Id and BookName is empty
        LibraryEvent libraryEvent = new LibraryEvent(null, LibraryEventType.NEW,
                new Book(null, "","Kafka Using Spring Boot" ));

        String json = objectMapper.writeValueAsString(libraryEvent);
        when(libraryEventsProducer.sendEvent(isA(LibraryEvent.class))).thenReturn(null);
        //expect
        String expectedErrorMessage = "book.id - must not be null, book.name - must not be blank";
        mockMvc.perform(post("/v1/libraryevent")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(expectedErrorMessage));

    }

}
