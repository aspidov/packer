package com.mobiquityinc.parser;

/**
 * A validator of input format of packer
 */
public interface PackerInputValidator {
    /**
     * Method checks if line does meet a requirements of packer format
     * @param line line which describes single knapsack problem
     * @return is syntax valid or not valid
     */
    boolean isSyntaxValid(String line);
}
