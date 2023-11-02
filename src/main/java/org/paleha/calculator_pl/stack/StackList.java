package org.paleha.calculator_pl.stack;

import org.paleha.calculator_pl.list.LinkedList;
import java.math.BigDecimal;

public class StackList extends AbstractStack {
    public LinkedList list = new LinkedList();

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public void push(BigDecimal data) {
        this.list.add(data);
    }

    public BigDecimal peek() {
        return this.list.lastItemInList();
    }

    public BigDecimal pop() {
        BigDecimal lastItem;
        lastItem = this.list.lastItemInList(); // Find the last value in the list
        this.list.deleteLast(); // Delete last value in the list
        return lastItem;
    }

    /** The function returns a string with the contents of Info */
    public String infoEng() {
        String resultInfo = "Linked List data structure is used.\n";
        if (isEmpty()) {
            resultInfo += "Content: Null";
        } else {
            String data = copy();
            resultInfo += "Content: " + data;
        }
        return resultInfo;
    }

     public String infoRus() {
        String resultInfo = "Используется структура данных связный список.\n";
        if (isEmpty()) {
            resultInfo += "Содержимое: Null";
        } else {
            String data = copy();
            resultInfo += "Содержимое: " + data;
        }
        return resultInfo;
    }
}

