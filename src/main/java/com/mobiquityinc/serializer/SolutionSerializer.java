package com.mobiquityinc.serializer;

import com.mobiquityinc.model.KnapsackItem;

import java.util.Collection;

public interface SolutionSerializer {
    String serializeSolution(Collection<KnapsackItem> solution);
}
