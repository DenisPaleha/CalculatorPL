import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Scanner;

public class MainTest {

    /**
     * Проверяем существует ли документ с сохраненными данными
     */
    @Test
    public void testLoadState() {
        State state = new State();
        state.loadState();
        boolean result = false;
        File memoryTxt = new File("Memory.txt");
        if (memoryTxt.exists()) {
            result = true;
        }
        Assert.assertTrue(result);
    }

    /**
     * Проверяем работоспособность функции "Является ли строка Дробным числом"
     */
    @Test
    public void testIsDouble() {
        boolean decimalTrue = Main.checkDouble("9876.2");
        boolean decimalFalse = Main.checkDouble("fdn");
        Assert.assertTrue(decimalTrue);
        Assert.assertFalse(decimalFalse);
    }

    /**
     * Проверяем работоспособность функции "Является ли строка Римским числом"
     */
    @Test
    public void testIsRome() {
        boolean romeTrue = RomeNumerals.defineRomeContent("mmclxxxvii");
        boolean romeFalse = RomeNumerals.defineRomeContent("fdn");
        Assert.assertTrue(romeTrue);
        Assert.assertFalse(romeFalse);
    }

    /**
     * Проверяем работоспособность функции "Является ли строка Восьмеричным числом"
     */
    @Test
    public void testIsOctal() {
        boolean octalTrue = OctalNumbers.hasOctalNumber("0o465");
        boolean octalFalse = OctalNumbers.hasOctalNumber("fdn");
        Assert.assertTrue(octalTrue);
        Assert.assertFalse(octalFalse);
    }

    /**
     * Проверяем работоспособность функции "Является ли строка Шестнадцатеричным числом"
     */
    @Test
    public void testIsHex() {
        boolean hexTrue = HexNumbers.hasHexNumber("0xab5");
        boolean hexFalse = HexNumbers.hasHexNumber("fdn");
        Assert.assertTrue(hexTrue);
        Assert.assertFalse(hexFalse);
    }

    /**
     * Проверяем перевод десятичных чисел в восьмеричные, шестнадцатеричные, двоичные и римские
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
     * Проверяем вставку (в список или массив) римских, восьмеричных шестнадцатеричных и двоичных чисел и ех извлечение pop()
     */
    @Test
    public void testConvertToDecimal() /* throws Exception */ {
        String str1 = "MMCCXXII";
        BigDecimal resultRome = RomeNumerals.convertRomeToPush(str1);
        String result = resultRome.toString();
        Assert.assertEquals("2222.0", result);

        str1 = "CCXXLII";
        resultRome = RomeNumerals.convertRomeToPush(str1);
        result = resultRome.toString();
        Assert.assertEquals("0.0", result);

        String str2 = "0o675";
        BigDecimal resultOct = OctalNumbers.convertOctalToPush(str2);

        BigDecimal test2 = BigDecimal.valueOf(445.0);
        Assert.assertEquals(resultOct, test2);

        String str3 = "0xa75";
        BigDecimal resultHex = HexNumbers.hexNumbersToPush(str3);
        BigDecimal test3 = BigDecimal.valueOf(2677.0);
        Assert.assertEquals(resultHex, test3);

        String str4 = "0b10011100010";
        BigDecimal resultBinary = BinaryNumbers.binaryToPush(str4);
        BigDecimal test4 = BigDecimal.valueOf(1250.0);
        Assert.assertEquals(resultBinary, test4);
    }

