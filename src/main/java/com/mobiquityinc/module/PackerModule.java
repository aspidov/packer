package com.mobiquityinc.module;

import com.google.inject.AbstractModule;
import com.mobiquityinc.configuration.PackerConfigurationProvider;
import com.mobiquityinc.configuration.impl.DefaultPackerConfigurationProviderImpl;
import com.mobiquityinc.parser.PackerInputValidator;
import com.mobiquityinc.parser.PackerParserService;
import com.mobiquityinc.parser.impl.PackerInputParserImpl;
import com.mobiquityinc.parser.impl.PackerInputValidatorImpl;
import com.mobiquityinc.serializer.SolutionSerializer;
import com.mobiquityinc.serializer.impl.SolutionSerializerImpl;
import com.mobiquityinc.solver.KnapsackProblemSolver;
import com.mobiquityinc.solver.PackerSolutionFacade;
import com.mobiquityinc.solver.impl.KnapsackProblemSolverImpl;
import com.mobiquityinc.solver.impl.PackerSolutionFacadeImpl;


/**
 * Class that defines module for Guice
 */
public class PackerModule extends AbstractModule {
    @Override
    protected void configure() {
        super.configure();
        bind(PackerParserService.class).to(PackerInputParserImpl.class);
        bind(PackerInputValidator.class).to(PackerInputValidatorImpl.class);
        bind(PackerConfigurationProvider.class).to(DefaultPackerConfigurationProviderImpl.class);
        bind(KnapsackProblemSolver.class).to(KnapsackProblemSolverImpl.class);
        bind(SolutionSerializer.class).to(SolutionSerializerImpl.class);
        bind(PackerSolutionFacade.class).to(PackerSolutionFacadeImpl.class);
    }

}
