import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Logger {
    private final String fileTxtName;
    private final String directoryName = "LoggerFiles";
    private final File outputFile; // Декларируем объект File, представляющий путь к файлу и имя файла

    public Logger() {  // The constructor should generate a file name and create the file itself.
        this.fileTxtName = generateFileName();
        // Объект типа Path содержащий строку и путем
        createNewDir(this.directoryName);
        this.outputFile = new File(this.directoryName, fileTxtName); // Объект File, представляющий путь к файлу и имя файла
        writeFileToDir(this.outputFile); // Write file to dir
    }

    /**
     * Function write file to dir
     */
    public void writeFileToDir(File outputFile) {
        Writer writer = new Writer(outputFile.toString(), true); // true = rewritable
        writer.writerInTxt("New document \n");
        writer.closeWriter();
    }

    /**
     * The function writes a string to a file located in the selected directory
     */
    public void writeLogToDoc(String content) {
        Writer writer = new Writer(outputFile.toString(), true); // Append false - overwrite, true - continue writing
        writer.writerInTxt(content);
        writer.closeWriter();
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
     * Function for reading files from the logger. Need for test only
     */
    public String readLog() {  // Check what the function returns if the file is empty! +++
        String result = ""; // The final string should contain line breaks.
        try {
            Scanner scanner = new Scanner(new File(this.fileTxtName));
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
        writeLogToDoc(logInput); // Append the string to the document.
    }

    /**
     * Function to write a string to the file.
     */
    public void logOutput(String string) {
        String dateTime = generateFileName();
        String logOutput = dateTime + " out " + string + "\n";
        // There can be exceptions here, for example, spaces.
        writeLogToDoc(logOutput); // Append the string to the document.
    }

    public void createNewDir(String path) { // "LoggerFiles/TempLogs"
        Path newDirPath = Paths.get(path); // Create Path of new Directory
        if (!Files.exists(newDirPath)) { // If the directory has not been created yet (at the first startup)
            try {
                Files.createDirectory(newDirPath); // Create new directory
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function for automatic clearing of the log storage folder
     */
    public void clearLogg() {  // tempPath = "LoggerFiles/TempLogs"
        final String tempPath = "LoggerFiles/TempLogs"; // указываем путь для субдиректории TempLogs

        createNewDir(tempPath); // tempPath = "LoggerFiles/TempLogs"
        File tempDirAndFile = new File(tempPath, this.fileTxtName); // Объект File, представляющий путь к файлу и имя файла
        try {
            File LogsFolder = new File(this.directoryName); // Файл с содержимым всей директории
            File[] filesInFolder = LogsFolder.listFiles(); // Создаем массив с именами файлов в папке Логер

            int i = 0;
            if (filesInFolder != null) {
                for (File file : filesInFolder) { // extract all the files one by one and calculate
                    i++;
                    if (i > 5) {
                        writeFileToDir(tempDirAndFile); //Copy file to dir TempLog
                    }
                    if (i > 9) {
                        cleanFolder(new File(this.directoryName)); // Delete all files from "LoggerFiles" folder

                        copyAllFromSourceDirToTargetDir(tempPath, this.directoryName); // Copy all files to "LoggerFiles" folder

                        cleanFolder(new File(tempPath)); // Delete all files from "tempPath" folder
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function for copying files from one folder to another
     */
    public void copyAllFromSourceDirToTargetDir(String sourceDir, String targetDir) {
        try {
            File sourceFolder = new File(sourceDir); // Файл с содержимым всей директории
            File[] filesInFolder = sourceFolder.listFiles(); // Создаем массив с файлами в папке из sourceDir

            if (filesInFolder != null) {
                for (File file : filesInFolder) { // get files one by one
                    File targetDirAndFile = new File(targetDir, file.getName()); // Object File = path to folder and name of file
                    writeFileToDir(targetDirAndFile); // Write file to folder "targetDir"
                }
            } else {
                System.out.println("Folder is not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function delete all files in folder
     */
    public void cleanFolder(File folder) {
        if (folder.exists()) {
            try {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        file.delete();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Need for test only
     */
    public String getFileName() {
        return this.fileTxtName;
    }

}

