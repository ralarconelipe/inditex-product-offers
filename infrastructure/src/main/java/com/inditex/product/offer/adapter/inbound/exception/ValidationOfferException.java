package com.inditex.product.offer.adapter.inbound.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Validation exception extends {@link ResponseStatusException} using default Spring error handler
 *
 *
 */
@ResponseStatus(code = BAD_REQUEST)
public class ValidationOfferException extends RuntimeException {

  /**
   * Default constructor
   * @param reason error reason
   */
  public ValidationOfferException(String reason) {
    super(reason);
  }
}
