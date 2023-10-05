import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private FileWriter writer;

    public Writer(String fileTxtName, boolean append) {
        try {
            writer = new FileWriter(fileTxtName, append);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void writerInTxt(String content) {
        try {
            writer.write(content);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void closeWriter() {
        try {
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
