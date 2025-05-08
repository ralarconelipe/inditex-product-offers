package com.inditex.product.offer.adapter.outbound.jpa.repository;


import com.inditex.product.offer.adapter.outbound.entity.OfferEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing OfferEntity objects in the database. Extends JpaRepository to provide CRUD operations
 * and query methods for OfferEntity.
 * <p>
 * This interface serves as the data access layer for OfferEntity and interacts with the database.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Repository
public interface OfferJpaAdapterRepository extends JpaRepository<OfferEntity, Long> {

    @Query("SELECT o FROM OfferEntity o WHERE o.brandEntity.id = :brandId AND o.productPartNumber = :productPartNumber")
    List<OfferEntity> findByBrandIdAndProductPartNumber(@Param("brandId") Integer brandId,
      @Param("productPartNumber") String productPartNumber);

}