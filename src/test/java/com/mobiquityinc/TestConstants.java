package com.mobiquityinc;

import com.mobiquityinc.model.PackerConfiguration;

public class TestConstants {
    private TestConstants() {

    }

    private static final String WEIGHT_CONTENT_DELIMETER = " : ";
    private static final String ITEM_BLOCK_START_TOKEN = "(";
    private static final String ITEM_BLOCK_END_TOKEN = ")";
    private static final String ITEM_PROPERTY_SEPARATOR = ",";
    private static final String DECIMAL_SEPARATOR = ".";
    private static final String CURRENCY_SYMBOL = "€";
    private static final String ITEM_SEPARATOR = " ";

    public static final PackerConfiguration DEFAULT_PACKER_CONFIG = PackerConfiguration
            .builder()
            .currencySymbol(CURRENCY_SYMBOL)
            .decimalSeparator(DECIMAL_SEPARATOR)
            .itemPropertySeparator(ITEM_PROPERTY_SEPARATOR)
            .itemBlockStartToken(ITEM_BLOCK_START_TOKEN)
            .itemBlockEndToken(ITEM_BLOCK_END_TOKEN)
            .weightContentDelimiter(WEIGHT_CONTENT_DELIMETER)
            .itemSeparator(ITEM_SEPARATOR)
            .build();
}
