package com.kakfa.example.eventsproducer.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record LibraryEvent(
        Integer libraryEventId,
        LibraryEventType libraryEventType,
        @Valid
        @NotNull
        Book book
) {
}
