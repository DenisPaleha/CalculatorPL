package org.paleha.calculator_pl.logger;

import org.paleha.calculator_pl.exception.LoggerException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Integer.parseInt;

public class LoadLoggerByTime extends AbstractLogger {
    boolean amTime = isAmTime();
    AbstractLogger logger;

    public LoadLoggerByTime() throws LoggerException {
        if (amTime) {
            this.logger = new LoggerPl();
        } else {
            this.logger = new LoggerSlf4j();
        }
    }

    /**
     * Function for writing a string to the log
     */
    public void logOutput(String result, String prefix) throws LoggerException {
        this.logger.logOutput(result, prefix);
    }


//    /**
//     * The function loads the logger depending on the time of day
//     */
//    private AbstractLogger loggerLoad(boolean amTime) throws LoggerException {
//        if (amTime) {
//            return new LoggerPl();
//        } else {
//            return new LoggerSlf4j();
//        }
//    }

    /** function determines whether the time is before or after noon */
    public static boolean isAmTime(){
        String time;
        boolean amTime;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH"); // "yy-MM-dd_HH-mm-ss"
        time = now.format(formatter);
        amTime = parseInt(time) < 12;
        return amTime;
    }

//    /**
//     * The function changes the logger used in the program
//     */
//    public static AbstractLogger changeLogger(boolean set) throws LoggerException {
//        if (set) {
//            return new LoggerSlf4j();
//        } else {
//            return new LoggerPl();
//        }
//    }
}
