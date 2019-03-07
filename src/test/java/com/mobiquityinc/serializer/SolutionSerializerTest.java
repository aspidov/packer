package com.mobiquityinc.serializer;

import com.mobiquityinc.model.KnapsackItem;
import com.mobiquityinc.serializer.impl.SolutionSerializerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SolutionSerializerTest {
    private SolutionSerializerImpl service;

    @BeforeEach
    void init() {
        service = new SolutionSerializerImpl();
    }

    @Test
    void whenSolutionFound_thenReturnSortedSolutionString() {
        final var solution = new ArrayList<KnapsackItem>();
        solution.add(KnapsackItem.builder()
                .indexNumber(2L)
                .weight(3D)
                .cost(5D).build());
        solution.add(KnapsackItem.builder()
                .indexNumber(1L)
                .weight(3D)
                .cost(5D).build());
        final var expectedResult = "1,2";
        assertEquals(service.serializeSolution(solution), expectedResult);
    }

    @Test
    void whenSolutionNotFound_thenReturnPlaceholder() {
        final var solution = new ArrayList<KnapsackItem>();
        final var expectedResult = "-";
        assertEquals(service.serializeSolution(solution), expectedResult);
    }
}

