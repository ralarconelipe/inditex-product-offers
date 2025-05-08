package com.inditex.product.offer.adapter.inbound.controller;

import static com.inditex.product.offer.adapter.inbound.utils.OfferDateRangeFlattener.flatten;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

import com.inditex.product.offer.adapter.inbound.dto.OfferCreateRequestDTO;
import com.inditex.product.offer.adapter.inbound.dto.OfferDateRangeFlattenedResponseDTO;
import com.inditex.product.offer.adapter.inbound.dto.OfferResponseDTO;
import com.inditex.product.offer.adapter.inbound.dto.OfferResponseIdDTO;
import com.inditex.product.offer.adapter.inbound.exception.ValidationOfferException;
import com.inditex.product.offer.adapter.inbound.mapper.OfferDtoMapper;
import com.inditex.product.offer.adapter.inbound.validator.OfferCreateRequestValidator;
import com.inditex.product.offer.service.OfferCreateService;
import com.inditex.product.offer.service.OfferDeleteService;
import com.inditex.product.offer.service.OfferGetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing offers.
 * <p>
 * This controller provides endpoints for creating, retrieving, and deleting offers. It handles HTTP requests and delegates
 * the business logic to the appropriate services.
 * </p>
 * <p>
 * The controller includes validation for input data and uses mappers to convert between domain objects and DTOs. It also
 * logs operations for better traceability.
 * </p>
 * <p>
 * Endpoints:
 * <ul>
 *   <li>Create an offer</li>
 *   <li>Delete all offers</li>
 *   <li>Delete an offer by ID</li>
 *   <li>Retrieve all offers</li>
 *   <li>Retrieve an offer by ID</li>
 *   <li>Retrieve offers by brand ID and part number</li>
 * </ul>
 * </p>
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Slf4j
@RestController
@Tag(name = "Offers")
public class OfferController extends ValidatorRequest {

    private final OfferCreateRequestValidator offerCreateRequestValidator;

    private final OfferCreateService offerCreateService;

    private final OfferDeleteService offerDeleteService;

    private final OfferGetService offerGetService;

    private final OfferDtoMapper offerDtoMapper;

    /**
     * Constructor
     *
     * @param offerCreateService {@link OfferCreateService}
     * @param offerDeleteService {@link OfferDeleteService}
     * @param offerGetService    {@link OfferGetService}
     * @param offerDtoMapper     {@link OfferDtoMapper}
     */
    public OfferController(OfferCreateService offerCreateService,
      OfferDeleteService offerDeleteService, OfferGetService offerGetService, OfferDtoMapper offerDtoMapper) {
        this.offerCreateService = offerCreateService;
        this.offerDeleteService = offerDeleteService;
        this.offerGetService = offerGetService;
        this.offerDtoMapper = offerDtoMapper;
        this.offerCreateRequestValidator = new OfferCreateRequestValidator();
    }

    @PostMapping(value = "/offer", consumes = "application/json")
    @Operation(
      description = "Create an offer",
      responses = {
        @ApiResponse(
          content = @Content(
            schema = @Schema(implementation = OfferResponseIdDTO.class)),
          description = "Create successful",
          responseCode = "201"),
        @ApiResponse(
          content = @Content(
            schema = @Schema(implementation = ValidationOfferException.class)),
          description = "Bad Request",
          responseCode = "400")
      }
    )
    public ResponseEntity<Void> createOffer(
      @Parameter(description = "Offer to save", required = true, schema = @Schema(implementation = OfferCreateRequestDTO.class))
      @RequestBody @Valid OfferCreateRequestDTO offerCreateRequestDTO) {

        LOGGER.info("[createOffer] Create offer: [{}]", offerCreateRequestDTO);

        validateRequest(offerCreateRequestValidator,
          offerCreateRequestDTO,
          "offerCreateRequestDTO",
          "[offerCreateRequestDTO] Errors validating object: {}");

        offerCreateService.createOffer(offerDtoMapper.toOfferDomain(offerCreateRequestDTO));
        return new ResponseEntity<>(CREATED);
    }

