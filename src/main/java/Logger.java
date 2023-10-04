import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Logger {
    private final String fileName;
    private final File outputFile; // Декларируем объект File, представляющий путь к файлу и имя файла
    private Path dirPath; // Объект типа Path содержащий строку и путем

    public Logger() {  // The constructor should generate a file name and create the file itself.
        this.fileName = generateFileName();
        String directoryName = "LoggerFiles";
        this.dirPath = Paths.get(directoryName);

        createNewDir(directoryName);

        this.outputFile = new File(directoryName, fileName); // Объект File, представляющий путь к файлу и имя файла

        writeFileToDir(outputFile); // Write file to dir
    }

    public String getFileName() {
        return this.fileName;
    }

    /**
     * Function write file to dir
     */

    public void writeFileToDir(File outputFile) {
        try (FileWriter writer = new FileWriter(outputFile, true)) {
            // Creating the "fileLogg" document, deleting anything already present if the file already exists.
            writer.write(" New document \n");
            writer.close();
            writer.flush();
        } catch (IOException ex) {
//            System.err.println("Error creating file " + ex.getMessage());  +++ WTF?
        }
    }

    /**
     * Function returns a string with the date and time in the required format.
     */
    public String generateFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd_HH-mm-ss");
        return now.format(formatter);
    }

    /**
     * The write function should have parameters: a string with content and a boolean value "append,"
     * where false means overwriting the document, and true means continuing to write to the document.
     */
    public void writeLogToDoc(String content, boolean append) { // Append false - overwrite, true - continue writing
        try (FileWriter writer = new FileWriter(this.outputFile, append)) {
            // Creating the "MemoryTwo.txt" document with overwrite or append functionality.
            writer.write(content);
            writer.close();
            writer.flush();
        } catch (IOException ex) {
//            System.err.println("Error writing to memory " + ex.getMessage());  Always throws an error - why? +++
        }
    }

    /**
     * Function for reading files from the logger.
     */
    public String readLog() {  // Check what the function returns if the file is empty! +++
        String result = ""; // The final string should contain line breaks.
        try {
            Scanner scanner = new Scanner(new File(this.fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine(); // Each line starts with date and time.
                result += line + "\n";  // Add a line break.
            }
            scanner.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Hey! Logger notes are not being read!";
    }

    /**
     * Function to write a string to the file.
     */
    public void logInput(String string) {
        String dateTime = generateFileName();
        String logInput = dateTime + " in " + string + "\n";
        // There can be exceptions here, for example, spaces.
        writeLogToDoc(logInput, true); // Append the string to the document.
    }

    /**
     * Function to write a string to the file.
     */
    public void logOutput(String string) {
        String dateTime = generateFileName();
        String logOutput = dateTime + " out " + string + "\n";
        // There can be exceptions here, for example, spaces.
        writeLogToDoc(logOutput, true); // Append the string to the document.
    }

    public void createNewDir(String path) { // "LoggerFiles/TempLogs"
        Path dirTmpPath = Paths.get(path); // Создаем временную директорию
        if (!Files.exists(dirTmpPath)) { // If the directory has not been created yet (at the first startup)
            try {
                Files.createDirectory(dirTmpPath); // Create new directory
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearLogg(String tempPath) {  // tempPath = "LoggerFiles/TempLogs"
        File LogsFolder = new File("LoggerFiles"); // Файл с содержимым всей директории
        File[] filesInFolder = LogsFolder.listFiles(); // Создаем массив с именами файлов в папке Логер

        createNewDir(tempPath); // tempPath = "LoggerFiles/TempLogs"
        File tempDirAndFile = new File(String.valueOf(tempPath), this.fileName); // Объект File, представляющий путь к файлу и имя файла

        int i = 0; // счетчик минимума и максимума для начала сохранения и очистки.
        if (filesInFolder != null) {
            for (File file : filesInFolder) { // извлекаем все имена по очереди и считаем
                i++;
                if (i > 5) { // Если в папке более Х имен, начинаем записывать следующие в резервную строку
                    writeFileToDir(tempDirAndFile); //Copy file to dir TempLog
                }
                if (i > 9) {
                    // Удаляем все файлы из папки "LoggerFiles"
                    cleanFolder(new File(String.valueOf(this.dirPath)));
                    System.out.println("<Больше десяти - Удаляем");
                    // Копируем все файлы из папки темп в очищенную папку "LoggerFiles"
                    copyAllFromSourceDirToTargetDir(String.valueOf(tempPath), String.valueOf(LogsFolder));
//                    System.out.println("Файлы успешно скопированы назад");

                    // И удаляем все из папки темпа tempPath
                    cleanFolder(new File(String.valueOf(tempPath)));
//                    System.out.println("Папка Темп очищена");
                }
            }
        }

    }

    public void copyAllFromSourceDirToTargetDir(String sourceDir, String targetDir) {
        File sourceFolder = new File(sourceDir); // Файл с содержимым всей директории
        File[] filesInFolder = sourceFolder.listFiles(); // Создаем массив с файлами в папке из sourceDir

        if (filesInFolder != null) {
            for (File file : filesInFolder) { // извлекаем все имена по очереди и записываем
//                System.out.println(file.getName());
                File targetDirAndFile = new File(targetDir,file.getName()); // Объект File, представляющий путь к файлу и имя файла
                writeFileToDir(targetDirAndFile); // Write file to dir targetDir
            }
        }
    }

    public void cleanFolder(File folder) {
        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }

}

