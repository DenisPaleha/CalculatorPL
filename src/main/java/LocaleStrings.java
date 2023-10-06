import java.io.*;
import java.util.Properties;

public class LocaleStrings {
    private final Properties properties;

    public LocaleStrings(Boolean isEnglish) {
        properties = new Properties();
        String fileName;
        if (isEnglish) {
            fileName = "/English.properties";
        } else {
            fileName = "/Russian.properties";
        }
        try (InputStream inputStream = getClass().getResourceAsStream(fileName)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                System.err.println("File not found: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String key){return properties.getProperty(key);}

}
