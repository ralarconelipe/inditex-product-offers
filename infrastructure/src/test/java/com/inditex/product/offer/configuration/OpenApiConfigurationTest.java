package com.inditex.product.offer.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

/**
 * This class test {@link OpenApiConfiguration}, beans and properties
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
class OpenApiConfigurationTest {

    private final ApplicationContextRunner contextRunner =
      new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(OpenApiConfiguration.class));


    @Test
    @DisplayName("Should start up context with OpenApiConfiguration bean and properties configured")
    void shouldStartUpContextWithOpenapiProperties() {

        OpenApiProperties openApiProperties = OpenApiProperties.builder()
          .title("Offer application REST API")
          .description("Offers management")
          .version("1.0.0")
          .contactName("Product Offer Team")
          .contactEmail("product-offers@inditex.es")
          .contactUrl("https://confluence")
          .licenseName("Inditex")
          .licenseUrl("https://inditex.local.es")
          .security("basic")
          .securityScheme("basicScheme").build();

        contextRunner
          .withPropertyValues(
            OpenApiProperties.OPEN_API_PREFIX + ".title=Offer application REST API",
            OpenApiProperties.OPEN_API_PREFIX + ".description=Offers management",
            OpenApiProperties.OPEN_API_PREFIX + ".version=1.0.0",
            OpenApiProperties.OPEN_API_PREFIX + ".contact-email=product-offers@inditex.es",
            OpenApiProperties.OPEN_API_PREFIX + ".contact-name=Product Offer Team",
            OpenApiProperties.OPEN_API_PREFIX + ".contact-url=https://confluence",
            OpenApiProperties.OPEN_API_PREFIX + ".license-name=Inditex",
            OpenApiProperties.OPEN_API_PREFIX + ".license-url=https://inditex.local.es",
            OpenApiProperties.OPEN_API_PREFIX + ".security=basic",
            OpenApiProperties.OPEN_API_PREFIX + ".security-scheme=basicScheme"
          )
          .run(context -> {
              assertThat(context).hasNotFailed();

              assertThat(context).hasSingleBean(OpenApiConfiguration.class);
              assertThat(context).hasSingleBean(OpenApiProperties.class);

              assertOpenApiProperties(openApiProperties, context);
          });
    }

    @Test
    @DisplayName("Should start up context with OpenApiConfiguration bean and properties default values")
    void shouldStartUpContextWithDefaultOpenapiProperties() {

        OpenApiProperties openApiProperties = new OpenApiProperties();

        contextRunner
          .run(context -> {
              assertThat(context).hasNotFailed();

              assertThat(context).hasSingleBean(OpenApiConfiguration.class);
              assertThat(context).hasSingleBean(OpenApiProperties.class);

              assertOpenApiProperties(openApiProperties, context);
          });
    }

    private void assertOpenApiProperties(OpenApiProperties openApiProperties,
      AssertableApplicationContext context) {
        assertThat(context).getBean(OpenApiProperties.class)
          .hasFieldOrPropertyWithValue("title", openApiProperties.getTitle());
        assertThat(context).getBean(OpenApiProperties.class)
          .hasFieldOrPropertyWithValue("version", openApiProperties.getVersion());
        assertThat(context).getBean(OpenApiProperties.class)
          .hasFieldOrPropertyWithValue("description", openApiProperties.getDescription());
        assertThat(context).getBean(OpenApiProperties.class)
          .hasFieldOrPropertyWithValue("contactName", openApiProperties.getContactName());
        assertThat(context).getBean(OpenApiProperties.class)
          .hasFieldOrPropertyWithValue("contactUrl", openApiProperties.getContactUrl());
        assertThat(context).getBean(OpenApiProperties.class)
          .hasFieldOrPropertyWithValue("contactEmail", openApiProperties.getContactEmail());
        assertThat(context).getBean(OpenApiProperties.class)
          .hasFieldOrPropertyWithValue("licenseName", openApiProperties.getLicenseName());
        assertThat(context).getBean(OpenApiProperties.class)
          .hasFieldOrPropertyWithValue("licenseUrl", openApiProperties.getLicenseUrl());
        assertThat(context).getBean(OpenApiProperties.class)
          .hasFieldOrPropertyWithValue("security", openApiProperties.getSecurity());
        assertThat(context).getBean(OpenApiProperties.class)
          .hasFieldOrPropertyWithValue("securityScheme", openApiProperties.getSecurityScheme());
    }
}
