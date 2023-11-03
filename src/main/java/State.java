import org.paleha.calculator_pl.memory.FileOperator;
import org.paleha.calculator_pl.languages.LocaleStrings;
import org.paleha.calculator_pl.stack.AbstractStack;
import org.paleha.calculator_pl.stack.StackArr;
import org.paleha.calculator_pl.stack.StackList;

import org.paleha.calculator_pl.numbers.BinaryNumbers;
import org.paleha.calculator_pl.numbers.HexNumbers;
import org.paleha.calculator_pl.numbers.OctalNumbers;
import org.paleha.calculator_pl.numbers.RomeNumerals;

import static org.paleha.calculator_pl.constanse.ConstantLibrary.*;

import java.io.IOException;
import java.math.BigDecimal;

public class State {
    private boolean isArray = true; // Data structure switch: true = array; false = list;
    private boolean isEnglish = true; // Language switch
    BigDecimal memoryResult = new BigDecimal("0.0"); // Saved memory
    private String numberSystem = OUT_DEC; // Number system
    //    private final String fileTxtName = "Memory.txt";
    private AbstractStack stack = new StackArr();
    private final FileOperator memoryOperator = new FileOperator();
    private LocaleStrings localeStrings = new LocaleStrings(isEnglish);
    private int tripMeter = 0; // invisible counter (only visible in Memory.txt string 5)

    /**
     * Function to switch the program's language.
     */
    public void setLanguage(boolean newLanguage) {  // true = English
        this.isEnglish = newLanguage;
        this.localeStrings = new LocaleStrings(newLanguage);
    }

    /**
     * Function to return a string by string keyWord
     */
    public String getPhrase(String keyWord) {
        return this.localeStrings.getString(keyWord);
    }

    public boolean isEnglish() {
        return this.isEnglish;
    }

    /**
     * Function to change the stack data structure.
     */
    public void setStorageType(boolean isArray) {
        if (this.isArray == isArray) {
            return; // Nothing will happen if attempting to switch to the already used data structure
        }
        AbstractStack newStack;
        String data = this.stack.copy();
        if (this.isArray) {
            newStack = new StackList();
        } else {
            newStack = new StackArr();
        }

        if (!data.equals("0 ")) { // This check is needed to ensure that a zero is only added when the stack is empty
            newStack.write(data);
        }

        this.isArray = isArray;
        this.stack = newStack;
    }

    /**
     * Function to clear the current stack.
     */
    public void clear() {
        if (this.isArray) {
            this.stack = new StackArr();
        } else {
            this.stack = new StackList();
        }
    }

    /**
     * Function to return the current value of the boolean variable this.isArray.
     */
    public boolean isArray() {
        return this.isArray;
    }

    /**
     * Function to return a copy of the current stack (array or linked list).
     */
    private AbstractStack copyStack() {
        AbstractStack copy;
        if (isArray()) {
            copy = new StackArr();
        } else {
            copy = new StackList();
        }
        String dataInfo = stack.copy(); // Get a copy of the stack's content as a string
        copy.write(dataInfo);
        return copy;
    }

    /**
     * Function to transfer values of 'state' to a temporary 'copy' object.
     */
    public State copyState() {
        State copy = new State();
        copy.isArray = this.isArray; // Create duplicates of variables and assign existing values to them
        copy.memoryResult = new BigDecimal(this.memoryResult.toString()); // Create and copy the BigDecimal via a string
        copy.stack = copyStack();
        return copy;
    }

    /**
     * Function for saving memory.
     */
    public String saveState() throws IOException {  // Make the method return a String for testing purposes
        String dataInfo = stack.copy(); // Create a string with the stack's content
        BigDecimal tmp = peek(); // Store the last value
        String memoryRes = tmp.toString(); // Store it as a string
        String methodStatus = String.valueOf(this.isArray); // Stores the method state (array/list - true/false).
        String languageStatus = String.valueOf(this.isEnglish); // Stores the language state (English/Russian - true/false)
        String tripMeter = String.valueOf(this.tripMeter); // invisible counter

        String allMemory = dataInfo + "\n" + memoryRes + "\n" + methodStatus + "\n" + languageStatus + "\n" + tripMeter + "\n";
        String allMemoryTest = dataInfo + " " + memoryRes + " " + methodStatus + " " + languageStatus;
        // Pack all memory types into strings
        memoryOperator.wroteToMemoryFile(allMemory);
        return allMemoryTest;
    }

    /**
     * Function for loading the State from saved txt data.
     */
    public void loadState() throws IOException {
        if (!memoryOperator.isFileExist()) {  // Check if the file exists
            saveState(); // If the file does not exist, save the state
        }

        try {
            String fileContent = memoryOperator.readFromMemoryFile();
            // Now 'fileContent' contains the file's content as a single string
            String[] massive = fileContent.split("\n"); // Create a string array and split the 'line' string into it

            String dataInfo = massive[0]; // Stack contents
            String memoryRes = massive[1]; // Memory of the last number
            String methodStatus = massive[2]; // Data structure state
            String languageStatus = massive[3]; // Language state - as a string
            String tripMeter = massive[4];

            this.memoryResult = new BigDecimal(memoryRes); // Memory
            this.isArray = methodStatus.equals("true");  // Simplified notation: If methodStatus = true, then this.isArray = true
            setLanguage(languageStatus.equals("true")); // Simplified notation: If languageStatus = true, then this.isEnglish = true and update the phrases array
            clear(); // Clearing is necessary to avoid duplicating data during loading
            stack.write(dataInfo); // Write the contents of the 'dataInfo' string to the stack
            this.tripMeter = Integer.parseInt(tripMeter) + 1;


        } catch (IOException e) { // In case of unsuccessful loading from the file, restore default data
            e.printStackTrace();
            this.memoryResult = new BigDecimal("0.0");
            this.isArray = true;
            stack = new StackArr();
        }
    }

    /**
     * Function to switch the display of BigDecimal results to another number system.
     */
    public String universalConverter(BigDecimal out) throws Exception {
        int num = out.intValue(); // Convert to int with rounding to the nearest integer
        String result;
        switch (numberSystem) {
            case OUT_DEC ->  // If decimal is chosen, return BigDecimal as a string
                    result = out.toString();
            case OUT_BIN -> result = BinaryNumbers.convertDecimalToBinary(num); // Convert to binary
            case OUT_OCT -> result = OctalNumbers.convertDecimalToOctal(num); // Convert to octal
            case OUT_HEX -> result = HexNumbers.convertDecimalToHex(num); // Convert to hexadecimal
            case OUT_ROM -> result = RomeNumerals.convertDecimalToRome(num); // Convert to Roman numerals

            default -> result = "Unknown numeral system.";
        }
        return result;
    }

    /**
     * Function to switch the variable 'numberSystem' being used.
     */
    public void setNumberSystem(String newNumberSystem) {
        this.numberSystem = newNumberSystem;
    }

    public void push(BigDecimal value) {
        stack.push(value);
    }

    public BigDecimal pop() {
        return stack.pop();
    }

    public BigDecimal peek() {
        return stack.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public String infoEng() {
        return stack.infoEng();
    }

    public String infoRus() {
        return stack.infoRus();
    }

}

