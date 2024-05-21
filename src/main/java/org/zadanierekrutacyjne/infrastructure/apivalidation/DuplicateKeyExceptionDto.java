package org.zadanierekrutacyjne.infrastructure.apivalidation;

import lombok.Builder;


@Builder
public record DuplicateKeyExceptionDto(
        String message
) {
}
