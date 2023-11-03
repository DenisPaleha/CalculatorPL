package org.paleha.calculator_pl.memory;

import org.paleha.calculator_pl.logger.Writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileOperator {
    private final String fileTxtName = "Memory.txt";

    public boolean isFileExist(){  // Check if the file exists
        return Files.exists(Paths.get(this.fileTxtName));
    }

    /** Функция записи строки в файл */
    public void wroteToMemoryFile(String data) throws IOException {
        try {
            Writer writer = new Writer(this.fileTxtName, false);
            writer.writerInTxt(data);
            writer.closeWriter();
        } catch (IOException e) {
            throw new IOException("Can't safe memory to the " + this.fileTxtName);
        }

    }

    /** Функция чтения строки из файла */
    public String readFromMemoryFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get(this.fileTxtName)));
    }
}
