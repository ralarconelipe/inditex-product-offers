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
@Schema(description = "DTO response for flattened offer date ranges")
public class OfferDateRangeFlattenedResponseDTO {

    @Schema(description = "Start date of the flattened range in ISO-8601 format", example = "2020-06-14T00:00:00Z")
    private String startDate;

    @Schema(description = "End date of the flattened range in ISO-8601 format", example = "2020-06-14T14:59:59Z")
    private String endDate;

    @Schema(description = "Price applied to the flattened range", example = "35.50")
    private BigDecimal price;

    @Schema(description = "Currency ISO code", example = "EUR")
    private String currencyIso;

}