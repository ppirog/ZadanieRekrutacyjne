package org.zadanierekrutacyjne.infrastructure.security.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(
        String login,
        String token
) {
}
