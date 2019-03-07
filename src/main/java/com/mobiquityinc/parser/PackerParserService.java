package com.mobiquityinc.parser;

import com.mobiquityinc.model.KnapsackTask;

public interface PackerParserService {
    /***
     * The method for parse single knapsack problem line
     */
    KnapsackTask parseSingleKnapsack(String line);
}
