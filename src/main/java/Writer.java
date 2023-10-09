import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private FileWriter writer;

    public Writer(String fileTxtName, boolean append) {
        try {
            writer = new FileWriter(fileTxtName, append);
        }  catch (FileNotFoundException e) {
            System.out.println("File " + fileTxtName + " is not found");
        } catch (IOException e) {
            System.out.println("Writing to " + fileTxtName +" error");

        }
    }

    public void writerInTxt(String content) {
        try {
            writer.write(content);
            writer.flush();
        }  catch (FileNotFoundException e) {
            System.out.println("File for writing is not found");
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
