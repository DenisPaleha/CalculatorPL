import junit.framework.TestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class HashMapTest extends TestCase {


    /**
     * Проверка функции определяющей существуют ли данные в таблице
     */
    @Test
    public void testHasKey() {
        HashMap hashmap = new HashMap(8); // Создаем таблицу
        hashmap.put("key", "values");
        boolean x = hashmap.hasKey("key");
        assertTrue(x);
    }

    /**
     * Проверка записи и получения данных
     */
    @Test
    public void testPutAndGet() {
        HashMap hashmap = new HashMap(8); // Создаем таблицу
        hashmap.put("key", "value");
        String testValue;
        testValue = hashmap.get("key");
        assertEquals(testValue, "value");
    }

    /**
     * Проверка перезаписи данных если ключ существует
     */
    @Test
    public void testRewrite() {
        HashMap hashmap = new HashMap(8); // Создаем таблицу
        hashmap.put("key", "value");
        hashmap.put("key", "newValue");

        String testValue;
        testValue = hashmap.get("key");
        assertEquals(testValue, "newValue");
    }

    /**
     * Проверка текста исключения в случае попытки получения данных по несуществующему ключу
     */
    @Test
    public void testGetIfNoKey() {
        HashMap hashmap = new HashMap(8); // Создаем таблицу
        hashmap.put("key", "value");
        String testValue;
        testValue = hashmap.get("Wrong Key");
        assertNull(testValue); // функция get() возвращает null
    }

    /**
     * Проверка удаления ключа и данных
     */
    @Test
    public void testRemoveFromHashmap() {
        HashMap hashmap = new HashMap(8); // Создаем таблицу
        hashmap.put("key", "value");
//        String testMessage;
        try {
            hashmap.removeFromHashmap("key");
        } catch (Exception e) {
//            testMessage = (e.getMessage());
        }
        boolean x = hashmap.hasKey("key");
        assertFalse(x);
    }

    /**
     * Проверка текста исключения в случае попытки удаления несуществующего ключа
     */
    @Test
    public void testRemoveException() {
        HashMap hashmap = new HashMap(8); // Создаем таблицу
        hashmap.put("key", "value");
        String testMessage = null;

        try {
            hashmap.removeFromHashmap("Wrong Key");
        } catch (Exception e) {
            testMessage = (e.getMessage());
        }
        assertEquals("You can't delete what doesn't exist...", testMessage);
    }

    /**
     * Проверка динамического увеличения Массива
     */
    @Test
    public void testArrayLengthExtension() {
        HashMap hashmap = new HashMap(1); // Создаем таблицу с начальной глубиной 1
        hashmap.put("key", "value");
        assertEquals(2, hashmap.getCapacity());
    }

    /**
     * Проверяем корректно ли загрузились данные в HashMap
     */
    @Test
    public void testLoadHasMap() {
        HashMap hashmap = new HashMap(8); // Создаем таблицу
        hashmap.loadHashMap();
        String result = hashmap.get("oct"); // берем одну произвольную константу из списка
        assertEquals(result, ConstantLibrary.TO_OCTAL);
    }

    /**
     * Проверяем количество содержащихся в таблице пар Ключ - Значение
     */
    @Test
    public void testSizeOfDate() {
        HashMap hashmap = new HashMap(8); // Создаем таблицу
        hashmap.put("key1", "value1");
        hashmap.put("key2", "value2");
        hashmap.put("key3", "value3");
        int result = hashmap.size();
        assertEquals(3, result);
    }

    /** Тест выводит на экран содержимое таблицы hashmap */
    @Test
    @Disabled
    public void testPrintDataHashMap() {
        HashMap hashmap = new HashMap(8); // Создаем таблицу
        hashmap.loadHashMap();
        hashmap.printHashMapKeys(); //Распечатываем ключи
        hashmap.printHashMapValues(); // Распечатываем значения
    }
}
