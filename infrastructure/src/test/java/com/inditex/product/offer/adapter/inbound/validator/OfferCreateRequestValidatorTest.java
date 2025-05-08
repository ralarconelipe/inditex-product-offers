package com.inditex.product.offer.adapter.inbound.validator;

import static com.inditex.product.offer.adapter.inbound.validator.OfferCreateRequestValidator.BRAND_ID;
import static com.inditex.product.offer.adapter.inbound.validator.OfferCreateRequestValidator.CURRENCY_ISO;
import static com.inditex.product.offer.adapter.inbound.validator.OfferCreateRequestValidator.END_DATE;
import static com.inditex.product.offer.adapter.inbound.validator.OfferCreateRequestValidator.OFFER_ID;
import static com.inditex.product.offer.adapter.inbound.validator.OfferCreateRequestValidator.PRICE;
import static com.inditex.product.offer.adapter.inbound.validator.OfferCreateRequestValidator.PRICE_LIST_ID;
import static com.inditex.product.offer.adapter.inbound.validator.OfferCreateRequestValidator.PRIORITY;
import static com.inditex.product.offer.adapter.inbound.validator.OfferCreateRequestValidator.PRODUCT_PART_NUMBER;
import static com.inditex.product.offer.adapter.inbound.validator.OfferCreateRequestValidator.START_DATE;
import static com.inditex.product.offer.adapter.inbound.validator.OfferCreateRequestValidator.TEXT_FIELD_IS_INVALID;
import static com.inditex.product.offer.adapter.inbound.validator.OfferCreateRequestValidator.TEXT_FIELD_IS_REQUIRED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.FileLoader.getObjectFromJsonFile;

import com.inditex.product.offer.adapter.inbound.dto.OfferCreateRequestDTO;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;

/**
 * This class test {@link OfferCreateRequestValidator}
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
class OfferCreateRequestValidatorTest extends AbstractOfferValidator {

    private static final String OFFER_CREATE_REQUEST_PATH = "src/test/resources/OfferCreateRequestDTO.json";
    private static final String OFFER_CREATE_REQUEST_WITH_INVALID_FORMAT_DATE_PATH = "src/test/resources/OfferCreateRequestDTOWithInvalidFormatDate.json";

    private static final String OFFERS = "offers";

    private final OfferCreateRequestValidator offerCreateRequestValidator = new OfferCreateRequestValidator();

    @DisplayName("Test valid class")
    @Test
    void testClassIsSupport() {
        final boolean support = offerCreateRequestValidator.supports(OfferCreateRequestDTO.class);
        assertTrue(support);
    }

    @DisplayName("Test empty Offer Request dto with result KO")
    @Test
    void testEmptyOfferRequestDtoResultKO() {
        var offerCreateRequestDTO = new OfferCreateRequestDTO();

        var errors = new BeanPropertyBindingResult(offerCreateRequestDTO, OFFERS);
        offerCreateRequestValidator.validate(offerCreateRequestDTO, errors);

        assertThat(errors.getFieldErrorCount()).isEqualTo(9);

        assertField(errors, OFFER_ID, String.format(TEXT_FIELD_IS_REQUIRED, OFFER_ID));
        assertField(errors, BRAND_ID, String.format(TEXT_FIELD_IS_REQUIRED, BRAND_ID));
        assertField(errors, START_DATE, String.format(TEXT_FIELD_IS_REQUIRED, START_DATE));
        assertField(errors, END_DATE, String.format(TEXT_FIELD_IS_REQUIRED, END_DATE));
        assertField(errors, PRICE_LIST_ID, String.format(TEXT_FIELD_IS_REQUIRED, PRICE_LIST_ID));
        assertField(errors, PRODUCT_PART_NUMBER, String.format(TEXT_FIELD_IS_REQUIRED, PRODUCT_PART_NUMBER));
        assertField(errors, PRIORITY, String.format(TEXT_FIELD_IS_REQUIRED, PRIORITY));
        assertField(errors, PRICE, String.format(TEXT_FIELD_IS_REQUIRED, PRICE));
        assertField(errors, CURRENCY_ISO, String.format(TEXT_FIELD_IS_REQUIRED, CURRENCY_ISO));
    }

    @DisplayName("Test valid Offer Create Request dto with result OK")
    @Test
    void testValidOfferCreateRequestResultOk() throws IOException {

        final var offerCreateRequestDTO = getObjectFromJsonFile(OFFER_CREATE_REQUEST_PATH,
          OfferCreateRequestDTO.class);

        var errors = new BeanPropertyBindingResult(offerCreateRequestDTO, OFFERS);
        offerCreateRequestValidator.validate(offerCreateRequestDTO, errors);

        assertThat(errors.getFieldErrorCount()).isZero();
    }

    @DisplayName("Test valid Offer Create Request dto with invalid format date")
    @Test
    void testValidOfferCreateRequestWithInvalidFormatDate() throws IOException {

        var offerCreateRequestDTO = getObjectFromJsonFile(OFFER_CREATE_REQUEST_WITH_INVALID_FORMAT_DATE_PATH,
          OfferCreateRequestDTO.class);

        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(offerCreateRequestDTO,
          OFFERS);
        offerCreateRequestValidator.validate(offerCreateRequestDTO, errors);

        assertThat(errors.getFieldErrorCount()).isEqualTo(2);
        assertField(errors, START_DATE, String.format(TEXT_FIELD_IS_INVALID, START_DATE));
        assertField(errors, END_DATE, String.format(TEXT_FIELD_IS_INVALID, END_DATE));
    }
}
