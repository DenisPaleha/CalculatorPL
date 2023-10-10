import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private final FileWriter writer;

    public Writer(String fileTxtName, boolean append) throws IOException {
            writer = new FileWriter(fileTxtName, append);
    }

    public void writerInTxt(String content) throws IOException {
            writer.write(content);
            writer.flush();
    }

    public void closeWriter() throws IOException {
            writer.close();
    }
}
