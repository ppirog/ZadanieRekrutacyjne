package org.zadanierekrutacyjne.infrastructure.loginandregister.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zadanierekrutacyjne.domain.loginandregister.dto.UserRequestDto;
import org.zadanierekrutacyjne.domain.loginandregister.dto.UserResponseDto;
import org.zadanierekrutacyjne.infrastructure.loginandregister.controller.dto.RegisterRequestDto;
import org.zadanierekrutacyjne.infrastructure.loginandregister.controller.dto.RegisterResponseDto;

@Service
@AllArgsConstructor
class LoginAndRegisterMapper {
    private final PasswordEncoder passwordEncoder;

    UserRequestDto fromReqisterRequestDto(RegisterRequestDto dto) {
        return UserRequestDto.builder()
                .login(dto.login())
                .password(passwordEncoder.encode(dto.password()))
                .build();
    }

    RegisterResponseDto fromUserResponseDto(UserResponseDto dto, String message) {
        return RegisterResponseDto.builder()
                .login(dto.login())
                .message(message)
                .build();
    }

}
