import java.math.BigDecimal;
import static mypackage.ConstantLibrary.*;

public class RomeNumerals {

    // The library of Roman numeral strings is located in the ConstantLibrary class

    /**
     * Function converts a decimal number to Roman numeral (1-3999)
     */
    public static String convertDecimalToRome(double doubleValue) throws Exception {

        // Create arrays of strings for each digit; ones, tens, hundreds, thousands.
        String[] arrayTen = new String[]{ONE_ROME, TWO_ROME, THREE_ROME, FORE, FIVE_ROME, SIX_ROME, SEVEN_ROME, EIGHT_ROME, NINE_ROME};
        String[] arrayDozens = new String[]{TEN_ROME, TWENTY, THIRTY, FORTY, FIFTY, SIXTY, SEVENTY, EIGHTY, NINETY};
        String[] arrayHundreds = new String[]{ONE_HUNDRED, TWO_HUNDRED, THREE_HUNDRED, FORE_HUNDRED, FIVE_HUNDRED, SIX_HUNDRED, SEVEN_HUNDRED, EIGHT_HUNDRED, NINE_HUNDRED};
        String[] arrayThousands = new String[]{THOUSAND, TWO_THOUSAND, THREE_THOUSAND};

        int value = (int) doubleValue;
        if (value < 0 || value >= MAX_VALUE) {  // Check the value of the input number
            throw new Exception(HEAD_MESSAGE_ROME_2);

        } else if (value == 0) {
            return "Null";
        }

        String str = Integer.toString(value); // Convert the integer to a string
        int[] nums = new int[str.length()];   // Create an integer array with the same length as the string
        char[] chars = str.toCharArray();     // Create a character array and parse the string character by character
        for (int i = 0; i < nums.length; i++) {
            String oneCharString = new String(chars, i, 1); // Create a string with a single character
            nums[i] = Integer.parseInt(oneCharString);            // Parse the string into integers for each new value of i
        }

        String result = fineNumber4(arrayThousands, nums); // Build the resulting string incrementally by digit places
        result = result + fineNumber3(arrayHundreds, nums);
        result = result + fineNumber2(arrayDozens, nums);
        result = result + fineNumber1(arrayTen, nums);
        return result;
    }

    /**
     * Returns a string with the first (rightmost) Roman numeral
     */
    public static String fineNumber1(String[] arrayTen, int[] nums) {
        int i = nums[nums.length - 1]; // Digit place of the input number
        if (nums[nums.length - 1] == 0) {
            return "";
        }
        return arrayTen[i - 1];
    }

    /**
     * Returns a string with the second Roman numeral
     */
    public static String fineNumber2(String[] arrayDozens, int[] nums) {
        String result = "";
        if (nums.length < 2) {
            return result;
        } else if (nums[nums.length - 2] == 0) {
            return "";
        }

        int i = nums[nums.length - 2]; // Digit place of the input number
        result = arrayDozens[i - 1];
        return result;
    }

    /**
     * Returns a string with the third Roman numeral
     */
    public static String fineNumber3(String[] arrayHundreds, int[] nums) {
        String result = "";
        if (nums.length < 3) {
            return result;
        } else if (nums[nums.length - 3] == 0) {
            return "";
        }
        int i = nums[nums.length - 3]; // Digit place of the input number
        result = arrayHundreds[i - 1];
        return result;
    }

    /**
     * Returns a string with the fourth Roman numeral
     */
    public static String fineNumber4(String[] arrayThousands, int[] nums) {
        String result = "";
        if (nums.length < 4) {
            return result;
        }
        int i = nums[nums.length - 4]; // Digit place of the input number
        result = arrayThousands[i - 1];
        return result;
    }

