package com.mobiquityinc.parser;

import com.mobiquityinc.TestConstants;
import com.mobiquityinc.configuration.PackerConfigurationProvider;
import com.mobiquityinc.exception.IncorrectFormatException;
import com.mobiquityinc.model.KnapsackItem;
import com.mobiquityinc.model.KnapsackTask;
import com.mobiquityinc.parser.impl.PackerInputParserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PackerInputParserImplTest {
    private PackerInputParserImpl service;
    private PackerInputValidator packerInputValidatorMock;

    @BeforeEach
    void init(@Mock PackerConfigurationProvider packerConfigurationProvider, @Mock PackerInputValidator parserInputValidator) {
        packerInputValidatorMock = parserInputValidator;
        service = new PackerInputParserImpl(parserInputValidator, packerConfigurationProvider);

        Mockito.lenient()
                .when(packerConfigurationProvider.getConfiguration())
                .thenReturn(TestConstants.DEFAULT_PACKER_CONFIG);
    }

    @Test
    void whenIncorrectInputLine_thenThrows() {
        final var line = "line";
        Mockito.lenient()
                .when(packerInputValidatorMock.isSyntaxValid(line))
                .thenReturn(false);
        assertThrows(IncorrectFormatException.class, () -> service.parseSingleKnapsack(line));
    }

    @Test
    void whenCorrectInputLine_thenReturnsCorrectResult() {
        final var line = "8.4 : (1,15.3,€34)";
        Mockito.lenient()
                .when(packerInputValidatorMock.isSyntaxValid(line))
                .thenReturn(true);
        final var expectedResult = KnapsackTask
                .builder()
                .weight(8.4)
                .items(
                        Collections.singletonList(KnapsackItem.builder()
                                .indexNumber(1L)
                                .weight(15.3)
                                .cost(34D)
                                .build()
                        )
                )
                .build();

        assertEquals(service.parseSingleKnapsack(line), expectedResult);
    }

    @Test
    void whenWeightGreaterThanMaxWeigth_thenThrows() {
        final var line = "150 : (1,15.3,€34)";
        Mockito.lenient()
                .when(packerInputValidatorMock.isSyntaxValid(line))
                .thenReturn(true);
        assertThrows(IncorrectFormatException.class, () -> service.parseSingleKnapsack(line));
    }

    @Test
    void whenCostGreaterThanMaxCost_thenThrows() {
        final var line = "54 : (1,15.3,€101)";
        Mockito.lenient()
                .when(packerInputValidatorMock.isSyntaxValid(line))
                .thenReturn(true);
        assertThrows(IncorrectFormatException.class, () -> service.parseSingleKnapsack(line));
    }

    @Test
    void whenWeightOfItemGreaterThanMax_thenThrows() {
        final var line = "54 : (1,101,€1)";
        Mockito.lenient()
                .when(packerInputValidatorMock.isSyntaxValid(line))
                .thenReturn(true);
        assertThrows(IncorrectFormatException.class, () -> service.parseSingleKnapsack(line));
    }

    @Test
    void whenItemQuantityGreaterThanMax_thenThrows() {
        final var line = "54 :" + " (1,15,€1)".repeat(50);
        Mockito.lenient()
                .when(packerInputValidatorMock.isSyntaxValid(line))
                .thenReturn(true);
        assertThrows(IncorrectFormatException.class, () -> service.parseSingleKnapsack(line));
    }
}
