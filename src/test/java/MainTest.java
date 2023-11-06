import org.paleha.calculator_pl.numbers.BinaryNumbers;
import org.paleha.calculator_pl.numbers.HexNumbers;
import org.paleha.calculator_pl.numbers.OctalNumbers;
import org.paleha.calculator_pl.numbers.RomeNumerals;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class MainTest {


    /**
     * Check if the function "Is the string a Roman number".
     */
    @Test
    public void testIsRome() {
        boolean romeTrue = RomeNumerals.isRome("mmclxxxvii");
        boolean romeFalse = RomeNumerals.isRome("fdn");
        Assert.assertTrue(romeTrue);
        Assert.assertFalse(romeFalse);
    }

    /**
     * Checking the function "Is the string an Octal number"
     */
    @Test
    public void testIsOctal() {
        boolean octalTrue = OctalNumbers.isOctalNumber("0o465");
        boolean octalFalse = OctalNumbers.isOctalNumber("fdn");
        Assert.assertTrue(octalTrue);
        Assert.assertFalse(octalFalse);
    }

    /**
     * Check if the function "Is the string a hexadecimal number"
     */
    @Test
    public void testIsHex() {
        boolean hexTrue = HexNumbers.isHexNumber("0xab5");
        boolean hexFalse = HexNumbers.isHexNumber("fdn");
        Assert.assertTrue(hexTrue);
        Assert.assertFalse(hexFalse);
    }

    /**
     * Check the conversion of decimal numbers to octal, hexadecimal, binary and Roman numerals
     */
    @Test
    public void convertToOctHexRomBin() throws Exception {
        int num = 48; // Переводим BigDecimal в int
        String actualOct = OctalNumbers.convertDecimalToOctal(num);
        Assert.assertEquals("0o60", actualOct);

        String actualHex = HexNumbers.convertDecimalToHex(num);
        Assert.assertEquals("0x30", actualHex);

        String actualRom = RomeNumerals.convertDecimalToRome(num);
        Assert.assertEquals("XLVIII", actualRom);

        String actualBinary = BinaryNumbers.convertDecimalToBinary(num);
        Assert.assertEquals("0b110000", actualBinary);
    }

    /**
     * Check the operation of the save function
     */
    @Test
    //   @Disabled // Тест отключен
    public void testSaveArr() {
        // Для массива
        State state = new State();
        state.push(BigDecimal.valueOf(12));
        state.push(BigDecimal.valueOf(13));

        String control = state.prepareForSave();
        Assert.assertEquals("12 13 \n13\ntrue\ntrue\n0\n", control);
    }

    @Test
    public void testSaveList() {
        State state = new State();
        state.setStorageType(false); //  LinkedList
        state.push(BigDecimal.valueOf(12));

        String control = state.prepareForSave(); // Save state to string.
        Assert.assertEquals("12 \n12\nfalse\ntrue\n0\n", control);
    }

}