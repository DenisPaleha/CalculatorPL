import static org.paleha.calculator_pl.constanse.ConstantLibrary.*;

import org.paleha.calculator_pl.constanse.HashMap;
import org.paleha.calculator_pl.math.MathFunctions;
import org.paleha.calculator_pl.numbers.BinaryNumbers;
import org.paleha.calculator_pl.numbers.HexNumbers;
import org.paleha.calculator_pl.numbers.OctalNumbers;
import org.paleha.calculator_pl.numbers.RomeNumerals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Scanner;

public class MainTest {


//    /**
//     * Теперь эта функция в Core
//     * Проверяем работоспособность функции "Является ли строка Дробным числом"
//     */
//    @Test
//    @Disabled
//    public void testIsDouble() {
//        boolean decimalTrue = Main.checkDouble("9876.2");
//        boolean decimalFalse = Main.checkDouble("fdn");
//        Assert.assertTrue(decimalTrue);
//        Assert.assertFalse(decimalFalse);
//    }

    /**
     * Проверяем работоспособность функции "Является ли строка Римским числом"
     */
    @Test
    public void testIsRome() {
        boolean romeTrue = RomeNumerals.isRome("mmclxxxvii");
        boolean romeFalse = RomeNumerals.isRome("fdn");
        Assert.assertTrue(romeTrue);
        Assert.assertFalse(romeFalse);
    }

    /**
     * Проверяем работоспособность функции "Является ли строка Восьмеричным числом"
     */
    @Test
    public void testIsOctal() {
        boolean octalTrue = OctalNumbers.isOctalNumber("0o465");
        boolean octalFalse = OctalNumbers.isOctalNumber("fdn");
        Assert.assertTrue(octalTrue);
        Assert.assertFalse(octalFalse);
    }

    /**
     * Проверяем работоспособность функции "Является ли строка Шестнадцатеричным числом"
     */
    @Test
    public void testIsHex() {
        boolean hexTrue = HexNumbers.isHexNumber("0xab5");
        boolean hexFalse = HexNumbers.isHexNumber("fdn");
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
        try {
            BigDecimal resultRome = RomeNumerals.convertRomeToPush(str1);
            String result = resultRome.toString();
            Assert.assertEquals("2222.0", result);
        } catch (Exception wrongNumber) {
            System.out.println(wrongNumber.getMessage());
        }

        str1 = "CCXXLII";
        try {
            BigDecimal resultRome = RomeNumerals.convertRomeToPush(str1);
        } catch (Exception wrongNumber) {
            String result = wrongNumber.getMessage();
            Assert.assertEquals("Write error: Two identical Roman numerals of lesser value cannot precede a larger numeral.", result);
        }

        String str2 = "0o675";
        try {
            BigDecimal resultOct = OctalNumbers.convertOctalToPush(str2);
            BigDecimal test2 = BigDecimal.valueOf(445.0);
            Assert.assertEquals(resultOct, test2);
        } catch (Exception wrongNumber) {
            System.out.println(wrongNumber.getMessage());
        }

        String str3 = "0xa75";
        try {
            BigDecimal resultHex = HexNumbers.hexNumbersToPush(str3);
            BigDecimal test3 = BigDecimal.valueOf(2677.0);
            Assert.assertEquals(resultHex, test3);
        } catch (Exception wrongNumber) {
            System.out.println(wrongNumber.getMessage());
        }

        try {
            String str4 = "0b10011100010";
            BigDecimal resultBinary = BinaryNumbers.binaryToPush(str4);
            BigDecimal test4 = BigDecimal.valueOf(1250.0);
            Assert.assertEquals(resultBinary, test4);
        } catch (Exception wrongNumber) {
            System.out.println(wrongNumber.getMessage());
        }
    }

