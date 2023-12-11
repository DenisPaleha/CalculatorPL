package org.paleha.calculator_pl.logger;

import org.paleha.calculator_pl.exception.LoggerException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public final class LoggerPl extends AbstractLogger {
    private final String loggerPath = "LoggerFiles";
    private File outputFile; // Creating File, combining the file path and file name
    private final int availableFilesQuantity = 5; // minimum number of files in the folder loggerPath

    public LoggerPl() throws LoggerException {  // The constructor should generate a file name and create the file itself.
        String fileTxtName = "log_" + generateFileName() + ".txt"; // generate a filename
        createNewDir(this.loggerPath); // Create a logger folder if there is no folder
        this.outputFile = new File(this.loggerPath, fileTxtName); // File object combining the file path and file name
        writeFileToDir(this.outputFile, "New document:\n"); // Write file to dir
    }

    /**
     * Function write file to dir
     */
    private void writeFileToDir(File outputFile, String content) throws LoggerException {
        try {
            deleteFileIfNeed();
        } catch (Exception e) {
            throw new LoggerException("Cannot delete file from " + loggerPath + "The file not exist or folder is empty");
        }

        try {
            Writer writer = new Writer(outputFile.toString(), true); // true = rewritable
            writer.writerInTxt(content);
            writer.closeWriter();
        } catch (IOException e) {
            throw new LoggerException("Writing to logger folder error");
        }
    }

    /**
     * Method checks if the file exists
     */
    private void isFileExist() throws LoggerException {
        if (!Files.exists(this.outputFile.toPath())) {
            isFoldersExist(); // Checking if folders exist first
            String fileTxtName = generateFileName();
            this.outputFile = new File(this.loggerPath, fileTxtName);
            writeFileToDir(this.outputFile, "New document: PL logger is used. \n"); // Write new file to dir
            if (!Files.exists(this.outputFile.toPath())) {
                throw new LoggerException("Can't create the log to file " + outputFile);
            }
        }
    }

    /**
     * The function checks for directories and corrects errors if possible
     */
    private void isFoldersExist() throws LoggerException {
        Path targetDirectory = Paths.get(loggerPath);

        if (!Files.isDirectory(targetDirectory)) { // Check if the specified directories exist
            createNewDir(this.loggerPath);
        }
    }

    /**
     * Function returns a string with the date and time in the required format.
     */
    private String generateFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd_HH-mm");
        return now.format(formatter);
    }

    /**
     * Function to write a string to the file.
     */
    public void logOutput(String result, String prefix) throws LoggerException {
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("dd_HH-mm-ss");
        String logOutput = dateTime + " [LogPl] " + prefix + ": " + result + "\n";
        isFileExist();
        writeFileToDir(this.outputFile, logOutput); // Append the string to the document.
    }

    private void createNewDir(String folderPath) throws LoggerException { // "LoggerFiles/TempLogs"
        Path newDirPath = Paths.get(folderPath); // Create Path of new Directory
        if (!Files.exists(newDirPath)) { // If the directory has not been created yet (at the first startup)
            try {
                Files.createDirectory(newDirPath); // Create new directory
            } catch (IOException e) {
                throw new LoggerException("Can't create a folder " + this.loggerPath);
            }
        }
    }

    /**
     * Function for deleting files from the logger 2
     */
    private void deleteFileIfNeed() throws LoggerException {
        Path sourceDirectory = Paths.get(loggerPath);
        isFoldersExist();

        try (Stream<Path> filesStream = Files.list(sourceDirectory)) {
            List<Path> files = filesStream.toList(); // Get the list of files in the source directory Temp

            if (availableFilesQuantity < files.size()) { // If the maximum number of files in a folder is exceeded
                removeOldestFile();
            }
        } catch (IOException e) {
            throw new LoggerException("Can't delete file from folder " + this.loggerPath);
        }
    }

    /** Function for deleting files from the logger 1 */
    private void removeOldestFile() {
        File folder = new File(this.loggerPath);
        File[] files = folder.listFiles();

        if (files != null && files.length > 0) {
            Arrays.sort(files, Comparator.comparing(File::lastModified)); // Sort files by time
            File oldestFile = files[0]; // Get the oldest file
            oldestFile.delete();
        }
    }

}