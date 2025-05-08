package com.inditex.product.offer.adapter.inbound.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeUtils {

    private ZonedDateTimeUtils() {

    }


    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH.mm.ss'Z'")
      .withZone(ZoneId.of("Z"));

}
