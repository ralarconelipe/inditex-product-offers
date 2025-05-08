package com.inditex.product.offer.service;

import com.inditex.product.offer.model.Offer;
import com.inditex.product.offer.port.dao.OfferDao;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class responsible for handling the retrieval of offers.
 * <p>
 * This class provides methods to retrieve offers by ID, all offers, or offers based on specific criteria. It interacts with
 * the {@link OfferDao} to perform the retrieval operations.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Slf4j
public class OfferGetService {

    private final OfferDao offerDao;

    /**
     * Constructs an instance of OfferGetService with the specified OfferDao.
     *
     * @param offerDao the DAO used for offer retrieval
     */
    public OfferGetService(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    /**
     * Retrieves an offer by its ID.
     *
     * @param id the ID of the offer to be retrieved
     * @return the retrieved offer
     */
    public Offer getOfferById(Long id) {
        LOGGER.info("Retrieving offer with id: [{}]", id);
        return offerDao.getById(id);
    }

    /**
     * Retrieves all offers.
     *
     * @return a list of all offers
     */
    public List<Offer> getAllOffers() {
        LOGGER.info("Retrieving all offers");
        return offerDao.getAll();
    }

    /**
     * Retrieves offers based on specific criteria.
     *
     * @return a list of offers matching the criteria
     */
    public List<Offer> getOffersByCriteria(Integer brandId, String productPartNumber) {
        LOGGER.info("Retrieving offers by criteria: brandId=[{}], productPartNumber=[{}]", brandId, productPartNumber);
        return offerDao.getByCriteria(brandId, productPartNumber);
    }
}
