package com.inditex.product.offer.adapter.outbound.jpa.repository;

import com.inditex.product.offer.adapter.outbound.mapper.OfferDboMapper;
import com.inditex.product.offer.model.Offer;
import com.inditex.product.offer.port.repository.OfferRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the OfferRepository interface for managing Offer entities using an H2 database. This class interacts
 * with the persistence layer through the OfferJpaAdapterRepository and maps data between the persistence and domain layers
 * using the OfferDboMapper.
 * <p>
 * This implementation provides methods to create and delete Offer data in the H2 database.
 * </p>
 *
 * <p>
 * It is annotated with {@link Repository} to indicate that it is a Spring-managed component and {@link Transactional} to
 * ensure transactional behaviour for its methods.
 * </p>
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Slf4j
@Repository
@Transactional
public class OfferH2Repository implements OfferRepository {

    private final OfferJpaAdapterRepository offerJpaAdapterRepository;

    private final OfferDboMapper offerDboMapper;

    /**
     * Constructor for OfferH2Repository.
     *
     * @param offerJpaAdapterRepository of type {@link OfferJpaAdapterRepository}
     * @param offerDboMapper            of type {@link OfferDboMapper}
     */
    public OfferH2Repository(OfferJpaAdapterRepository offerJpaAdapterRepository, OfferDboMapper offerDboMapper) {
        this.offerJpaAdapterRepository = offerJpaAdapterRepository;
        this.offerDboMapper = offerDboMapper;
    }

    @Override
    public Offer create(Offer offer) {
        LOGGER.info("Creating offer: {}", offer);
        var offerToSave = offerDboMapper.toOfferEntity(offer);
        var offerSaved = offerJpaAdapterRepository.saveAndFlush(offerToSave);
        return offerDboMapper.toOfferDomain(offerSaved);
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Deleting offer with id: {}", id);
        offerJpaAdapterRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.info("Deleting all offers");
        offerJpaAdapterRepository.deleteAll();
    }
}
