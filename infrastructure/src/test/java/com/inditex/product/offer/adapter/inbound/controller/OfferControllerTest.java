package com.inditex.product.offer.adapter.inbound.controller;

import static com.inditex.product.offer.adapter.inbound.utils.OfferDateRangeFlattener.flatten;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static util.FileLoader.getObjectFromJsonFile;
import static util.FileLoader.objectMapper;

import com.inditex.product.offer.adapter.inbound.controller.advice.OfferControllerAdvice;
import com.inditex.product.offer.adapter.inbound.dto.OfferCreateRequestDTO;
import com.inditex.product.offer.adapter.inbound.dto.OfferDateRangeFlattenedResponseDTO;
import com.inditex.product.offer.adapter.inbound.dto.OfferResponseDTO;
import com.inditex.product.offer.adapter.inbound.mapper.OfferDtoMapper;
import com.inditex.product.offer.adapter.inbound.utils.OfferDateRangeFlattener;
import com.inditex.product.offer.adapter.outbound.jpa.exception.OfferException;
import com.inditex.product.offer.model.Offer;
import com.inditex.product.offer.service.OfferCreateService;
import com.inditex.product.offer.service.OfferDeleteService;
import com.inditex.product.offer.service.OfferGetService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/**
 * Unit tests for the {@link OfferController} class.
 * <p>
 * This class tests the behavior of the OfferController endpoints, including creating, retrieving, and deleting offers. It
 * uses Mockito to mock dependencies and MockMvc to simulate HTTP requests and responses.
 * </p>
 * <p>
 * The tests cover various scenarios, such as successful operations, invalid inputs, and service errors, ensuring the
 * controller behaves as expected.
 * </p>
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
class OfferControllerTest {

    private static final String OFFER_CREATE_REQUEST_PATH = "src/test/resources/OfferCreateRequestDTO.json";
    private static final String OFFER_URI_TEMPLATE = "/offer";
    private static final String FATAL_ERROR = "Fatal Error";

    private MockMvc mockMvc;

    @Mock
    private OfferCreateService offerCreateService;

    @Mock
    private OfferDeleteService offerDeleteService;

    @Mock
    private OfferGetService offerGetService;

    @Mock
    private OfferDtoMapper offerDtoMapper;

    @InjectMocks
    private OfferController offerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(offerController)
          .setControllerAdvice(new OfferControllerAdvice())
          .build();
    }

    @Test
    @DisplayName("Test Create Offer OK")
    void testCreateOfferOK() throws Exception {

        final var offerCreateRequestDTO = getObjectFromJsonFile(OFFER_CREATE_REQUEST_PATH, OfferCreateRequestDTO.class);
        final var offer = mock(Offer.class);

        when(offerDtoMapper.toOfferDomain(offerCreateRequestDTO)).thenReturn(offer);
        when(offerCreateService.createOffer(offer)).thenReturn(offer);

        mockMvc.perform(post(OFFER_URI_TEMPLATE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(offerCreateRequestDTO)))
          .andExpect(status().isCreated());

        verify(offerDtoMapper, times(1)).toOfferDomain(any(OfferCreateRequestDTO.class));
        verify(offerCreateService, only()).createOffer(any(Offer.class));
    }

    @Test
    @DisplayName("Test Create invalid Offer with result KO")
    void testCreateInvalidOfferWithResultKO() throws Exception {

        final var offerCreateRequestDTO = new OfferCreateRequestDTO();

        mockMvc.perform(post(OFFER_URI_TEMPLATE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(offerCreateRequestDTO)))
          .andExpect(status().isBadRequest());

        verify(offerDtoMapper, never()).toOfferDomain(any(OfferCreateRequestDTO.class));
        verify(offerCreateService, never()).createOffer(any(Offer.class));
    }

    @Test
    @DisplayName("Test Create Offer with service error")
    void testCreateOfferWithServiceError() throws Exception {

        final var offerCreateRequestDTO = getObjectFromJsonFile(OFFER_CREATE_REQUEST_PATH, OfferCreateRequestDTO.class);
        final var offer = mock(Offer.class);

        when(offerDtoMapper.toOfferDomain(offerCreateRequestDTO)).thenReturn(offer);
        when(offerCreateService.createOffer(offer)).thenThrow(new RuntimeException(FATAL_ERROR));

        mockMvc.perform(post(OFFER_URI_TEMPLATE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(offerCreateRequestDTO)))
          .andExpect(status().is5xxServerError());

        verify(offerDtoMapper, times(1)).toOfferDomain(any(OfferCreateRequestDTO.class));
        verify(offerCreateService, only()).createOffer(any(Offer.class));
    }

    @Test
    @DisplayName("Test Get Offer by id OK")
    void testGetOfferByIdOK() throws Exception {

        final var offerId = 1L;
        final var offer = mock(Offer.class);
        final var offerResponseDTO = mock(OfferResponseDTO.class);

        when(offerGetService.getOfferById(offerId)).thenReturn(offer);
        when(offerDtoMapper.toOfferResponseDTO(offer)).thenReturn(offerResponseDTO);

        mockMvc.perform(get(OFFER_URI_TEMPLATE + "/{offerId}", offerId))
          .andExpect(status().isOk());

        verify(offerGetService, only()).getOfferById(any(Long.class));
        verify(offerDtoMapper, only()).toOfferResponseDTO(any(Offer.class));
    }

    @Test
    @DisplayName("Test Get Offer by id with service error")
    void testGetOfferByIdWithServiceError() throws Exception {

        final var offerId = 1L;

        when(offerGetService.getOfferById(offerId)).thenThrow(
          new OfferException("Offer not found with id: " + offerId, HttpStatus.NOT_FOUND));

        mockMvc.perform(get(OFFER_URI_TEMPLATE + "/{offerId}", offerId))
          .andExpect(status().isNotFound());

        verify(offerGetService, only()).getOfferById(any(Long.class));
        verify(offerDtoMapper, never()).toOfferResponseDTO(any(Offer.class));
    }

    @Test
    @DisplayName("Test Get All Offers")
    void testGetAllOffers() throws Exception {

        final var offer = mock(Offer.class);
        final var offerResponseDTO = mock(OfferResponseDTO.class);

        when(offerGetService.getAllOffers()).thenReturn(List.of(offer, offer));
        when(offerDtoMapper.toOfferResponseDTO(offer)).thenReturn(offerResponseDTO);

        mockMvc.perform(get(OFFER_URI_TEMPLATE))
          .andExpect(status().isOk());

        verify(offerGetService, only()).getAllOffers();
        verify(offerDtoMapper, times(2)).toOfferResponseDTO(any(Offer.class));
    }

    @Test
    @DisplayName("Test Get All Offers with service error")
    void testGetAllOfferWithServiceError() throws Exception {

        when(offerGetService.getAllOffers()).thenThrow(new RuntimeException(FATAL_ERROR));

        mockMvc.perform(get(OFFER_URI_TEMPLATE))
          .andExpect(status().is5xxServerError());

        verify(offerGetService, only()).getAllOffers();
        verify(offerDtoMapper, never()).toOfferResponseDTO(any(Offer.class));
    }

    @Test
    @DisplayName("Test Get All Offers by criteria")
    void testGetAllOffersByCriteria() throws Exception {

        final var brandId = 1;
        final var partNumber = "0001002";

        final var offer = mock(Offer.class);
        final var offerDateRangeFlattenedResponseDTO = mock(OfferDateRangeFlattenedResponseDTO.class);

        final var mockOffers = List.of(offer, offer);
        final var mockResponse = List.of(offerDateRangeFlattenedResponseDTO, offerDateRangeFlattenedResponseDTO);

        when(offerGetService.getOffersByCriteria(brandId, partNumber)).thenReturn(mockOffers);

        try (MockedStatic<OfferDateRangeFlattener> mockedStatic = mockStatic(OfferDateRangeFlattener.class)) {
            mockedStatic.when(() -> flatten(mockOffers)).thenReturn(mockResponse);

            mockMvc.perform(get("/brand/{brandId}/partnumber/{partNumber}/offer", brandId, partNumber))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON));

            mockedStatic.verify(() -> flatten(mockOffers), times(1));
        }

        verify(offerGetService, only()).getOffersByCriteria(brandId, partNumber);
    }

    @Test
    @DisplayName("Test Get All Offers by criteria with service error")
    void testGetAllOffersByCriteriaWithServiceError() throws Exception {

        final var brandId = 1;
        final var partNumber = "0001002";

        when(offerGetService.getOffersByCriteria(brandId, partNumber)).thenThrow(new RuntimeException(FATAL_ERROR));

        try (MockedStatic<OfferDateRangeFlattener> mockedStatic = mockStatic(OfferDateRangeFlattener.class)) {
            mockMvc.perform(get("/brand/{brandId}/partnumber/{partNumber}/offer", brandId, partNumber))
              .andExpect(status().is5xxServerError());

            mockedStatic.verifyNoInteractions();
        }

        verify(offerGetService, only()).getOffersByCriteria(brandId, partNumber);
    }

    @Test
    @DisplayName("Test Delete All Offers with result OK")
    void testDeleteAllOffers() throws Exception {
        doNothing().when(offerDeleteService).deleteAllOffers();

        mockMvc.perform(delete(OFFER_URI_TEMPLATE))
          .andExpect(status().isOk());

        verify(offerDeleteService, only()).deleteAllOffers();
    }

    @Test
    @DisplayName("Test Delete All Offers with error service")
    void testDeleteAllOffersWithErrorService() throws Exception {

        doThrow(new RuntimeException(FATAL_ERROR)).when(offerDeleteService).deleteAllOffers();

        mockMvc.perform(delete(OFFER_URI_TEMPLATE))
          .andExpect(status().is5xxServerError());

        verify(offerDeleteService, only()).deleteAllOffers();
    }

    @Test
    @DisplayName("Test Delete Offer by id OK")
    void testDeleteOfferByIdOK() throws Exception {

        final var offerId = 1L;
        final var offer = mock(Offer.class);

        when(offerGetService.getOfferById(offerId)).thenReturn(offer);
        doNothing().when(offerDeleteService).deleteOfferById(offerId);

        mockMvc.perform(delete(OFFER_URI_TEMPLATE + "/{offerId}", offerId))
          .andExpect(status().isOk());

        verify(offerGetService, only()).getOfferById(offerId);
        verify(offerDeleteService, only()).deleteOfferById(offerId);
    }

    @Test
    @DisplayName("Test Delete Offer by id with error service")
    void testDeleteOfferByIdWithErrorService() throws Exception {

        final var offerId = 1L;
        final var offer = mock(Offer.class);

        when(offerGetService.getOfferById(offerId)).thenReturn(offer);
        doThrow(new RuntimeException(FATAL_ERROR)).when(offerDeleteService).deleteOfferById(offerId);

        mockMvc.perform(delete(OFFER_URI_TEMPLATE + "/{offerId}", offerId))
          .andExpect(status().is5xxServerError());

        verify(offerGetService, only()).getOfferById(offerId);
        verify(offerDeleteService, only()).deleteOfferById(offerId);
    }

    @Test
    @DisplayName("Test Delete Offer by non existing id")
    void testDeleteOfferByNonExistingId() throws Exception {

        final var offerId = 1L;

        when(offerGetService.getOfferById(offerId)).thenReturn(null);

        mockMvc.perform(delete(OFFER_URI_TEMPLATE + "/{offerId}", offerId))
          .andExpect(status().isNotFound());

        verify(offerGetService, only()).getOfferById(offerId);
        verify(offerDeleteService, never()).deleteOfferById(offerId);
    }

    @Test
    @DisplayName("Test Delete Offer by invalid id")
    void testDeleteOfferByInvalidId() throws Exception {

        final var offerId = -1L;

        mockMvc.perform(delete(OFFER_URI_TEMPLATE + "/{offerId}", offerId))
          .andExpect(status().isBadRequest());

        verify(offerGetService, never()).getOfferById(anyLong());
        verify(offerDeleteService, never()).deleteOfferById(anyLong());
    }


}