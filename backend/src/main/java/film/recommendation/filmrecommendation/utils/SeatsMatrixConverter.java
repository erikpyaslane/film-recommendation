package film.recommendation.filmrecommendation.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


/**
 * Created with a help of ChatGPT:
 * <a href="https://chat.openai.com/c/13ce56cd-f939-4f93-b8a5-cfd3a3b4b20f">...</a>
 *
 *
 * Konverteerib boolean[][] massiivi sobivale kujule andmebaasi jaoks
 */
@Converter
public class SeatsMatrixConverter implements AttributeConverter<boolean[][], String> {

    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public String convertToDatabaseColumn(boolean[][] attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            // Handle the exception accordingly
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean[][] convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, boolean[][].class);
        } catch (JsonProcessingException e) {
            // Handle the exception accordingly
            e.printStackTrace();
            return null;
        }
    }
}