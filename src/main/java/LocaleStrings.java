import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class LocaleStrings {

    private Properties properties;

    public LocaleStrings(String fileName) {
        properties = new Properties();
        try (InputStream inputStream = getClass().getResourceAsStream(fileName)){

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                System.err.println("File not found: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) {

        String languageFile = "/English.properties";
//        String languageFile = "/Russian.properties";
        LocaleStrings localeStrings = new LocaleStrings(languageFile); // Убедитесь, что путь к файлу правильный
        String hello1 = localeStrings.getString("hello_massage_one");
        String hello2 = localeStrings.getString("hello_massage_two");
        String roman = localeStrings.getString("roman_number");
        String decimal = localeStrings.getString("decimal");
        System.out.println(hello1);
        System.out.println(hello2);
        System.out.println(roman + decimal + "100.00");

    }
}
