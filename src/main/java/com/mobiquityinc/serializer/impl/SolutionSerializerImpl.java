package com.mobiquityinc.serializer.impl;

import com.mobiquityinc.model.KnapsackItem;
import com.mobiquityinc.serializer.SolutionSerializer;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SolutionSerializerImpl implements SolutionSerializer {

    private static final String RESULT_DELIMETER = ",";
    private static final String EMPTY_SOLUTION_PLACEHOLDER = "-";

    @Override
    public String serializeSolution(Collection<KnapsackItem> solution) {
        if (solution.isEmpty()) {
            return EMPTY_SOLUTION_PLACEHOLDER;
        }
        return solution.stream()
                .sorted(Comparator.comparingLong(KnapsackItem::getIndexNumber))
                .mapToLong(KnapsackItem::getIndexNumber)
                .mapToObj(Long::toString)
                .collect(Collectors.joining(RESULT_DELIMETER));
    }
}
