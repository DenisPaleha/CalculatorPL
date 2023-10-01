import java.math.BigDecimal;

public class Iterator {

    //    private Node node;
    private int size;
    private int index;
    private LinkedList list;
//    private Node head;

    public Iterator(LinkedList list) { // Создаем экземпляр итератора с данными списка
        this.list = list;
        this.size = list.listSize();
        this.index = 0;
//        this.node = linkedList.head;
    }

    public BigDecimal next() {
        // Так не работает - почему?
//        BigDecimal value;
//        Node node = list.getHead();
////        int currentIndex = 0;
//        while (node != null) {
////            if (currentIndex == this.index) {
//                value = node.data;
//                return value;
//
//            }
//            node = node.next;
////            currentIndex++;
////            this.index++;
////        }
//        return null;

        // Так работает
        BigDecimal value = list.get(index);
        index++;
        return value;
    }

    public boolean hasNext() {
        return index < size;
    }
}
