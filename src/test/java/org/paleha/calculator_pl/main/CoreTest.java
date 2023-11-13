package org.paleha.calculator_pl.main;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;

import static org.paleha.calculator_pl.constanse.ConstantLibrary.*;

public class CoreTest extends TestCase {
    /**
     * Does the string contain a fractional number
     */
    @Test
    public void testIsDouble() {
        State state = new State();
        Core core = new Core(state);
        boolean decimalTrue = core.isDouble("9876.2");
        boolean decimalFalse = core.isDouble("fdn");
        Assert.assertTrue(decimalTrue);
        Assert.assertFalse(decimalFalse);
    }

    /**
     * Check the work of hashmapCore
     */
    @Test
    public void testHashmapLoad() throws Exception {
        State state = new State();
        Core core = new Core(state);

        core.calculator("12");
        core.calculator("12");
        core.calculator("+");
        BigDecimal result = state.pop();
        Assert.assertEquals("24.0000000000", result.toString());
    }

    /**
     * Checking the conversion of numbers to other counting systems
     */
    @Test
    public void mathOperationsNumbers() throws Exception {
        State state = new State();
        Core core = new Core(state);

        // Check decimal numbers
        core.calculator("12");
        BigDecimal result = state.pop();
        Assert.assertEquals("12", result.toString());

        //Check rome numbers
        core.calculator("XXV");
        result = state.pop();
        Assert.assertEquals("25.0", result.toString());

        // Check octa numbers
        core.calculator("0o1234");
        result = state.pop();
        Assert.assertEquals("668.0", result.toString());

        // Check hex numbers
        core.calculator("0x1a34");
        result = state.pop();
        Assert.assertEquals("6708.0", result.toString());

        // Check binary numbers
        core.calculator("0b1011010");
        result = state.pop();
        Assert.assertEquals("90.0", result.toString());
    }

    @Test
    public void mathOperationPlus() throws Exception {
        State state = new State();
        Core core = new Core(state);

        core.calculator("12");
        core.calculator("12");
        core.calculator(PLUS);
        BigDecimal result = state.pop();
        Assert.assertEquals("24.0000000000", result.toString());
    }

    @Test
    public void mathOperationMinus() throws Exception {
        State state = new State();
        Core core = new Core(state);

        core.calculator("24");
        core.calculator("12");
        core.calculator(MINUS);
        BigDecimal result = state.pop();
        Assert.assertEquals("12.0000000000", result.toString());
    }

    @Test
    public void mathOperationMultiply() throws Exception {
        State state = new State();
        Core core = new Core(state);

        core.calculator("5");
        core.calculator("3");
        core.calculator(MULTIPLY);
        BigDecimal result = state.pop();
        Assert.assertEquals("15.0000000000", result.toString());
    }

    @Test
    public void mathOperationDivide() throws Exception {
        State state = new State();
        Core core = new Core(state);

        core.calculator("33");
        core.calculator("3");
        core.calculator(DIVIDE);
        BigDecimal result = state.pop();
        Assert.assertEquals("11.0000000000", result.toString());
    }

    @Test
    public void mathOperationSquare() throws Exception {
        State state = new State();
        Core core = new Core(state);

        core.calculator("5");
        core.calculator("2");
        core.calculator(SQUARE);
        BigDecimal result = state.pop();
        Assert.assertEquals("1.4142", result.toString());
    }

    @Test
    public void mathOperationExponent() throws Exception {
        State state = new State();
        Core core = new Core(state);

        core.calculator("2.0");
        core.calculator("5.0");

        core.calculator("st");
        BigDecimal result = state.pop();
        Assert.assertEquals("32.0000000000", result.toString());
    }

    @Test
    public void mathOperationPercent() throws Exception {
        State state = new State();
        Core core = new Core(state);

        core.calculator("100");
        core.calculator("2");

        core.calculator(PERCENT);
        BigDecimal result = state.pop();
        Assert.assertEquals("2.0000000000", result.toString());
    }

    @Test
    public void mathOperationMemory() throws Exception {
        State state = new State();
        Core core = new Core(state);

        core.calculator("100");

        core.calculator(MEMORY);
        BigDecimal result = state.pop();
        Assert.assertEquals("100", result.toString());
    }

}