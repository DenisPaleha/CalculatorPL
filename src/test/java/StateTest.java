import junit.framework.TestCase;
import junit.framework.TestResult;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.paleha.calculator_pl.constanse.ConstantLibrary.*;

public class StateTest extends TestCase {

    @Test
    public void testSetLanguage() {
        State state = new State();
        assertTrue(state.isEnglish());

        String result = state.getPhrase("hello_massage_one");
        assertEquals("Enter text in the format: m 10 5 + + 3 /", result);

        state.setLanguage(false);
        assertFalse(state.isEnglish());

        result = state.getPhrase("hello_massage_one");
        assertEquals("Введите текст в формате: m 10 5 + + 3 /: m 10 5 + + 3 /", result);
    }

    @Test
    public void testStateIsEmpty() {
        State state = new State();
        assertTrue(state.isEmpty());

        state.push(BigDecimal.valueOf(12));
        assertFalse(state.isEmpty());

        state.setStorageType(false); // меняем тип данных
        state.clear(); // обнуляем данные в стеке

        state.push(BigDecimal.valueOf(12));
        assertFalse(state.isEmpty());
    }

    /**
     * reversing the data type
     */
    @Test
    public void testIsArrayChange() {
        State state = new State();
        state.setStorageType(false);
        assertFalse(state.isArray());

        state.setStorageType(true);
        assertTrue(state.isArray());
    }

    @Test
    public void testPushToArr() {
        State state = new State();
        // Массив по умолчанию
        state.push(BigDecimal.valueOf(12));
        BigDecimal test = state.peek();
        String actual = test.toString();
        assertEquals("12", actual);
    }

    @Test
    public void testPushToList() {
        State state = new State();
        state.setStorageType(false); // меняем тип данных на список
        state.push(BigDecimal.valueOf(12));
        BigDecimal test = state.peek();
        String actual = test.toString();
        assertEquals("12", actual);
    }

    @Test
    public void testPeekFromArr() {
        State state = new State();
        state.push(BigDecimal.valueOf(300));
        state.push(BigDecimal.valueOf(12));
        BigDecimal test = state.peek();
        String actual = test.toString();
        assertEquals("12", actual);

        test = state.peek(); // При повторной операции данные никуда не делись.
        actual = test.toString();
        assertEquals("12", actual);
    }

    @Test
    public void testPeekFromList() {
        State state = new State();
        state.setStorageType(false); // меняем тип данных на список
        state.push(BigDecimal.valueOf(300));
        state.push(BigDecimal.valueOf(12));
        BigDecimal test = state.peek();
        String actual = test.toString();
        assertEquals("12", actual);

        test = state.peek(); // При повторной операции данные никуда не делись.
        actual = test.toString();
        assertEquals("12", actual);
    }

    @Test
    public void testPeekFromListIfNull() {
        State state = new State();
        state.setStorageType(false); // меняем тип данных на список
        BigDecimal test = state.peek();
        String actual = test.toString();
        assertEquals("0", actual);
    }

    @Test
    public void testPopFromArr() {
        State state = new State();
        state.push(BigDecimal.valueOf(12));
        BigDecimal test = state.pop();
        String actual = test.toString();
        assertEquals("12", actual);

        test = state.pop(); // Проверяем, что осталось в массиве.
        actual = test.toString();
        assertEquals("0", actual);
    }

    @Test
    public void testPopFromList() {
        State state = new State();
        state.setStorageType(false); // меняем тип данных на список
        state.push(BigDecimal.valueOf(12));
        BigDecimal test = state.pop();
        String actual = test.toString();
        assertEquals("12", actual);

        test = state.pop(); // Еще раз проверяем остаток при помощи pop()
        actual = test.toString();
        assertEquals("0", actual); // pop() функция возвращает из списка 0, а не null
    }

