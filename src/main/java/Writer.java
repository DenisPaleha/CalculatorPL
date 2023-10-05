import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private FileWriter writer;
    public static String fileName = "Memory.txt";

//    private boolean continueRecording = false;

    public Writer() {
        try {
            writer = new FileWriter(fileName, false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//    public String getFileName() {
//        return this.fileName;
//    }

//    public void setFileName(String newFileName){this.fileName = newFileName;}

//    public void setContinueRecording(boolean append){this.continueRecording = append;}

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
