
import stack.StackList;
import stack.AbstractStack;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class StackListTest extends TestCase {

    @Test
    public void testIsEmpty() {
        StackList stList = new StackList();
        boolean result = stList.isEmpty();
        assertTrue(result);
    }

    @Test
    public void testPush() {
        StackList stList = new StackList();
        stList.push(BigDecimal.valueOf(12));
        BigDecimal result = stList.list.get(0);
        String test = result.toString();
        assertEquals("12", test);

        int size = stList.list.listSize(); // Проверяем длину листа
        assertEquals(1, size);
    }

    @Test
    public void testPeek() {
        StackList stList = new StackList();
        stList.push(BigDecimal.valueOf(12));
        BigDecimal result = stList.peek();
        String test = result.toString();
        assertEquals("12", test);

        int size = stList.list.listSize(); // Проверяем длину листа
        assertEquals(1, size);
    }

    @Test
    public void testPop() {
        StackList stList = new StackList();
        stList.push(BigDecimal.valueOf(12));

        BigDecimal result = stList.pop();
        String test = result.toString();
        assertEquals("12", test);

        result = stList.pop();  // Пытаемся вытащить несуществующие данные из пустого списка
        test = result.toString();
        assertEquals("0", test);

        int size = stList.list.listSize(); // Проверяем длину списка
        assertEquals(0, size);
    }

    @Test
    public void testCopyStackEmpty() {
        StackList stList = new StackList();
        String result = stList.copy();
        assertEquals("0 ", result);
    }

    /**
     * Функция проверяет строку с содержимым стека
     */
    @Test
//    @Disabled
    public void testCopyStackData() {
        StackList stList = new StackList();
        stList.push(BigDecimal.valueOf(12));
        stList.push(BigDecimal.valueOf(13));
        stList.push(BigDecimal.valueOf(14));

        String result = stList.copy();
        assertEquals("12 13 14 ", result);
    }

    /**
     * Функция проверяет строку с содержимым info
     */
    @Test
    public void testInfo() {
        StackList stList = new StackList();
        String result = stList.infoEng();
        String expected = "Linked List data structure is used.\n" +
                "Content: Null";
        assertEquals(expected, result);

        stList.push(BigDecimal.valueOf(12));
        stList.push(BigDecimal.valueOf(13));
        stList.push(BigDecimal.valueOf(14));
        result = stList.infoEng();
        System.out.println(result);
        expected = "Linked List data structure is used.\n" +
                "Content: 12 13 14 ";
        assertEquals(expected, result);
    }

    /**
     * Проверяем функцию абстрактного класса write - запись строки в стек
     */
    @Test
    public void testWrite() {
        AbstractStack stack = new StackList() {
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

