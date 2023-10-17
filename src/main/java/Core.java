import java.math.BigDecimal;

public class Core {
    private final HashMap coreHashMap = new HashMap(8);
    private final State state;

    public Core(State state) {
        this.coreHashMap.loadCoreHashMap();
        this.state = state;
    }

    public boolean calculator (String operand) {
        boolean isDecimal = isDouble(operand);
        boolean isRome = RomeNumerals.isRome(operand); // Check the content of the Roman numeral string
        boolean isOctal = OctalNumbers.isOctalNumber(operand); // Check the content of the octal number string
        boolean isHex = HexNumbers.isHexNumber(operand); // Check the content of the hexadecimal number string
        boolean isBinary = BinaryNumbers.isBinaryNumber(operand); // Check the content of the binary number string
        boolean keyHashMap = coreHashMap.hasKey(operand); // Check if str is a HashMap key

        if (isDecimal) {    // Put the string in a BigDecimal and add it to the stack
            BigDecimal num = new BigDecimal(operand); // Assign the value of the string to a BigDecimal number
            state.push(num);
            return false;
        } else if (isRome) {
            BigDecimal romeResult = RomeNumerals.convertRomeToPush(operand);
            state.push(romeResult); // Add the obtained number to the stack.
            return false;
        } else if (isOctal) {
            BigDecimal octalResult = OctalNumbers.convertOctalToPush(operand);
            state.push(octalResult);
            return false;
        } else if (isHex) {
            BigDecimal hexResult = HexNumbers.hexNumbersToPush(operand);
            state.push(hexResult);
            return false;
        } else if (isBinary) {
            BigDecimal binaryResult = BinaryNumbers.binaryToPush(operand);
            state.push(binaryResult);
            return false;
        } else if (keyHashMap) { // If the string matches an existing key
            operand = coreHashMap.get(operand); // Assign str a value by HashMap key

            if (operand.equals(ConstantLibrary.PLUS)) {
                BigDecimal value1 = state.pop();
                BigDecimal value2 = state.pop();
                BigDecimal value3 = MathFunctions.calculatePlus(value1, value2);
                state.push(value3);
                return false;
            } else if (operand.equals(ConstantLibrary.MINUS)) {
                BigDecimal value1 = state.pop();
                BigDecimal value2 = state.pop();
                BigDecimal value3 = MathFunctions.calculateMinus(value1, value2);
                state.push(value3);
                return false;
            } else if (operand.equals(ConstantLibrary.MULTIPLY)) {
                BigDecimal value1 = state.pop();
                BigDecimal value2 = state.pop();
                BigDecimal value3 = MathFunctions.calculateMultiply(value1, value2);
                state.push(value3);
                return false;
            } else if (operand.equals(ConstantLibrary.DIVIDE)) {
                BigDecimal value1 = state.pop();
                BigDecimal value2 = state.pop();
                try {
                    BigDecimal value3 = MathFunctions.calculateDivide(value1, value2);
                    state.push(value3);
                } catch (Exception e) {
                    System.out.println(e.getMessage());                                    ///++++ String!
                }
                return false;
            } else if (operand.equals(ConstantLibrary.SQUARE)) { // Square root function
                BigDecimal value = state.pop();
                value = MathFunctions.calculateSquare(value);
                state.push(value);
                return false;
            } else if (operand.equals(ConstantLibrary.EXPONENT)) { // Exponentiation function
                BigDecimal value1 = state.pop();
                BigDecimal value2 = state.pop();
                BigDecimal value3 = MathFunctions.calculateExponent(value1, value2);
                state.push(value3);
                return false;
            } else if (operand.equals(ConstantLibrary.PERCENT)) { // Percentage calculation function
                BigDecimal value1 = state.pop();
                BigDecimal value2 = state.pop();
                BigDecimal value3 = MathFunctions.calculatePercentages(value1, value2);
                state.push(value3);
                return false;
            } else if (operand.equals(ConstantLibrary.MEMORY)) { // "M" or "m" command
                BigDecimal value = state.peek();
                state.push(value);
                return false;
            }
        }
        return true;
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
