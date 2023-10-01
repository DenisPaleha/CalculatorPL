import java.math.BigDecimal;

public abstract class AbstractStack {

    // Все наследующие абстрактному классу классы должны содержать следующие функции:
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
            while (!isEmpty()) { // копируем содержимое стека в зеркальную копию
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
        String[] dataArr = dataString.split(" "); // Создаем массив строк и нарезаем в него строку.
        for (String data : dataArr) { // (int i = 0; i < dataArr.length; i++) последовательно извлекаем содержимое
            BigDecimal tmp = new BigDecimal(data);
            push(tmp);
        }
    }

}

