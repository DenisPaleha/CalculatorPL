package org.paleha.calculator_pl.exception;

public class OutOfRangeException extends ConversionException {
    // Allows to decode a conversion error from Roman to decimal number
    // when exceeding the possible range.
    // Can contain additional fields such as strings or numbers (error number).
//    String message = "";
//    public int code = 100;
    public OutOfRangeException(String message) { // Constructor to get a message string
        super(message);
    }
}