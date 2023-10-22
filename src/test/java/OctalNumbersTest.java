import junit.framework.TestCase;
import numbers.OctalNumbers;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

public class OctalNumbersTest extends TestCase {

    /**
     * Проверяем работоспособность функции "Является ли строка Восьмеричным числом"
     */
    @Test
    public void testHasOctalNumber() {
        boolean octalTrue = OctalNumbers.isOctalNumber("0o465");
        boolean octalFalse = OctalNumbers.isOctalNumber("fdn");
        assertTrue(octalTrue);
        assertFalse(octalFalse);
    }

    /**
     * Проверяем конвертацию из строки восьмеричного числа в BidDecimal десятичного
     */
    @Test
    public void testConvertOctalToDecimal() {
        String test = "60"; // Строка без префикса "0o"
        BigDecimal actualOct = OctalNumbers.convertOctalToDecimal(test);
        String result = actualOct.toString();
        assertEquals("48.0", result);
    }

    /**
     * Функция получает на вход строку проверяет ее возможность и возвращает BigDecimal для вставки в массив или список
     */
    @Test
    public void testConvertOctalToPush() {
        String num = "0o60"; // Переводим BigDecimal в int
        BigDecimal actualOct = OctalNumbers.convertOctalToPush(num);
        String result = actualOct.toString();
        assertEquals("48.0", result);

        num = "0o68"; // Переводим BigDecimal в int
        actualOct = OctalNumbers.convertOctalToPush(num);
        result = actualOct.toString();
        assertEquals("0.0", result);
    }

    /**
     * Функция проверяет, содержит ли строка недопустимые значения (не учитывая Префикс!)
     */
    @Test
    public void testIsOctalCorrect() {
        String test = "608";
        boolean result = OctalNumbers.isOctalCorrect(test);
        assertFalse(result);

        test = "675";
        result = OctalNumbers.isOctalCorrect(test);
        assertTrue(result);
    }

    /**
     * Функция проверяет правильность конвертации десятичных чисел в восьмеричные
     */
    @Test
    public void testConvertDecimalToOctal() {
        int num = 48;
        String result = OctalNumbers.convertDecimalToOctal(num);
        assertEquals("0o60", result);
    }
}