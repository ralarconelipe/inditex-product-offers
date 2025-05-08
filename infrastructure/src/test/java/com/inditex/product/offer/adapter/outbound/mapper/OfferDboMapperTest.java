package com.inditex.product.offer.adapter.outbound.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static util.FileLoader.getObjectFromJsonFile;

import com.inditex.product.offer.adapter.outbound.entity.OfferEntity;
import com.inditex.product.offer.model.Offer;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * This class test {@link OfferDboMapper}
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
class OfferDboMapperTest {

    private static final String OFFER_DOMAIN_PATH = "src/test/resources/Offer.json";
    private static final String OFFER_ENTITY_PATH = "src/test/resources/OfferEntity.json";

    final OfferDboMapper offerDboMapper = Mappers.getMapper(
      OfferDboMapper.class);

    @Test
    @DisplayName("This test check a conversion from domain offer to entity")
    void convertDomainOfferToEntity() throws IOException {

        final var offerDomain = getObjectFromJsonFile(OFFER_DOMAIN_PATH, Offer.class);

        assertNotNull(offerDomain);

        var offerEntity = offerDboMapper.toOfferEntity(offerDomain);
        assertAll(
          () -> assertNotNull(offerEntity),
          () -> assertEquals(offerDomain.getOfferId(), offerEntity.getId()),
          () -> assertNotNull(offerEntity.getBrandEntity()),
          () -> assertEquals(offerDomain.getBrandId(), offerEntity.getBrandEntity().getId()),
          () -> assertEquals(offerDomain.getStartDate(), offerEntity.getStartDate()),
          () -> assertEquals(offerDomain.getEndDate(), offerEntity.getEndDate()),
          () -> assertEquals(offerDomain.getPriceListId(), offerEntity.getPriceListId()),
          () -> assertEquals(offerDomain.getProductPartNumber(), offerEntity.getProductPartNumber()),
          () -> assertEquals(offerDomain.getPriority(), offerEntity.getPriority()),
          () -> assertEquals(offerDomain.getPrice(), offerEntity.getPrice()),
          () -> assertEquals(offerDomain.getCurrencyIso(), offerEntity.getCurrencyIso())
        );
    }

    @Test
    @DisplayName("This test check a conversion from entity to domain offer")
    void convertOfferEntityToDomainOfferOk() throws IOException {

        final var offerEntity = getObjectFromJsonFile(OFFER_ENTITY_PATH, OfferEntity.class);

        assertNotNull(offerEntity);
        assertNotNull(offerEntity.getBrandEntity());

        var offerDomain = offerDboMapper.toOfferDomain(offerEntity);
        assertAll(
          () -> assertNotNull(offerDomain),
          () -> assertEquals(offerEntity.getId(), offerDomain.getOfferId()),
          () -> assertEquals(offerEntity.getBrandEntity().getId(), offerDomain.getBrandId()),
          () -> assertEquals(offerEntity.getStartDate(), offerDomain.getStartDate()),
          () -> assertEquals(offerEntity.getEndDate(), offerDomain.getEndDate()),
          () -> assertEquals(offerEntity.getPriceListId(), offerDomain.getPriceListId()),
          () -> assertEquals(offerEntity.getProductPartNumber(), offerDomain.getProductPartNumber()),
          () -> assertEquals(offerEntity.getPriority(), offerDomain.getPriority()),
          () -> assertEquals(offerEntity.getPrice(), offerDomain.getPrice()),
          () -> assertEquals(offerEntity.getCurrencyIso(), offerDomain.getCurrencyIso())
        );
    }
}
