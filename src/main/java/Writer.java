import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private FileWriter writer;

    public Writer(String fileTxtName, boolean append) throws IOException {
            writer = new FileWriter(fileTxtName, append);
    }

    public void writerInTxt(String content) throws IOException {
        try {
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            System.out.println("Writing error");
        }
    }

    public void closeWriter() {
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Writing error");
        }
    }
}
