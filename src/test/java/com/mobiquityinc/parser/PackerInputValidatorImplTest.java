package com.mobiquityinc.parser;

import com.mobiquityinc.TestConstants;
import com.mobiquityinc.configuration.PackerConfigurationProvider;
import com.mobiquityinc.parser.impl.PackerInputValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PackerInputValidatorImplTest {
    private PackerInputValidatorImpl service;

    @BeforeEach
    void init(@Mock PackerConfigurationProvider packerConfigurationProvider) {
        service = new PackerInputValidatorImpl(packerConfigurationProvider);

        Mockito.lenient()
                .when(packerConfigurationProvider.getConfiguration())
                .thenReturn(TestConstants.DEFAULT_PACKER_CONFIG);
    }

    @Test
    void whenCorrectLine_thenValidatorReturnsTrue() {
        final var correctLine = "75 : (1,85.31,€29) (3,3.98,€16) (4,26.24,€55) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)";
        assertTrue(service.isSyntaxValid(correctLine));
    }

    @Test
    void whenBrokenEndOfItemBlock_thenValidatorReturnsFalse() {
        final var correctLine = "75 : (1,85.31 (3,3.98,€16) (4,26.24,€55) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)";
        assertFalse(service.isSyntaxValid(correctLine));
    }

    @Test
    void whenBrokenStartOfItemBlock_thenValidatorReturnsFalse() {
        final var correctLine = "75 : (1,85.31,€29) €16) (4,26.24,€55) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)";
        assertFalse(service.isSyntaxValid(correctLine));
    }

    @Test
    void whenMissingTargetWeight_thenValidatorReturnsFalse() {
        final var correctLine = "(1,85.31,€29) (4,26.24,€55) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)";
        assertFalse(service.isSyntaxValid(correctLine));
    }

    @Test
    void whenMissingCurrencySymbol_thenValidatorReturnsFalse() {
        final var correctLine = "24 : (1,85.31,€29) (4,26.24,55) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)";
        assertFalse(service.isSyntaxValid(correctLine));
    }

    @Test
    void whenEmptyItem_thenValidatorReturnsFalse() {
        final var correctLine = "75 : () () (4,26.24,€55) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)";
        assertFalse(service.isSyntaxValid(correctLine));
    }
}
