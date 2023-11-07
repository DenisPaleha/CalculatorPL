import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.paleha.calculator_pl.exception.ConversionException;
import org.paleha.calculator_pl.exception.OutOfRangeException;
import org.paleha.calculator_pl.numbers.RomeNumerals;

import java.math.BigDecimal;

public class RomeNumeralsTest  {

    @Test
    public void testConvertDecimalToRome() throws Exception {
        String result = RomeNumerals.convertDecimalToRome(478);
        Assert.assertEquals("CDLXXVIII", result);

        try {
            RomeNumerals.convertDecimalToRome(4780);
        } catch (OutOfRangeException wrongNumber) {
            result = wrongNumber.getMessage();
            Assert.assertEquals("Conversion is correct only for numbers from 0 to 3999.", result);
        }

        try {
            RomeNumerals.convertDecimalToRome(-47);
        } catch (OutOfRangeException wrongNumber) {
            result = wrongNumber.getMessage();
            Assert.assertEquals("Conversion is correct only for numbers from 0 to 3999.", result);
        }
    }

    @Test
    public void testIsRome() {
        boolean result = RomeNumerals.isRome("XXXX");
        Assert.assertTrue(result);
        result  = RomeNumerals.isRome("XMDLVIIXX");
        Assert.assertTrue(result);
        result  = RomeNumerals.isRome("XMDE");
        Assert.assertFalse(result);
    }

    @Test
    public void testConvertRomeToPush() throws Exception {
        BigDecimal test = RomeNumerals.convertRomeToPush("XXV");
        Assert.assertEquals("25.0", test.toString());

        try {
            RomeNumerals.convertRomeToPush("XXVV");
        } catch (ConversionException wrongNumber) {
            String result = wrongNumber.getMessage();
            Assert.assertEquals("Write error: Roman numerals V, L, and D cannot be repeated.", result);
        }

        try {
            RomeNumerals.convertRomeToPush("XVD");
        } catch (ConversionException wrongNumber) {
            String result = wrongNumber.getMessage();
            Assert.assertEquals("Write error: Roman numerals V, L, and D cannot precede larger numerals.", result);
        }

        try {
            RomeNumerals.convertRomeToPush("XXXX");
        } catch (ConversionException wrongNumber) {
            String result = wrongNumber.getMessage();
            Assert.assertEquals("Write error: Roman numerals 1, 10, 100, 1000 cannot repeat more than three times consecutively.", result);
        }

        try {
            RomeNumerals.convertRomeToPush("XXL");
        } catch (ConversionException wrongNumber) {
            System.out.println(wrongNumber.getMessage());
            String result = wrongNumber.getMessage();
            Assert.assertEquals("Write error: Two identical Roman numerals of lesser value cannot precede a larger numeral.", result);
        }
    }

}