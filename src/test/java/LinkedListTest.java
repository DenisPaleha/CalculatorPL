import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

public class LinkedListTest extends TestCase {

    @Test
    public void testIsEmpty() {
        LinkedList list1 = new LinkedList();
        boolean result = false;
        if (list1.isEmpty()) { // Если в списке есть данные выдаст ошибку, если нет -- true.
            result = true;
        }
        assertTrue(result);
    }

    @Test
    public void testAdd() {
        LinkedList list1 = new LinkedList();
        BigDecimal one = new BigDecimal(25);
        list1.add(one);
        BigDecimal lastItem = list1.lastItemInList();
        assertEquals(lastItem, one);
    }


    @Test
    public void testGet() { // Получаем данные по индексу
        LinkedList list1 = new LinkedList();
        BigDecimal one = new BigDecimal(10);
        BigDecimal two = new BigDecimal(20);
        list1.add(one);
        list1.add(two);
        BigDecimal result = list1.get(1);
        assertEquals(result, two);
    }

    @Test
    public void testPrintAll() {
        LinkedList list1 = new LinkedList();
        BigDecimal one = new BigDecimal(25);
        BigDecimal two = new BigDecimal(50);
        BigDecimal three = new BigDecimal(75);
        list1.add(one);
        list1.add(two);
        list1.add(three);
        for (int i = 0; i < list1.listSize(); /* O(N) */ i++) {
            BigDecimal value = list1.get(i) /* O(N) */;
            if (i == 0) {
                assertEquals(one, value);
            } else if (i == 1) {
                assertEquals(two, value);
            } else {
                assertEquals(three, value);
            }
        }
        String result = list1.printAll();
        assertEquals(result, "25 50 75 ");
    }

    @Test
    public void testIterator() {  // Задание. Переписать итератор на более низком уровне Листа
        LinkedList list1 = new LinkedList();
        BigDecimal one = new BigDecimal(25);
        BigDecimal two = new BigDecimal(50);
        BigDecimal three = new BigDecimal(75);

        Iterator emptyIterator = list1.iterator(); // Создаем экземпляр итератора со значениями пустого пока листа1
        assertFalse(emptyIterator.hasNext()); // Если дубликат тоже пуст - условие тесто выполнено.

        list1.add(one);  // Добавляем в существующий лист1 значения.
        list1.add(two);
        list1.add(three);

        /* */
        Iterator iterator = list1.iterator();  // Создаем новый экземпляр итератора с новыми данными листа1
        int size = 0; // Задаем индекс начального узла
        while (iterator.hasNext()) {  // Цикл выполняется если в списке есть следующее значение. Т.е. длинна списка меньше текущего индекса.
            BigDecimal value = iterator.next(); // Возвращает первое значение и увеличивает индекс на 1.
            System.out.println(value);
            size = size + 1; // каждая итерация увеличивает счетчик длинны списка.
        }
        assertEquals(3, size); // Условие выполняется, если длинна списка соответствует реальному количеству узлов (3).
    }

    @Test
    public void testDeleteFirst() {        // Не работает!!
        LinkedList list1 = new LinkedList();
        BigDecimal one = new BigDecimal(10);
        BigDecimal two = new BigDecimal(20);
        list1.add(one);
        list1.add(two);
        list1.deleteFirst();
        BigDecimal result = list1.get(0);
        assertEquals(result, two);

        list1.deleteFirst();
        result = list1.get(0);
        assertNull(result);
    }

    @Test
    public void testDeleteData() {
        LinkedList list1 = new LinkedList();
        BigDecimal one = new BigDecimal(10);
        BigDecimal two = new BigDecimal(20);
        list1.add(one);
        list1.add(two);
        list1.deleteData(one);
        BigDecimal result = list1.get(0);
        assertEquals(result, two); // Проверяем удаление первого узла из двух

        LinkedList list2 = new LinkedList();
        one = new BigDecimal(10);
        two = new BigDecimal(20);
        BigDecimal three = new BigDecimal(30);

        list2.add(one);
        list2.add(two);
        list2.add(three);
        list2.deleteData(two);
        result = list2.get(1);
        assertEquals(result, three); // Проверяем удаление второго узла из двух

        result = list2.get(0);
        assertEquals(result, one);

        LinkedList list3 = new LinkedList();
        list3.add(one);
        list3.deleteData(one);
        boolean result1 = false;
        if (list3.isEmpty()) { // Если в списке есть данные выдаст ошибку, если нет -- true.
            result1 = true;
        }
        assertTrue(result1);

        LinkedList list4 = new LinkedList();
        list4.add(one);
        list4.deleteData(two); // Пытаемся удалить несуществующее значение
        // Ничего не выдает, просто прокручивает цикл ОК

        LinkedList list5 = new LinkedList();
        list5.deleteData(two); // Пытаемся удалить значение из пустого списка
        // Выдает строку "Список пуст, из него нечего удалять!". ОК
    }

    @Test
    public void testCopyList() {
        LinkedList list1 = new LinkedList();
        BigDecimal one = new BigDecimal(10);
        BigDecimal two = new BigDecimal(20);
        list1.add(one);
        list1.add(two);

        LinkedList copy = list1.copyList();

        BigDecimal result1 = copy.get(0);
        BigDecimal result2 = copy.get(1);

        assertEquals(result1, one);
        assertEquals(result2, two);
    }

    @Test
    public void testListSize() {
        LinkedList list1 = new LinkedList();
        BigDecimal one = new BigDecimal(10);
        BigDecimal two = new BigDecimal(20);
        BigDecimal three = new BigDecimal(30);
        list1.add(one);
        list1.add(two);
        list1.add(three);
        int result = list1.listSize();
        assertEquals(result, 3);

        LinkedList copy = list1.copyList(); // Копируем предыдущий список и считаем количество узлов
        result = copy.listSize();
        assertEquals(result, 3);
    }

    @Test
    public void testLastItemInList() {
        LinkedList list1 = new LinkedList();
        BigDecimal one = new BigDecimal(10);
        BigDecimal two = new BigDecimal(20);
        BigDecimal three = new BigDecimal(30);
        list1.add(one);
        list1.add(two);
        list1.add(three);
        BigDecimal lastItem = list1.lastItemInList();
        assertEquals(lastItem, three);

    }

    @Test
    public void testDeleteLastFromEmptyList() {
        LinkedList list = new LinkedList();
        list.deleteLast();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testDeleteLastFromListWithOneNode() {
        LinkedList list = new LinkedList();
        list.add(BigDecimal.ONE);
        assertFalse(list.isEmpty());
        assertEquals(1, list.listSize());
        list.deleteLast();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testDeleteLastFromListWithManyNodes() {
        int size = 100;

        LinkedList list = new LinkedList();
        for (int i = 0; i < size; i++) {
            list.add(BigDecimal.ONE);
        }
        assertFalse(list.isEmpty());
        assertEquals(size, list.listSize());
        list.deleteLast();
        assertEquals(size - 1, list.listSize());
    }
}