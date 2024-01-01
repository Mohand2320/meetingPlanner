package com.meetingPlanner.exception.handler;



import com.meetingPlanner.exception.EntityAleadyExisteException;
import com.meetingPlanner.exception.EntityNotFoundException;
import com.meetingPlanner.outil.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {
    /**
     * L'annotation @ExceptionHandler est utilisée pour gérer les exceptions dans des classes
     * de gestionnaires spécifiques ou des méthodes de gestionnaires. Elle permet de définir
     * des méthodes qui seront appelées pour traiter des types
     * spécifiques d'exceptions qui peuvent survenir lors du traitement d'une requête.
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> entityNotFoundException(EntityNotFoundException ex){

        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .code(404)
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = {EntityAleadyExisteException.class})
    public ResponseEntity<Object> entityAleadyExisteException(EntityAleadyExisteException ex){

        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .code(409)
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> HandlerMethodeArgumentNotValid(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach( error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return  new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);

    }
}
