package org.paleha.calculator_pl.memory;

import org.paleha.calculator_pl.logger.Writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileOperator {

    public boolean isFileExist(String memoryFileName){  // Check if the file exists
        return Files.exists(Paths.get(memoryFileName));
    }

    /** Функция записи строки в файл */
    public void wroteToMemoryFile(String data, String fileTxtName) throws IOException {
        try {
            Writer writer = new Writer(fileTxtName, false);
            writer.writerInTxt(data);
            writer.closeWriter();
        } catch (IOException e) {
            throw new IOException("Can't safe memory to the " + fileTxtName);
        }

    }

    /** Функция чтения строки из файла */
    public String readFromMemoryFile(String fileTxtName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileTxtName)));
    }
}
