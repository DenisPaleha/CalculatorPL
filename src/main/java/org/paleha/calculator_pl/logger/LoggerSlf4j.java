package org.paleha.calculator_pl.logger;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.util.StatusPrinter;
import org.paleha.calculator_pl.exception.LoggerException;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class LoggerSlf4j extends AbstractLogger {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    private final int availableFilesQuantity = 5; // minimum number of files in the folder loggerPath
    public LoggerSlf4j() throws LoggerException {
        ensureLogFolderExists();
        configureLogger();
        logOutput("Slf4j logger" +
                " is used.", "New document: ");
    }


    /** Function for writing a string to the log */
    public void logOutput(String result, String prefix) throws LoggerException {
        ensureLogFolderExists();
        deleteFileIfNeed();
        String logMessage = prefix + " " + result;
        logger.info(logMessage);
    }

    /** Checking the existence of the Logger folder */
    private void ensureLogFolderExists() throws LoggerException {
        File logFolder = new File("LoggerFiles");
        if (!logFolder.exists() && !logFolder.mkdirs()) {
            throw new LoggerException("Failed to create directory: LoggerFiles");
        }
    }

    private void configureLogger() throws LoggerException {
        String timestamp = new SimpleDateFormat("yy-MM-dd_HH-mm-ss").format(new Date());
        String logFileName = "LoggerFiles/log_" + timestamp + ".txt";

        // Disable the output of messages to the console for the root logger
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset(); // You need to reset the context before reconfiguration

        // Create logger context and configurator
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(loggerContext);

        // Configuring the logger using the configuration file
        try {
            configurator.doConfigure(Objects.requireNonNull(getClass().getClassLoader().getResource("logback.xml")));
        } catch (Exception e) {
            throw new LoggerException("Failed to configure logger");
        }

        // Open the log file
        File logFile = new File(logFileName);
        try {
            if (!logFile.exists() && !logFile.createNewFile()) {
                throw new LoggerException("Failed to create log file: " + logFileName);
            }
        } catch (IOException e){
            throw new LoggerException("Failed to create log file: " + logFileName);
        }

        // Set the handler to write logs to a file
        FileAppender fileAppender = (FileAppender) logger.getAppender("FILE");
        if (fileAppender != null) {
            fileAppender.setFile(logFileName);
            fileAppender.start();
        } else {
            throw new LoggerException("Failed to find FILE appender in logback.xml");
        }

        // Output Logback status to the console (this may be useful for debugging)
        StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);
    }

    /**
     * Function for deleting files from the logger 2
     */
    private void deleteFileIfNeed() throws LoggerException {
        Path sourceDirectory = Paths.get("LoggerFiles");

        try (Stream<Path> filesStream = Files.list(sourceDirectory)) {
            List<Path> files = filesStream.toList(); // Get the list of files in the source directory Temp

            if (availableFilesQuantity < files.size()) { // If the maximum number of files in a folder is exceeded
                removeOldestFile();
            }
        } catch (IOException e) {
            throw new LoggerException("Can't delete file from folder 'LoggerFiles'");
        }
    }

    /**
     * Function for deleting files from the logger 1
     */
    private void removeOldestFile() {
        File folder = new File("LoggerFiles");
        File[] files = folder.listFiles();

        if (files != null && files.length > 0) {
            Arrays.sort(files, Comparator.comparing(File::lastModified)); // Sort files by time
            File oldestFile = files[0]; // Get the oldest file
            oldestFile.delete();
        }
    }

}
