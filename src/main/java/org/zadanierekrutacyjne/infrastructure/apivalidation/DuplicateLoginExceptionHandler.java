package org.zadanierekrutacyjne.infrastructure.apivalidation;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ControllerAdvice
public class DuplicateLoginExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DuplicateKeyExceptionDto handleException(DataIntegrityViolationException exception) {
        return DuplicateKeyExceptionDto.builder()
                .message("Login already exists")
                .build();
    }

}
