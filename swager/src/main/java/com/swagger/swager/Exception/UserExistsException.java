package com.swagger.swager.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExistsException extends Throwable {
    public UserExistsException(String e) {
        super(e);
        log.info("User Not Available");
    }

    public UserExistsException() {

        super();
        log.info("User Not Available");
    }
}
