package Project;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonParser {
    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> String FromJson(T dataClass) {
        String object;
        try {
            object = mapper.writeValueAsString(dataClass);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonGenerationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return object;
    }


    public static <T> T ToJson(String dataString, Class<T> clazz) {
        T object;
        try {
            object = mapper.readValue(dataString, clazz);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return object;
    }
}
