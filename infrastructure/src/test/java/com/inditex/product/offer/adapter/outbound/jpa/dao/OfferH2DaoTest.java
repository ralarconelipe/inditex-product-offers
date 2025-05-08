package com.inditex.product.offer.adapter.outbound.jpa.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.inditex.product.offer.adapter.outbound.entity.OfferEntity;
import com.inditex.product.offer.adapter.outbound.jpa.exception.OfferException;
import com.inditex.product.offer.adapter.outbound.jpa.repository.OfferJpaAdapterRepository;
import com.inditex.product.offer.adapter.outbound.mapper.OfferDboMapper;
import com.inditex.product.offer.model.Offer;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

/**
 * This class test {@link OfferH2Dao}
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
class OfferH2DaoTest {

    @Mock
    private OfferJpaAdapterRepository offerJpaAdapterRepository;

    @Mock
    private OfferDboMapper offerDboMapper;

    @InjectMocks
    private OfferH2Dao offerH2Dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getById - Success")
    void testGetByIdSuccess() {
        final Long offerId = 1L;
        var mockEntity = new OfferEntity();
        var mockOffer = new Offer();
        when(offerJpaAdapterRepository.findById(offerId)).thenReturn(Optional.of(mockEntity));
        when(offerDboMapper.toOfferDomain(mockEntity)).thenReturn(mockOffer);

        final var result = offerH2Dao.getById(offerId);

        assertEquals(mockOffer, result);
        verify(offerJpaAdapterRepository).findById(offerId);
        verify(offerDboMapper).toOfferDomain(mockEntity);
    }

    @Test
    @DisplayName("Test getById - Not Found")
    void testGetByIdNotFound() {
        final Long offerId = 1L;
        when(offerJpaAdapterRepository.findById(offerId)).thenReturn(Optional.empty());

        var exception = assertThrows(OfferException.class, () -> offerH2Dao.getById(offerId));
        assertEquals("Offer not found with id: " + offerId, exception.getErrorMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(offerJpaAdapterRepository).findById(offerId);
        verifyNoInteractions(offerDboMapper);
    }

    @Test
    @DisplayName("Test getAll - Success")
    void testGetAllSuccess() {
        final List<OfferEntity> mockEntities = List.of(new OfferEntity());
        final List<Offer> mockOffers = Collections.singletonList(new Offer());
        when(offerJpaAdapterRepository.findAll()).thenReturn(mockEntities);
        when(offerDboMapper.toOfferDomain(any(OfferEntity.class))).thenReturn(mockOffers.getFirst());

        final List<Offer> result = offerH2Dao.getAll();

        assertEquals(mockOffers, result);
        verify(offerJpaAdapterRepository).findAll();
        verify(offerDboMapper, times(mockEntities.size())).toOfferDomain(any(OfferEntity.class));
    }

    @Test
    @DisplayName("Test getByCriteria - Success")
    void testGetByCriteriaSuccess() {
        final Integer brandId = 1;
        final String productPartNumber = "0001005";
        List<OfferEntity> mockEntities = List.of(new OfferEntity());
        List<Offer> mockOffers = List.of(new Offer());
        when(offerJpaAdapterRepository.findByBrandIdAndProductPartNumber(brandId, productPartNumber))
          .thenReturn(mockEntities);
        when(offerDboMapper.toOfferDomain(any(OfferEntity.class))).thenReturn(mockOffers.getFirst());

        final List<Offer> result = offerH2Dao.getByCriteria(brandId, productPartNumber);

        assertEquals(mockOffers, result);
        verify(offerJpaAdapterRepository).findByBrandIdAndProductPartNumber(brandId, productPartNumber);
        verify(offerDboMapper, times(mockEntities.size())).toOfferDomain(any(OfferEntity.class));
    }
}
