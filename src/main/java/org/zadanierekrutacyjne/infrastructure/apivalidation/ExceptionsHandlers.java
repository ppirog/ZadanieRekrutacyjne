package org.zadanierekrutacyjne.infrastructure.apivalidation;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ControllerAdvice
public class ExceptionsHandlers {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public DuplicateKeyExceptionDto handleDataIntegrityViolationException() {
        final String loginAlreadyExists = "Login already exists";
        return DuplicateKeyExceptionDto.builder()
                .message(loginAlreadyExists)
                .build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public UsernameNotFoundExceptionDto handleUsernameNotFoundException() {
        final String loginNotFound = "Login not found";
        return UsernameNotFoundExceptionDto.builder()
                .message(loginNotFound)
                .build();
    }

}
