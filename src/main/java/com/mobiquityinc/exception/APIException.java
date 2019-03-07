package com.mobiquityinc.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class APIException extends Exception {
    public APIException(String message) {
        super(message);
    }
}
