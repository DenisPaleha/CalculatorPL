import static constants.ConstantLibrary.*;
import junit.framework.TestCase;
import numbers.BinaryNumbers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BinaryNumbersTest extends TestCase {

    @Test
    public void testHasBinaryNumber() {
        boolean isBinary;
        String x = PREFIX_2 + "10011100010";
        isBinary = BinaryNumbers.isBinaryNumber(x);
        assertTrue(isBinary);

        x = "10011100010";
        isBinary = BinaryNumbers.isBinaryNumber(x);
        assertFalse(isBinary);
    }

//    @Test
//    public void testIsBinaryCorrect() {  // need public
//        boolean isBinary;
//        String x = "10011100010";
//        isBinary = BinaryNumbers.isBinaryCorrect(x);
//        assertTrue(isBinary);
//
//        x = "10011100013";
//        isBinary = BinaryNumbers.isBinaryCorrect(x);
//        assertFalse(isBinary);
//    }

    @Test
    public void testDecimalToBinary() {
        int decimal = 1250;
        String result = BinaryNumbers.convertDecimalToBinary(decimal);
        assertEquals(PREFIX_2 + "10011100010", result);
    }

    @Test
    public void testBinaryToDecimal() {
        String binary = "0b10011100010";
        BigDecimal Bd = BinaryNumbers.binaryToPush(binary);
        String result = Bd.toString();
        assertEquals("1250.0", result);
    }

}
