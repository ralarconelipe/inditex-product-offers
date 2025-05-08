package com.inditex.product.offer.port.repository;

import com.inditex.product.offer.model.Offer;


/**
 * Repository interface for managing Offer entities in the domain layer. Provides methods to create and delete Offer data.
 * <p>
 * This interface abstracts the data access layer for Offer objects, allowing for different implementations depending on the
 * underlying data source.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
public interface OfferRepository {

    /**
     * Creates a new Offer in the system.
     *
     * @param offer the Offer to be created
     * @return the created Offer
     */
    Offer create(Offer offer);

    /**
     * Deletes an Offer by its unique identifier.
     *
     * @param id the unique identifier of the Offer to be deleted
     */
    void deleteById(Long id);

    void deleteAll();
}