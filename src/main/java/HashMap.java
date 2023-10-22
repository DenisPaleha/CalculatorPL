import java.util.Objects;
import static constants.ConstantLibrary.*;

public final class HashMap {

    private int capacity;
    private String[][] keys;
    private String[][] values;

    public HashMap(int capacity) { // The array size is set when creating an instance
        this.capacity = capacity;
        this.keys = new String[16][capacity]; // Set the size of the table array
        this.values = new String[16][capacity]; // Set the size of the table array
    }

    public int getCapacity() {
        return this.capacity;
    }

    /**
     * This function looks for a cell to write to.
     */
    public void put(String key, String value) {
        int hash = key.hashCode(); // Get the hash code of the key
        int index = hash % this.keys.length; // Divide by the array length and get the remainder, which is the array index
        if (index < 0) { // Sometimes key.hashCode() returns a negative number,
            index = index * -1; // so check and make it positive
        }

        for (int i = 0; i < this.keys[0].length; i++) {
            if (this.keys[index][i] == null) { // If the entry does not exist, write the key and value
                this.keys[index][i] = key;
                this.values[index][i] = value;

                // Dynamic array expansion
                if (i == this.keys[0].length - 1) { // If writing to the last cell of the array, increase the array
                    this.capacity = this.capacity + 1; // Increase the bucket array
                    String[][] tmpKeys = new String[16][this.capacity]; // Set the size of the table array
                    String[][] tmpValues = new String[16][this.capacity]; // Set the size of the table array
                    for (i = 0; i < this.keys.length; i++) { // Iterate through rows
                        for (int j = 0; j < this.keys[0].length; j++) { // Iterate through columns
                            tmpKeys[i][j] = this.keys[i][j];
                            tmpValues[i][j] = this.values[i][j];
                        }
                    }
                    this.keys = tmpKeys;
                    this.values = tmpValues;
                    break;
                }
                break;
            } else if (this.keys[index][i].equals(key)) {
                this.values[index][i] = value;
                break;
            }
        }
    }

    /**
     * This function finds the value by key.
     */
    public String get(String key) {
        boolean exists = hasKey(key);
        if (exists) {
            int hash = key.hashCode(); // Find the hash code of the key
            int index = hash % this.keys.length; // Divide by the array length and get the remainder, which is the array index
            if (index < 0) {
                index = index * -1;
            }
            for (int i = 0; i < this.keys[0].length; i++) {
                if (Objects.equals(this.keys[index][i], key)) { // Compare values, handling null
                    return this.values[index][i];
                }
            }
        }
        return null; // Return null if the key is absent
    }

    /**
     * This method prints the KEYS of the table.
     */
    public void printHashMapKeys() {
        for (String[] key : this.keys) { // Iterate through rows
            for (int j = 0; j < this.keys[0].length; j++) { // Iterate through columns
                System.out.print(" " + key[j] + " "); // Print the element
            }
            System.out.println(); // Line break for visual table formatting
        }
        System.out.println();
    }

    /**
     * This method prints the VALUES of the table.
     */
    public void printHashMapValues() {
        for (String[] value : this.values) { // Iterate through rows
            for (int j = 0; j < this.values[0].length; j++) { // Iterate through columns
                System.out.print(" " + value[j] + " "); // Print the element
            }
            System.out.println(); // Line break for visual table formatting
        }
        System.out.println();
    }

    /**
     * This function returns the number of key-value pairs in the table.
     */
    public int size() {
        int total = 0;

        for (String[] key : this.keys) { // Iterate through rows
            for (int j = 0; j < this.keys[0].length; j++) { // Iterate through columns
                if (key[j] != null) {
                    total++;
                }
            }
        }
        return total;
    }

    /**
     * This function checks if a pair exists in the table.
     */
    public boolean hasKey(String key) {
        int hash = key.hashCode(); // Get the hash code of the key
        int index = hash % this.keys.length; // Divide by the array length and get the remainder, which is the array index
        if (index < 0) {
            index = index * -1;
        }
        for (int i = 0; i < this.keys[0].length; i++) {
            if (Objects.equals(this.keys[index][i], key)) { // Compare objects using this form to handle null
                return true;
            }
        }
        return false;
    }

    /**
     * This method removes data by key if it exists.
     */
    public void removeFromHashmap(String key) throws Exception {
        boolean exists = hasKey(key);
        if (exists) {
            int hash = key.hashCode(); // Get the hash code of the key
            int index = hash % this.keys.length; // Divide by the array length and get the remainder, which is the array index
            if (index < 0) {
                index = index * -1;
            }
            for (int i = 0; i < this.keys[0].length; i++) {
                if (Objects.equals(this.keys[index][i], key)) { // Compare objects using this form to handle null
                    this.keys[index][i] = null;
                    this.values[index][i] = null;
                }
            }
        } else {
            throw new Exception("You can't delete what doesn't exist...");
        }
    }

    /**
     * This method writes data for Main class.
     */

    public void loadMainHashMap() {
        put("e", EXIT);
        put("exit", EXIT);
        put("выход", EXIT);

        put("m", MEMORY);
        put("м", MEMORY); // рус.
        put("п", MEMORY);

        put("c", CLEAR);
        put("с", CLEAR); // рус.

        put("h", HELP);
        put("help", HELP);
        put("справка", HELP);
        put("помощь", HELP);

        put("info", INFO);
        put("инфо", INFO);

        put("sm", SWITCH_METHOD);
        put("переключить", SWITCH_METHOD);

        put("s", SAVE);
        put("save", SAVE);
        put("сохранить", SAVE);

        put("torom", TO_ROME);
        put("torome", TO_ROME);
        put("римские", TO_ROME);
        put("рим", TO_ROME);

        put("tooct", TO_OCTAL);
        put("oct", TO_OCTAL);
        put("tooctal", TO_OCTAL);

        put("tohex", TO_HEX);
        put("hex", TO_HEX);

        put("tobin", TO_BIN);
        put("binary", TO_BIN);
        put("tobinary", TO_BIN);

        put("outdec", OUT_DEC);
        put("outbin", OUT_BIN);
        put("outoct", OUT_OCT);
        put("outhex", OUT_HEX);
        put("outrom", OUT_ROM);

        put("outrus", OUT_RUS);
        put("rus", OUT_RUS);
        put("outeng",OUT_ENG);
        put("eng",OUT_ENG);
    }

    /**
     * This method writes data for Core class.
     */

    public void loadCoreHashMap() {
        put("+", PLUS);
        put("plus", PLUS);
        put("плюс", PLUS);

        put("-", MINUS);
        put("minus", MINUS);
        put("минус", MINUS);

        put("*", MULTIPLY);
        put("multiply", MULTIPLY);
        put("умножить", MULTIPLY);

        put("/", DIVIDE);
        put(":", DIVIDE);
        put("divide", DIVIDE);
        put("разделить", DIVIDE);
        put("поделить", DIVIDE);
        put("делить", DIVIDE);

        put("st", EXPONENT);
        put("exponent", EXPONENT);
        put("ст", EXPONENT);
        put("степень", EXPONENT);

        put("root", SQUARE);
        put("square", SQUARE);
        put("sq", SQUARE);
        put("корень", SQUARE);

        put("%", PERCENT);
        put("percent", PERCENT);
        put("процент", PERCENT);
        put("проценты", PERCENT);

    }
}

