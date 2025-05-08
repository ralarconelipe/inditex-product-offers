package com.inditex.product.offer.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * Configuration class for setting up OpenAPI documentation.
 * This class uses {@link OpenApiProperties} to configure the OpenAPI metadata.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(OpenApiProperties.class)
@Slf4j
public class OpenApiConfiguration {

    /**
     * Creates and configures an {@link OpenAPI} bean using the provided {@link OpenApiProperties}.
     *
     * @param openApiProperties the properties containing OpenAPI metadata such as title, description, version, etc.
     * @return a configured {@link OpenAPI} instance
     * @throws IllegalArgumentException if {@code openApiProperties} is null
     */
    @Bean
    public OpenAPI getOpenApi(OpenApiProperties openApiProperties) {
        LOGGER.info("[getOpenAPI] OpenAPI configured with values {}:", openApiProperties);
        Assert.notNull(openApiProperties, "OpenApiProperties must not be null");
        return new OpenAPI()
          .info(new Info()
            .title(openApiProperties.getTitle())
            .description(openApiProperties.getDescription())
            .version(openApiProperties.getVersion())
            .contact(new Contact()
              .email(openApiProperties.getContactEmail())
              .name(openApiProperties.getContactName())
              .url(openApiProperties.getContactUrl()))
            .license(new License()
              .name(openApiProperties.getLicenseName())
              .url(openApiProperties.getLicenseUrl()))
          );
    }
}