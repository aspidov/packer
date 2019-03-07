package com.mobiquityinc.solver.impl;

import com.mobiquityinc.model.KnapsackItem;
import com.mobiquityinc.model.PackerTreeNode;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The builder for build tree of possible solutions for knapsack problem
 */
public class KnapsackTreeBuilder {
    private final Double weightConstraint;

    public KnapsackTreeBuilder(Double weightConstraint) {
        this.weightConstraint = weightConstraint;
    }

    /**
     * The method builds tree of knapsack problem possible solutions
     * @param items items for pack
     * @return collection of tree's root nodes
     */
    public Collection<PackerTreeNode<KnapsackItem>> build(Collection<KnapsackItem> items) {
        final var sortedItems = items
                .stream()
                .sorted(Comparator.comparingDouble(KnapsackItem::getWeight))
                .collect(Collectors.toList()); // items should be sorted by weight ASC for branch and bound method
        return sortedItems.stream()
                .map(item -> childrenMapper(sortedItems, item, null))
                .filter(Objects::nonNull) // null item means that weight constraint is violated
                .collect(Collectors.toList());
    }

    private PackerTreeNode<KnapsackItem> buildSolutionNode(PackerTreeNode<KnapsackItem> parentNode,
                                                           KnapsackItem item,
                                                           List<KnapsackItem> slicedItems) {
        var totalWeight = 0D; // total weight of branch needs to be calculated for exclude obviously impossible cases from tree
        if (parentNode != null) {
            totalWeight += parentNode.getBranchTotalWeight();
        }
        totalWeight += item.getWeight();
        if (totalWeight > weightConstraint) {
            return null;
        }
        final var newNode = PackerTreeNode
                .builder()
                .parent(parentNode)
                .item(item)
                .build();
        final var children = slicedItems.stream()
                .map(childItem -> childrenMapper(slicedItems, childItem, newNode))
                .filter(Objects::nonNull)// null item means that weight constraint is violated
                .collect(Collectors.toList());
        newNode.setChildren(children);
        return newNode;
    }

    private PackerTreeNode<KnapsackItem> childrenMapper(List<KnapsackItem> items, KnapsackItem currentItem, PackerTreeNode<KnapsackItem> parent) {
        final var index = items.indexOf(currentItem);
        return buildSolutionNode(parent, currentItem, getSkippedItems(items, index + 1L)); // skip items before current item (including current item) is required for exclude permutated entries
    }

    private List<KnapsackItem> getSkippedItems(List<KnapsackItem> itemsToSkip, Long skip) {
        return itemsToSkip.stream()
                .skip(skip)
                .collect(Collectors.toList());
    }


}
