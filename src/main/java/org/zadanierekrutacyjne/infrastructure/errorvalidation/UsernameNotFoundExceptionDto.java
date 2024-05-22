package org.zadanierekrutacyjne.infrastructure.errorvalidation;

import lombok.Builder;

@Builder
public record UsernameNotFoundExceptionDto(
        String message
) {
}
