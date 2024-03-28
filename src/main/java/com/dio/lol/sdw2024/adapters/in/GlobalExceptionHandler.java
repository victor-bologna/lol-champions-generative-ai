package com.dio.lol.sdw2024.adapters.in;

import com.dio.lol.sdw2024.domain.exception.ChampionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ChampionNotFoundException.class)
    public ResponseEntity<ApiError> handleDomainException(ChampionNotFoundException championNotFoundException) {
        return ResponseEntity.unprocessableEntity()
                .body(new ApiError(championNotFoundException.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpectedError(Exception exception) {
        String message = "Ops, ocorreu um erro inesperado.";
        logger.error(message, exception);
        return ResponseEntity.internalServerError()
                .body(new ApiError(message));
    }

    public record ApiError(String message) { }
}
