package org.zadanierekrutacyjne.infrastructure.loginandregister.controller;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.zadanierekrutacyjne.domain.loginandregister.LoginAndRegisterFacade;
import org.zadanierekrutacyjne.domain.loginandregister.dto.UserResponseDto;
import org.zadanierekrutacyjne.infrastructure.loginandregister.controller.dto.RegisterRequestDto;
import org.zadanierekrutacyjne.infrastructure.loginandregister.controller.dto.RegisterResponseDto;

@RestController
@Log
@AllArgsConstructor
public class LoginAndRegisterRestController {

    private final LoginAndRegisterFacade loginAndRegisterFacade;
    private final LoginAndRegisterMapper mapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RegisterResponseDto> registerUser(@RequestBody RegisterRequestDto registerRequestDto) {
        final UserResponseDto userResponseDto = loginAndRegisterFacade.register(mapper.fromReqisterRequestDto(registerRequestDto));
        return ResponseEntity.ok(mapper.fromUserResponseDto(userResponseDto, "REGISTERED"));
    }
}