    @Operation(
      description = "Delete all offers",
      responses = {
        @ApiResponse(
          content = @Content(schema = @Schema(implementation = HttpStatus.class)),
          description = "Delete successful",
          responseCode = "200")
      })
    @DeleteMapping(value = "/offer")
    public ResponseEntity<Void> deleteAllOffers() {
        offerDeleteService.deleteAllOffers();
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping(value = "/offer/{offerId}")
    @Operation(
      description = "Delete an offer by its unique identifier id",
      responses = {
        @ApiResponse(
          content = @Content(schema = @Schema(implementation = HttpStatus.class)),
          description = "Delete successful",
          responseCode = "200"),
        @ApiResponse(
          content = @Content(schema = @Schema(implementation = HttpStatus.class)),
          description = "Not found",
          responseCode = "404"),
        @ApiResponse(
          content = @Content(schema = @Schema(implementation = HttpStatus.class)),
          description = "Bad request",
          responseCode = "400")
      })
    public ResponseEntity<Void> deleteOfferById(
      @Parameter(
        description = "Offer unique identifier",
        required = true,
        example = "1")
      @PathVariable("offerId") Long offerId) {

        LOGGER.info("[deleteOfferById] Delete Offer with id: [{}]", offerId);

        if (offerId <= 0) {
            LOGGER.error("[deleteOfferById] Invalid offerId: {}", offerId);
            throw new ValidationOfferException("The offerId must be a positive number.");
        }

        return Optional.ofNullable(offerGetService.getOfferById(offerId))
          .map(offer -> {
              offerDeleteService.deleteOfferById(offerId);
              return new ResponseEntity<Void>(OK);

          })
          .orElse(new ResponseEntity<>(NOT_FOUND));
    }

    @GetMapping(value = "/offer", produces = "application/json")
    @Operation(
      description = "Get all offers",
      responses = {
        @ApiResponse(
          content =
          @Content(
            array =
            @ArraySchema(schema = @Schema(implementation = OfferResponseDTO.class))),
          description = "Successful search",
          responseCode = "200")
      })
    public List<OfferResponseDTO> getAllOffers() {

        LOGGER.info("[getAllOffers]: get all offers");

        return offerGetService.getAllOffers()
          .stream()
          .map(offerDtoMapper::toOfferResponseDTO)
          .toList();

    }

    @GetMapping(value = "/offer/{offerId}", produces = "application/json")
    @Operation(
      description = "Fetch an offer by id",
      responses = {
        @ApiResponse(
          content = @Content(schema = @Schema(implementation = OfferResponseDTO.class)),
          description = "Successful retrieval",
          responseCode = "200")
      })
    public ResponseEntity<OfferResponseDTO> getOfferById(
      @Parameter(
        description = "Offer unique identifier",
        required = true,
        example = "1")
      @PathVariable("offerId") final Long offerId) {

        LOGGER.info("[getOfferById] Get offer with id: [{}]", offerId);

        var offer = offerGetService.getOfferById(offerId);
        var offerResponseDTO = offerDtoMapper.toOfferResponseDTO(offer);
        return status(OK).body(offerResponseDTO);
    }

    @GetMapping(value = "/brand/{brandId}/partnumber/{partNumber}/offer", produces = "application/json")
    @Operation(
      description = "Get flattened offers by brand id and part number",
      responses = {
        @ApiResponse(
          content =
          @Content(
            array =
            @ArraySchema(schema = @Schema(implementation = OfferDateRangeFlattenedResponseDTO.class))),
          description = "Successful search",
          responseCode = "200")
      })
    public ResponseEntity<List<OfferDateRangeFlattenedResponseDTO>> getOfferByPartNumber(
      @Parameter(
        description = "Brand identifier",
        required = true,
        example = "1")
      @PathVariable("brandId") final Integer brandId,
      @Parameter(
        description = "Product code identifier",
        required = true,
        example = "0001002")
      @PathVariable("partNumber") final String partNumber) {

        LOGGER.info("[getOfferByPartNumber] Get offers by brand id [{}] and part number: [{}]", brandId, partNumber);
        List<OfferDateRangeFlattenedResponseDTO> offerDateRangeFlattenedResponseDTOS = flatten(
          offerGetService.getOffersByCriteria(brandId, partNumber));
        return status(OK).body(offerDateRangeFlattenedResponseDTOS);
    }
}
