package org.paleha.calculator_pl.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Integer.parseInt;
import org.paleha.calculator_pl.exception.LoggerException;

public class LoadLogger extends AbstractLogger {

    private AbstractLogger loggerAm;
    private AbstractLogger loggerPm;

    public LoadLogger(AbstractLogger loggerAm, AbstractLogger loggerPm) {
        this.loggerAm = loggerAm;
        this.loggerPm = loggerPm;
    }

    public void logOutput(String prefix, String result) throws LoggerException {
        if (isAm()) {
            this.loggerAm.logOutput(prefix, result);
        } else {
            this.loggerPm.logOutput(prefix, result);
        }
    }

    private static boolean isAm() {
        String time;
//        boolean amTime;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH"); // "yy-MM-dd_HH-mm-ss"
        time = now.format(formatter);
        return parseInt(time) < 12;
    }
}


/*
 * AbstractLogger.logOutput()
 *
 * OOP:
 *    - inheritance
 *    - composition (aggregation)
 *
 * Inheritance:
 *
 * class LoggerPl { ... }
 *
 * class LoggerPlEx extends LoggerPl {
 *     public void logOutput(String result, String prefix) throws LoggerException {
 *        super.logOutput(result, prefix);
 *        super.logOutput("abc");
 *     }
 * }
 *
 * LoggerPl logger = new LoggerPlEx();
 *
 * Composition:
 *
 * class SpecialLogger {
 *
 *     private LoggerPl logger;
 *
 *     public(LoggerPl logger) {
 *        this.logger = logger;
 *     }
 *
 *     public log(String longString) {
 *        //
 *        this.logger(longString.substring(0, 1), longString.substring(1));
 *     }
 * }
 *
 * LoggerPl logger = new LoggerPlEx();
 * SpecialLogger specialLogger = new SpecialLogger(logger);
 *
 * AbstractLogger amLogger = new LoggerPl();
 * AbstractLogger pmLogger = new LoggerSlf4J();
 * AbstractLogger logger = new TimeLogger(amLogger, pmLogger);
 *
 * class TimeLogger extends AbstractLogger {
 *
 *     private AbstractLogger loggerAm;
 *     private AbstractLogger loggerPm;
 *
 *     public TimeLogger(AbstractLogger loggerAm, AbstractLogger loggerPm) {
 *        this.loggerAm = loggerAm;
 *        this.loggerPm = loggerPm;
 *     }
 *
 *     public void logOutput(String prefix, String result) throws LoggerException {
 *        if (isAm()) {
 *             this.loggerAm.logOutput(prefix, result);
 *        } else {
 *             this.loggerPm.logOutput(prefix, result);
 *        }
 *     }
 *
 *     private static boolean isAm() {
 *        String time;
 *        boolean amTime;
 *        LocalDateTime now = LocalDateTime.now();
 *        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH"); // "yy-MM-dd_HH-mm-ss"
 *        time = now.format(formatter);
 *        return parseInt(time) < 12;
 *     }
 * }
 *
 */

