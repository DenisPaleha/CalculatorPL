import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

public class HexNumbersTest extends TestCase {

    /**
     * The function checks if the entered string is a hexadecimal (16) number
     */
    @Test
    public void testHasHexNumber() {
        boolean hexTrue = HexNumbers.hasHexNumber("0xab5");
        boolean hexFalse = HexNumbers.hasHexNumber("fdn");
        assertTrue(hexTrue);
        assertFalse(hexFalse);
    }

    /**
     * Check the conversion of a hexadecimal (string) number to a decimal (BidDecimal) number
     */
    @Test
    public void testConvertHexToDecimal() {
        String test = "30"; // Строка 0x38 без префикса "0x"
        BigDecimal actualHex = HexNumbers.convertHexToDecimal(test);
        String result = actualHex.toString();
        assertEquals("48.0", result);
    }

    /**
     * The function receives a string as a parameter, checks the possibility for conversion,
     * and returns a BigDecimal to insert into the stack
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
     * The function checks if there are invalid values in the string (not considering the Prefix!).
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
     * The function checks the conversion of decimal numbers to octal numbers
     */
    @Test
    public void testConvertDecimalToHex() {
        int num = 48;
        String result = HexNumbers.convertDecimalToHex(num);
        assertEquals("0x30", result);
    }
}