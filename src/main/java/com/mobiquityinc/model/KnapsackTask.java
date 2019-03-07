package com.mobiquityinc.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;


/**
 * The class represents task for knapsack problem
 */
@Data
@Builder
@EqualsAndHashCode
public class KnapsackTask {
    /**
     * Constant for max weight of package constraint
     */
    public static final int MAX_WEIGTH_OF_PACKAGE = 100;
    public static final int MAX_ITEMS_QUANTITY = 15;

    /**
     * Weight constraint for the package. Should be less than {@value MAX_WEIGTH_OF_PACKAGE}
     */
    @Max(MAX_WEIGTH_OF_PACKAGE)
    private Double weight;

    /**
     * Items of knapsack problem. Items quantity should be less than {@value MAX_ITEMS_QUANTITY}
     */
    @Valid
    @Size(max = MAX_ITEMS_QUANTITY)
    private Collection<KnapsackItem> items = new ArrayList<>();
}
