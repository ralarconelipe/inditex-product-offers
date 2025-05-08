package com.inditex.product.offer.configuration;

import jakarta.validation.Validator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * This class load {@link Validator} only for test
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@TestConfiguration
public class ValidationConfiguration {

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

}
