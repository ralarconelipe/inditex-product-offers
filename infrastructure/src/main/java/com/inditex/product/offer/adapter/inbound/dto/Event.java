package com.inditex.product.offer.adapter.inbound.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event implements Comparable<Event> {

    private ZonedDateTime time;

    private boolean isStart;

    private BigDecimal price;

    private Integer priority;

    private String currencyIso;


    @Override
    public int compareTo(Event o) {
        return this.time.compareTo(o.time);
    }

}
