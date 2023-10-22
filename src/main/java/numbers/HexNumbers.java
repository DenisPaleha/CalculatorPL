package numbers;

import java.math.BigDecimal;
import static constants.ConstantLibrary.*;

public final class HexNumbers {

    /**
     * Function checks if the entered string is a hexadecimal (base 16) number. Use the 0x prefix
     */
    public static Boolean isHexNumber(String str){return str.startsWith("0x");}

    /**
     * Function takes a BigDecimal without the hexadecimal (base 16) prefix and returns a string with the decimal result
     */
    public static BigDecimal convertHexToDecimal(String str) {
        String[] hexNums = str.split(""); // Convert the string into an array of strings

        for (int i = 0; i < hexNums.length; i++) { // Replace alphabetic characters with their decimal equivalents
            if (hexNums[i].equalsIgnoreCase(TEN)) {
                hexNums[i] = "10";
            } else if (hexNums[i].equalsIgnoreCase(ELEVEN)) {
                hexNums[i] = "11";
            } else if (hexNums[i].equalsIgnoreCase(TWELVE)) {
                hexNums[i] = "12";
            } else if (hexNums[i].equalsIgnoreCase(THIRTEEN)) {
                hexNums[i] = "13";
            } else if (hexNums[i].equalsIgnoreCase(FOURTEEN)) {
                hexNums[i] = "14";
            } else if (hexNums[i].equalsIgnoreCase(FIFTEEN)) {
                hexNums[i] = "15";
            }
        }
        double result = 0;
        for (int i = 0; i < hexNums.length; i++) {
            int x = Integer.parseInt(hexNums[hexNums.length - i - 1]); // Assign numbers from the end to the beginning of the string.
            result += x * Math.pow(16, i); // Add numbers multiplied by 16 raised to the power of the digit position (from end = 0, to start)
        }
        return BigDecimal.valueOf(result);
    }

    /** Function takes a string as input, checks its validity, and returns a BigDecimal */
    public static BigDecimal hexNumbersToPush(String str) {
        str = str.substring(2); // Remove the prefix
        Boolean isCorrect = HexNumbers.isHexCorrect(str);
        if (isCorrect) {
            return convertHexToDecimal(str);
        } else {
            System.out.println("Write error: Hexadecimal number " + str + " contains invalid characters.");
        }
        return BigDecimal.valueOf(0.0);
    }

    /**
     * Function checks if the number contains any unsupported characters (base 16)
     */
    public static Boolean isHexCorrect(String str) { // The string with the number already without the prefix
        String[] hexNums = str.split(""); // Convert the string into an array of strings
        String[] validValues = new String[]{ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRTEEN, FOURTEEN, FIFTEEN};
        int i = 0;
        while (i < hexNums.length) {
            String x = hexNums[i];
            int j = 0;
            while (j < validValues.length) {
                String y = validValues[j];
                if (x.equalsIgnoreCase(y)) {
                    break;
                } else {
                    j++;
                    if (j == validValues.length) {
                        return false;
                    }
                }
            }
            i++;
        }
        return true;
    }

    /** Function converts decimal numbers to hexadecimal */

    public static String convertDecimalToHex(int input) {
        if (input < 0){ // If the number is negative, make it positive
            input = input * -1;
        }
        String result = "";
        while (input > 0) {
            int x = input % 16;
            if (x == 10) {
                result = TEN + result;
            } else if (x == 11) {
                result = ELEVEN + result;
            } else if (x == 12) {
                result = TWELVE + result;
            } else if (x == 13) {
                result = THIRTEEN + result;
            } else if (x == 14) {
                result = FOURTEEN + result;
            } else if (x == 15) {
                result = FIFTEEN + result;
            } else {
                result = x + "" + result;
            }
            input = input / 16;
        }
        result = PREFIX_16 + result; // Add the prefix
        return result;
    }
}

