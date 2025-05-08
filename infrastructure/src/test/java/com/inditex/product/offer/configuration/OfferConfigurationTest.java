package com.inditex.product.offer.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import com.inditex.product.offer.service.OfferCreateService;
import com.inditex.product.offer.service.OfferDeleteService;
import com.inditex.product.offer.service.OfferGetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Import;

/**
 * This class test {@link OfferConfiguration}, beans and properties
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Import(ValidationConfiguration.class)
class OfferConfigurationTest {

    private final ApplicationContextRunner contextRunner =
      new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(OfferConfiguration.class));

    @Test
    @DisplayName(
      "Should start up context with custom OfferConfiguration bean and properties configured")
    void shouldCustomBeansOfferConfiguration() {

        contextRunner
          .run(
            context -> {
                assertThat(context).hasNotFailed();
                assertThat(context).hasSingleBean(OfferConfiguration.class);
                assertThat(context).hasSingleBean(OfferGetService.class);
                assertThat(context).hasSingleBean(OfferCreateService.class);
                assertThat(context).hasSingleBean(OfferDeleteService.class);
            });
    }

}




