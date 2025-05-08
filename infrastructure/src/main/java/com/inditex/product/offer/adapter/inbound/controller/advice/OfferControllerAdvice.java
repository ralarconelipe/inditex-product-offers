package com.inditex.product.offer.adapter.inbound.controller.advice;


import com.inditex.product.offer.adapter.inbound.exception.ValidationOfferException;
import com.inditex.product.offer.adapter.outbound.jpa.exception.OfferException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller advice for handling exceptions in the OfferController. This class intercepts exceptions thrown by the
 * controller methods and provides a consistent response format.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@ControllerAdvice
public class OfferControllerAdvice {

    @ExceptionHandler(OfferException.class)
    public ResponseEntity<String> handleOfferException(OfferException offerException) {
        return ResponseEntity.status(offerException.getHttpStatus()).body(offerException.getErrorMessage());
    }

    @ExceptionHandler(ValidationOfferException.class)
    public ResponseEntity<String> handleValidationOfferException(ValidationOfferException validationOfferException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationOfferException.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRunTimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Database error occurred: [" + ex.getMessage() + "]. Please try again later.");
    }
}
