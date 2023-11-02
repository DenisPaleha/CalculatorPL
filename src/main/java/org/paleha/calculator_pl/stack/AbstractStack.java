package org.paleha.calculator_pl.stack;

import java.math.BigDecimal;

public abstract class AbstractStack {

    // All classes that inherit an abstract class must contain the following functions:
    public abstract boolean isEmpty();

    public abstract void push(BigDecimal value);

    public abstract BigDecimal peek();

    public abstract BigDecimal pop();

    public abstract String infoEng();

    public abstract String infoRus();

    /**
     * The function returns the stack contents as a string
     */
    public String copy() {
        String dataString = "";
        BigDecimal tmp;
        StackArr mirror = new StackArr();
        if (isEmpty()) {
            return "0 ";
        } else {
            while (!isEmpty()) { // copy the contents of the stack to the mirror copy
                tmp = pop();
                mirror.push(tmp);
            }
            while (!mirror.isEmpty()) {
                tmp = mirror.pop();
                push(tmp);
                dataString += tmp.toString() + " ";
            }
        }
        return dataString;
    }

    /**
     * The function writes the contents of the string to the stack
     */
    public void write(String dataString) {
        String[] dataArr = dataString.split(" ");
        for (String data : dataArr) { // (int i = 0; i < dataArr.length; i++) extract the contents
            BigDecimal tmp = new BigDecimal(data);
            push(tmp);
        }
    }

}

