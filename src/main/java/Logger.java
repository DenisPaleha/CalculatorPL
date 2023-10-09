import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Logger {
    private String fileTxtName;
    private final String directoryName = "LoggerFiles";
    private File outputFile; // Creating File, combining the file path and file name

    public Logger() {  // The constructor should generate a file name and create the file itself.
        this.fileTxtName = generateFileName(); // generate a filename
        createNewDir(this.directoryName); // Create a folder if there is no folder
        this.outputFile = new File(this.directoryName, fileTxtName); // File object combining the file path and file name
        writeFileToDir(this.outputFile); // Write file to dir
    }

    /**
     * Function write file to dir
     */
    public void writeFileToDir(File outputFile) {
        try {
            Writer writer = new Writer(outputFile.toString(), true); // true = rewritable
            writer.writerInTxt("New document \n");
            writer.closeWriter();
        } catch (IOException e) {
            System.out.println("Writing to logger error");
        }
    }

    /**
     * TThe function writes a string to a file located in the selected directory
     */
    public void writeLogToDoc(String content) { // Append false - overwrite, true - continue writing
        try {
            Writer writer = new Writer(this.outputFile.toString(), true);
            writer.writerInTxt(content);
            writer.closeWriter();
        } catch (IOException e) {
            this.fileTxtName = generateFileName();
            createNewDir(this.directoryName);
            this.outputFile = new File(this.directoryName, fileTxtName);
            try {
                Writer writer = new Writer(outputFile.toString(), true);
                writer.writerInTxt("Replacement document \n");
            } catch (IOException ex) {
                System.out.println("Can't create ore write to file " + this.outputFile);
            }
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
     * Function to write a string to the file.
     */
    public void logInput(String string) {
        String dateTime = generateFileName();
        String logInput = dateTime + " in " + string + "\n";
        writeLogToDoc(logInput); // Append the string to the document.
    }

    /**
     * Function to write a string to the file.
     */
    public void logOutput(String string) {
        String dateTime = generateFileName();
        String logOutput = dateTime + " out " + string + "\n";
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
        final String tempPath = "LoggerFiles/TempLogs"; // Path for the TempLogs subdirectory

        createNewDir(tempPath); // tempPath = "LoggerFiles/TempLogs"
        File tempDirAndFile = new File(tempPath, this.fileTxtName); // File object containing the file path and file name
        try {
            File LogsFolder = new File(this.directoryName); // File with contents of the entire directory
            File[] filesInFolder = LogsFolder.listFiles(); // Create an array with file names in the Logger folder

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
            System.out.println("Failed to clear the folder 'LoggerFiles'");
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
            System.out.println("Unable to move folder " + sourceDir + " contents");
        }
    }

    /**
     * Function delete all files in folder
     */
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

    /**
     * Need for test only
     */
    public String getFileName() {
        return this.fileTxtName;
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
            System.out.println("Error reading a file " + this.fileTxtName);
        }
        return "Hey! Logger notes are not being read!";
    }

}

