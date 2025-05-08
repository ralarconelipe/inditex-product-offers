package com.inditex.product.offer.service;

import com.inditex.product.offer.model.Offer;
import com.inditex.product.offer.port.repository.OfferRepository;
import lombok.extern.slf4j.Slf4j;


/**
 * Service class responsible for handling the creation of offers.
 * <p>
 * This class provides methods to create and persist offers. It interacts with the {@link OfferRepository} to perform the
 * persistence operations.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Slf4j
public class OfferCreateService {

    private final OfferRepository offerRepository;

    /**
     * Constructs an instance of OfferCreateService with the specified OfferRepository.
     *
     * @param offerRepository the repository used for offer persistence
     */
    public OfferCreateService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    /**
     * Creates a new offer and persists it using the repository.
     *
     * @param offer the offer to be created
     * @return the created offer
     */
    public Offer createOffer(Offer offer) {
        LOGGER.info("Creating offer: {}", offer);
        return offerRepository.create(offer);
    }

}
