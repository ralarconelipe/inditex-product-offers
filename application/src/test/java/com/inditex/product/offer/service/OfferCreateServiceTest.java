package com.inditex.product.offer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.inditex.product.offer.model.Offer;
import com.inditex.product.offer.port.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for the OfferCreateService class.
 * <p>
 * This test class verifies the functionality of the createOffer method in the
 * OfferCreateService class.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
class OfferCreateServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private OfferCreateService offerCreateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test createOffer - Success")
    void testCreateOfferSuccess() {

        final var mockOffer = mock(Offer.class);
        when(offerRepository.create(mockOffer)).thenReturn(mockOffer);

        final var result = offerCreateService.createOffer(mockOffer);

        assertEquals(mockOffer, result);
        verify(offerRepository).create(mockOffer);
    }
}