package com.mobiquityinc.parser;

import com.mobiquityinc.model.KnapsackTask;

public interface PackerParserService {
    /**
     * The method for parse single knapsack line
     * @param line knapsack problem definition line
     * @return a parsed knapsack problem task
     */
    KnapsackTask parseSingleKnapsack(String line);
}
