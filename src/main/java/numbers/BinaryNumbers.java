package numbers;

import java.math.BigDecimal;
import static constants.ConstantLibrary.*;

public final class BinaryNumbers {

    /**
     * Function checks if a string contains a binary number (2), use the prefix 0b (zero + b)
     */
    public static Boolean isBinaryNumber(String str){return str.startsWith("0b");}

    /**
     * Function checks if a number contains any unsupported digits (other than 1-2)
     */
    public static Boolean isBinaryCorrect(String str) { // Input the number (including the prefix)
        String[] binaryNums = str.split(""); // Convert the string into an array of strings
        String[] validValues = new String[]{ZERO, ONE};
        int i = 0;
        while (i < binaryNums.length) { // Check all strings in the binaryNums array
            String x = binaryNums[i]; // x = a string from the binaryNums array
            int j = 0;
            while (j < validValues.length) { // Compare with constants
                String y = validValues[j];
                if (x.equalsIgnoreCase(y)) {
                    break; // If the value matches, move to the next string
                } else { // If not, move to the next constant
                    j++;
                    if (j == validValues.length) { // If the array length reaches the limit
                        return false;
                    }
                }
            }
            i++;
        }
        return true;
    }

    /**
     * Function takes a binary prefix string (0b) and returns a BigDecimal with the decimal result
     */
    public static String convertBinaryToDecimal(String str) {
        String[] binaryNums = str.split("");
        double result = 0;
        for (int i = 0; i < binaryNums.length; i++) {
            int x = Integer.parseInt(binaryNums[binaryNums.length - i - 1]); // Assign numbers in reverse order
            result += x * Math.pow(2, i); // Add numbers multiplied by 2 to the power of the position i (from end = 0, to start)
        }
        return result + "";
    }

    /** Function takes an input string, checks its validity, and returns a BigDecimal for pushing onto the stack */
    public static BigDecimal binaryToPush(String str) {
        str = str.substring(2); // Remove the prefix
        Boolean isCorrect = isBinaryCorrect(str); // Check validity
        if (isCorrect) {
            return new BigDecimal(convertBinaryToDecimal(str));
        } else {
            System.out.println("Write error: Binary number " + str + " contains invalid characters.");
        }
        return BigDecimal.valueOf(0.0);
    }

    /** Function for converting decimal numbers to binary */
    public static String convertDecimalToBinary(int input) {
        if (input < 0){ // If the number is negative, make it positive
            input = input * -1;
        }
        String result = "";
        while (input > 0) {
            result = input % 2 + "" + result;
            input = input / 2;
        }
        result = PREFIX_2 + result; // Add the prefix (0b)
        return result;
    }
}


