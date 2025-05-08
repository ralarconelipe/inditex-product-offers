package com.inditex.product.offer.adapter.inbound.mapper;

import static com.inditex.product.offer.adapter.inbound.utils.ZonedDateTimeUtils.dateFormatter;

import com.inditex.product.offer.adapter.inbound.dto.OfferCreateRequestDTO;
import com.inditex.product.offer.adapter.inbound.dto.OfferDateRangeFlattenedResponseDTO;
import com.inditex.product.offer.adapter.inbound.dto.OfferResponseDTO;
import com.inditex.product.offer.adapter.inbound.dto.OfferResponseIdDTO;
import com.inditex.product.offer.model.Offer;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper interface for converting between Offer domain objects and their corresponding DTOs. Utilizes MapStruct to generate
 * the implementation at compile time.
 *
 * <p>This interface provides methods to map data between the domain layer and the inbound layer
 * (DTOs) for various Offer-related operations.
 *
 * <p>Mapping methods include:
 * <ul>
 *   <li>Converting from {@link Offer} to various response DTOs.</li>
 *   <li>Converting from {@link OfferCreateRequestDTO} to {@link Offer}.</li>
 * </ul>
 *
 * <p>After-mapping methods are used to handle additional transformations, such as parsing dates
 * or formatting fields.
 *
 * <p>Supported mappings:
 * <ul>
 *   <li>{@link Offer} to {@link OfferResponseDTO}</li>
 *   <li>{@link Offer} to {@link OfferResponseIdDTO}</li>
 *   <li>{@link Offer} to {@link OfferDateRangeFlattenedResponseDTO}</li>
 *   <li>{@link OfferCreateRequestDTO} to {@link Offer}</li>
 * </ul>
 *
 * <p>Note: This mapper is a Spring component.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface OfferDtoMapper {

    /**
     * Convert from {@link Offer} to {@link OfferResponseDTO}
     *
     * @param offer {@link Offer}
     * @return {@link OfferResponseDTO}
     */
    @Mapping(source = "productPartNumber", target = "productPartnumber")
    OfferResponseDTO toOfferResponseDTO(Offer offer);


    /**
     * Convert from {@link OfferCreateRequestDTO} to {@link Offer}
     *
     * @param offerCreateRequestDTO {@link OfferCreateRequestDTO}
     * @return {@link Offer}
     */
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    @Mapping(source = "productPartnumber", target = "productPartNumber")
    Offer toOfferDomain(OfferCreateRequestDTO offerCreateRequestDTO);

    @AfterMapping
    default void toDomainAfterMapping(OfferCreateRequestDTO offerCreateRequestDTO,
      @MappingTarget Offer offer) {

        Optional.ofNullable(offerCreateRequestDTO.getStartDate())
          .ifPresent(startDate -> offer.setStartDate(ZonedDateTime.parse(startDate, dateFormatter)));

        Optional.ofNullable(offerCreateRequestDTO.getEndDate())
          .ifPresent(endDate -> offer.setEndDate(ZonedDateTime.parse(endDate, dateFormatter)));
    }

    @AfterMapping
    default void toResponseDTOAfterMapping(Offer offer,
      @MappingTarget OfferResponseDTO offerResponseDTO) {

        Optional.ofNullable(offer.getStartDate())
          .ifPresent(startDate ->
            offerResponseDTO.setStartDate(startDate.format(dateFormatter)));

        Optional.ofNullable(offer.getEndDate())
          .ifPresent(endDate ->
            offerResponseDTO.setEndDate(endDate.format(dateFormatter)));
    }

}
