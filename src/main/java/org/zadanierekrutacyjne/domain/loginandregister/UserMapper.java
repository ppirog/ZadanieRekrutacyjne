package org.zadanierekrutacyjne.domain.loginandregister;

import org.springframework.stereotype.Service;
import org.zadanierekrutacyjne.domain.loginandregister.dto.UserRequestDto;
import org.zadanierekrutacyjne.domain.loginandregister.dto.UserResponseDto;

@Service
class UserMapper {

    User mapToUser(UserRequestDto userRequestDto) {
        return User.builder()
                .login(userRequestDto.login())
                .password(userRequestDto.password())
                .build();
    }

    UserResponseDto mapToUserResponseDto(User user) {
        return UserResponseDto.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
    }
}
