package com.mobiquityinc.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;

/**
 * The class represents item in knapsack problem
 */
@Data
@Builder
@EqualsAndHashCode
public class KnapsackItem {
    public static final int MAX_ITEM_COST = 100;
    public static final int MAX_ITEM_WEIGHT = 100;

    /**
     * Identifier of item
     */
    private Long indexNumber;

    /**
     * Cost of item. Should be less than {@value MAX_ITEM_COST}
     */
    @Max(MAX_ITEM_COST)
    private Double cost;

    /**
     * Weight of item. Should be less than {@value MAX_ITEM_WEIGHT}
     */
    @Max(MAX_ITEM_WEIGHT)
    private Double weight;
}
