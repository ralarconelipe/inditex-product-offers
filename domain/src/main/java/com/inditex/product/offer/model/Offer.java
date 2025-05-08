package com.inditex.product.offer.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing an Offer in the system. This class is used to encapsulate offer-related data in the domain layer.
 * It includes details such as the associated brand, validity period, pricing, and priority.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offer {

    private Long offerId;

    private Integer brandId;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private Long priceListId;

    private String productPartNumber;

    private Integer priority;

    private BigDecimal price;

    private String currencyIso;
}

