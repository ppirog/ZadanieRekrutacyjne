package org.zadanierekrutacyjne.infrastructure.loginandregister.controller.dto;

import lombok.Builder;

@Builder
public record RegisterResponseDto(
        String login,
        String message
) {
}
