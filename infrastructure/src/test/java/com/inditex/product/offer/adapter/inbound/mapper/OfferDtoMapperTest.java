package com.inditex.product.offer.adapter.inbound.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static util.FileLoader.getObjectFromJsonFile;

import com.inditex.product.offer.adapter.inbound.dto.OfferCreateRequestDTO;
import com.inditex.product.offer.model.Offer;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * This class test {@link OfferDtoMapper}
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
class OfferDtoMapperTest {

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH.mm.ss'Z'")
      .withZone(ZoneId.of("Z"));

    private static final String OFFER_PATH = "src/test/resources/Offer.json";

    private static final String OFFER_CREATE_REQUEST_PATH = "src/test/resources/OfferCreateRequestDTO.json";

    final OfferDtoMapper offerDtoMapper = Mappers.getMapper(
      OfferDtoMapper.class);

    @Test
    @DisplayName("This test check a conversion from domain offer to response DTO")
    void convertDomainOfferToOfferResponseDtoOk() throws IOException {

        final var offer = getObjectFromJsonFile(OFFER_PATH, Offer.class);

        assertNotNull(offer);

        var offerResponseDTO = offerDtoMapper.toOfferResponseDTO(offer);
        assertAll(
          () -> assertNotNull(offerResponseDTO),
          () -> assertEquals(offer.getOfferId(), offerResponseDTO.getOfferId()),
          () -> assertEquals(offer.getBrandId(), offerResponseDTO.getBrandId()),
          () -> assertEquals("2025-05-04T00.00.00Z", offerResponseDTO.getStartDate()),
          () -> assertEquals("2025-05-04T23.59.59Z", offerResponseDTO.getEndDate()),
          () -> assertEquals(offer.getPriceListId(), offerResponseDTO.getPriceListId()),
          () -> assertEquals(offer.getProductPartNumber(), offerResponseDTO.getProductPartnumber()),
          () -> assertEquals(offer.getPriority(), offerResponseDTO.getPriority()),
          () -> assertEquals(offer.getPrice(), offerResponseDTO.getPrice()),
          () -> assertEquals(offer.getCurrencyIso(), offerResponseDTO.getCurrencyIso())
        );
    }

    @Test
    @DisplayName("This test check a conversion from create request DTO to domain offer")
    void convertCreateRequestDTOToDomainOfferOk() throws IOException {

        final var offerCreateRequestDTO = getObjectFromJsonFile(OFFER_CREATE_REQUEST_PATH, OfferCreateRequestDTO.class);

        assertNotNull(offerCreateRequestDTO);

        var offer = offerDtoMapper.toOfferDomain(offerCreateRequestDTO);
        assertAll(
          () -> assertNotNull(offer),
          () -> assertEquals(offer.getOfferId(), offerCreateRequestDTO.getOfferId()),
          () -> assertEquals(offer.getBrandId(), offerCreateRequestDTO.getBrandId()),
          () -> assertEquals(offer.getStartDate(), ZonedDateTime.parse(offerCreateRequestDTO.getStartDate(), dateFormatter)),
          () -> assertEquals(offer.getEndDate(), ZonedDateTime.parse(offerCreateRequestDTO.getEndDate(), dateFormatter)),
          () -> assertEquals(offer.getPriceListId(), offerCreateRequestDTO.getPriceListId()),
          () -> assertEquals(offer.getProductPartNumber(), offerCreateRequestDTO.getProductPartnumber()),
          () -> assertEquals(offer.getPriority(), offerCreateRequestDTO.getPriority()),
          () -> assertEquals(offer.getPrice(), offerCreateRequestDTO.getPrice()),
          () -> assertEquals(offer.getCurrencyIso(), offerCreateRequestDTO.getCurrencyIso())
        );
    }
}
