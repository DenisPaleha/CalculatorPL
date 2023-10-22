package numbers;

import java.math.BigDecimal;
import static constants.ConstantLibrary.*;
public final class OctalNumbers {

    /**
     * Function checks if the input string is an octal (base 8) number. Use the prefix 0o.
     */
    public static Boolean isOctalNumber(String str) {return str.startsWith("0o");}

    /**
     * Function takes an octal number string without the prefix (0o) and returns a BigDecimal.
     */
    public static BigDecimal convertOctalToDecimal(String str) {
        String[] octalNums = str.split("");
        double result = 0;
        for (int i = 0; i < octalNums.length; i++) {
            int x = Integer.parseInt(octalNums[octalNums.length - i - 1]); // Assign numbers in reverse order
            result += x * Math.pow(8, i); // Add numbers multiplied by 8 raised to the power of i (from the end = 0, to the beginning)
        }
        return BigDecimal.valueOf(result);
    }

    /**
     * Function takes a string with the prefix, checks its validity, and returns a BigDecimal.
     */
    public static BigDecimal convertOctalToPush(String str) {
        str = str.substring(2); // Remove the prefix
        Boolean isCorrect = isOctalCorrect(str);
        if (isCorrect) {
            return convertOctalToDecimal(str);
        } else {
            System.out.println("Octal number " + str + " contains invalid characters.");
        }
        return BigDecimal.valueOf(0.0);
    }

    /**
     * Function checks if the number contains unsupported digits (8)
     */
    public static Boolean isOctalCorrect(String str) { // input number without the prefix
        String[] octalNums = str.split(""); // Convert the string to an array of strings
        String[] validValues = new String[]{ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN};
        int i = 0;
        while (i < octalNums.length) { // Check all the strings in the octalNums array
            String x = octalNums[i]; // x = a string from the octalNums array
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
     * Function converts decimal numbers to octal.
     */
    public static String convertDecimalToOctal(int input) {
        if (input < 0){ // If the number is negative, make it positive
            input = input * -1;
        }
        String result = "";
        while (input > 0) {
            result = input % 8 + "" + result;
            input = input / 8;
        }
        result = PREFIX_8 + result; // Add the prefix
        return result;
    }
}

