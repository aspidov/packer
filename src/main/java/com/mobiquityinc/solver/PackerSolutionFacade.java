package com.mobiquityinc.solver;

import com.mobiquityinc.exception.APIException;

public interface PackerSolutionFacade {
    String solveFromFile(String filePath) throws APIException;
}
