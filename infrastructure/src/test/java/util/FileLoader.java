package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * This interface is used for test that need load objects from json files
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
public class FileLoader {

    public static final ObjectMapper objectMapper;

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(ZonedDateTime.class, new CustomZonedDateTimeDeserializer());

        objectMapper = new ObjectMapper()
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .registerModule(javaTimeModule);
    }

    /**
     * Gets object form json file
     *
     * @param <T>       type of object to load
     * @param path      path of the file
     * @param classType {@link Class}
     * @return an object of type T
     * @throws IOException when occurs
     */
    public static <T> T getObjectFromJsonFile(String path, Class<T> classType)
      throws IOException {
        return objectMapper.readValue(new File(path), classType);
    }

    /**
     * Gets object form json file
     *
     * @param <T>  type of object to load
     * @param path path of the file
     * @param type {@link TypeReference}
     * @return an object of type T
     * @throws IOException when occurs
     */
    public static <T> T getObjectFromJsonFile(String path, TypeReference<T> type)
      throws IOException {
        return objectMapper.readValue(new File(path), type);
    }

}
