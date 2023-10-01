import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class StackArrTest extends TestCase {

    @Test
    public void testIsEmptyStack() {
        StackArr stackArr = new StackArr();
        boolean result = stackArr.isEmpty();
        assertTrue(result);
    }

    @Test
    public void testPush() {
        StackArr stackArr = new StackArr();
        stackArr.push(BigDecimal.valueOf(12));
        BigDecimal result = stackArr.nums[0];
        String test = result.toString();
        assertEquals("12", test);

        int test2 = stackArr.freeIndex;
        assertEquals(1, test2);

        stackArr.push(BigDecimal.valueOf(12));
        stackArr.push(BigDecimal.valueOf(12));
        stackArr.push(BigDecimal.valueOf(12));
        stackArr.push(BigDecimal.valueOf(12));
        stackArr.push(BigDecimal.valueOf(12));
        stackArr.push(BigDecimal.valueOf(12));
        stackArr.push(BigDecimal.valueOf(12));
        stackArr.push(BigDecimal.valueOf(12));
        stackArr.push(BigDecimal.valueOf(12));

        test2 = stackArr.nums.length;
        assertEquals(20, test2);
    }

    @Test
    public void testPeek() {
        StackArr stackArr = new StackArr();
        BigDecimal result = stackArr.peek(); // Проверяем функцию с пустым стеком
        String test = result.toString();
        assertEquals("0", test);

        stackArr.push(BigDecimal.valueOf(12));
        result = stackArr.peek();
        test = result.toString();
        assertEquals("12", test);

        result = stackArr.nums[stackArr.freeIndex - 1]; // Проверяем что ничего не пропало
        test = result.toString();
        assertEquals("12", test);
    }

    @Test
    public void testPop() {
        StackArr stackArr = new StackArr();
        stackArr.push(BigDecimal.valueOf(12));
        BigDecimal result = stackArr.pop();
        String test = result.toString();
        assertEquals("12", test);

        result = stackArr.pop();  // Попытаемся извлечь значение из пустого стека
        test = result.toString();
        assertEquals("0", test);

        boolean empty = stackArr.isEmpty(); // Убеждаемся что стек пуст
        assertTrue(empty);
    }

    /**
     * Функция проверяет строку с содержимым стека
     */
    @Test
    public void testCopyStackEmpty() {
        StackArr stackArr = new StackArr();
        String result = stackArr.copy();
        assertEquals("0 ", result);
    }

    @Test
    public void testCopyStackData() {
        StackArr stackArr = new StackArr();
        stackArr.push(BigDecimal.valueOf(12));
        stackArr.push(BigDecimal.valueOf(13));
        stackArr.push(BigDecimal.valueOf(14));

        String result = stackArr.copy();
        assertEquals("12 13 14 ", result);
    }

    /**
     * Функция проверяет строку с содержимым info
     */
    @Test
//    @Disabled
    public void testInfo() {
        StackArr stackArr = new StackArr();
        String result = stackArr.infoEng();
        String expected = "Array data structure is used.\n" +
                "Content: Null. Array index: 0. Array length: 10";
        assertEquals(expected, result);

        stackArr.push(BigDecimal.valueOf(12));
        stackArr.push(BigDecimal.valueOf(13));
        stackArr.push(BigDecimal.valueOf(14));
        result = stackArr.infoEng();
        System.out.println(result);
        expected = "Array data structure is used.\n" +
                "Content: 12 13 14 Array index: 3 Array length: 10";
        assertEquals(expected, result);
    }

    /**
     * Проверяем функцию абстрактного класса write - запись строки в стек
     */
    @Test
    public void testWrite() {
        AbstractStack stack = new StackArr() {
        };
        String str = "12 13 14 ";
        stack.write(str);
        BigDecimal tmp;
        System.out.println("Функция записи строки");
        while (!stack.isEmpty()) {
            tmp = stack.pop();
            System.out.println(tmp);
        }
    }

}