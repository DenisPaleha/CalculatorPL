package stack;

import java.math.BigDecimal;

public class StackArr extends AbstractStack {
    private int freeIndex = 0;  // Array counter
    private BigDecimal[] nums = new BigDecimal[10]; // BigDecimal array

    /**
     * Returns true if the array stack is empty
     */
    public boolean isEmpty() {
        return freeIndex == 0;
    }

    /**
     * Adds BigDecimal value to the array. If the array is full, it increases the array size by 10.
     */
    public void push(BigDecimal value) {
        this.nums[this.freeIndex] = value; // freeIndex represents the index in the array, num stores numbers in the array in the order they are input.
        this.freeIndex = this.freeIndex + 1; // Increment the counter of entered numbers by 1
        if (this.freeIndex == this.nums.length) { // If the counter exceeds the array length
            this.nums = arrLengthChanging(this.nums.length + 10); // Call the array length-changing function
        }
    }

    /**
     * Function for dynamically increasing the array size
     */
    public BigDecimal[] arrLengthChanging(int newArraySize) {
        BigDecimal[] arr = new BigDecimal[newArraySize];  // Create a new array of length X
        // Fill the new space with zeros.
        // Copy data from the nums array to the arr array.
        System.arraycopy(this.nums, 0, arr, 0, this.nums.length);
        return arr;
    }

    public BigDecimal peek() {
        if (this.freeIndex < 1) {
            return new BigDecimal(0);
        } else {
            return this.nums[this.freeIndex - 1];
        }
    }

    public BigDecimal pop() {
        if (this.freeIndex == 0) { // If no numbers have been entered
            push(BigDecimal.valueOf(0)); // Add zero
        }
        BigDecimal result = this.nums[this.freeIndex - 1];
        this.freeIndex = this.freeIndex - 1;
        return result;
    }

    public String infoEng() {
        String resultInfo = "Array data structure is used.\n";
        if (isEmpty()) {
            resultInfo += "Content: Null. Array index: " + freeIndex + ". Array length: " + nums.length;
        } else {
            String data = copy();
            resultInfo += "Content: " + data + "Array index: " + freeIndex + " Array length: " + nums.length;
        }
        return resultInfo;
    }

    public String infoRus() {
        String resultInfo = "Используется структура данных массив.\n";
        if (isEmpty()) {
            resultInfo += "Содержимое: Null. Индекс массива: " + freeIndex + ". Длина массива: " + nums.length;
        } else {
            String data = copy();
            resultInfo += "Содержимое: " + data + " Индекс массива: " + freeIndex + " Длина массива: " + nums.length;
        }
        return resultInfo;
    }

    public int getFreeIndex() { //Need for test only
        return this.freeIndex;
    }

    public int getNumsLength() { //Need for test only
        return nums.length;
    }

    public BigDecimal getByIndex(int freeIndex) {
        return nums[freeIndex];
    }

}
