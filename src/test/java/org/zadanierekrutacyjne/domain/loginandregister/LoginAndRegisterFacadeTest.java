package org.zadanierekrutacyjne.domain.loginandregister;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.zadanierekrutacyjne.domain.loginandregister.dto.UserRequestDto;
import org.zadanierekrutacyjne.domain.loginandregister.dto.UserResponseDto;

import static org.junit.jupiter.api.Assertions.assertAll;

class LoginAndRegisterFacadeTest {
    final UserRepoTestImpl userRepoTest = new UserRepoTestImpl();
    final UserMapper userMapper = new UserMapper();
    LoginAndRegisterFacade loginAndRegisterFacade;

    {
        loginAndRegisterFacade = new LoginAndRegisterFacade(
                userRepoTest,
                userMapper,
                new UserUpdater(userRepoTest, userMapper)
        );
        UserRequestDto userRequestDto1 = new UserRequestDto("login1", "password1");
        UserResponseDto userResponseDto1 = loginAndRegisterFacade.register(userRequestDto1);

        UserRequestDto userRequestDto2 = new UserRequestDto("login2", "password2");
        UserResponseDto userResponseDto2 = loginAndRegisterFacade.register(userRequestDto2);

        UserRequestDto userRequestDto3 = new UserRequestDto("login3", "password3");
        UserResponseDto userResponseDto3 = loginAndRegisterFacade.register(userRequestDto3);
    }

    @ParameterizedTest(name = "Test {index} - login: {0}, password: {1}")
    @CsvSource({
            "login1, password1",
            "login2, password2",
            "login3, password3"
    })
    void check_if_user_can_be_registered(String login, String password) {
        UserRequestDto userRequestDto = new UserRequestDto(login, password);
        UserResponseDto userResponseDto = loginAndRegisterFacade.register(userRequestDto);

        assertAll(
                () -> Assertions.assertEquals(login, userResponseDto.login()),
                () -> Assertions.assertEquals(password, userResponseDto.password())
        );
    }


    @ParameterizedTest(name = "Test {index} - login: {0}, password: {1}")
    @CsvSource({
            "login1, password1",
            "login2, password2",
            "login3, password3"
    })
    void check_if_user_can_be_found_by_login(String login, String password) {
        UserResponseDto userResponseDto = loginAndRegisterFacade.findByUsername(login);

        assertAll(
                () -> Assertions.assertEquals(login, userResponseDto.login()),
                () -> Assertions.assertEquals(password, userResponseDto.password())
        );
    }


    @ParameterizedTest(name = "Test {index} - login: {0}, password: {1}")
    @CsvSource({
            "login1, newLogin1, password1",
            "login2, newLogin2,  password2",
            "login3, newLogin3, password3"
    })
    void check_if_user_can_be_updated_by_login(String login, String newLogin, String password) {
        UserRequestDto userRequestDto = new UserRequestDto(newLogin, password);
        UserResponseDto userResponseDto = loginAndRegisterFacade.updateByLogin(login, userRequestDto);

        assertAll(
                () -> Assertions.assertEquals(newLogin, userResponseDto.login()),
                () -> Assertions.assertEquals(password, userResponseDto.password())
        );
    }

    @ParameterizedTest(name = "Test {index} - login: {0}, password: {1}")
    @CsvSource({
            "login1, password1",
            "login2, password2",
            "login3, password3"
    })
    void check_if_user_can_be_deleted_by_login(String login, String password) {
        UserResponseDto userResponseDto = loginAndRegisterFacade.deleteUser(login);

        assertAll(
                () -> Assertions.assertEquals(login, userResponseDto.login()),
                () -> Assertions.assertEquals(password, userResponseDto.password()),
                () -> Assertions.assertThrows(
                        org.springframework.security.core.userdetails.UsernameNotFoundException.class,
                        () -> loginAndRegisterFacade.findByUsername(login)
                )
        );
    }
}