    /**
     * Проверяем работу математических операций Этот тест не учитывает ключи constants.HashMap!
     * Main.checkDouble(str); - Теперь эта функция в классе Core
     */
    @Test
    @Disabled
    public void mainMath() {
        State state = new State();
        String line = "1000 420 2 42 12 3 2 St + - * / % root"; // Сохраняем введенное в переменную line

        try {  // Проверка исключения для операции деления - просто заглушка

            Scanner lineScanner = new Scanner(line).useLocale(Locale.ENGLISH);
            while (lineScanner.hasNext()) { // сканируем строку incomingData
                String str = lineScanner.next(); // Разбиваем строку incomingData с введенными данными на части по порядку
                boolean hasDecimal = true;

                if (hasDecimal) {    // Запихиваем строку в BigDecimal и пушим
                    BigDecimal num = new BigDecimal(str); // Присваиваем значение строки числу BigDecimal
                    state.push(num);

                } else if (str.equals(PLUS)) {  // Сравниваем содержание переменных плюс, минус, умножить, разделить
                    BigDecimal value1 = state.pop();
                    BigDecimal value2 = state.pop();
                    BigDecimal value3 = MathFunctions.calculatePlus(value1, value2); // Функция вычисления результата сложения
                    state.push(value3);

                } else if (str.equals(MINUS)) {
                    BigDecimal value1 = state.pop();
                    BigDecimal value2 = state.pop();
                    BigDecimal value3 = MathFunctions.calculateMinus(value1, value2); // Функция вычисления результата вычитания
                    state.push(value3);

                } else if (str.equals(MULTIPLY)) {
                    BigDecimal value1 = state.pop();
                    BigDecimal value2 = state.pop();
                    BigDecimal value3 = MathFunctions.calculateMultiply(value1, value2); // Функция вычисления результата умножения
                    state.push(value3);

                } else if (str.equals(DIVIDE)) {
                    BigDecimal value1 = state.pop();
                    BigDecimal value2 = state.pop();
                    BigDecimal value3 = MathFunctions.calculateDivide(value1, value2); // Функция вычисления результата деления
                    state.push(value3);


                } else if (str.equals(SQUARE)) { // Функция извлечение корня (переменная)
                    BigDecimal value = state.pop();
                    value = MathFunctions.calculateSquare(value);
                    state.push(value);

                } else if (str.equals(EXPONENT)) { // Функция возведение в степень (переменная)
                    BigDecimal value1 = state.pop();
                    BigDecimal value2 = state.pop();
                    BigDecimal value3 = MathFunctions.calculateExponent(value1, value2);
                    state.push(value3);

                } else if (str.equals(PERCENT)) { // Функция подсчета процентов (переменные)
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
    @Disabled
    public void testMathInHashMap() {
        State state = new State();

        HashMap hashmap = new HashMap(8); // Создаем таблицу
        hashmap.loadMainHashMap(); // Загружаем данные

        String line = "1000 420 2 42 12 3 2 St плюс минус умножить разделить проценты корень";
        // Сохраняем введенное в переменную line

        try {  // Проверка исключения для операции деления - просто заглушка
            Scanner lineScanner = new Scanner(line).useLocale(Locale.ENGLISH);
            while (lineScanner.hasNext()) { // сканируем строку incomingData
                String str = lineScanner.next(); // Разбиваем строку incomingData с введенными данными на части по порядку
                str = str.toLowerCase(); // Переводим всё в нижний регистр

                boolean hasDecimal = true;

                if (hasDecimal) {    // Запихиваем строку в BigDecimal и пушим
                    BigDecimal num = new BigDecimal(str); // Присваиваем значение строки числу BigDecimal
                    state.push(num);

                } else {
                    str = hashmap.get(str); // Присваиваем str значение по ключу Map
                    if (str.equals(PLUS)) {  // Сравниваем содержание переменных плюс, минус, умножить, разделить
                        BigDecimal value1 = state.pop();
                        BigDecimal value2 = state.pop();
                        BigDecimal value3 = MathFunctions.calculatePlus(value1, value2); // Функция вычисления результата сложения
                        state.push(value3);

                    } else if (str.equals(MINUS)) {
                        BigDecimal value1 = state.pop();
                        BigDecimal value2 = state.pop();
                        BigDecimal value3 = MathFunctions.calculateMinus(value1, value2); // Функция вычисления результата вычитания
                        state.push(value3);

                    } else if (str.equals(MULTIPLY)) {
                        BigDecimal value1 = state.pop();
                        BigDecimal value2 = state.pop();
                        BigDecimal value3 = MathFunctions.calculateMultiply(value1, value2); // Функция вычисления результата умножения
                        state.push(value3);

                    } else if (str.equals(DIVIDE)) {
                        BigDecimal value1 = state.pop();
                        BigDecimal value2 = state.pop();
                        BigDecimal value3 = MathFunctions.calculateDivide(value1, value2); // Функция вычисления результата деления
                        state.push(value3);

                    } else if (str.equals(SQUARE)) { // Функция извлечение корня (переменная)
                        BigDecimal value = state.pop();
                        value = MathFunctions.calculateSquare(value);
                        state.push(value);

                    } else if (str.equals(EXPONENT)) { // Функция возведение в степень (переменная)
                        BigDecimal value1 = state.pop();
                        BigDecimal value2 = state.pop();
                        BigDecimal value3 = MathFunctions.calculateExponent(value1, value2);
                        state.push(value3);

                    } else if (str.equals(PERCENT)) { // Функция подсчета процентов (переменные)
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

        String control = state.prepareSave();
        Assert.assertEquals("12 13 \n13\ntrue\ntrue\n0\n", control);
    }

    @Test
    public void testSaveList() {
        State state = new State();
        state.setStorageType(false); //  LinkedList
        state.push(BigDecimal.valueOf(12));

        String control = state.prepareSave(); // Save state to string.
        Assert.assertEquals("12 \n12\nfalse\ntrue\n0\n", control);

    }


}