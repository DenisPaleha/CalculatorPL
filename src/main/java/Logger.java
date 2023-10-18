import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

public class Logger {
    private final String loggerPath = "LoggerFiles";
    private final String tempPath = "LoggerFiles/TempLogs";
    private File outputFile; // Creating File, combining the file path and file name
    private final int availableFileIndex = 3; // Minimum number of logs available

    public Logger() throws IOException {  // The constructor should generate a file name and create the file itself.
        String fileTxtName = generateFileName(); // generate a filename
        createNewDir(this.loggerPath); // Create a logger folder if there is no folder
        createNewDir(this.tempPath); // Create a temp folder if there is no folder
        this.outputFile = new File(this.loggerPath, fileTxtName); // File object combining the file path and file name
        writeFileToDir(this.outputFile, "New document \n"); // Write file to dir
    }

    /**
     * Function write file to dir
     */
    public void writeFileToDir (File outputFile, String content) throws IOException {
        try {
            Writer writer = new Writer(outputFile.toString(), true); // true = rewritable
            writer.writerInTxt(content);
            writer.closeWriter();
        } catch (IOException e) {
            throw new IOException("Writing to logger folder error");
        }
    }

    /**
     * Method checks if the file exists
     */
    public void isFileExist() throws Exception {
        if (!Files.exists(this.outputFile.toPath())) {
            String fileTxtName = generateFileName();
            this.outputFile = new File(this.loggerPath, fileTxtName);
            if (!Files.exists(this.outputFile.toPath())) {
                throw new Exception("Can't create the log to file " + outputFile);
            }
        }
    }

    /**
     * The function checks for directories and corrects errors if possible
     */
    public void isFoldersExist() throws Exception {
        Path sourceDirectory = Paths.get(tempPath);
        Path targetDirectory = Paths.get(loggerPath);

        // Check if the specified directories exist
        if (!Files.exists(sourceDirectory) || !Files.isDirectory(sourceDirectory)) {
            createNewDir(this.loggerPath);
            if (!Files.exists(sourceDirectory) || !Files.isDirectory(sourceDirectory)) {
                throw new IOException("Can't create a folder " + this.loggerPath);
            }
        }

        if (!Files.exists(targetDirectory) || !Files.isDirectory(targetDirectory)) {
            createNewDir(this.tempPath);
            if (!Files.exists(targetDirectory) || !Files.isDirectory(targetDirectory)) {
                throw new IOException("Can't create a folder " + this.tempPath);
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
    public void logInput(String string) throws Exception {
        String dateTime = generateFileName();
        String logInput = dateTime + " in " + string + "\n";
        isFileExist();
        writeFileToDir(this.outputFile, logInput); // Append the string to the document.
    }

    /**
     * Function to write a string to the file.
     */
    public void logOutput(String string) throws Exception {
        String dateTime = generateFileName();
        String logOutput = dateTime + " out " + string + "\n";
        isFileExist();
        writeFileToDir(this.outputFile, logOutput); // Append the string to the document.
    }

    public void createNewDir(String folderPath) { // "LoggerFiles/TempLogs"
        Path newDirPath = Paths.get(folderPath); // Create Path of new Directory
        if (!Files.exists(newDirPath)) { // If the directory has not been created yet (at the first startup)
            try {
                Files.createDirectory(newDirPath); // Create new directory
            } catch (IOException e) {
                System.out.println("Can't create a folder " + folderPath);
            }
        }
    }

    /**
     * Функция копирует файлы из папки логер в папку темп если в ней более 3 файлов
     */
    public void CopyFilesFromLoggerToTemp() throws Exception {
        Path sourceDirectory = Paths.get(loggerPath);
        Path targetDirectory = Paths.get(tempPath);
        isFoldersExist();

        try (Stream<Path> filesStream = Files.list(sourceDirectory)) {
            List<Path> files = filesStream.toList(); // Get the list of files in the source directory Temp

            for (int i = availableFileIndex; i < files.size() - 1; i++) {
                // If you remove "-1" from the "files.size() - 1" expression, an extra subdirectory will be created.
                Path file = files.get(i);
                Path targetFile = targetDirectory.resolve(file.getFileName());// Create a path for the target directory
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);// Copy the file to the target directory
            }

            CopyFilesTempAndClean(); // Move files from the Temp folder back to the Logger folder when it is full
        } catch (IOException e) {
            throw new IOException("Can't copy files from " + loggerPath + " to " + tempPath);
        }
    }

    /**
     * The function copies files from the Temp folder back to the Logger folder if there are more than X files there
     */
    public void CopyFilesTempAndClean() throws Exception {
        Path sourceDirectory = Paths.get(tempPath);
        Path targetDirectory = Paths.get(loggerPath);
        isFoldersExist();
        try (Stream<Path> filesStream = Files.list(sourceDirectory)) {
            List<Path> files = filesStream.toList(); // Get the list of files in the source directory Temp

            if (files.size() >= availableFileIndex) {
                cleanFolder(loggerPath);  // delete everything from the loggerPath directory
                for (Path file : files) {
                    Path targetFile = targetDirectory.resolve(file.getFileName()); // Create a path for the target directory
                    Files.move(file, targetFile, StandardCopyOption.REPLACE_EXISTING); // Copy the file to the target directory
                }
            }
        } catch (IOException e) {
            throw new IOException("Can't clean the TempLogs folder");
        }
    }

    /**
     * Function delete all files in folder
     */
    public void cleanFolder(String folderPath) throws IOException {
        File directory = new File(folderPath);
        try {
            if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        file.delete();
                    }
                }
            } else {
                throw new IOException("Folder " + folderPath + " doesn't exist");
            }
        } catch (IOException e) {
            throw new IOException("Cannot delete files from " + folderPath);
        }
    }

}