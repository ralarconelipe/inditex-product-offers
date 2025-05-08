package com.inditex.product.offer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.inditex.product.offer.model.Offer;
import com.inditex.product.offer.port.dao.OfferDao;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for the OfferGetService class.
 * <p>
 * This test class verifies the functionality of the getOfferById, getAllOffers, and getOffersByCriteria methods in the
 * OfferGetService class.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
class OfferGetServiceTest {

    @Mock
    private OfferDao offerDao;

    @InjectMocks
    private OfferGetService offerGetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getOfferById - Success")
    void testGetOfferByIdSuccess() {
        // Arrange
        Long offerId = 1L;
        Offer mockOffer = new Offer();
        when(offerDao.getById(offerId)).thenReturn(mockOffer);

        // Act
        Offer result = offerGetService.getOfferById(offerId);

        // Assert
        assertEquals(mockOffer, result);
        verify(offerDao).getById(offerId);
    }

    @Test
    @DisplayName("Test getAllOffers - Success")
    void testGetAllOffersSuccess() {
        // Arrange
        List<Offer> mockOffers = List.of(new Offer());
        when(offerDao.getAll()).thenReturn(mockOffers);

        // Act
        List<Offer> result = offerGetService.getAllOffers();

        // Assert
        assertEquals(mockOffers, result);
        verify(offerDao).getAll();
    }

    @Test
    @DisplayName("Test getOffersByCriteria - Success")
    void testGetOffersByCriteriaSuccess() {
        // Arrange
        Integer brandId = 1;
        String productPartNumber = "0001005";
        List<Offer> mockOffers = List.of(new Offer());
        when(offerDao.getByCriteria(brandId, productPartNumber)).thenReturn(mockOffers);

        // Act
        List<Offer> result = offerGetService.getOffersByCriteria(brandId, productPartNumber);

        // Assert
        assertEquals(mockOffers, result);
        verify(offerDao).getByCriteria(brandId, productPartNumber);
    }
}