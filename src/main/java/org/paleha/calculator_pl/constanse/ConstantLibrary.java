package org.paleha.calculator_pl.constanse;
public final class ConstantLibrary {
    // Constant library, for the Main class
    public static final String EXIT = "E";
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";
    public static final String MEMORY = "M";// Memory command
    public static final String CLEAR = "C";// Clear memory command (reset to zero)
    public static final String EXPONENT = "St";// Exponentiation command
    public static final String SQUARE = "root";// Square root extraction command
    public static final String PERCENT = "%";// Percentage calculation command
    public static final String HELP = "h";// Help command
    public static final String INFO = "info";// State information command
    public static final String SWITCH_METHOD = "sm";// Switch data structure command (array / list)
    public static final String SAVE = "S";// Save command
    public static final String TO_ROME = "toRom";// Conversion of the last result to Roman numerals command
    public static final String TO_OCTAL = "toOct";// Conversion to octal command
    public static final String TO_HEX = "toHex";// Conversion to hexadecimal command
    public static final String TO_BIN = "toBin"; // Conversion to binary command

    //----------------------------------------
    public static final String OUT_ROM = "outRom";// Output result in Roman numerals command
    public static final String OUT_OCT = "outOct";// Output result in octal command
    public static final String OUT_HEX = "outHex";// Output result in hexadecimal command
    public static final String OUT_BIN = "outBin"; // Output result in binary command
    public static final String OUT_DEC = "outDec"; // Output result in decimal command

    public static final String OUT_RUS = "outRus"; // Language switch command (Russian)
    public static final String OUT_ENG = "outEng"; // Language switch command (English)

    //    Библиотека констант, для класса OctalAndHexNumbers ===========================================================
    public static final String PREFIX_8 = "0o";
    public static final String PREFIX_16 = "0x";
    public static final String PREFIX_2 = "0b";

    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String FOUR = "4";
    public static final String FIVE = "5";
    public static final String SIX = "6";
    public static final String SEVEN = "7";
    public static final String EIGHT = "8";
    public static final String NINE = "9";
    public static final String TEN = "A";
    public static final String ELEVEN = "B";
    public static final String TWELVE = "C";
    public static final String THIRTEEN = "D";
    public static final String FOURTEEN = "E";
    public static final String FIFTEEN = "F";

    // ----------- mypackage.numbers.RomeNumerals -------------

    public static final String HEAD_MESSAGE_ROME_1 = "I = 1; V = 5; X = 10; L = 50; C = 100; D = 500; M = 1000.";
    public static final String HEAD_MESSAGE_ROME_2 = "Conversion is correct only for numbers from 0 to 3999.";
    public static final String ONE_ROME = "I";
    public static final String TWO_ROME = ONE_ROME + ONE_ROME;
    public static final String THREE_ROME = TWO_ROME + ONE_ROME;
    public static final String FIVE_ROME = "V";
    public static final String EIGHT_ROME = FIVE_ROME + THREE_ROME;
    public static final String SEVEN_ROME = FIVE_ROME + TWO_ROME;
    public static final String SIX_ROME = FIVE_ROME + ONE_ROME;
    public static final String FORE = ONE_ROME + FIVE_ROME;
    public static final String TEN_ROME = "X";
    // --------------------------------------------------
    public static final String THIRTY = TEN_ROME + TEN_ROME + TEN_ROME;
    public static final String TWENTY = TEN_ROME + TEN_ROME;
    public static final String NINE_ROME = ONE_ROME + TEN_ROME;
    public static final String FIFTY = "L";
    public static final String EIGHTY = FIFTY + TEN_ROME + TEN_ROME + TEN_ROME;
    public static final String SEVENTY = FIFTY + TEN_ROME + TEN_ROME;
    public static final String SIXTY = FIFTY + TEN_ROME;
    public static final String FORTY = TEN_ROME + FIFTY;
    public static final String ONE_HUNDRED = "C";
    public static final String NINETY = TEN_ROME + ONE_HUNDRED;
    //-------------------------------------------
    public static final String TWO_HUNDRED = ONE_HUNDRED + ONE_HUNDRED;
    public static final String THREE_HUNDRED = ONE_HUNDRED + ONE_HUNDRED + ONE_HUNDRED;
    public static final String FIVE_HUNDRED = "D";
    public static final String EIGHT_HUNDRED = FIVE_HUNDRED + ONE_HUNDRED + ONE_HUNDRED + ONE_HUNDRED;
    public static final String SEVEN_HUNDRED = FIVE_HUNDRED + ONE_HUNDRED + ONE_HUNDRED;
    public static final String SIX_HUNDRED = FIVE_HUNDRED + ONE_HUNDRED;
    public static final String FORE_HUNDRED = ONE_HUNDRED + FIVE_HUNDRED;
    public static final String THOUSAND = "M";
    public static final String NINE_HUNDRED = ONE_HUNDRED + THOUSAND;
    //-------------------------------------------
    public static final String TWO_THOUSAND = THOUSAND + THOUSAND;
    public static final String THREE_THOUSAND = THOUSAND + THOUSAND + THOUSAND;
    public static final int MAX_VALUE = 4000;

