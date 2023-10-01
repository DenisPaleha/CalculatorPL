import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

public class StateTest extends TestCase {

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
    /** Проверяем переключение типа данных на противоположный*/
    @Test
    public void testIsArrayChange(){
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
    public void testCopyStack(){
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
    public void testStateSaveArr(){
        State state = new State();
//        state.setStorageType(true); // Должен быть true по умолчанию! +++
        assertTrue(state.isArray());

        state.setLanguage(false);
        state.push(BigDecimal.valueOf(120));
        state.push(BigDecimal.valueOf(130));
        state.push(BigDecimal.valueOf(140));
        String actual = state.saveState(); // Читаем строку результата state.saveState()
        assertEquals("120 130 140  140 true false", actual);
    }

    @Test
    public void testStateSaveList(){
        State state = new State();
        state.setStorageType(false);
        state.setLanguage(false);
        state.push(BigDecimal.valueOf(120));
        state.push(BigDecimal.valueOf(130));
        state.push(BigDecimal.valueOf(140));
        String actual = state.saveState(); // Читаем строку результата state.saveState()
        assertEquals("120 130 140  140 false false", actual);
    }

    @Test
//    @Disabled
    public void testLoadStateArr() {
        State state = new State();
        state.push(BigDecimal.valueOf(120));
        state.push(BigDecimal.valueOf(130));
        state.push(BigDecimal.valueOf(140));
        state.saveState(); // сохраняем состояние

        state.pop();
        state.pop();
        state.pop();
        state.loadState(); // загружаем сохраненное состояние

        BigDecimal memoryResult = state.memoryResult;
        String actual = memoryResult.toString();
        assertEquals("140", actual);

        assertTrue(state.isArray());

        BigDecimal test = state.pop();
        actual = test.toString();
        assertEquals("140", actual);
    }

    @Test
    public void testLoadStateList() {
        State state = new State();
        state.setStorageType(false);
        state.push(BigDecimal.valueOf(12));
        state.push(BigDecimal.valueOf(13));
        state.push(BigDecimal.valueOf(14));
        state.saveState();
        state.pop();
        state.pop();
        state.pop();
        state.loadState();

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

    /**
     * Проверяем работу функции Info
     */
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
        state.clear(); // Очищаем массив
        actual = state.infoEng();

        assertEquals("Array data structure is used.\n" +
                "Content: Null. Array index: 0. Array length: 10", actual);

        state.setStorageType(false); // Переключаем структуру данных на Список

        state.push(BigDecimal.valueOf(12));
        state.push(BigDecimal.valueOf(13));

        actual = state.infoEng();
        assertEquals("Linked List data structure is used.\n" +
                "Content: 12 13 ", actual);

        state.clear();
        actual = state.infoEng();
        assertEquals("Linked List data structure is used.\n" +
                "Content: Null", actual);

//        state.isArray = true;
        state.setStorageType(true); // Переключаем структуру данных на Массив

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
     * Проверяем функцию удаления Массив и затем, Список.
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
     * Проверяем функцию переключения структуры данных Массив/Список
     */
    @Test
    public void testIsArrayStorage() {
        State state = new State();
        state.setStorageType(false); // Переключаем значение на противоположное
        assertFalse(state.isArray());
        state.setStorageType(true); // Опять переключаем на Массив
        assertTrue(state.isArray());
    }

    @Test
    public void testIsArrayStorageWithData() {
        State state = new State();
        state.push(BigDecimal.valueOf(12)); // Добавляем число в массив
        state.setStorageType(false); // Переключаем значение на Список
        assertFalse(state.isArray());  // Список

        BigDecimal test = state.peek();
        String actual = test.toString();
        assertEquals("12", actual); // число из списка = 12

        test = state.peek();
        actual = test.toString();
        assertEquals("12", actual); // Убедились, что в массиве те же 12

        state.setStorageType(true); // Переключаем значение на Массив
        assertTrue(state.isArray()); // Опять массив

        test = state.peek();
        actual = test.toString();
        assertEquals("12", actual); // Достаем число из массива (должно быть 12)
    }

    @Test
//    @Disabled
    public void testPrintPhrasesRUS(){
        State state = new State();
        state.setLanguage(false);
        int length = state.phrasesArrLength();
        for (int i = 0; i<length; i++){
            System.out.println(i + " : " + state.getPhrases(i));
        }
    }

}