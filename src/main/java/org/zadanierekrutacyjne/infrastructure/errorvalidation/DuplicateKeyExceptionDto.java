package org.zadanierekrutacyjne.infrastructure.errorvalidation;

import lombok.Builder;


@Builder
public record DuplicateKeyExceptionDto(
        String message
) {
}
