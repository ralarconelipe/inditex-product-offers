package com.inditex.product.offer.adapter.inbound.utils;

import static com.inditex.product.offer.adapter.inbound.utils.OfferDateRangeFlattener.flatten;
import static org.assertj.core.api.Assertions.assertThat;
import static util.FileLoader.getObjectFromJsonFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.inditex.product.offer.adapter.inbound.dto.OfferDateRangeFlattenedResponseDTO;
import com.inditex.product.offer.model.Offer;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OfferDateRangeFlattenerTest {

    private static final String OFFERS_PATH = "src/test/resources/Offers.json";

    private static final String OFFERS_DATE_RANGE_FLATTENED_RESPONSE_PATH = "src/test/resources/OffersDateRangeFlattenedResponseDTO.json";

    @Test
    @DisplayName("Test flatten - Resolves overlapping date ranges")
    void testFlattenOverlappingDateRanges() throws IOException {

        final List<Offer> offers = getObjectFromJsonFile(OFFERS_PATH, new TypeReference<>() {
        });

        final List<OfferDateRangeFlattenedResponseDTO> offerDateRangeFlattenedResponseDTOS = getObjectFromJsonFile(
          OFFERS_DATE_RANGE_FLATTENED_RESPONSE_PATH, new TypeReference<>() {
          });

        assertThat(offers).isNotNull().isNotEmpty();
        assertThat(offerDateRangeFlattenedResponseDTOS).isNotNull().isNotEmpty();

        List<OfferDateRangeFlattenedResponseDTO> result = flatten(offers);

        assertThat(result).hasSameSizeAs(offerDateRangeFlattenedResponseDTOS);
        assertThat(result).usingRecursiveComparison().isEqualTo(offerDateRangeFlattenedResponseDTOS);

    }
}