package com.mobiquityinc.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IncorrectFormatException extends RuntimeException {
    public IncorrectFormatException(String message) {
        super(message);
    }
}
