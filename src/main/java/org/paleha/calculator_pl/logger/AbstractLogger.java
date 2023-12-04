package org.paleha.calculator_pl.logger;


import org.paleha.calculator_pl.exception.LoggerException;

public abstract class AbstractLogger {
    public abstract void logOutput(String result, String prefix) throws LoggerException;

}