    //----------Help--------------
    public static final String HELP_TEXT_ENG = "| +++++++++++++++++++++++++++++  Help  ++++++++++++++++++++++++++++++++++++|\n" +
            "| The calculator supports operators: +, -, multiply '*', and divide '/'               |\n" +
            "| Language switching commands: rus, eng                                               |\n" +
            "| The 'M' command allows you to insert the last obtained result into the calculation, |\n" +
            "| which is automatically saved. 'C + Enter' - clear memory.                           |\n" +
            "| The 'St' command raises the penultimate number to the power of the last number. For |\n" +
            "| example, '2 5 st' (two to the fifth power) will give you 32.00                      |\n" +
            "| The 'root' command extracts the square root of the last number.                     |\n" +
            "| The '%' command extracts the percentage of the penultimate number equal to the last |\n" +
            "| number. For example, '120 10 %' will return 12.00                                   |\n" +
            "| The 'info' command displays the contents of the State class on the screen.          |\n" +
            "| The 'S' command saves the current data to a document.                               |\n" +
            "| The 'sm' command switches between the Array / List data structure method.           |\n" +
            "| The 'toRom' command converts the memory content into Roman numerals.                |\n" +
            "| The 'toBin' command converts the memory content into binary number.                 |\n" +
            "| The 'toOct' command converts the memory content into octal number.                  |\n" +
            "| The 'toHex' command converts the memory content into hexadecimal number.            |\n" +
            "| The calculator performs operations with Roman, binary, octal, and hexadecimal       |\n" +
            "| numbers.                                                                            |\n" +
            "| For octal numbers, the prefix '0o' is mandatory, for hexadecimal '0x',              |\n" +
            "| and for binary '0b'                                                                 |\n" +
            "| To automatically output all results in the desired numbering format, use the        |\n" +
            "| commands: outDec (decimal), outBin (binary), outOct (octal), outHex (hexadecimal),  |\n" +
            "| ourRome (Roman).                                                                    |\n" +
            "| Exit the program: 'E + Enter'.                                                      |\n" +
            "---------------------------------------------------------------------------------------";


    public static final String HELP_TEXT_RUS = "| ++++++++++++++++++++++++++++  Справка  ++++++++++++++++++++++++++++++++|\n" +
            "| Калькулятор поддерживает операторы: +, -, умножить '*' и разделить '/'             |\n" +
            "| Команды переключения языка: rus, eng                                               |\n" +
            "| Команда 'M' позволяет вставить в вычисление последний полученный результат,        |\n" +
            "| который сохраняется автоматически. 'C + Enter' - очистить память.                  |\n" +
            "| Команда 'St ' - возводит предпоследнее число в степень соответствующую последнему  |\n" +
            "| числу. Например, '2 5 st' (два в пятой степени) даст 32.00                         |\n" +
            "| Команда 'root' - извлечет квадратный корень из последнего числа.                   |\n" +
            "| Команда '%' - извлечет из предпоследнего числа проценты, количество которых равно  |\n" +
            "| последнему числу. Например, '120 10 %' вернет 12.00                                |\n" +
            "| Команда 'info' - выведет на экран содержимое класса State.                         |\n" +
            "| Команда 'S' - Сохраняет текущие данные в документ.                                 |\n" +
            "| Команда 'sm' - переключает метод структуры данных Массив / Строка                  |\n" +
            "| Команда 'toRom' - конвертирует содержимое памяти в римские числа.                  |\n" +
            "| Команда 'toBin' - конвертирует содержимое памяти в двоичное число                  |\n" +
            "| Команда 'toOct' - конвертирует содержимое памяти в восьмеричное число              |\n" +
            "| Команда 'toHex' - конвертирует содержимое памяти в шестнадцатеричное число         |\n" +
            "| Калькулятор выполняет операции с римскими, двоичными, восьмеричными и              |\n" +
            "| шестнадцатеричными числами.                                                        |\n" +
            "| Для восьмеричных чисел обязателен префикс '0o', шестнадцатеричных '0x',            |\n" +
            "| двоичных '0b'                                                                      |\n" +
            "| Для вывода всех результатов в нужном формате исчисления автоматически, используйте |\n" +
            "| команды: outDec (десятеричные), outBin (двоичные), outOct (восьмеричные),          |\n" +
            "| outHex (шестнадцатеричные), ourRome (римские).                                     |\n" +
            "| Выход из программы: 'E + Enter'.                                                   |\n" +
            "--------------------------------------------------------------------------------------";

    public static final String PASSWORD_TEXT_HALLO_ENG = """
    The program stores your logs in encrypted form,\s
    you have to set a password to read them.""";

    public static final String PASSWORD_TEXT_RULES_ENG = """
    Password can contain only Latin letters and/or numbers, e.g. 'password123'.\s
    The minimum password length is 5 characters. Set a new password...""";

}

