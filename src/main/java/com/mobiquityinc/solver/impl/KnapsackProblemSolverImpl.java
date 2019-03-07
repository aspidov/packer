package com.mobiquityinc.solver.impl;

import com.mobiquityinc.model.KnapsackItem;
import com.mobiquityinc.model.KnapsackTask;
import com.mobiquityinc.model.PackerTreeNode;
import com.mobiquityinc.solver.KnapsackProblemSolver;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class KnapsackProblemSolverImpl implements KnapsackProblemSolver {

    private static final int EQUAL_ITEMS_COMPARATION_RESULT = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<KnapsackItem> solve(KnapsackTask knapsackTask) {
        final var solutionTree = new KnapsackTreeBuilder(knapsackTask.getWeight()).build(knapsackTask.getItems());
        final var leafNodes = solutionTree.stream()
                .map(PackerTreeNode::getAllLeafNodes)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        final var solutionMaybe = findSolutionInTree(leafNodes);

        if (solutionMaybe.isEmpty()) {
            return Collections.emptyList();
        }
        return solutionMaybe.get().getBranch().stream().map(PackerTreeNode::getItem).collect(Collectors.toList());

    }

    /**
     * The method finds optimal solution in list of leaf nodes.
     * The optimal solution is solution with max cost. If costs of two solutions are same, more optiomal will be solution with less weight
     *
     * @param leafNodes all leaf nodes from branch and bound tree
     * @return leaf with optimal solution
     */
    private Optional<PackerTreeNode<KnapsackItem>> findSolutionInTree(List<PackerTreeNode<KnapsackItem>> leafNodes) {
        return leafNodes.stream().min((item1, item2) -> {
            final var costCompareResult = -Double.compare(item1.getBranchTotalCost(), item2.getBranchTotalCost());
            if (costCompareResult == EQUAL_ITEMS_COMPARATION_RESULT) {
                return Double.compare(item1.getBranchTotalWeight(), item2.getBranchTotalWeight());
            }
            return costCompareResult;
        });
    }
}
