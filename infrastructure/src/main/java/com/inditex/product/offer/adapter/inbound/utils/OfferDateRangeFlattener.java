package com.inditex.product.offer.adapter.inbound.utils;


import static com.inditex.product.offer.adapter.inbound.utils.ZonedDateTimeUtils.dateFormatter;

import com.inditex.product.offer.adapter.inbound.dto.Event;
import com.inditex.product.offer.adapter.inbound.dto.OfferDateRangeFlattenedResponseDTO;
import com.inditex.product.offer.model.Offer;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * Utility class for flattening a list of {@link Offer} objects into a list of {@link OfferDateRangeFlattenedResponseDTO}
 * objects. The flattening process resolves overlapping date ranges by applying the price with the highest priority.
 *
 * <p>This class processes the offers by creating events for the start and end
 * of each offer, sorting them, and then iterating through the events to generate non-overlapping date ranges with the
 * correct price applied.</p>
 *
 * <p>Usage:</p>
 * <pre>
 * List&lt;Offer&gt; offers = ...;
 * List&lt;OfferDateRangeFlattenedResponseDTO&gt; flattenedOffers = OfferDateRangeFlattenedMapper.flatten(offers);
 * </pre>
 *
 * <p>Note: This class is not meant to be instantiated.</p>
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
public class OfferDateRangeFlattener {

    private OfferDateRangeFlattener() {

    }

    public static List<OfferDateRangeFlattenedResponseDTO> flatten(List<Offer> offers) {
        List<Event> events = new ArrayList<>();
        offers.forEach(offer -> {
            events.add(new Event(offer.getStartDate(), true, offer.getPrice(), offer.getPriority(), offer.getCurrencyIso()));
            events.add(new Event(offer.getEndDate(), false, offer.getPrice(), offer.getPriority(), offer.getCurrencyIso()));
        });

        Collections.sort(events);

        List<OfferDateRangeFlattenedResponseDTO> result = new ArrayList<>();
        TreeMap<Integer, List<BigDecimal>> activePrices = new TreeMap<>(Collections.reverseOrder());

        ZonedDateTime prevTime = null;
        for (Event event : events) {
            if (prevTime != null && !activePrices.isEmpty() && prevTime.isBefore(event.getTime())) {
                var appliedPrice = activePrices.firstEntry().getValue().getFirst();
                result.add(
                  new OfferDateRangeFlattenedResponseDTO(prevTime.format(dateFormatter),
                    event.getTime().minusSeconds(1).format(dateFormatter),
                    appliedPrice, event.getCurrencyIso()));
            }

            if (event.isStart()) {
                activePrices.computeIfAbsent(event.getPriority(), k -> new ArrayList<>()).add(event.getPrice());
            } else {
                activePrices.get(event.getPriority()).remove(event.getPrice());
                if (activePrices.get(event.getPriority()).isEmpty()) {
                    activePrices.remove(event.getPriority());
                }
            }

            prevTime = event.getTime();
        }
        return result;
    }
}
