package org.zadanierekrutacyjne.infrastructure.security;

import lombok.Builder;

@Builder
public record TokenRequestDto(
        String login,
        String password
) {
}
