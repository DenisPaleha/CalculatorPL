import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Logger {
    private final String fileName;

    public Logger() {  // The constructor should generate a file name and create the file itself.
        this.fileName = generateFileName();

        try (FileWriter writer = new FileWriter(fileName, true)) {
            // Creating the "fileLogg" document, deleting anything already present if the file already exists.
            writer.write(" New document \n");
            writer.close();
            writer.flush();
        } catch (IOException ex) {
//            System.err.println("Error creating file " + ex.getMessage());  +++ WTF?
        }
    }

    public String getFileName() {
        return this.fileName;
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
        try (FileWriter writer = new FileWriter(this.fileName, append)) {
            // Creating the "MemoryTwo.txt" document with overwrite or append functionality.
            writer.write(content);
            writer.close();
            writer.flush();
        } catch (IOException ex) {
//            System.err.println("Error writing to memory " + ex.getMessage());  Always throws an error - why? +++
        }
    }

    /**
     * Read function to read records from the logger.
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

//    /**
//     * Функция очистки документа /нужна для авто-уборки/
//     */
//    public void clearLogg() {
//        String newContent = "";
//        writeLoggToDoc(newContent, false); // Перезапись.
//        System.out.println("All logs have been deleted.");
//    }
//
//    /**
//     * Функция автоматической уборки логера
//     */
//    public void autoCleanLog() { // Пока не используется и навряд ли будет
//        try {
//            Scanner scanner = new Scanner(new File(this.fileName));
//            int i = 0; // счетчик минимума для точки сохранения
//            int ii = 0; // счетчик максимума для удаления
//            String tmpLin = ""; // Строка сохранения данных
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine(); // Сканируем содержимое документа построчно
//                i++;
//                ii++;
//                if (i > 70) { // Если в документе более 70 строк, начинаем записывать следующие в резервную строку
//                    tmpLin = tmpLin + line + "\n";
//                }
//                if (ii >= 100) { // Если количество строк достигло 100 или более
//                    writeLoggToDoc(tmpLin, false); // Перезаписываем документ с последними 30+ строками
//                }
//            }
//            scanner.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}

