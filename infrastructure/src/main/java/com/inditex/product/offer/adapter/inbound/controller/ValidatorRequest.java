package com.inditex.product.offer.adapter.inbound.controller;

import com.inditex.product.offer.adapter.inbound.exception.ValidationOfferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

@Slf4j
public abstract class ValidatorRequest {

    /**
     * Validate Request from a DTO
     *
     * @param validator    {@link Validator}
     * @param dto          {@link Object} to validate
     * @param dtoId        DTO id
     * @param errorMessage error message if occurs
     * @throws ValidationOfferException if not pass the validations
     */
    protected void validateRequest(Validator validator, Object dto, String dtoId,
      String errorMessage) {
        var errors = new BeanPropertyBindingResult(dto, dtoId);
        validator.validate(dto, errors);

        if (errors.hasErrors()) {
            LOGGER.error(errorMessage, errors);
            throw new ValidationOfferException(errors.toString());
        }
    }
}
