package com.inditex.product.offer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.product.offer.adapter.inbound.controller.OfferController;
import com.inditex.product.offer.adapter.inbound.dto.OfferCreateRequestDTO;
import com.inditex.product.offer.adapter.outbound.entity.BrandEntity;
import com.inditex.product.offer.adapter.outbound.entity.OfferEntity;
import com.inditex.product.offer.adapter.outbound.jpa.repository.OfferJpaAdapterRepository;
import com.inditex.product.offer.service.OfferCreateService;
import com.inditex.product.offer.service.OfferDeleteService;
import com.inditex.product.offer.service.OfferGetService;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link OfferController}
 * <p>
 * This class verifies the end-to-end functionality of the OfferController by testing its endpoints using MockMvc. It ensures
 * that the controller behaves as expected when handling requests for creating, retrieving, and deleting offers.
 *
 * <p>Test scenarios include:
 * <ul>
 *   <li>Creating an offer and verifying the response.</li>
 *   <li>Retrieving all offers (commented out).</li>
 *   <li>Deleting all offers and verifying the database is empty (commented out).</li>
 * </ul>
 *
 * <p>Dependencies are injected using Spring's dependency injection, and the
 * database is cleared before each test to ensure isolation.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class OfferControllerIntegrationTest {

    private static final String OFFER_URI_TEMPLATE = "/offer";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OfferJpaAdapterRepository offerJpaAdapterRepository;

    @Autowired
    private OfferCreateService offerCreateService;

    @Autowired
    private OfferDeleteService offerDeleteService;

    @Autowired
    private OfferGetService offerGetService;

    @BeforeEach
    void setUp() {
        offerJpaAdapterRepository.deleteAll();
    }

    @Test
    @DisplayName("Integration Test: Create Offer")
    void testCreateOffer() throws Exception {

        final var requestDTO = getOfferCreateRequestDTO();

        mockMvc.perform(post(OFFER_URI_TEMPLATE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDTO)))
          .andExpect(status().isCreated());
    }


    @Test
    @DisplayName("Integration Test: Get All Offers")
    void testGetAllOffers() throws Exception {

        final var offerEntity = getOfferEntity();
        offerJpaAdapterRepository.save(offerEntity);

        mockMvc.perform(get(OFFER_URI_TEMPLATE))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Integration Test: Delete All Offers")
    void testDeleteAllOffers() throws Exception {

        final var offerEntity = getOfferEntity();
        offerJpaAdapterRepository.save(offerEntity);

        mockMvc.perform(delete(OFFER_URI_TEMPLATE))
          .andExpect(status().isOk());

        mockMvc.perform(get(OFFER_URI_TEMPLATE))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$").isEmpty());
    }


    private static OfferCreateRequestDTO getOfferCreateRequestDTO() {
        return OfferCreateRequestDTO.builder()
          .offerId(1L)
          .brandId(1)
          .price(BigDecimal.valueOf(25.50))
          .currencyIso("EUR")
          .startDate("2025-05-14T00.00.00Z")
          .endDate("2025-05-14T23.59.59Z")
          .priority(1)
          .productPartnumber("0001002")
          .priceListId(1L)
          .build();
    }

    private static OfferEntity getOfferEntity() {
        var offerEntity = new OfferEntity();
        var brandEntity = new BrandEntity();
        brandEntity.setId(1);
        offerEntity.setId(1L);
        offerEntity.setBrandEntity(brandEntity);
        offerEntity.setProductPartNumber("0001002");
        offerEntity.setPrice(BigDecimal.valueOf(25.50));
        offerEntity.setCurrencyIso("EUR");
        offerEntity.setStartDate(ZonedDateTime.parse("2025-05-14T00.00.00Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH.mm.ss'Z'")
          .withZone(ZoneId.of("Z"))));
        offerEntity.setEndDate(ZonedDateTime.parse("2025-05-14T23.59.59Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH.mm.ss'Z'")
          .withZone(ZoneId.of("Z"))));
        offerEntity.setPriority(1);
        offerEntity.setPriceListId(1L);
        return offerEntity;

    }
}