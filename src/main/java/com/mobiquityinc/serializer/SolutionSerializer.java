package com.mobiquityinc.serializer;

import com.mobiquityinc.model.KnapsackItem;

import java.util.Collection;

/**
 * A service which provides a possibility to serialize solution
 */
public interface SolutionSerializer {
    /**
     * The method for serialize Knapsack problem's solution into string like 1,2,3
     * @param solution a list of knapsack items, which is optimal solution for knapsack problem
     * @return serialized solution
     */
    String serializeSolution(Collection<KnapsackItem> solution);
}
