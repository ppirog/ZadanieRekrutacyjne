package org.zadanierekrutacyjne.infrastructure.security;

import lombok.Builder;

@Builder
public record JwtResponseDto(
        String login,
        String token
) {
}
