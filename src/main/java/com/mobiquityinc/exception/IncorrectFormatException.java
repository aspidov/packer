package com.mobiquityinc.exception;

import lombok.NoArgsConstructor;

/**
 * Exception which throws internally if format does not meets input format requirements
 */
@NoArgsConstructor
public class IncorrectFormatException extends RuntimeException {
    public IncorrectFormatException(String message) {
        super(message);
    }
}
