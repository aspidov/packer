package com.mobiquityinc.solver;

import com.mobiquityinc.model.KnapsackItem;
import com.mobiquityinc.model.KnapsackTask;
import com.mobiquityinc.solver.impl.KnapsackProblemSolverImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class KnapsackProblemSolverTest {

    private KnapsackProblemSolverImpl service;

    @BeforeEach
    void init() {
        service = new KnapsackProblemSolverImpl();
    }

    @Test
    void whenSumOfAllWeightLessThanTarget_thenReturnAllItems() {
        final var mockItems = new ArrayList<KnapsackItem>();
        mockItems.add(KnapsackItem.builder()
                .indexNumber(1L)
                .cost(5D)
                .weight(2D)
                .build());
        mockItems.add(KnapsackItem.builder()
                .indexNumber(1L)
                .cost(6D)
                .weight(1D)
                .build());
        final var mockTask = KnapsackTask.builder()
                .weight(10D)
                .items(mockItems)
                .build();
        assertEquals(service.solve(mockTask), mockItems);
    }

    @Test
    void whenSolutionNotFound_thenReturnEmptyCollection() {
        final var mockItems = new ArrayList<KnapsackItem>();
        mockItems.add(KnapsackItem.builder()
                .indexNumber(1L)
                .cost(5D)
                .weight(56D)
                .build());
        mockItems.add(KnapsackItem.builder()
                .indexNumber(1L)
                .cost(6D)
                .weight(39D)
                .build());
        final var mockTask = KnapsackTask.builder()
                .weight(10D)
                .items(mockItems)
                .build();
        assertEquals(service.solve(mockTask), Collections.emptyList());
    }

    @Test
    void whenItemsHasEqualCost_thenReturnItemWithLessWeight() {
        final var mockItems = new ArrayList<KnapsackItem>();
        mockItems.add(KnapsackItem.builder()
                .indexNumber(1L)
                .cost(5D)
                .weight(4D)
                .build());
        mockItems.add(KnapsackItem.builder()
                .indexNumber(1L)
                .cost(5D)
                .weight(3D)
                .build());
        final var mockTask = KnapsackTask.builder()
                .weight(6D)
                .items(mockItems)
                .build();
        assertEquals(service.solve(mockTask), Collections.singletonList(mockItems.get(1)));
    }

}
