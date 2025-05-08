package com.inditex.product.offer.port.dao;


import com.inditex.product.offer.model.Offer;
import java.util.List;

/**
 * Data Access Object (DAO) interface for managing Offer entities. Provides methods to retrieve Offer data from the
 * persistence layer.
 * <p>
 * This interface abstracts the data access operations for Offer objects, allowing for different implementations depending on
 * the underlying data source.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
public interface OfferDao {

    /**
     * Retrieves an Offer by its unique identifier.
     *
     * @param id the unique identifier of the Offer
     * @return the Offer with the specified ID
     */
    Offer getById(Long id);

    /**
     * Retrieves all Offer entities.
     *
     * @return a list of all Offer entities
     */
    List<Offer> getAll();


    List<Offer> getByCriteria(Integer brandId, String productPartNumber);

}
