package com.inditex.product.offer.adapter.inbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO response offer")
public class OfferResponseDTO {

    @Schema(description = "Offer identifier", example = "1")
    private Long offerId;

    @Schema(description = "Brand identifier", example = "1")
    private Integer brandId;

    @Schema(description = "Start date of the offer in ISO-8601 format", example = "2020-06-14T00:00:00Z")
    private String startDate;

    @Schema(description = "End date of the offer in ISO-8601 format", example = "2020-12-31T23:59:59Z")
    private String endDate;

    @Schema(description = "Price list identifier", example = "1")
    private Long priceListId;

    @Schema(description = "Product part number", example = "0001002")
    private String productPartnumber;

    @Schema(description = "Priority of the offer", example = "0")
    private Integer priority;

    @Schema(description = "Price of the offer", example = "35.50")
    private BigDecimal price;

    @Schema(description = "Currency ISO code", example = "EUR")
    private String currencyIso;
}
