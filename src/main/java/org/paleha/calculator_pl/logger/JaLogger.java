package org.paleha.calculator_pl.logger;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class JaLogger extends AbstractLogger {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    public JaLogger() throws IOException {
        configureLogger();
    }

    public void logOutput(String result, String prefix) throws Exception {
        deleteFileIfNeed();
        String logMessage = prefix + " " + result;
        logger.info(logMessage);
    }

    private void configureLogger() throws IOException {
        String timestamp = new SimpleDateFormat("yy-MM-dd_HH-mm-ss").format(new Date());
        String logFileName = "LoggerFiles/log_" + timestamp + ".txt";

        // Отключаем вывод сообщений в консоль для корневого логгера
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset(); // Необходимо сбросить контекст перед повторной настройкой

        // Создаем контекст логгера и конфигуратор
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(loggerContext);

        // Настраиваем логгер с использованием файла конфигурации
        try {
            configurator.doConfigure(getClass().getClassLoader().getResource("logback.xml"));
        } catch (Exception e) {
            // Если возникает ошибка при конфигурации логгера, выводим сообщение и выбрасываем IOException
            System.err.println("Error configuring logger: " + e.getMessage());
            throw new IOException("Failed to configure logger", e);
        }

        // Открываем файл лога
        File logFile = new File(logFileName);
        if (!logFile.exists() && !logFile.createNewFile()) {
            throw new IOException("Failed to create log file: " + logFileName);
        }

        // Устанавливаем обработчик для записи логов в файл
        FileAppender fileAppender = (FileAppender) logger.getAppender("FILE");
        if (fileAppender != null) {
            fileAppender.setFile(logFileName);
            fileAppender.start();
        } else {
            throw new IOException("Failed to find FILE appender in logback.xml");
        }

        // Выводим статус Logback в консоль (это может быть полезно при отладке)
        StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);
    }

    /**
     * Функция удаления файлов из логгера
     */
    private void deleteFileIfNeed() throws Exception {
        Path sourceDirectory = Paths.get("LoggerFiles");
//        isFoldersExist();

        try (Stream<Path> filesStream = Files.list(sourceDirectory)) {
            List<Path> files = filesStream.toList(); // Get the list of files in the source directory Temp

            if (5 < files.size()) { // If the maximum number of files in a folder is exceeded
                removeOldestFile();
            }
        }
    }

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
