package com.mobiquityinc.model;

import lombok.Builder;
import lombok.Data;

/**
 * The class represents configuration of packer.
 */
@Data
@Builder
public class PackerConfiguration {
    /**
     * Delimiter between pack weight and items
     */
    private String weightContentDelimiter;

    /**
     * Token of start of item block
     */
    private String itemBlockStartToken;

    /**
     * Token of end of item block
     */
    private String itemBlockEndToken;

    /**
     * Separator between items' properties (identity, weight, cost)
     */
    private String itemPropertySeparator;

    /**
     * Decimal separator for float numbers
     */
    private String decimalSeparator;

    /**
     * Currency symbol, which can be used for cost
     */
    private String currencySymbol;

    /**
     * Separator between items
     */
    private String itemSeparator;
}
