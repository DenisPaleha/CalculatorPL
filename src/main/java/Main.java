import static constants.ConstantLibrary.*;

import constants.HashMap;

import logger.Logger;
import numbers.BinaryNumbers;
import numbers.HexNumbers;
import numbers.OctalNumbers;
import numbers.RomeNumerals;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in).useLocale(Locale.ENGLISH);

        try {
            State state = new State();
            state.loadState();

            Logger logger = new Logger();

            HashMap hashmapMain = new HashMap(8);
            hashmapMain.loadMainHashMap();

            System.out.println(state.getPhrase("hello_massage_one")); // Main info
            System.out.println(state.getPhrase("hello_massage_two")); // Info on calling help
            System.out.println(String.format(state.getPhrase("loaded_memory"), state.memoryResult)); // Reading the saved memory string

            END_PROGRAM:
            while (scanner.hasNextLine()) { // Main program loop with user input
                String line = scanner.nextLine(); // Save user input to the variable line

                try {
                    logger.logInput(line); // Copy all input data to the logger
                } catch (Exception e) {
                    logger.logOutput(e.getMessage());
                    System.out.println(e.getMessage());
                }

                String output; // Declare output string

                State copy = state.copyState();  // Copy the State class instance to insert in case of expression reading error

                Core core = new Core(state);

                Scanner lineScanner = new Scanner(line).useLocale(Locale.ENGLISH); // Scan the incomingData variable
                // Insert possible values supported by the calculator
                try {
                    while (lineScanner.hasNext()) { // Scan the incomingData string
                        String operand = lineScanner.next(); // Split the incomingData string with input data into parts in order
                        operand = operand.toLowerCase(); // Convert everything to lowercase
                        boolean coreNotUsed; // Method core.calculator(str) trigger switch
                        try {
                        coreNotUsed = core.calculator(operand);
                        } catch (Exception wrongNumber){
                            coreNotUsed = false;
                            logger.logOutput(wrongNumber.getMessage());
                            System.out.println(wrongNumber.getMessage());
                        }

                        boolean keyHashMap = hashmapMain.hasKey(operand); // Check if str is a constants.HashMap key
                        if (keyHashMap) { // If the string matches an existing key
                            operand = hashmapMain.get(operand); // Assign str a value by constants.HashMap key
                            if (operand.equals(HELP)) {
                                if (state.isEnglish()) {
                                    System.out.println(HELP_TEXT_ENG);
                                } else {
                                    System.out.println(HELP_TEXT_RUS);
                                }

                            } else if (operand.equals(CLEAR)) { // Memory clearing function, "c" command
                                state.clear(); // Clear memory
                                output = state.getPhrase("memory_cleared");
                                logger.logOutput(output);
                                System.out.println(output);

                            } else if (operand.equals(INFO)) { // Screen output function, "info" command
                                String info;
                                if (state.isEnglish()) {
                                    info = state.infoEng();
                                } else {
                                    info = state.infoRus();
                                }
                                logger.logOutput(info);
                                System.out.println(info);

                            } else if (operand.equals(SWITCH_METHOD)) {  // Data structure switching function Array/List
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

                            } else if (operand.equals(SAVE)) {  //  "S" command Saves all data to MemoryTwo.txt
                                output = state.getPhrase("data_saved");
                                logger.logOutput(output);
                                state.saveState();
                                System.out.println(output);

                            } else if (operand.equals(TO_ROME)) { // "ToRome" function, converts memory to Roman numeral
                                System.out.println(HEAD_MESSAGE_ROME_1);
                                double mem = state.memoryResult.doubleValue(); // Convert BigDecimal to double
                                try {
                                    String result = RomeNumerals.convertDecimalToRome(mem);
                                    output = String.format(state.getPhrase("roman_number_equal"), result);
                                } catch (Exception e) {
                                    output = e.getMessage();
                                }
                                logger.logOutput(output);
                                System.out.println(output); // Roman numeral

                            } else if (operand.equals(TO_OCTAL)) {
                                int num = state.memoryResult.intValue(); // Convert BigDecimal to int with rounding to the nearest integer
                                String result = OctalNumbers.convertDecimalToOctal(num); // Conversion
                                output = String.format(state.getPhrase("octal_number_equal"), result);
                                logger.logOutput(output);
                                System.out.println(output);

                            } else if (operand.equals(TO_HEX)) {
                                int num = state.memoryResult.intValue(); // Convert BigDecimal to int with rounding to the nearest integer
                                String result = HexNumbers.convertDecimalToHex(num); // Conversion
                                output = String.format(state.getPhrase("hexadecimal_number_equal"), result);
                                logger.logOutput(output);
                                System.out.println(output);

                            } else if (operand.equals(TO_BIN)) {
                                int num = state.memoryResult.intValue(); // Convert BigDecimal to int with rounding to the nearest integer
                                String result = BinaryNumbers.convertDecimalToBinary(num); // Conversion
                                output = String.format(state.getPhrase("binary_number_equal"), result);
                                logger.logOutput(output);
                                System.out.println(output);

                            } else if (operand.equals(OUT_DEC)) { // Automatically converts all results to
                                state.setNumberSystem(OUT_DEC);
                                output = state.getPhrase("decimal_enabled"); // Decimal number system enabled
                                logger.logOutput(output);
                                System.out.println(output);

                            } else if (operand.equals(OUT_BIN)) {
                                state.setNumberSystem(OUT_BIN);
                                output = state.getPhrase("binary_enabled"); // Binary number system enabled
                                logger.logOutput(output);
                                System.out.println(output);

                            } else if (operand.equals(OUT_OCT)) {
                                state.setNumberSystem(OUT_OCT);
                                output = state.getPhrase("octal_enabled"); // Octal number system enabled
                                logger.logOutput(output);
                                System.out.println(output);

                            } else if (operand.equals(OUT_HEX)) {
                                state.setNumberSystem(OUT_HEX);
                                output = state.getPhrase("hexadecimal_enabled"); // Hexadecimal number system enabled
                                logger.logOutput(output);
                                System.out.println(output);

                            } else if (operand.equals(OUT_ROM)) {
                                state.setNumberSystem(OUT_ROM);
                                output = state.getPhrase("roman_enabled"); // Roman number system enabled
                                logger.logOutput(output);
                                System.out.println(output);

                            } else if (operand.equals(OUT_RUS)) { // Language switch
                                state.setLanguage(false);
                                output = "Язык: Русский";
                                logger.logOutput(output);
                                System.out.println(output);

                            } else if (operand.equals(OUT_ENG)) { // Language switch
                                state.setLanguage(true);
                                output = "Language: English";
                                logger.logOutput(output);
                                System.out.println(output);

                            } else if (operand.equals(EXIT)) { // Exit function "E"
                                state.saveState();
                                output = state.getPhrase("exiting");
                                logger.logOutput(output);

                                try {
                                    logger.CopyFilesFromLoggerToTemp();
                                } catch (IOException e) {
                                    logger.logOutput(e.getMessage());
                                    System.out.println(e.getMessage());
                                }

                                System.out.println(output);
                                lineScanner.close();
                                scanner.close();
                                break END_PROGRAM;
                            }
                        } else {
                            if (coreNotUsed) {
                                output = String.format(state.getPhrase("unknown_value"), operand);
                                logger.logOutput(output);
                                System.out.println(output);
                            }
                        }
                    }
                    state.memoryResult = state.peek();

                    BigDecimal out = state.memoryResult.setScale(2, RoundingMode.HALF_UP); // Round the result to two decimal places for display
                    String result;
                    try {
                        result = state.universalConverter(out);
                    } catch (Exception wrongNumber) {
                        result = out.toString();
                    }
                    output = String.format(state.getPhrase("result"), result);
                    logger.logOutput(output); // Result
                    System.out.println(output); // Result
                } catch (Exception e) {
//                e.printStackTrace();
                    System.out.println(e.getMessage()); // In case of an error
                    output = state.getPhrase("output_error");
                    logger.logOutput(output);
                    System.out.println(output);
                    state = copy; //  return previous State values
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
