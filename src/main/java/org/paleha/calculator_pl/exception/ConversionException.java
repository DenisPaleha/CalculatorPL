package org.paleha.calculator_pl.exception;

public class ConversionException extends Exception {
    // The class allows to handle conversion errors when decoding to decimal system
    // from all others number systems.
    public ConversionException(String message) {
        super(message);
    }
}
