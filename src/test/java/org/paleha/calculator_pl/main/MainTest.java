package org.paleha.calculator_pl.main;

import junit.framework.TestCase;

import static org.paleha.calculator_pl.constanse.ConstantLibrary.*;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.paleha.calculator_pl.constanse.HashMap;
import org.paleha.calculator_pl.logger.MyLogger;

import org.junit.Assert;
import org.junit.Test;

import org.paleha.calculator_pl.numbers.BinaryNumbersTest;
import org.paleha.calculator_pl.numbers.HexNumbersTest;
import org.paleha.calculator_pl.numbers.OctalNumbersTest;
import org.paleha.calculator_pl.numbers.RomeNumeralsTest;

import java.io.IOException;
import java.math.BigDecimal;

@RunWith(Suite.class)
@Suite.SuiteClasses({StateTest.class, CoreTest.class, HexNumbersTest.class,
        OctalNumbersTest.class, BinaryNumbersTest.class, RomeNumeralsTest.class/* all over classes if we need it */})

public class MainTest extends TestCase {

    @Test
    public void testNweState() {
        State state = new State();
        state.push(BigDecimal.valueOf(12));
        BigDecimal control = state.peek();
        Assert.assertEquals("12", control.toString());
    }

    @Test
    public void testLogger() {
        String isWork;
        try {
            MyLogger myLogger = new MyLogger();
            String anyWord = "The test of logger work";

            try {
                myLogger.logOutput(anyWord, "in"); // Copy all input data to the logger
                isWork = "Yes";
            } catch (Exception e) {
                isWork = "No";
            }
            Assert.assertEquals("Yes", isWork);

        } catch (IOException e) {
            isWork = "No";
        }
        Assert.assertEquals("Yes", isWork);
    }

    @Test
    public void testHashmapLoad() {
        HashMap hashmapMain = new HashMap(8);
        hashmapMain.loadMainHashMap();
        String testWord = "help";
        boolean keyHashMap = hashmapMain.hasKey(testWord);
        Assert.assertTrue(keyHashMap);
    }

    @Test
    public void testHashMapWork() {
        HashMap hashmapMain = new HashMap(8);
        hashmapMain.loadMainHashMap();
        String operand = "help";
        String control = hashmapMain.get(operand); // Get value by key = HELP
        Assert.assertEquals(HELP, control);


        operand = "c";
        control = hashmapMain.get(operand);
        Assert.assertEquals(CLEAR, control);

        operand = "info";
        control = hashmapMain.get(operand);
        Assert.assertEquals(INFO, control);

        // Switch method body check in StateTest
        operand = "sm";
        control = hashmapMain.get(operand);
        Assert.assertEquals(SWITCH_METHOD, control);

        // Save method body check in StateTest
        operand = "save";
        control = hashmapMain.get(operand);
        Assert.assertEquals(SAVE, control);

        // ToRome method body check in RomeNumeralsTest
        operand = "torome";
        control = hashmapMain.get(operand);
        Assert.assertEquals(TO_ROME, control);

        // ToOctal method body check in OctalNumbersTest
        operand = "tooctal";
        control = hashmapMain.get(operand);
        Assert.assertEquals(TO_OCTAL, control);

        // ToHex method body check in HexNumbersTest
        operand = "tohex";
        control = hashmapMain.get(operand);
        Assert.assertEquals(TO_HEX, control);

        // ToBin method body check in BinaryNumbersTest
        operand = "tobin";
        control = hashmapMain.get(operand);
        Assert.assertEquals(TO_BIN, control);

        // OutRome method body check in StateTest
        operand = "outdec";
        control = hashmapMain.get(operand);
        Assert.assertEquals(OUT_DEC, control);

        // OutRome method body check in StateTest
        operand = "outrome";
        control = hashmapMain.get(operand);
        Assert.assertEquals(OUT_ROM, control);

        // OutOctal method body check in StateTest
        operand = "outoct";
        control = hashmapMain.get(operand);
        Assert.assertEquals(OUT_OCT, control);

        // OutHex method body check in StateTest
        operand = "outhex";
        control = hashmapMain.get(operand);
        Assert.assertEquals(OUT_HEX, control);

        // OutBin method body check in StateTest
        operand = "outbin";
        control = hashmapMain.get(operand);
        Assert.assertEquals(OUT_BIN, control);

        // OutRus method body check in StateTest
        operand = "rus";
        control = hashmapMain.get(operand);
        Assert.assertEquals(OUT_RUS, control);

        // OutEng method body check in StateTest
        operand = "eng";
        control = hashmapMain.get(operand);
        Assert.assertEquals(OUT_ENG, control);
    }


}