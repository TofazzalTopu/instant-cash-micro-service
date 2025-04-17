package com.info.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Slf4j
public class ObjectConverter {
    private ObjectConverter() {
    }

    public static final Logger logger = LoggerFactory.getLogger(ObjectConverter.class);

    public static <T> String convertObjectToString(T data) {
        try {
            return Objects.nonNull(data) ? new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(data) : "";
        } catch (Exception e) {
            logger.info("Data parse error -----------------------------> \r\n {}", data);
        }
        return "";
    }

    public static <T> T convertStringToObject(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // If you have LocalDate/LocalDateTime fields
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("Failed to convert string to object: {}, message: {}", json, e.getMessage());
            return null;
        }
    }

}
