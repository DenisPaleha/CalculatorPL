import junit.framework.TestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class LoggerTest extends TestCase {

    /**
     * Тест создания файла и вывод на экран имени файла
     */
    @Test
    @Disabled
    public void testCreateFile() {
        Logger logger = new Logger();
        String time = logger.generateFileName();
        String name = logger.getFileName();
        System.out.println(name);
        assertEquals(time, name);
    }

    /**
     * Тест ввода и чтения строки функцией loggInPut (входящие данные)
     */
    @Test
//    @Order(1)
    @Disabled
    public void testWriteAndReadIn() {
        Logger logger = new Logger();
        String time = logger.generateFileName();
        String testString = "Какой-то произвольный текст";
        logger.logInput(testString);
        String test = logger.readLog();
        assertEquals(time + " in Какой-то произвольный текст\n", test);
    }

    /**
     * Тест ввода и чтения строки функцией loggOutPut (выходящие данные)
     */
    @Test
//    @Order(2)
    @Disabled
    public void testWriteAndReadOut() {
        Logger logger = new Logger();
        String time = logger.generateFileName();
        String testString = "Текст вывода на экран";

        logger.logOutput(testString);
        String test = logger.readLog();
        assertEquals(time + " out Текст вывода на экран\n", test);
    }

//    /** Автоматическое удаление первых 50 логов, если их более 100*/
//    @Test
//    @Disabled
//    void autoCleanLog(){
//        Logger logger = new Logger();
//        for (int i = 0; i < 120; i++){
//            System.out.println("Вывод данных " + i);
//            logger.logOutput("Вывод данных " + i);
//        }
//        logger.autoCleanLog();
//    }

}
