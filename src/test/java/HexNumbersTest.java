import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

public class HexNumbersTest extends TestCase {

    /**
     * Функция проверяет не является ли введенная строка шестнадцатеричным (16) числом
     */
    @Test
    public void testHasHexNumber() {
        boolean hexTrue = HexNumbers.hasHexNumber("0xab5");
        boolean hexFalse = HexNumbers.hasHexNumber("fdn");
        assertTrue(hexTrue);
        assertFalse(hexFalse);
    }

    /**
     * Проверяем конвертацию из строки шестнадцатеричного числа в BidDecimal десятичного
     */
    @Test
    public void testConvertHexToDecimal() {
        String test = "30"; // Строка 0x38 без префикса "0x"
        BigDecimal actualHex = HexNumbers.convertHexToDecimal(test);
        String result = actualHex.toString();
        assertEquals("48.0", result);
    }

    /**
     * Функция получает на вход строку проверяет ее возможность и возвращает BigDecimal для вставки в массив или список
     */
    @Test
    public void testHexNumbersToPush() {
        String num = "0x30";
        BigDecimal actualHex = HexNumbers.hexNumbersToPush(num);
        String result = actualHex.toString();
        assertEquals("48.0", result);

        num = "0x3Z";
        actualHex = HexNumbers.hexNumbersToPush(num);
        result = actualHex.toString();
        assertEquals("0.0", result);
    }

    /**
     * Функция проверяет, содержит ли строка недопустимые значения (не учитывая Префикс!)
     */
    @Test
    public void testIsHexCorrect() {
        String test = "60Z";
        boolean result = HexNumbers.isHexCorrect(test);
        assertFalse(result);

        test = "6A";
        result = HexNumbers.isHexCorrect(test);
        assertTrue(result);
    }

    /**
     * Функция проверяет правильность конвертации десятичных чисел в восьмеричные
     */
    @Test
    public void testConvertDecimalToHex() {
        int num = 48;
        String result = HexNumbers.convertDecimalToHex(num);
        assertEquals("0x30", result);
    }
}