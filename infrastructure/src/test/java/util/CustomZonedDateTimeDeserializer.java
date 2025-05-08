package util;

import static com.inditex.product.offer.adapter.inbound.utils.ZonedDateTimeUtils.dateFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.ZonedDateTime;

public class CustomZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String date = p.getText();
        return ZonedDateTime.parse(date, dateFormatter);
    }
}