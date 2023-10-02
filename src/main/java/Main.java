import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        State state = new State();
        state.loadState();

        Logger logger = new Logger();

        HashMap hashmap = new HashMap(8);
        hashmap.loadHashMap();

        System.out.println(state.getPhrases(0)); // Main info
        System.out.println(state.getPhrases(1)); // Info on calling help
        System.out.println(state.getPhrases(2) + state.memoryResult); // Reading the saved memory string

        Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);

        while (sc.hasNextLine()) { // Main program loop with user input
            boolean theEnd = false;
            String line = sc.nextLine(); // Save user input to the variable line

            logger.logInput(line); // Copy all input data to the logger
            String output; // Declare output string

            State copy = state.copyState();  // Copy the State class instance to insert in case of expression reading error

            Scanner lineScanner = new Scanner(line).useLocale(Locale.ENGLISH); // Scan the incomingData variable
            // Insert possible values supported by the calculator
            try {
                while (lineScanner.hasNext()) { // Scan the incomingData string
                    String str = lineScanner.next(); // Split the incomingData string with input data into parts in order
                    str = str.toLowerCase(); // Convert everything to lowercase
                    boolean hasDecimal = checkDouble(str);
                    boolean hasRome = RomeNumerals.defineRomeContent(str); // Check the content of the Roman numeral string
                    boolean hasOctal = OctalNumbers.hasOctalNumber(str); // Check the content of the octal number string
                    boolean hasHex = HexNumbers.hasHexNumber(str); // Check the content of the hexadecimal number string
                    boolean hesBinary = BinaryNumbers.hasBinaryNumber(str); // Check the content of the binary number string
                    boolean keyHashMap = hashmap.hasKey(str); // Check if str is a HashMap key

                    if (hasDecimal) {    // Put the string in a BigDecimal and add it to the stack
                        BigDecimal num = new BigDecimal(str); // Assign the value of the string to a BigDecimal number
                        state.push(num);
                    } else if (hasRome) {
                        BigDecimal romeResult = RomeNumerals.convertRomeToPush(str);
                        state.push(romeResult); // Add the obtained number to the stack.
                        output = state.getPhrases(3) + str + state.getPhrases(4) + romeResult;
                        logger.logOutput(output);
                        System.out.println(output);
                    } else if (hasOctal) {
                        BigDecimal octalResult = OctalNumbers.convertOctalToPush(str);
                        state.push(octalResult);
                        output = state.getPhrases(5) + str + state.getPhrases(6) + octalResult;
                        logger.logOutput(output);
                        System.out.println(output);
                    } else if (hasHex) {
                        BigDecimal hexResult = HexNumbers.hexNumbersToPush(str);
                        state.push(hexResult);
                        output = state.getPhrases(7) + str + state.getPhrases(8) + hexResult;
                        logger.logOutput(output);
                        System.out.println(output);
                    } else if (hesBinary) {
                        BigDecimal binaryResult = BinaryNumbers.binaryToPush(str);
                        state.push(binaryResult);
                        output = state.getPhrases(9) + str + state.getPhrases(10) + binaryResult;
                        logger.logOutput(output);
                        System.out.println(output);
                    } else if (keyHashMap) { // If the string matches an existing key
                        str = hashmap.get(str); // Assign str a value by HashMap key

                        if (str.equals(ConstantLibrary.PLUS)) {
                            BigDecimal value1 = state.pop();
                            BigDecimal value2 = state.pop();
                            BigDecimal value3 = MathFunctions.calculatePlus(value1, value2);
                            state.push(value3);
                        } else if (str.equals(ConstantLibrary.MINUS)) {
                            BigDecimal value1 = state.pop();
                            BigDecimal value2 = state.pop();
                            BigDecimal value3 = MathFunctions.calculateMinus(value1, value2);
                            state.push(value3);
                        } else if (str.equals(ConstantLibrary.MULTIPLY)) {
                            BigDecimal value1 = state.pop();
                            BigDecimal value2 = state.pop();
                            BigDecimal value3 = MathFunctions.calculateMultiply(value1, value2);
                            state.push(value3);
                        } else if (str.equals(ConstantLibrary.DIVIDE)) {
                            BigDecimal value1 = state.pop();
                            BigDecimal value2 = state.pop();
                            BigDecimal value3 = MathFunctions.calculateDivide(value1, value2);
                            state.push(value3);
                        } else if (str.equals(ConstantLibrary.SQUARE)) { // Square root function
                            BigDecimal value = state.pop();
                            value = MathFunctions.calculateSquare(value);
                            state.push(value);
                        } else if (str.equals(ConstantLibrary.EXPONENT)) { // Exponentiation function
                            BigDecimal value1 = state.pop();
                            BigDecimal value2 = state.pop();
                            BigDecimal value3 = MathFunctions.calculateExponent(value1, value2);
                            state.push(value3);
                        } else if (str.equals(ConstantLibrary.PERCENT)) { // Percentage calculation function
                            BigDecimal value1 = state.pop();
                            BigDecimal value2 = state.pop();
                            BigDecimal value3 = MathFunctions.calculatePercentages(value1, value2);
                            state.push(value3);
                        } else if (str.equals(ConstantLibrary.HELP)) {
                            if(state.isEnglish()){
                                System.out.println(ConstantLibrary.HELP_TEXT_ENG);
                            } else {
                                System.out.println(ConstantLibrary.HELP_TEXT_RUS);
                            }

                        } else if (str.equals(ConstantLibrary.MEMORY)) { // "M" or "m" command
                            BigDecimal value = state.peek();
                            state.push(value);

                        } else if (str.equals(ConstantLibrary.CLEAR)) { // Memory clearing function, "c" command
                            state.clear(); // Clear memory
                            output = state.getPhrases(11);
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
                                output = state.getPhrases(12);
                            } else {
                                output = state.getPhrases(13);
                            }
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.SAVE)) {  //  "S" command Saves all data to MemoryTwo.txt
                            output = state.getPhrases(14);
                            logger.logOutput(output);
                            state.saveState();

                        } else if (str.equals(ConstantLibrary.TO_ROME)) { // "ToRome" function, converts memory to Roman numeral
                            System.out.println(ConstantLibrary.HEAD_MESSAGE_ROME_1);
                            double mem = state.memoryResult.doubleValue(); // Convert BigDecimal to double
                            String result = RomeNumerals.convertDecimalToRome(mem);
                            output = state.getPhrases(15) + result;
                            logger.logOutput(output);
                            System.out.println(output); // Roman numeral

                        } else if (str.equals(ConstantLibrary.TO_OCTAL)) {
                            int num = state.memoryResult.intValue(); // Convert BigDecimal to int with rounding to the nearest integer
                            String result = OctalNumbers.convertDecimalToOctal(num); // Conversion
                            output = state.getPhrases(16) + result;
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.TO_HEX)) {
                            int num = state.memoryResult.intValue(); // Convert BigDecimal to int with rounding to the nearest integer
                            String result = HexNumbers.convertDecimalToHex(num); // Conversion
                            output = state.getPhrases(17) + result;
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.TO_BIN)) {
                            int num = state.memoryResult.intValue(); // Convert BigDecimal to int with rounding to the nearest integer
                            String result = BinaryNumbers.convertDecimalToBinary(num); // Conversion
                            output = state.getPhrases(18) + result;
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.OUT_DEC)) { // Automatically converts all results to
                            state.setNumberSystem(ConstantLibrary.OUT_DEC);
                            output = state.getPhrases(19); // Decimal number system enabled
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.OUT_BIN)) {
                            state.setNumberSystem(ConstantLibrary.OUT_BIN);
                            output = state.getPhrases(20); // Binary number system enabled
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.OUT_OCT)) {
                            state.setNumberSystem(ConstantLibrary.OUT_OCT);
                            output = state.getPhrases(21); // Octal number system enabled
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.OUT_HEX)) {
                            state.setNumberSystem(ConstantLibrary.OUT_HEX);
                            output = state.getPhrases(22); // Hexadecimal number system enabled
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.OUT_ROM)) {
                            state.setNumberSystem(ConstantLibrary.OUT_ROM);
                            output = state.getPhrases(23); // Roman number system enabled
                            logger.logOutput(output);
                            System.out.println(output);

                        } else if (str.equals(ConstantLibrary.OUT_RUS)) { // Language switch
                            state.setLanguage(false);
                            output = "Language: Russian";
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
                        output = state.getPhrases(24) + str;
                        logger.logOutput(output);
                        System.out.println(output);
                    }
                }
                if (theEnd) { // If theEnd boolean value is true, exit the program.
                    state.saveState();
                    output = state.getPhrases(25);
                    logger.logOutput(output);
                    System.out.println(output);
                    lineScanner.close();
                    break;
                } else {
                    state.memoryResult = state.peek();
                }
                BigDecimal out = state.memoryResult.setScale(2, RoundingMode.HALF_UP); // Round the result to two decimal places for display
                // Use try-catch block to handle exceptions
                String result;
                try {
                    result = state.universalConverter(out);
                }catch (Exception e) {
                    result = out.toString();
                }
                logger.logOutput(state.getPhrases(26) + result); // Result
                System.out.println(state.getPhrases(27) + result); // Result
            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println(e.getMessage()); // In case of an error
                output = state.getPhrases(28);
                logger.logOutput(output);
                System.out.println(output);
                state = copy; //  return previous State values
            }
        }
    }

    /**
     * Function checks if the incoming value is a decimal number
     */
    public static boolean checkDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}

