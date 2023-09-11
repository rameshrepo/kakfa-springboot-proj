package com.kakfa.example.eventsproducer.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Book(
        @NotNull
        Integer id,
        @NotBlank
        String name,
        @NotBlank
        String Author
) {
}
