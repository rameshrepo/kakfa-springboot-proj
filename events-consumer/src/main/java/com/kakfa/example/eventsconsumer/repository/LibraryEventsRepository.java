package com.kakfa.example.eventsconsumer.repository;

import com.kakfa.example.eventsconsumer.entity.LibraryEvent;
import org.springframework.data.repository.CrudRepository;

public interface LibraryEventsRepository extends CrudRepository<LibraryEvent,Integer> {
}