    @Test
    public void testCopyStack() {
        State state = new State();
        state.push(BigDecimal.valueOf(120));
        state.push(BigDecimal.valueOf(12));
        state.push(BigDecimal.valueOf(1));
        State copy = state.copyState();

        BigDecimal test = copy.pop();
        String actual = test.toString() + " ";
        assertEquals("1 ", actual);

        copy.setStorageType(false); // переключим на список

        test = copy.pop();
        actual += test.toString() + " ";
        assertEquals("1 12 ", actual);

        test = copy.pop();
        actual += test.toString() + " ";
        assertEquals("1 12 120 ", actual);

        test = copy.pop();
        actual = test.toString();
        assertEquals("0", actual);

    }

    @Test
    public void testClearStack() {
        State state = new State(); // Array
        state.push(BigDecimal.valueOf(120));
        state.push(BigDecimal.valueOf(130));
        state.push(BigDecimal.valueOf(140));

        assertFalse(state.isEmpty());

        state.clear();
        assertTrue(state.isEmpty());

        state.setStorageType(false);  // LinkedList
        state.push(BigDecimal.valueOf(120));
        state.push(BigDecimal.valueOf(130));
        state.push(BigDecimal.valueOf(140));

        assertFalse(state.isEmpty());

        state.clear();
        assertTrue(state.isEmpty());
    }

    @Test
    public void testCopyState() {
        State state = new State();
        state.setStorageType(false);
        state.push(BigDecimal.valueOf(120));
        state.push(BigDecimal.valueOf(12));
        state.memoryResult = BigDecimal.valueOf(500); // Сохраненная память

        State copy = state.copyState();
        BigDecimal test = copy.pop();
        String actual = test.toString();
        assertEquals("12", actual);


        test = copy.memoryResult;
        actual = test.toString();
        assertEquals("500", actual);

        assertFalse(copy.isArray());
    }

    @Test
//    @Disabled
    public void testPrepareSaveArr() {
        State state = new State();
        state.setLanguage(false);

        state.push(BigDecimal.valueOf(120));
        state.push(BigDecimal.valueOf(130));
        state.push(BigDecimal.valueOf(140));

        String actual = state.prepareForSave(); // Читаем строку результата state.saveState()
        assertEquals("120 130 140 \n140\ntrue\nfalse\n0\n", actual);

    }

    @Test
    public void testPrepareSaveList() {
        State state = new State();
        state.setStorageType(false);
        state.setLanguage(false);
        state.push(BigDecimal.valueOf(120));
        state.push(BigDecimal.valueOf(130));
        state.push(BigDecimal.valueOf(140));

        String actual = state.prepareForSave(); // Читаем строку результата state.saveState()
        assertEquals("120 130 140 \n140\nfalse\nfalse\n0\n", actual);

    }


    @Test
//    @Disabled
    public void testLoadStateArr() {
        State state = new State();

        String control = "120 130 140 \n140\ntrue\ntrue\n0\n";
        state.loadFromPrepared(control); // сохраняем состояние State

        BigDecimal memoryResult = state.memoryResult;
        String actual = memoryResult.toString();
        assertEquals("140", actual);

        assertTrue(state.isArray());

        assertTrue(state.isEnglish());

        BigDecimal test = state.pop();
        actual = test.toString();
        assertEquals("140", actual);
    }

    @Test
    public void testLoadStateList() {
        State state = new State();

        String control = "12 13 14 \n14\nfalse\ntrue\n0\n";
        state.loadFromPrepared(control); // сохраняем состояние State

        BigDecimal memoryResult = state.memoryResult;
        String actual = memoryResult.toString();
        assertEquals("14", actual);

        assertFalse(state.isArray());

        BigDecimal test = state.pop();
        actual = test + " ";
        test = state.pop();
        actual += test + " ";
        test = state.pop();
        actual += test + " ";
        assertEquals("14 13 12 ", actual);
    }

