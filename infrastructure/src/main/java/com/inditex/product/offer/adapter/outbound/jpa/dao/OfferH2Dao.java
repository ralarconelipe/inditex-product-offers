package com.inditex.product.offer.adapter.outbound.jpa.dao;

import com.inditex.product.offer.adapter.outbound.jpa.exception.OfferException;
import com.inditex.product.offer.adapter.outbound.jpa.repository.OfferJpaAdapterRepository;
import com.inditex.product.offer.adapter.outbound.mapper.OfferDboMapper;
import com.inditex.product.offer.model.Offer;
import com.inditex.product.offer.port.dao.OfferDao;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the OfferDao interface for managing Offer entities using an H2 database. This class interacts with the
 * persistence layer through the OfferJpaAdapterRepository and maps data between the persistence and domain layers using the
 * OfferDboMapper.
 * <p>
 * This implementation provides methods to retrieve Offer data from the H2 database.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Slf4j
@Repository
public class OfferH2Dao implements OfferDao {

    private final OfferJpaAdapterRepository offerJpaAdapterRepository;

    private final OfferDboMapper offerDboMapper;

    /**
     * Constructor for OfferH2Dao.
     *
     * @param offerJpaAdapterRepository of type {@link OfferJpaAdapterRepository}
     * @param offerDboMapper            of type {@link OfferDboMapper}
     */
    public OfferH2Dao(OfferJpaAdapterRepository offerJpaAdapterRepository, OfferDboMapper offerDboMapper) {
        this.offerJpaAdapterRepository = offerJpaAdapterRepository;
        this.offerDboMapper = offerDboMapper;
    }

    @Override
    public Offer getById(Long id) {
        LOGGER.info("Retrieving offer with id: {}", id);
        return offerJpaAdapterRepository.findById(id)
          .map(offerDboMapper::toOfferDomain)
          .orElseThrow(() -> new OfferException("Offer not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Offer> getAll() {
        LOGGER.info("Retrieving all offers");
        return offerJpaAdapterRepository.findAll()
          .stream()
          .map(offerDboMapper::toOfferDomain)
          .toList();
    }

    @Override
    public List<Offer> getByCriteria(Integer brandId, String productPartNumber) {
        LOGGER.info("Retrieving offers by criteria: brandId={}, productPartNumber={}", brandId, productPartNumber);
        return offerJpaAdapterRepository.findByBrandIdAndProductPartNumber(brandId, productPartNumber)
          .stream()
          .map(offerDboMapper::toOfferDomain)
          .toList();
    }
}
