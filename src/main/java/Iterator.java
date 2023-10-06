import java.math.BigDecimal;

public class Iterator {

    private final int size;
    private int index;
    private final LinkedList list;

    public Iterator(LinkedList list) {
        this.list = list;
        this.size = list.listSize();
        this.index = 0;
    }

    public BigDecimal next() {
        BigDecimal value = list.get(index);
        index++;
        return value;
    }

    public boolean hasNext() {
        return index < size;
    }
}
