package com.inditex.product.offer.adapter.outbound.jpa.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Custom exception for handling offer-related errors.
 * <p>
 * This exception is used to encapsulate error messages and HTTP status codes
 * for offer-related operations. It extends {@link RuntimeException} to allow
 * unchecked exceptions.
 * </p>
 * <p>
 * The {@code errorMessage} provides a detailed description of the error, while
 * the {@code httpStatus} indicates the corresponding HTTP status code to be
 * returned in the response.
 * </p>
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Getter
@Setter
public class OfferException extends RuntimeException {

    private final String errorMessage;

    private final HttpStatus httpStatus;

    /**
     * Default constructor
     *
     * @param errorMessage error errorMessage
     * @param httpStatus   httpStatus
     *
     */
    public OfferException(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

}
