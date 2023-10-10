import java.math.BigDecimal;

public class Core {
    private final HashMap hashmapCore = new HashMap(8);
    private final State state;

    public Core(State state) {
        this.hashmapCore.loadCoreHashMap();
        this.state = state;
    }

    public boolean calculator (String string) {
        boolean hasDecimal = checkDouble(string);
        boolean hasRome = RomeNumerals.defineRomeContent(string); // Check the content of the Roman numeral string
        boolean hasOctal = OctalNumbers.hasOctalNumber(string); // Check the content of the octal number string
        boolean hasHex = HexNumbers.hasHexNumber(string); // Check the content of the hexadecimal number string
        boolean hesBinary = BinaryNumbers.hasBinaryNumber(string); // Check the content of the binary number string
        boolean keyHashMap = hashmapCore.hasKey(string); // Check if str is a HashMap key

        if (hasDecimal) {    // Put the string in a BigDecimal and add it to the stack
            BigDecimal num = new BigDecimal(string); // Assign the value of the string to a BigDecimal number
            state.push(num);
            return false;
        } else if (hasRome) {
            BigDecimal romeResult = RomeNumerals.convertRomeToPush(string);
            state.push(romeResult); // Add the obtained number to the stack.
            return false;
        } else if (hasOctal) {
            BigDecimal octalResult = OctalNumbers.convertOctalToPush(string);
            state.push(octalResult);
            return false;
        } else if (hasHex) {
            BigDecimal hexResult = HexNumbers.hexNumbersToPush(string);
            state.push(hexResult);
            return false;
        } else if (hesBinary) {
            BigDecimal binaryResult = BinaryNumbers.binaryToPush(string);
            state.push(binaryResult);
            return false;
        } else if (keyHashMap) { // If the string matches an existing key
            string = hashmapCore.get(string); // Assign str a value by HashMap key

            if (string.equals(ConstantLibrary.PLUS)) {
                BigDecimal value1 = state.pop();
                BigDecimal value2 = state.pop();
                BigDecimal value3 = MathFunctions.calculatePlus(value1, value2);
                state.push(value3);
                return false;
            } else if (string.equals(ConstantLibrary.MINUS)) {
                BigDecimal value1 = state.pop();
                BigDecimal value2 = state.pop();
                BigDecimal value3 = MathFunctions.calculateMinus(value1, value2);
                state.push(value3);
                return false;
            } else if (string.equals(ConstantLibrary.MULTIPLY)) {
                BigDecimal value1 = state.pop();
                BigDecimal value2 = state.pop();
                BigDecimal value3 = MathFunctions.calculateMultiply(value1, value2);
                state.push(value3);
                return false;
            } else if (string.equals(ConstantLibrary.DIVIDE)) {
                BigDecimal value1 = state.pop();
                BigDecimal value2 = state.pop();
                try {
                    BigDecimal value3 = MathFunctions.calculateDivide(value1, value2);
                    state.push(value3);
                } catch (Exception e) {
                    System.out.println(e.getMessage());                                    ///++++ String!
                }
                return false;
            } else if (string.equals(ConstantLibrary.SQUARE)) { // Square root function
                BigDecimal value = state.pop();
                value = MathFunctions.calculateSquare(value);
                state.push(value);
                return false;
            } else if (string.equals(ConstantLibrary.EXPONENT)) { // Exponentiation function
                BigDecimal value1 = state.pop();
                BigDecimal value2 = state.pop();
                BigDecimal value3 = MathFunctions.calculateExponent(value1, value2);
                state.push(value3);
                return false;
            } else if (string.equals(ConstantLibrary.PERCENT)) { // Percentage calculation function
                BigDecimal value1 = state.pop();
                BigDecimal value2 = state.pop();
                BigDecimal value3 = MathFunctions.calculatePercentages(value1, value2);
                state.push(value3);
                return false;
            } else if (string.equals(ConstantLibrary.MEMORY)) { // "M" or "m" command
                BigDecimal value = state.peek();
                state.push(value);
                return false;
            }
        }
        return true;
    }

    private boolean checkDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
