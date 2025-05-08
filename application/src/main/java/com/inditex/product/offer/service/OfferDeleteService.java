package com.inditex.product.offer.service;

import com.inditex.product.offer.port.repository.OfferRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class responsible for handling the deletion of offers.
 * <p>
 * This class provides methods to delete all offers or a specific offer by its ID. It interacts with the
 * {@link OfferRepository} to perform the deletion operations.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Slf4j
public class OfferDeleteService {

    private final OfferRepository offerRepository;

    /**
     * Constructs an instance of OfferDeleteService with the specified OfferRepository.
     *
     * @param offerRepository the repository used for offer persistence
     */
    public OfferDeleteService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    /**
     * Deletes all offers from the repository.
     */
    public void deleteAllOffers() {
        LOGGER.info("Deleting all offers");
        offerRepository.deleteAll();
    }

    /**
     * Deletes a specific offer by its id unique identifier.
     *
     * @param id the unique identifier of the offer to be deleted
     */
    public void deleteOfferById(Long id) {
        LOGGER.info("Deleting offer with id: [{}]", id);
        offerRepository.deleteById(id);
    }

}
