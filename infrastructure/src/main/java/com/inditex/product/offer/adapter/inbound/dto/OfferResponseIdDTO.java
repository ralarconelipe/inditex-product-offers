package com.inditex.product.offer.adapter.inbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO response containing the offer identifier")
public class OfferResponseIdDTO {

    @Schema(description = "Offer identifier", example = "1")
    private Long offerId;
}