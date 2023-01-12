package Project;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Slf4j
public class XMLParser {

    public static <T> T unmarshal(File source, Class<T> clazz) {
        T result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            result = (T) unmarshaller.unmarshal(source);

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
