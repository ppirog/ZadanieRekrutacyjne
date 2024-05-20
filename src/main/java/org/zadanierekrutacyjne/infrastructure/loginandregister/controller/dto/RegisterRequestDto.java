package org.zadanierekrutacyjne.infrastructure.loginandregister.controller.dto;

import lombok.Builder;

@Builder
public record RegisterRequestDto(
        String login,
        String password
) {
}
