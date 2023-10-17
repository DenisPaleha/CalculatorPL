import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Logger {
    private final String fileTxtName;
    private final String loggerPath = "LoggerFiles";
    private final String tempPath = "LoggerFiles/TempLogs";
    //    private final String newFile = "New document \n";
    private final File outputFile; // Creating File, combining the file path and file name


    public Logger() {  // The constructor should generate a file name and create the file itself.
        this.fileTxtName = generateFileName(); // generate a filename
        createNewDir(this.loggerPath); // Create a logger folder if there is no folder
        createNewDir(this.tempPath); // Create a temp folder if there is no folder
        this.outputFile = new File(this.loggerPath, fileTxtName); // File object combining the file path and file name
        writeFileToDir(this.outputFile, "New document \n"); // Write file to dir
    }

    /**
     * Function write file to dir
     */
    public void writeFileToDir(File outputFile, String content) {
        try {
            Writer writer = new Writer(outputFile.toString(), true); // true = rewritable
            writer.writerInTxt(content);
            writer.closeWriter();
        } catch (IOException e) {
            System.out.println("Writing to logger folder error");
            // тут нужно что-то по эффективнее
            // нужно попытаться перезаписать файл и директорию в случае если они были утрачены        +++
            // если не получилось, то пробросить сообщение дальше                                     +++
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
            createNewDir(loggerPath);
            if (!Files.exists(sourceDirectory) || !Files.isDirectory(sourceDirectory)) {
                throw new IOException("Can't create a folder " + loggerPath);
            }
        }

        if (!Files.exists(targetDirectory) || !Files.isDirectory(targetDirectory)) {
            createNewDir(tempPath);
            if (!Files.exists(targetDirectory) || !Files.isDirectory(targetDirectory)) {
                throw new IOException("Can't create a folder " + tempPath);
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
        writeFileToDir(this.outputFile, logInput); // Append the string to the document.
    }

    /**
     * Function to write a string to the file.
     */
    public void logOutput(String string) {
        String dateTime = generateFileName();
        String logOutput = dateTime + " out " + string + "\n";
        writeFileToDir(this.outputFile, logOutput); // Append the string to the document.
    }

    public void createNewDir(String folderPath) { // "LoggerFiles/TempLogs"
        Path newDirPath = Paths.get(folderPath); // Create Path of new Directory
        if (!Files.exists(newDirPath)) { // If the directory has not been created yet (at the first startup)
            try {
                Files.createDirectory(newDirPath); // Create new directory
            } catch (IOException e) {
                System.out.println("Can't create a folder " + folderPath);                    // +++ пробросить дальше
            }
        }
    }

    /**
     * Функция копирует файлы из папки логер в папку темп если в ней более 3 файлов
     */

    public void CopyFilesFromLoggerToTemp() throws Exception {
        Path sourceDirectory = Paths.get(loggerPath);
        Path targetDirectory = Paths.get(tempPath);
        int startingFileIndex = 3; // index shows from which file we start copying to Temp folder
        isFoldersExist();
        try {

            // Get the list of files in the source directory Logger
            List<Path> files = Files.list(sourceDirectory).toList();

            for (int i = startingFileIndex; i < files.size() - 1; i++) {
                // If you remove "-1" from the "files.size() - 1" expression, an extra subdirectory will be created.
                Path file = files.get(i);
                // Create a path for the target directory
                Path targetFile = targetDirectory.resolve(file.getFileName());
                // Copy the file to the target directory
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
            }

            CopyFilesTempAndClean(); // Move files from the Temp folder back to the Logger folder when it is full
        } catch (IOException e) {
            e.printStackTrace();
            // Тут нужно что-то другое и не тут, а в Main                                                          +++
        }
    }

    /**
     * The function copies files from the Temp folder back to the Logger folder if there are more than X files there
     */
    public void CopyFilesTempAndClean() throws Exception {
        Path sourceDirectory = Paths.get(tempPath);
        Path targetDirectory = Paths.get(loggerPath);
        int startingFileIndex = 3; // If there are more than X files in the folder
        isFoldersExist();
        try {
            // Get the list of files in the source directory Temp
            List<Path> files = Files.list(sourceDirectory).toList();

            if (files.size() >= startingFileIndex) {
                cleanFolder(loggerPath);  // delete everything from the loggerPath directory
                for (Path file : files) {
                    // Create a path for the target directory
                    Path targetFile = targetDirectory.resolve(file.getFileName());
                    // Copy the file to the target directory
                    Files.move(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Тут нужно что-то предпринять и пробросить                                                       +++
        }
    }

    /**
     * Function delete all files in folder
     */
    public void cleanFolder(String folderPath) {
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
                System.out.println("Folder " + folderPath + " doesn't exist");                      // Вот куда это?
            }
        } catch (Exception e) {
            System.out.println("Cannot delete files from " + folderPath);                     // Пробрасываем наверх +++
        }
    }

}