    /**
     * Function accepts a Roman numeral string and returns a BigDecimal
     */
    public static double convertRomeToDecimal(String str) throws Exception {
        // Create arrays of strings for each type of Roman numeral.
        String[] romeNumerals = new String[]{ONE_ROME, FIVE_ROME, TEN_ROME, FIFTY, ONE_HUNDRED, FIVE_HUNDRED, THOUSAND};

        String[] stringInput = str.split(""); // Convert the string into an array of strings

        checkingAllowedCharacters(romeNumerals, stringInput);

        int[] inputInt = new int[stringInput.length]; // Create an integer array with the same length as the string array

        for (int i = 0; i < stringInput.length; i++) {
            for (int j = 0; j < romeNumerals.length; j++) {
                String numOne = romeNumerals[j];
                if (stringInput[i].equalsIgnoreCase(numOne)) { // If the array element equals the constant string
                    inputInt[i] = valueOfIndex(j); // Assign the corresponding integer value to the array
                }
            }
        }

        // Now we have an integer array inputInt[] containing the Arabic numerals in the order of the Roman numeral representation.
        // Perform validation checks

        permitsForSubtraction(inputInt); // Subtraction can only occur from the closest allowed numerals: IV, IX, XL, XC, CD, CM
        twoSmallerInRow(inputInt); // A smaller numeral cannot be repeated twice if it precedes a larger one
        moreThanThreeInRow(inputInt); // More than 3 identical numerals 1, 10, 100, 1000 cannot appear consecutively
        subtractionCheck(inputInt); // Numerals 5, 50, 500 cannot be subtracted
        twoNumberCheck(inputInt); // 5, 50, 500 cannot be repeated (only one occurrence in the array!)

        return resultNum(inputInt); // Return the result as a floating-point number
    }

    /**
     * Method checks if the subtracted numeral is followed by an allowable numeral
     * I cannot precede L, C, D, M
     * X cannot precede D, M
     */
    public static void permitsForSubtraction(int[] inputInt) throws Exception {
        for (int i = 0; i < inputInt.length - 1; i++) { // "inputInt.length - 1" because we compare with the next
            int x1 = 1;
            if (x1 == inputInt[i] && (inputInt[i + 1] == 50 || inputInt[i + 1] == 100 || inputInt[i + 1] == 500 || inputInt[i + 1] == 1000)) {
                throw new Exception("Write error: I cannot precede L, C, D, M.");
            }
        }
        for (int i = 0; i < inputInt.length - 1; i++) { // "inputInt.length - 1" because we compare with the next
            int x1 = 10;
            if (x1 == inputInt[i] && (inputInt[i + 1] == 500 || inputInt[i + 1] == 1000)) {
                throw new Exception("Write error: X cannot precede D, M.");
            }
        }
    }

    /**
     * Method checks if two identical smaller numerals are followed by a larger one (e.g., IIX)
     */
    public static void twoSmallerInRow(int[] inputInt) throws Exception {
        for (int x1 = 1; x1 < 1001; x1 = x1 * 10) {
            for (int i = 0; i < inputInt.length - 2; i++) { // "inputInt.length - 2" because we compare the next two numbers
                if (x1 == inputInt[i] && x1 == inputInt[i + 1] && x1 < inputInt[i + 2]) {
                    throw new Exception("Write error: Two identical Roman numerals of lesser value cannot precede a larger numeral.");
                }
            }
        }
    }

    /**
     * Method checks if numerals 1, 10, 100, 1000 are repeated more than three times in a row (e.g., "IIII")
     */
    public static void moreThanThreeInRow(int[] inputInt) throws Exception {
        if (inputInt.length > 3) {
            for (int x1 = 1; x1 < 10000; x1 = x1 * 10) {
                int i = 0;
                while (i < inputInt.length - 3) {
                    if (x1 == inputInt[i] && x1 == inputInt[i + 1] && x1 == inputInt[i + 2] && x1 == inputInt[i + 3]) {
                        throw new Exception("Write error: Roman numerals 1, 10, 100, 1000 cannot repeat more than three times consecutively.");
                    }
                    i++;
                }
            }
        }
    }

    /**
     * Method checks if numerals 5, 50, and 500 are subtracted (e.g., VX)
     */
    public static void subtractionCheck(int[] inputInt) throws Exception {
        for (int x1 = 5; x1 < 5000; x1 = x1 * 10) {
            for (int i = 0; i < inputInt.length; i++) {
                if (x1 == inputInt[i]) {
                    for (int j = i; j < inputInt.length; j++) {
                        if (x1 < inputInt[j]) {
                            throw new Exception("Write error: Roman numerals V, L, and D cannot precede larger numerals.");
                        }
                    }
                }
            }
        }
    }

