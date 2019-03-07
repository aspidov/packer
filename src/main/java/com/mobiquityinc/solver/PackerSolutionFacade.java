package com.mobiquityinc.solver;

import com.mobiquityinc.exception.APIException;

public interface PackerSolutionFacade {
    /**
     * The method which solves all knapsack problems provided in filePath param
     *
     * @param filePath path to file with problems
     * @return A list of solutions, separated by new line
     * @throws APIException if something went wrong (incorrect format, internal error)
     */
    String solveFromFile(String filePath) throws APIException;
}
