package com.mobiquityinc.solver.impl;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.exception.IncorrectFormatException;
import com.mobiquityinc.parser.PackerParserService;
import com.mobiquityinc.serializer.SolutionSerializer;
import com.mobiquityinc.solver.KnapsackProblemSolver;
import com.mobiquityinc.solver.PackerSolutionFacade;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PackerSolutionFacadeImpl implements PackerSolutionFacade {

    private final PackerParserService problemParser;
    private final KnapsackProblemSolver problemSolver;
    private final SolutionSerializer solutionSerializer;

    @Override
    public String solveFromFile(String filePath) throws APIException {
        final var path = getPathOrThrow(filePath);
        try (var lines = Files.lines(path)) {
            return lines.map(line -> {
                final var task = problemParser.parseSingleKnapsack(line);
                final var solution = problemSolver.solve(task);
                return solutionSerializer.serializeSolution(solution);
            })
                    .collect(Collectors.joining("\n"));
        } catch (IncorrectFormatException e) {
            throw new APIException(e.getMessage());
        } catch (IOException e) {
            throw new APIException("File not found in provided path");
        }
    }

    private Path getPathOrThrow(String filePath) throws APIException {
        try {
            return Paths.get(filePath);
        } catch (InvalidPathException e) {
            throw new APIException("File does not exists in provided path");
        }
    }
}
