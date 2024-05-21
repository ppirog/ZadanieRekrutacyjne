package org.zadanierekrutacyjne.domain.loginandregister;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.zadanierekrutacyjne.domain.loginandregister.dto.UserRequestDto;
import org.zadanierekrutacyjne.domain.loginandregister.dto.UserResponseDto;

@Log4j2
@AllArgsConstructor
@Component
public class LoginAndRegisterFacade {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto register(UserRequestDto requestDto){
        final User user = userMapper.mapToUser(requestDto);
        final User saved = userRepository.save(user);
        return userMapper.mapToUserResponseDto(saved);
    }

    public UserResponseDto findByUsername(String username){
        final User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userMapper.mapToUserResponseDto(user);
    }
}