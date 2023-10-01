import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

public class State {

    private boolean isArray = true; // Data structure switch: true = array; false = list;
    private boolean isEnglish = true; // Language switch
    BigDecimal memoryResult = new BigDecimal("0.0"); // Saved memory
    private String numberSystem = ConstantLibrary.OUT_DEC; // Number system
    private final String fileName = "MemoryTwo.txt";
    private AbstractStack stack = new StackArr();
    private String[] phrases = ConstantLibrary.ENGLISH_LANGUAGE.split("&");

    /**
     * Function to switch the program's language.
     */
    public void setLanguage(boolean newLanguage) {
        if (this.isEnglish == newLanguage) {
            return; // Nothing will happen if attempting to switch to the already set language
        }

        if (!newLanguage) { // newLanguage == false
            this.phrases = ConstantLibrary.RUSSIAN_LANGUAGE.split("&");
        } else {
            this.phrases = ConstantLibrary.ENGLISH_LANGUAGE.split("&");
        }
        this.isEnglish = newLanguage;
    }

    /** Function to return a string by index */
    public String getPhrases(int index){return this.phrases[index];}
    /** Function to return the length of the phrases array */
    public int phrasesArrLength(){return this.phrases.length;}

    public boolean isEnglish(){return this.isEnglish;}

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
    public String saveState() {  // Make the method return a String for testing purposes
        String dataInfo = stack.copy(); // Create a string with the stack's content
        BigDecimal tmp = peek(); // Store the last value
        String memoryRes = tmp.toString(); // Store it as a string
        String methodStatus = String.valueOf(this.isArray); // Stores the method state (array/list - true/false).
        String languageStatus = String.valueOf(this.isEnglish); // Stores the language state (English/Russian - true/false)

        String allMemory = dataInfo + "\n" + memoryRes + "\n" + methodStatus + "\n" + languageStatus + "\n";
        String allMemoryTest = dataInfo + " " + memoryRes + " " + methodStatus + " " + languageStatus;
        // Pack all memory types into strings
        writerInTxt(allMemory, this.fileName); // Save memory to the MemoryTwo.txt document
        System.out.println("Data saved...");
        return allMemoryTest;
    }

    /**
     * Universal function for writing to a txt file
     * Input parameters: string with data, file name string, for example, 'memory.txt'
     */
    public void writerInTxt(String content, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            // Create the 'MemoryOne.txt' document with the append parameter set to false
            writer.write(content); // Write the contents of the 'str' variable to the txt document
            writer.close();
            writer.flush();
        } catch (IOException ex) {
//            System.out.println("Write error in memory."); // Why does this run every time? IOException ex +++
        }
    }

    /**
     * Function for loading the State from saved txt data.
     */
    public void loadState() {
        if (!Files.exists(Paths.get(fileName))) { // Check if the file exists
            saveState(); // If the file does not exist, save the state
        }

        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
            // Now 'fileContent' contains the file's content as a single string
            String[] massive = fileContent.split("\n"); // Create a string array and split the 'line' string into it

            String dataInfo = massive[0]; // Stack contents
            String memoryRes = massive[1]; // Memory of the last number
            String methodStatus = massive[2]; // Data structure state
            String languageStatus = massive[3]; // Language state - as a string

            this.memoryResult = new BigDecimal(memoryRes); // Memory
            this.isArray = methodStatus.equals("true");  // Simplified notation: If methodStatus = true, then this.isArray = true
            setLanguage(languageStatus.equals("true")); // Simplified notation: If languageStatus = true, then this.isEnglish = true and update the phrases array
            clear(); // Clearing is necessary to avoid duplicating data during loading
            stack.write(dataInfo); // Write the contents of the 'dataInfo' string to the stack

        } catch (IOException e) { // In case of unsuccessful loading from the file, restore default data
            e.printStackTrace();
            this.memoryResult = new BigDecimal("0.0");
            this.isArray = true;  // Maybe this is redundant? +++
            stack = new StackArr();
        }
    }

    /**
     * Function to switch the display of BigDecimal results to another number system.
     */
    public String universalConverter(BigDecimal out) {
        int num = out.intValue(); // Convert to int with rounding to the nearest integer
        String result;
        switch (numberSystem) {
            case ConstantLibrary.OUT_DEC:  // If decimal is chosen, return BigDecimal as a string
                result = out.toString();
                break;
            case ConstantLibrary.OUT_BIN:
                result = BinaryNumbers.convertDecimalToBinary(num); // Convert to binary

                break;
            case ConstantLibrary.OUT_OCT:
                result = OctalNumbers.convertDecimalToOctal(num); // Convert to octal

                break;
            case ConstantLibrary.OUT_HEX:
                result = HexNumbers.convertDecimalToHex(num); // Convert to hexadecimal

                break;
            case ConstantLibrary.OUT_ROM:
                try {
                    result = RomeNumerals.convertDecimalToRome(num); // Convert to Roman numerals
                } catch (Exception e) {
//                e.printStackTrace();
                    System.out.println(e.getMessage()); // In case of an error, display the message.
                    result = out.toString(); // Return the original value
                }
                break;
            default:
                result = "Unknown numeral system.";
                break;
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

