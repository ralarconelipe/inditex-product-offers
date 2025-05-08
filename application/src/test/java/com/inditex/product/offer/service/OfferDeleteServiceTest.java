package com.inditex.product.offer.service;

import static org.mockito.Mockito.verify;

import com.inditex.product.offer.port.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for the OfferDeleteService class.
 * <p>
 * This test class verifies the functionality of the deleteOfferById and deleteAllOffers methods in the
 * OfferDeleteService class.
 * </p>
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
class OfferDeleteServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private OfferDeleteService offerDeleteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test deleteOfferById - Success")
    void testDeleteOfferByIdSuccess() {
        // Arrange
        Long offerId = 1L;

        // Act
        offerDeleteService.deleteOfferById(offerId);

        // Assert
        verify(offerRepository).deleteById(offerId);
    }

    @Test
    @DisplayName("Test deleteAllOffers - Success")
    void testDeleteAllOffersSuccess() {
        // Act
        offerDeleteService.deleteAllOffers();

        // Assert
        verify(offerRepository).deleteAll();
    }
}