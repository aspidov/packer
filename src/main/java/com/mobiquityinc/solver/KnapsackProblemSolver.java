package com.mobiquityinc.solver;

import com.mobiquityinc.model.KnapsackItem;
import com.mobiquityinc.model.KnapsackTask;

import java.util.Collection;

public interface KnapsackProblemSolver {
    Collection<KnapsackItem> solve(KnapsackTask knapsackTask);
}
