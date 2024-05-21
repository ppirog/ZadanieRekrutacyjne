package org.zadanierekrutacyjne.infrastructure.apivalidation;

import lombok.Builder;

@Builder
public record UsernameNotFoundExceptionDto(
        String message
) {
}
