package com.mobiquityinc.packer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.module.PackerModule;
import com.mobiquityinc.solver.PackerSolutionFacade;

public class Packer {
    private Packer() {

    }

    private static final Injector injector = Guice.createInjector(new PackerModule());

    /**
     * Method for solve knapsack problems which defined in file
     * @param filePath path to file
     * @return A list of solutions, separated by new line
     * @throws APIException if something went wrong (incorrect format, internal error)
     */
    public static String pack(String filePath) throws APIException {
        final var solutionFacade = injector.getInstance(PackerSolutionFacade.class);
        return solutionFacade.solveFromFile(filePath);
    }
}