    /**
     * Method checks if numerals 5, 50, 500 are repeated (each can only appear once in the number)
     */
    public static void twoNumberCheck(int[] inputInt) throws Exception {
        for (int x1 = 5; x1 < 5000; x1 = x1 * 10) {
            int x = 0;
            int i = 0;
            while (i < inputInt.length) {
                if (x1 == inputInt[i]) {
                    x = x + 1;
                    if (x == 2) {
                        throw new Exception("Write error: Roman numerals V, L, and D cannot be repeated.");
                    }
                }
                i++;
            }
        }
    }

    /**
     * Method checks if the string contains invalid characters
     */
    public static void checkingAllowedCharacters(String[] RomaNumerals, String[] arrStringInput) throws Exception {
        for (String s : arrStringInput) {
            String x = s + "";
            int j = 0;
            while (j < RomaNumerals.length) {
                String y = RomaNumerals[j] + "";
                if (x.equalsIgnoreCase(y)) {
                    break;
                } else {
                    j++;
                    if (j == RomaNumerals.length) {
                        throw new Exception("Write error: A Roman numeral can only contain the letters I, V, X, L, C, D, M.");
                    }
                }
            }
        }
    }

    /**
     * Function assigns a number based on the index in the string array
     */
    public static int valueOfIndex(int i) {
        int result = 0;
        if (i == 0) {
            result = 1;
        } else if (i == 1) {
            result = 5;
        } else if (i == 2) {
            result = 10;
        } else if (i == 3) {
            result = 50;
        } else if (i == 4) {
            result = 100;
        } else if (i == 5) {
            result = 500;
        } else if (i == 6) {
            result = 1000;
        }
        return result;
    }

    /**
     * Method performs final calculations. The result is the first number in the array
     */
    public static double resultNum(int[] arrInputInt) {
        for (int i = 1; i < arrInputInt.length; i++) {
            int j = i + 1;
            if ((arrInputInt[arrInputInt.length - i] > arrInputInt[arrInputInt.length - j])) {
                arrInputInt[arrInputInt.length - i] = arrInputInt[arrInputInt.length - i] - arrInputInt[arrInputInt.length - j];
                arrInputInt[arrInputInt.length - j] = 0;
            }
        }
        for (int i = 1; i < arrInputInt.length; i++) {
            int j = i + 1;
            arrInputInt[arrInputInt.length - j] = arrInputInt[arrInputInt.length - i] + arrInputInt[arrInputInt.length - j];
        }
        return arrInputInt[0];
    }

    /**
     * Method checks the contents of the string to distinguish a Roman numeral from all others
     */
    public static Boolean isRome(String str) {
        if (str.equalsIgnoreCase("M") || str.equalsIgnoreCase("C")) {
            return false; // Exclude calls to the Clear and Memory functions
        }
        String[] arrRomeNumeral = new String[]{ONE_ROME, FIVE_ROME, TEN_ROME, FIFTY, ONE_HUNDRED, FIVE_HUNDRED, THOUSAND};
        String[] arrStringInput = str.split(""); // Convert the string into an array of strings

        int i = 0;
        for (int j = 0; true; ) {
            if (!arrStringInput[i].equalsIgnoreCase(arrRomeNumeral[j])) {
                j++;
                if (j == arrRomeNumeral.length) {
                    return false;
                }
            } else if (arrRomeNumeral[j].equalsIgnoreCase(arrStringInput[i])) {
                i++;
                j = 0;
                if (i == arrStringInput.length) {
                    return true;
                }
            }
        }
    }

    /**
     * Function accepts a string and returns a BigDecimal for insertion into an array or list
     */
    public static BigDecimal convertRomeToPush(String str) /* throws Exception */ {
        BigDecimal romeResult;
        try {
            double result = RomeNumerals.convertRomeToDecimal(str); // Convert to a floating-point number
            romeResult = BigDecimal.valueOf(result); // Create a BigDecimal with the numeric value
            return romeResult;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            romeResult = BigDecimal.valueOf(0.0);
        }
        return romeResult;
    }
}

