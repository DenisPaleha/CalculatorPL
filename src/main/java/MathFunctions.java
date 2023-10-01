import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathFunctions {
    public static BigDecimal calculatePlus(BigDecimal value1, BigDecimal value2) {
        return value1.add(value2).setScale(10, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateMinus(BigDecimal value1, BigDecimal value2) {
        return value2.subtract(value1).setScale(10, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateMultiply(BigDecimal value1, BigDecimal value2) {
        return value1.multiply(value2).setScale(10, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateDivide(BigDecimal value1, BigDecimal value2) throws Exception {
        if (value1.compareTo(BigDecimal.valueOf(0)) == 0) {
            throw new Exception("Division by zero error");
        }
        return value2.divide(value1, 10, RoundingMode.HALF_UP);
    }

    /**
     * Function for square root extraction "V"
     */
    public static BigDecimal calculateSquare(BigDecimal value) {
        BigDecimal p1 = new BigDecimal(0); // Multiplier
        BigDecimal p2 = new BigDecimal(1); // Initial step by which the sought multiplier is increased

        if (value.compareTo(BigDecimal.valueOf(0)) < 0) { // If the number is negative, make it positive
            value = value.multiply(BigDecimal.valueOf(-1));

        } else if (value.compareTo(BigDecimal.valueOf(100)) >= 0) {
            p2 = new BigDecimal(10);

        } else if (value.compareTo(BigDecimal.valueOf(1000)) >= 0) {
            p2 = new BigDecimal(100);
        }

        if (value.compareTo(BigDecimal.valueOf(1)) >= 0) {  // Enter the calculation range
            while (true) {
                p1 = p1.add(p2);

                if (value.compareTo(p1.multiply(p1)) < 0) { // The entered number will not be less than p2 * p2
                    p1 = p1.subtract(p2); // Subtract the last added step
                    p2 = p2.divide(BigDecimal.valueOf(10).setScale(10, RoundingMode.HALF_UP));
                }

                if (value.compareTo(p1.multiply(p1)) == 0) {
                    value = p1;
                    return value;  // When it becomes equal, assign it to the last value in the array
                }

                BigDecimal Xvalue = p1; // p1
                Xvalue = Xvalue.multiply(Xvalue); // p1 * p1 = (p3 * p3)

                BigDecimal Xvalue1 = Xvalue; // p1 * p1 = (p3 * p3) duplicate one
                Xvalue1 = Xvalue1.subtract(BigDecimal.valueOf(0.0001)); // ((p1 * p1) = (p3 * p3) - 0.001)

                BigDecimal Xvalue2 = Xvalue; // p1 * p1 = (p3 * p3) duplicate two
                Xvalue2 = Xvalue2.add(BigDecimal.valueOf(0.0001)); // ((p1 * p1) = (p3 * p3) + 0.001)

                if ((value.compareTo(Xvalue1) >= 0) && (value.compareTo(Xvalue2) <= 0)) {
                    value = p1;  // Rounding for particularly large numbers
                    return value;
                }
            }
        }
        if (value.compareTo(BigDecimal.valueOf(1)) < 0) {
            if (value.compareTo(BigDecimal.valueOf(0.005)) < 0) {
                System.out.println("Square roots of numbers less than 0.005 are approximated.");
            }
            while (true) {
                p1 = p1.add(BigDecimal.valueOf(0.01)); // Add one hundredth to it until:
                if (value.compareTo(p1.multiply(p1)) <= 0) { // The entered number will not be less than or equal to p2 * p2
                    value = p1;
                    return value;  // When this happens, assign the number to the last value
                }

                BigDecimal Xvalue = p1; // p1
                Xvalue = Xvalue.multiply(Xvalue); // p1 * p1 = (p3 * p3)

                BigDecimal Xvalue1 = Xvalue; // p1 * p1 = (p3 * p3) duplicate one
                Xvalue1 = Xvalue1.subtract(BigDecimal.valueOf(0.001)); // ((p1 * p1) = (p3 * p3) - 0.001)

                BigDecimal Xvalue2 = Xvalue; // p1 * p1 = (p3 * p3) duplicate two
                Xvalue2 = Xvalue2.add(BigDecimal.valueOf(0.001)); // ((p1 * p1) = (p3 * p3) + 0.001)

                if ((value.compareTo(Xvalue1) >= 0) && (value.compareTo(Xvalue2) <= 0)) {
                    value = p1;  // Rounding for particularly wild numbers
                    return value;
                }
                if (p1.compareTo(BigDecimal.valueOf(1)) > 0) {
                    return new BigDecimal(0);
                }
            }
        }
        return new BigDecimal(0);
    }

    /**
     * Function for exponentiation "S"
     */
    public static BigDecimal calculateExponent(BigDecimal value1, BigDecimal value2) {
        int exponent = value1.intValue();
        String tmp = value2 + "";
        double result = Double.parseDouble(tmp);
        result = Math.pow(result, exponent);
        tmp = result + "";
        value2 = new BigDecimal(tmp);
        return value2.setScale(10, RoundingMode.HALF_UP);
    }

    /**
     * Function for calculating percentages "%"
     */
    public static BigDecimal calculatePercentages(BigDecimal value1, BigDecimal value2) {
        if ((value1.compareTo(BigDecimal.valueOf(0)) == 0) || value2.compareTo(BigDecimal.valueOf(0)) == 0) {
            System.out.println("An example of correct input of numbers: '12 12 %'.");
            return new BigDecimal(0);
        }
        value2 = value2.divide(BigDecimal.valueOf(100).setScale(10, RoundingMode.HALF_UP));
        return value2.multiply(value1).setScale(10, RoundingMode.HALF_UP);
    }
}

