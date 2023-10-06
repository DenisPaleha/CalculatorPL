import java.math.BigDecimal;

public class LinkedList {

    private Node head;  // 4 bytes, reference to an object of the Node class

    /** Function checks for the presence of data in the list */
    public boolean isEmpty() {
        return this.head == null;
    }

    /** Function adds data to the list */
    public void add(BigDecimal data) {
        Node newNode = new Node(data);

        if (this.head == null) {
            // Set the value of the node field of this object
            this.head = newNode;
        } else {
            Node node = this.head;
            while (node.next != null) { // Find the last node
                node = node.next;
            }
            node.next = newNode;
        }
    }

    /** Function returns a string with the contents of the entire list */
    public String printAll() {
        Node node = this.head;
        String listContent = "";
        while (node != null) {
            listContent += node.data + " ";
            node = node.next;
        }
        return listContent;
    }

    /** Function deletes the first node in the list */
    public void deleteFirst() {
        if (isEmpty()) {
            return;
        }
        head = head.next;
    }

    /** Function deletes the last node in the list */
    public void deleteLast() {
        Node node = this.head;
        if (isEmpty()) { // If the list is empty
            System.out.println("The list is empty, there's nothing to delete from it.");
        } else if (node.next == null) {
            deleteFirst();
        } else {
            Node previousNode;
            while (node.next != null) {
                previousNode = node;
                node = node.next;
                if (node.next == null) {
                    previousNode.next = null;
                }
            }
        }
    }

    /** Function deletes data from the list */
    public void deleteData(BigDecimal data) {
        Node node = this.head;
        if (isEmpty()) { // If the list is empty
            System.out.println("The list is empty, there's nothing to delete from it.");
        } else if (node.data.equals(data)) {
            deleteFirst();
        } else {
            Node previousNode;
            while (node.next != null) {
                previousNode = node;
                node = node.next;
                if (node.data.equals(data)) {
                    previousNode.next = node.next;
                }
            }
        }
    }

    /** Function returns data by index */
    public BigDecimal get(int index) {
        Node node = this.head;
        int currentIndex = 0;
        while (node != null) {
            if (currentIndex == index) {
                return node.data;
            }
            node = node.next;
            currentIndex = currentIndex + 1;
        }
        return null;
    }

    /** Function copies the current list and returns its copy */
    public LinkedList copyList() {
        LinkedList copy = new LinkedList();
        Node original = this.head;

        while (original != null) {
            copy.add(original.data);
            original = original.next;
        }
        return copy;
    }

    /** Function returns the size of the list */
    public int listSize() {
        int size = 0;
        Node node = this.head;
        while (node != null) {
            size++;
            node = node.next;
        }
        return size;
    }

    /** Function returns data from the last node in the list */
    public BigDecimal lastItemInList() {
        Node firstItem = this.head;
        if (firstItem == null) {
            return BigDecimal.valueOf(0);
        }
        while (firstItem.next != null) {
            firstItem = firstItem.next;
        }
        if (firstItem.data == null) {
            firstItem.data = BigDecimal.valueOf(0);
        }
        return firstItem.data;
    }

    public Node getHead(){return this.head;}

    public Iterator iterator() {
        return new Iterator(this);
    }
}

