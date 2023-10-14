import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in).useLocale(Locale.ENGLISH);

        State state = new State();
        state.loadState(scanner);

        Password password = new Password();
        password.checkPassword(state, scanner);

        Logger logger = new Logger();
        logger.clearLogg();

        HashMap hashmapMain = new HashMap(8);
        hashmapMain.loadMainHashMap();

        System.out.println(state.getPhrase("hello_massage_one")); // Main info
        System.out.println(state.getPhrase("hello_massage_two")); // Info on calling help
        System.out.println(String.format(state.getPhrase("loaded_memory"), state.memoryResult)); // Reading the saved memory string

        while (scanner.hasNextLine()) { // Main program loop with user input
            boolean theEnd = false;
            String line = scanner.nextLine(); // Save user input to the variable line

            logger.logInput(line); // Copy all input data to the logger

            String output; // Declare output string

            State copy = state.copyState();  // Copy the State class instance to insert in case of expression reading error

            Core core = new Core(state);                                                                //+++

            Scanner lineScanner = new Scanner(line).useLocale(Locale.ENGLISH); // Scan the incomingData variable
            // Insert possible values supported by the calculator
            try {
                while (lineScanner.hasNext()) { // Scan the incomingData string
                    String str = lineScanner.next(); // Split the incomingData string with input data into parts in order
                    str = str.toLowerCase(); // Convert everything to lowercase

                    boolean coreNotUsed; // Method core.calculator(str) trigger switch
                    coreNotUsed = core.calculator(str);

                    boolean keyHashMap = hashmapMain.hasKey(str); // Check if str is a HashMap key
                    if (keyHashMap) { // If the string matches an existing key
                        str = hashmapMain.get(str); // Assign str a value by HashMap key

                        if (str.equals(ConstantLibrary.HELP)) {
                            if (state.isEnglish()) {
                                System.out.println(ConstantLibrary.HELP_TEXT_ENG);
                            } else {
                                System.out.println(ConstantLibrary.HELP_TEXT_RUS);
                            }

                        } else if (str.equals(ConstantLibrary.CLEAR)) { // Memory clearing function, "c" command
                            state.clear(); // Clear memory
                            output = state.getPhrase("memory_cleared");
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.INFO)) { // Screen output function, "info" command
                            String info;
                            if (state.isEnglish()) {
                                info = state.infoEng();
                            } else {
                                info = state.infoRus();
                            }
                            logger.logOutput(info);
                            System.out.println(info);

                        } else if (str.equals(ConstantLibrary.SWITCH_METHOD)) {  // Data structure switching function Array/List
                            boolean storageType = state.isArray();
                            storageType = !storageType; // Change the current boolean value to the opposite
                            state.setStorageType(storageType); // Change the stack data structure to the opposite and transfer the content
                            if (storageType) {
                                output = state.getPhrase("now_calculator_uses_Array");
                            } else {
                                output = state.getPhrase("now_calculator_uses_List");
                            }
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.SAVE)) {  //  "S" command Saves all data to MemoryTwo.txt
                            output = state.getPhrase("data_saved");
                            logger.logOutput(output);
                            state.saveState();

                        } else if (str.equals(ConstantLibrary.TO_ROME)) { // "ToRome" function, converts memory to Roman numeral
                            System.out.println(ConstantLibrary.HEAD_MESSAGE_ROME_1);
                            double mem = state.memoryResult.doubleValue(); // Convert BigDecimal to double
                            try {
                                String result = RomeNumerals.convertDecimalToRome(mem);
                                output = String.format(state.getPhrase("roman_number_equal"), result);
                            } catch (Exception e) {
                                output = e.getMessage();
                            }
                            logger.logOutput(output);
                            System.out.println(output); // Roman numeral

                        } else if (str.equals(ConstantLibrary.TO_OCTAL)) {
                            int num = state.memoryResult.intValue(); // Convert BigDecimal to int with rounding to the nearest integer
                            String result = OctalNumbers.convertDecimalToOctal(num); // Conversion
                            output = String.format(state.getPhrase("octal_number_equal"), result);
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.TO_HEX)) {
                            int num = state.memoryResult.intValue(); // Convert BigDecimal to int with rounding to the nearest integer
                            String result = HexNumbers.convertDecimalToHex(num); // Conversion
                            output = String.format(state.getPhrase("hexadecimal_number_equal"), result);
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.TO_BIN)) {
                            int num = state.memoryResult.intValue(); // Convert BigDecimal to int with rounding to the nearest integer
                            String result = BinaryNumbers.convertDecimalToBinary(num); // Conversion
                            output = String.format(state.getPhrase("binary_number_equal"), result);
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.OUT_DEC)) { // Automatically converts all results to
                            state.setNumberSystem(ConstantLibrary.OUT_DEC);
                            output = state.getPhrase("decimal_enabled"); // Decimal number system enabled
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.OUT_BIN)) {
                            state.setNumberSystem(ConstantLibrary.OUT_BIN);
                            output = state.getPhrase("binary_enabled"); // Binary number system enabled
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.OUT_OCT)) {
                            state.setNumberSystem(ConstantLibrary.OUT_OCT);
                            output = state.getPhrase("octal_enabled"); // Octal number system enabled
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.OUT_HEX)) {
                            state.setNumberSystem(ConstantLibrary.OUT_HEX);
                            output = state.getPhrase("hexadecimal_enabled"); // Hexadecimal number system enabled
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.OUT_ROM)) {
                            state.setNumberSystem(ConstantLibrary.OUT_ROM);
                            output = state.getPhrase("roman_enabled"); // Roman number system enabled
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.OUT_RUS)) { // Language switch
                            state.setLanguage(false);
                            output = "Язык: Русский";
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.OUT_ENG)) { // Language switch
                            state.setLanguage(true);
                            output = "Language: English";
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.EXIT)) { // Exit function "E"
                            theEnd = true;
                        }

                    } else {
                        if (coreNotUsed) {
                            output = String.format(state.getPhrase("unknown_value"), str);
                            logger.logOutput(output);
                            System.out.println(output);
                        }
                    }
                }

                if (theEnd) { // If theEnd boolean value is true, exit the program.

                    state.saveState();
                    output = state.getPhrase("exiting");
                    logger.logOutput(output);
                    System.out.println(output);
                    lineScanner.close();                                                        // +++?
                    scanner.close();
                    break;
                } else {
                    state.memoryResult = state.peek();
                }
                BigDecimal out = state.memoryResult.setScale(2, RoundingMode.HALF_UP); // Round the result to two decimal places for display
                String result;
                try {
                    result = state.universalConverter(out);
                } catch (Exception e) {
                    result = out.toString();
                }
                output = String.format(state.getPhrase("result"), result);
                logger.logOutput(output); // Result
                System.out.println(output); // Result
                lineScanner.close();                                                           // +++?
            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println(e.getMessage()); // In case of an error
                output = state.getPhrase("output_error");
                logger.logOutput(output);
                System.out.println(output);
                state = copy; //  return previous State values
            }
        }

    }

}

