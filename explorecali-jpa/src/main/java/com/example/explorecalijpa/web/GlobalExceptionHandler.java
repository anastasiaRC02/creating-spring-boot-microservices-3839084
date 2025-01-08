package com.example.explorecalijpa.web;

import java.rmi.UnexpectedException;
import java.util.NoSuchElementException;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
  
  /**
   * Leverage Exception Handler framework for id not found exception
   * 
   * @param ex      NoSuchElementException
   * @param request WebRequest
   * @return http response
   */
  @ExceptionHandler(NoSuchElementException.class)
  public final ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
      ex.getMessage());
    return createResponseEntity(problemDetail, null, HttpStatus.NOT_FOUND, request);
  }

  /**
   * Leveraging Exception Handler framework for resource not found exception
   * 
   * @param ex      ResourceNotFoundException
   * @param request WebRequest
   * @return http response
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<Object> handleResourceNotFoundException(
    ResourceNotFoundException ex, WebRequest request) {
      ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
        ex.getMessage());
      return createResponseEntity(problemDetail, null, HttpStatus.NOT_FOUND, request);
    }

  /**
   * Leverage Exception Handler framework for unexpected exceptions
   * 
   * @param ex Exception
   * @param request WebRequest
   * @return http response
   */
  @ExceptionHandler(UnexpectedException.class)
  public final ResponseEntity<Object> handleUnexpectedException(
    Exception ex, WebRequest requst) {
      ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
        ex.getMessage());
      return createResponseEntity(problemDetail, null,
        HttpStatus.INTERNAL_SERVER_ERROR, requst);
    }
}