    /**
     * Проверяем работу математических операций Этот тест не учитывает ключи HashMap!
     */
    @Test
    public void mainMath() {
        State state = new State();
        state.loadState();
        state.clear();
        String line = "1000 420 2 42 12 3 2 St + - * / % root"; // Сохраняем введенное в переменную line

        try {  // Проверка исключения для операции деления - просто заглушка

            Scanner lineScanner = new Scanner(line).useLocale(Locale.ENGLISH);
            while (lineScanner.hasNext()) { // сканируем строку incomingData
                String str = lineScanner.next(); // Разбиваем строку incomingData с введенными данными на части по порядку
                boolean hasDecimal = Main.checkDouble(str);

                if (hasDecimal) {    // Запихиваем строку в BigDecimal и пушим
                    BigDecimal num = new BigDecimal(str); // Присваиваем значение строки числу BigDecimal
                    state.push(num);

                } else if (str.equals(ConstantLibrary.PLUS)) {  // Сравниваем содержание переменных плюс, минус, умножить, разделить
                    BigDecimal value1 = state.pop();
                    BigDecimal value2 = state.pop();
                    BigDecimal value3 = MathFunctions.calculatePlus(value1, value2); // Функция вычисления результата сложения
                    state.push(value3);

                } else if (str.equals(ConstantLibrary.MINUS)) {
                    BigDecimal value1 = state.pop();
                    BigDecimal value2 = state.pop();
                    BigDecimal value3 = MathFunctions.calculateMinus(value1, value2); // Функция вычисления результата вычитания
                    state.push(value3);

                } else if (str.equals(ConstantLibrary.MULTIPLY)) {
                    BigDecimal value1 = state.pop();
                    BigDecimal value2 = state.pop();
                    BigDecimal value3 = MathFunctions.calculateMultiply(value1, value2); // Функция вычисления результата умножения
                    state.push(value3);

                } else if (str.equals(ConstantLibrary.DIVIDE)) {
                    BigDecimal value1 = state.pop();
                    BigDecimal value2 = state.pop();
                    BigDecimal value3 = MathFunctions.calculateDivide(value1, value2); // Функция вычисления результата деления
                    state.push(value3);


                } else if (str.equals(ConstantLibrary.SQUARE)) { // Функция извлечение корня (переменная)
                    BigDecimal value = state.pop();
                    value = MathFunctions.calculateSquare(value);
                    state.push(value);

                } else if (str.equals(ConstantLibrary.EXPONENT)) { // Функция возведение в степень (переменная)
                    BigDecimal value1 = state.pop();
                    BigDecimal value2 = state.pop();
                    BigDecimal value3 = MathFunctions.calculateExponent(value1, value2);
                    state.push(value3);

                } else if (str.equals(ConstantLibrary.PERCENT)) { // Функция подсчета процентов (переменные)
                    BigDecimal value1 = state.pop();
                    BigDecimal value2 = state.pop();
                    BigDecimal value3 = MathFunctions.calculatePercentages(value1, value2);
                    state.push(value3);
                }
            }

            state.memoryResult = state.peek();
            BigDecimal out = state.memoryResult.setScale(2, RoundingMode.HALF_UP); // Округляем результат до двух знаков после точки
            String actual = "" + out;
            Assert.assertEquals("10.00", actual);
        } catch (Exception e) {
            // Ничего не делаем
        }
    }

