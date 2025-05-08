package com.inditex.product.offer.adapter.inbound.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

/**
 * Parent class for offer validators
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
public abstract class AbstractOfferValidator {

    final StaticMessageSource messageSource = new StaticMessageSource();

    protected void assertField(
      BeanPropertyBindingResult errors, String fieldName, String textCountryCodeIsRequired) {
        assertThat(errors.getFieldErrorCount(fieldName)).isEqualTo(1);
        FieldError error = errors.getFieldError(fieldName);
        assertThat(error).isNotNull();
        assertThat(messageSource.getMessage(error, Locale.ENGLISH))
          .isEqualTo(textCountryCodeIsRequired);
    }

}
