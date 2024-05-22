package org.zadanierekrutacyjne.infrastructure.security.dto;

import lombok.Builder;

@Builder
public record TokenRequestDto(
        String login,
        String password
) {
}