    /**
     * Проверяем работу математических операторов через вызов команд содержащихся в ХэшМар
     */
    @Test
    public void testMathInHashMap() {
        State state = new State();
        state.loadState(); //Загружаем сохраненные в txt документе данные

        HashMap hashmap = new HashMap(8); // Создаем таблицу
        hashmap.loadHashMap(); // Загружаем данные

        state.clear(); // Очищаем содержимое памяти (мало ли что там в доке было)

        String line = "1000 420 2 42 12 3 2 St плюс минус умножить разделить проценты корень";
        // Сохраняем введенное в переменную line

        try {  // Проверка исключения для операции деления - просто заглушка
            Scanner lineScanner = new Scanner(line).useLocale(Locale.ENGLISH);
            while (lineScanner.hasNext()) { // сканируем строку incomingData
                String str = lineScanner.next(); // Разбиваем строку incomingData с введенными данными на части по порядку
                str = str.toLowerCase(); // Переводим всё в нижний регистр

                boolean hasDecimal = Main.checkDouble(str);

                if (hasDecimal) {    // Запихиваем строку в BigDecimal и пушим
                    BigDecimal num = new BigDecimal(str); // Присваиваем значение строки числу BigDecimal
                    state.push(num);

                } else {
                    str = hashmap.get(str); // Присваиваем str значение по ключу Map
                    if (str.equals(ConstantLibrary.PLUS)) {  // Сравниваем содержание переменных плюс, минус, умножить, разделить
                        BigDecimal value1 = state.pop();
                        BigDecimal value2 = state.pop();
                        BigDecimal value3 = MathFunctions.calculatePlus(value1, value2); // Функция вычисления результата сложения
                        state.push(value3);

                    } else if (str.equals(ConstantLibrary.MINUS)) {
                        BigDecimal value1 = state.pop();
                        BigDecimal value2 = state.pop();
                        BigDecimal value3 = MathFunctions.calculateMinus(value1, value2); // Функция вычисления результата вычитания
                        state.push(value3);

                    } else if (str.equals(ConstantLibrary.MULTIPLY)) {
                        BigDecimal value1 = state.pop();
                        BigDecimal value2 = state.pop();
                        BigDecimal value3 = MathFunctions.calculateMultiply(value1, value2); // Функция вычисления результата умножения
                        state.push(value3);

                    } else if (str.equals(ConstantLibrary.DIVIDE)) {
                        BigDecimal value1 = state.pop();
                        BigDecimal value2 = state.pop();
                        BigDecimal value3 = MathFunctions.calculateDivide(value1, value2); // Функция вычисления результата деления
                        state.push(value3);

                    } else if (str.equals(ConstantLibrary.SQUARE)) { // Функция извлечение корня (переменная)
                        BigDecimal value = state.pop();
                        value = MathFunctions.calculateSquare(value);
                        state.push(value);

                    } else if (str.equals(ConstantLibrary.EXPONENT)) { // Функция возведение в степень (переменная)
                        BigDecimal value1 = state.pop();
                        BigDecimal value2 = state.pop();
                        BigDecimal value3 = MathFunctions.calculateExponent(value1, value2);
                        state.push(value3);

                    } else if (str.equals(ConstantLibrary.PERCENT)) { // Функция подсчета процентов (переменные)
                        BigDecimal value1 = state.pop();
                        BigDecimal value2 = state.pop();
                        BigDecimal value3 = MathFunctions.calculatePercentages(value1, value2);
                        state.push(value3);
                    }
                }
            }

            state.memoryResult = state.peek();
            BigDecimal out = state.memoryResult.setScale(2, RoundingMode.HALF_UP); // Округляем результат до двух знаков после точки
            String actual = "" + out;
            Assert.assertEquals("10.00", actual);
        } catch (Exception e) {
            // Ничего не делаем
        }
    }

    /**
     * Проверяем работу функцию сохранения
     */
    @Test
    //   @Disabled // Тест отключен
    public void testSaveArr() {
        // Для массива
        State state = new State();
        state.push(BigDecimal.valueOf(12));
        state.push(BigDecimal.valueOf(13));
        state.saveState();

        state.loadState();

        System.out.println(state.infoEng());

        BigDecimal test = state.pop();
        String result = test.toString();
        Assert.assertEquals("13", result);

        test = state.pop();
        result = test.toString();
        Assert.assertEquals("12", result);

        test = state.pop();
        result = test.toString();
        Assert.assertEquals("0", result);
    }

    @Test
    public void testSaveList() {
        State state = new State();
        state.setStorageType(false); // Переключаем на структуру данных LinkedList
//        Draft.switchMethod(state); // Переключаем на структуру данных LinkedList

        state.push(BigDecimal.valueOf(12));
        state.saveState(); // Сохраняем введенные данные.
        state.clear(); // Очищаем память State - иначе при загрузке она удвоится!
        state.loadState(); //Загружаем сохраненные в txt документе данные

        BigDecimal result = state.pop();
        String actual = result.toString();
        Assert.assertEquals("12", actual); // Тут все верно, извлекается введенное число.

        result = state.pop(); // Просто убедиться, что список пуст.
        actual = result.toString();
        Assert.assertEquals("0", actual); // Данные удваиваются при сохранении Списка!
    }


}