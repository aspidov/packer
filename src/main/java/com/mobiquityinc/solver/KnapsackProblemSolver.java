package com.mobiquityinc.solver;

import com.mobiquityinc.model.KnapsackItem;
import com.mobiquityinc.model.KnapsackTask;

import java.util.Collection;

public interface KnapsackProblemSolver {
    /**
     * Solve the knapsack problem with branch and bound algorithm.
     * @param knapsackTask a task for solving
     * @return knapsack items for pack
     */
    Collection<KnapsackItem> solve(KnapsackTask knapsackTask);
}
