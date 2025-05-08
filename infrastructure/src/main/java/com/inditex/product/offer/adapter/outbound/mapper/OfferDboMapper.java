package com.inditex.product.offer.adapter.outbound.mapper;

import com.inditex.product.offer.adapter.outbound.entity.BrandEntity;
import com.inditex.product.offer.adapter.outbound.entity.OfferEntity;
import com.inditex.product.offer.model.Offer;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper interface for converting between OfferEntity and Offer domain objects. Utilizes MapStruct to generate the
 * implementation at compile time.
 * <p>
 * This interface is used to map data between the persistence layer (OfferEntity) and the domain layer (Offer).
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface OfferDboMapper {

    /**
     * Converts an OfferEntity object to an Offer domain object.
     *
     * @param entity the OfferEntity to be converted
     * @return the corresponding Offer domain object
     */
    @Mapping(target = "offerId", source = "id")
    @Mapping(target = "brandId", source = "brandEntity.id")
    Offer toOfferDomain(OfferEntity entity);


    /**
     * Converts an Offer domain object to an OfferEntity.
     *
     * @param offer the Offer domain to be converted
     * @return the corresponding OfferEntity
     */
    @Mapping(target = "id", source = "offerId")
    @Mapping(target = "brandEntity", ignore = true)
    OfferEntity toOfferEntity(Offer offer);

    @AfterMapping
    default void toEntityAfterMapping(Offer offer,
      @MappingTarget OfferEntity offerEntity) {
        var brandEntity = new BrandEntity();
        brandEntity.setId(offer.getBrandId());
        offerEntity.setBrandEntity(brandEntity);
    }

}
