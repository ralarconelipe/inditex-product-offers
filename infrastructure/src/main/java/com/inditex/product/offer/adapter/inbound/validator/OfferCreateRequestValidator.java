package com.inditex.product.offer.adapter.inbound.validator;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

import com.inditex.product.offer.adapter.inbound.dto.OfferCreateRequestDTO;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for {@link OfferCreateRequestDTO} objects. Ensures that all required fields are present and valid according to
 * the specified rules.
 *
 * <p>Validation rules include:
 * <ul>
 *   <li>Required fields must not be null or empty.</li>
 *   <li>Dates must be in ISO-8601 instant format.</li>
 * </ul>
 *
 * <p>Fields validated:
 * <ul>
 *   <li>brandId</li>
 *   <li>startDate</li>
 *   <li>endDate</li>
 *   <li>priceListId</li>
 *   <li>productPartnumber</li>
 *   <li>priority</li>
 *   <li>price</li>
 *   <li>currencyIso</li>
 * </ul>
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
public class OfferCreateRequestValidator implements Validator {


    private static final String REQUIRED = ".required";
    private static final String INVALID = ".invalid";

    public static final String OFFER_ID = "offerId";
    public static final String BRAND_ID = "brandId";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String PRICE_LIST_ID = "priceListId";
    public static final String PRODUCT_PART_NUMBER = "productPartnumber";
    public static final String PRIORITY = "priority";
    public static final String PRICE = "price";
    public static final String CURRENCY_ISO = "currencyIso";

    public static final String TEXT_IS_REQUIRED = " is required";
    public static final String TEXT_IS_INVALID = " is invalid";
    public static final String TEXT_FIELD_IS_REQUIRED = "The %s" + TEXT_IS_REQUIRED;
    public static final String TEXT_FIELD_IS_INVALID = "The %s" + TEXT_IS_INVALID;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH.mm.ss'Z'")
      .withZone(ZoneId.of("UTC"));

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return OfferCreateRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Optional.of(target)
          .filter(OfferCreateRequestDTO.class::isInstance)
          .ifPresent(object -> validateDto((OfferCreateRequestDTO) object, errors));
    }

    private void validateDto(OfferCreateRequestDTO offerCreateRequestDTO, Errors errors) {

        rejectIfEmptyOrWhitespace(errors, START_DATE, START_DATE + REQUIRED,
          String.format(TEXT_FIELD_IS_REQUIRED, START_DATE));
        rejectIfEmptyOrWhitespace(errors, END_DATE, END_DATE + REQUIRED, String.format(TEXT_FIELD_IS_REQUIRED, END_DATE));
        rejectIfEmptyOrWhitespace(errors, PRODUCT_PART_NUMBER, PRODUCT_PART_NUMBER + REQUIRED,
          String.format(TEXT_FIELD_IS_REQUIRED, PRODUCT_PART_NUMBER));
        rejectIfEmptyOrWhitespace(errors, CURRENCY_ISO, CURRENCY_ISO + REQUIRED,
          String.format(TEXT_FIELD_IS_REQUIRED, CURRENCY_ISO));

        Optional.ofNullable(offerCreateRequestDTO.getOfferId())
          .ifPresentOrElse(offerId -> {
              if (offerId <= 0) {
                  errors.rejectValue(OFFER_ID, OFFER_ID + REQUIRED,
                    String.format(TEXT_FIELD_IS_INVALID, OFFER_ID));
              }
          }, () -> errors.rejectValue(OFFER_ID, OFFER_ID + REQUIRED,
            String.format(TEXT_FIELD_IS_REQUIRED, OFFER_ID)));

        Optional.ofNullable(offerCreateRequestDTO.getBrandId())
          .ifPresentOrElse(brandId -> {
              if (brandId <= 0) {
                  errors.rejectValue(BRAND_ID, BRAND_ID + REQUIRED,
                    String.format(TEXT_FIELD_IS_INVALID, BRAND_ID));
              }
          }, () -> errors.rejectValue(BRAND_ID, BRAND_ID + REQUIRED,
            String.format(TEXT_FIELD_IS_REQUIRED, BRAND_ID)));

        if (Objects.isNull(offerCreateRequestDTO.getPriceListId())) {
            errors.rejectValue(PRICE_LIST_ID, PRICE_LIST_ID + REQUIRED,
              String.format(TEXT_FIELD_IS_REQUIRED, PRICE_LIST_ID));
        }
        if (Objects.isNull(offerCreateRequestDTO.getPriority())) {
            errors.rejectValue(PRIORITY, PRIORITY + REQUIRED, String.format(TEXT_FIELD_IS_REQUIRED, PRIORITY));
        }
        if (Objects.isNull(offerCreateRequestDTO.getPrice())) {
            errors.rejectValue(PRICE, PRICE + REQUIRED, String.format(TEXT_FIELD_IS_REQUIRED, PRICE));
        }

        Optional.ofNullable(offerCreateRequestDTO.getStartDate())
          .filter(this::isInValidFormatDate)
          .ifPresent(startDate -> errors.rejectValue(START_DATE, START_DATE + INVALID,
            String.format(TEXT_FIELD_IS_INVALID, START_DATE)));

        Optional.ofNullable(offerCreateRequestDTO.getEndDate())
          .filter(this::isInValidFormatDate)
          .ifPresent(endDate -> errors.rejectValue(END_DATE, END_DATE + INVALID,
            String.format(TEXT_FIELD_IS_INVALID, END_DATE)));
    }

    private boolean isInValidFormatDate(String dateStr) {

        try {
            ZonedDateTime.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            return true;
        }
        return false;
    }
}