    @Test
//       @Disabled
    public void testMainInfo() {
        State state = new State();

        String actual = state.infoEng();
        assertEquals("Array data structure is used.\n" +
                "Content: Null. Array index: 0. Array length: 10", actual);

        state.push(BigDecimal.valueOf(12));
        state.push(BigDecimal.valueOf(13));

        actual = state.infoEng();
        assertEquals("Array data structure is used.\n" +
                "Content: 12 13 Array index: 2 Array length: 10", actual);
        state.clear();
        actual = state.infoEng();

        assertEquals("Array data structure is used.\n" +
                "Content: Null. Array index: 0. Array length: 10", actual);

        state.setStorageType(false); // LinkedList

        state.push(BigDecimal.valueOf(12));
        state.push(BigDecimal.valueOf(13));

        actual = state.infoEng();
        assertEquals("Linked List data structure is used.\n" +
                "Content: 12 13 ", actual);

        state.clear();
        actual = state.infoEng();
        assertEquals("Linked List data structure is used.\n" +
                "Content: Null", actual);

        state.setStorageType(true); // Array

        state.push(BigDecimal.valueOf(1));
        state.push(BigDecimal.valueOf(2));
        state.push(BigDecimal.valueOf(3));
        state.push(BigDecimal.valueOf(4));
        state.push(BigDecimal.valueOf(5));
        state.push(BigDecimal.valueOf(6));
        state.push(BigDecimal.valueOf(7));
        state.push(BigDecimal.valueOf(8));
        state.push(BigDecimal.valueOf(9));
        state.push(BigDecimal.valueOf(10));

        actual = state.infoEng();
        assertEquals("Array data structure is used.\n" +
                "Content: 1 2 3 4 5 6 7 8 9 10 Array index: 10 Array length: 20", actual);
    }

    /**
     * Check the function of deleting Array and then, List
     */
    @Test
    public void testMemoryClearFromArr() {
        State state = new State();
        state.push(BigDecimal.valueOf(12));
        state.push(BigDecimal.valueOf(13));

        BigDecimal testNum1 = state.peek();
        String test1 = testNum1.toString();
        assertEquals(test1, "13");

        state.clear();
        BigDecimal testNum2 = state.peek();
        String test2 = testNum2.toString();
        assertEquals(test2, "0");
    }

    @Test
    public void testMemoryClearFromList() {
        State state = new State();
        state.setStorageType(false);
        state.push(BigDecimal.valueOf(12));
        state.push(BigDecimal.valueOf(13));

        BigDecimal testNum = state.peek();
        String actual = testNum.toString();
        assertEquals("13", actual);

        state.clear();
        testNum = state.peek();
        actual = testNum.toString();
        assertEquals("0", actual);
    }

    /**
     * Check the Array/List data structure switching function
     */
    @Test
    public void testIsArrayStorage() {
        State state = new State();
        state.setStorageType(false); // Reverse the value
        assertFalse(state.isArray());
        state.setStorageType(true); // Reverse the value
        assertTrue(state.isArray());
    }

    @Test
    public void testIsArrayStorageWithData() {
        State state = new State();
        state.push(BigDecimal.valueOf(12));
        state.setStorageType(false); // Switch to List
        assertFalse(state.isArray());  // List

        BigDecimal test = state.peek();
        String actual = test.toString();
        assertEquals("12", actual); // Number from list = 12

        test = state.peek();
        actual = test.toString();
        assertEquals("12", actual); // in Array also 12

        state.setStorageType(true); // Switch to Array
        assertTrue(state.isArray()); // Array

        test = state.peek();
        actual = test.toString();
        assertEquals("12", actual); // get number from array (12)
    }

    @Test
    public void testUniversalConverter() throws Exception {
        State state = new State();
        BigDecimal inNumber = new BigDecimal(12);
        String result;

        state.setNumberSystem(OUT_BIN);
        result = state.universalConverter(inNumber);
        Assert.assertEquals("0b1100", result);

        state.setNumberSystem(OUT_OCT);
        result = state.universalConverter(inNumber);
        Assert.assertEquals("0o14", result);

        state.setNumberSystem(OUT_HEX);
        result = state.universalConverter(inNumber);
        Assert.assertEquals("0xC", result);

        state.setNumberSystem(OUT_ROM);
        result = state.universalConverter(inNumber);
        Assert.assertEquals("XII", result);
    }

}