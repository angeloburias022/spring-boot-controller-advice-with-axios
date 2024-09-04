package com.springboot.controller_advice.config;

import java.time.LocalDateTime; // Imports the LocalDateTime class for handling date and time information.
import java.util.HashMap; // Imports the HashMap class to create a map for storing key-value pairs.
import java.util.Map; // Imports the Map interface, which provides a structure for mapping keys to values.

import org.springframework.http.HttpStatus; // Imports the HttpStatus enumeration, which contains HTTP status codes.
import org.springframework.http.ResponseEntity; // Imports the ResponseEntity class, used to represent HTTP responses.
import org.springframework.web.bind.annotation.ControllerAdvice; // Imports the ControllerAdvice annotation, which allows defining global exception handling.
import org.springframework.web.bind.annotation.ExceptionHandler; // Imports the ExceptionHandler annotation, used to specify the exception types to handle.
import org.springframework.web.bind.MethodArgumentNotValidException; // Imports MethodArgumentNotValidException for handling validation errors.

@ControllerAdvice // Marks this class as a global exception handler for all controllers in the
                  // application.
public class GlobalExceptionHandler {

    /**
     * Handles RuntimeException and sends a structured response with details about
     * the error.
     *
     * @param ex the RuntimeException thrown in the application
     * @return ResponseEntity containing a map with error details and an HTTP status
     *         code
     */
    @ExceptionHandler(RuntimeException.class) // Specifies that this method handles exceptions of type RuntimeException.
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> response = new HashMap<>(); // Creates a new HashMap to store the response data.
        response.put("timestamp", LocalDateTime.now()); // Adds the current timestamp to the response.
        response.put("status", HttpStatus.BAD_REQUEST.value()); // Adds the HTTP status code to the response.
        response.put("error", "Bad Request"); // Adds a description of the error type to the response.
        response.put("message", ex.getMessage()); // Adds the error message from the exception.
        response.put("path", "/api/error"); // Adds the request path where the error occurred.

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // Returns the response entity with error
                                                                       // details.
    }

    /**
     * Handles NullPointerException and returns a response with a 500 Internal
     * Server Error status.
     *
     * @param ex the NullPointerException thrown in the application
     * @return ResponseEntity containing a map with error details and an HTTP status
     *         code
     */
    @ExceptionHandler(NullPointerException.class) // Handles exceptions of type NullPointerException.
    public ResponseEntity<Map<String, Object>> handleNullPointerException(NullPointerException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); // Sets the status code to 500.
        response.put("error", "Internal Server Error");
        response.put("message", "A null pointer exception occurred.");
        response.put("path", "/api/error");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // Returns response with 500 status.
    }

    /**
     * Handles IllegalArgumentException and returns a response with a 400 Bad
     * Request status.
     *
     * @param ex the IllegalArgumentException thrown in the application
     * @return ResponseEntity containing a map with error details and an HTTP status
     *         code
     */
    @ExceptionHandler(IllegalArgumentException.class) // Handles exceptions of type IllegalArgumentException.
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value()); // Sets the status code to 400.
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage());
        response.put("path", "/api/error");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // Returns response with 400 status.
    }

    /**
     * Handles MethodArgumentNotValidException for validation errors and returns a
     * 400 Bad Request status.
     *
     * @param ex the MethodArgumentNotValidException thrown when method arguments
     *           fail validation
     * @return ResponseEntity containing a map with error details and an HTTP status
     *         code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class) // Handles validation errors for method arguments.
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value()); // Sets the status code to 400.
        response.put("error", "Validation Error");
        response.put("message", "Validation failed for one or more arguments.");
        response.put("path", "/api/error");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // Returns response with 400 status.
    }

    // /**
    //  * Handles EntityNotFoundException when an entity is not found in the database
    //  * and returns a 404 Not Found status.
    //  *
    //  * @param ex the EntityNotFoundException thrown when an entity is not found
    //  * @return ResponseEntity containing a map with error details and an HTTP status
    //  *         code
    //  */
    // @ExceptionHandler(EntityNotFoundException.class) // Handles exceptions when an entity is not found.
    // public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
    //     Map<String, Object> response = new HashMap<>();
    //     response.put("timestamp", LocalDateTime.now());
    //     response.put("status", HttpStatus.NOT_FOUND.value()); // Sets the status code to 404.
    //     response.put("error", "Not Found");
    //     response.put("message", ex.getMessage());
    //     response.put("path", "/api/error");

    //     return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // Returns response with 404 status.
    // }
}
