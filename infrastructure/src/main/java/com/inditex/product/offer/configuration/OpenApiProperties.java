package com.inditex.product.offer.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * OpenApiProperties class.
 * It is used to load properties values defined in `application.yml` under the prefix `offer.openapi`
 * Use <resource.delimiter>@</resource.delimiter> in pom.xml to set the version: @project.version@
 *
 * @since 1.0.0
 * @author [product-offers@inditex.es]
 */
@ConfigurationProperties(prefix = OpenApiProperties.OPEN_API_PREFIX)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenApiProperties {

    public static final String OPEN_API_PREFIX = "offer.openapi";

    private String title;

    private String description;

    private String version;

    private String contactName;

    private String contactUrl;

    private String contactEmail;

    private String licenseName;

    private String licenseUrl;

    private String security;

    private String securityScheme;
}
