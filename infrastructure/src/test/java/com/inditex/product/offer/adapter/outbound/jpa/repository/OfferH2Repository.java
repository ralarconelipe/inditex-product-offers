package com.inditex.product.offer.adapter.outbound.jpa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.inditex.product.offer.adapter.outbound.entity.OfferEntity;
import com.inditex.product.offer.adapter.outbound.mapper.OfferDboMapper;
import com.inditex.product.offer.model.Offer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * This class test {@link OfferH2Repository}
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
class OfferH2RepositoryTest {

    @Mock
    private OfferJpaAdapterRepository offerJpaAdapterRepository;

    @Mock
    private OfferDboMapper offerDboMapper;

    @InjectMocks
    private OfferH2Repository offerH2Repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test create - Success")
    void testCreateSuccess() {
        final var mockOffer = mock(Offer.class);
        final var mockEntity = mock(OfferEntity.class);
        when(offerDboMapper.toOfferEntity(mockOffer)).thenReturn(mockEntity);
        when(offerJpaAdapterRepository.saveAndFlush(mockEntity)).thenReturn(mockEntity);
        when(offerDboMapper.toOfferDomain(mockEntity)).thenReturn(mockOffer);

        final var result = offerH2Repository.create(mockOffer);

        assertEquals(mockOffer, result);
        verify(offerDboMapper).toOfferEntity(mockOffer);
        verify(offerJpaAdapterRepository).saveAndFlush(mockEntity);
        verify(offerDboMapper).toOfferDomain(mockEntity);
    }

    @Test
    @DisplayName("Test deleteById - Success")
    void testDeleteByIdSuccess() {
        final Long offerId = 1L;
        offerH2Repository.deleteById(offerId);
        verify(offerJpaAdapterRepository).deleteById(offerId);
    }

    @Test
    @DisplayName("Test deleteAll - Success")
    void testDeleteAllSuccess() {
        offerH2Repository.deleteAll();
        verify(offerJpaAdapterRepository).deleteAll();
    }
}