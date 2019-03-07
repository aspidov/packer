package com.mobiquityinc.exception;

import lombok.NoArgsConstructor;

/**
 * Exception which throws externally to packer users
 */
@NoArgsConstructor
public class APIException extends Exception {
    public APIException(String message) {
        super(message);
    }
}
