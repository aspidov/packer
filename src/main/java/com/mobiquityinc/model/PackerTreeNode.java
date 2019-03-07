package com.mobiquityinc.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A tree node, which can be used in solution of knapsack problem
 *
 * @param <T> Type of knapsack item. Should be inherited from {@link com.mobiquityinc.model.KnapsackItem}
 */
@Data
@Builder
@ToString
public class PackerTreeNode<T extends KnapsackItem> {
    /**
     * Parent tree node
     */
    private PackerTreeNode<T> parent;

    /**
     * The content of tree node
     */
    private T item;
    /**
     * A list of children of the tree node
     */
    private List<PackerTreeNode<T>> children = new ArrayList<>();


    /**
     * Method for calculate total cost of current branch (sum of cost of this node and of parent nodes)
     * @return Total cost of current branch
     */
    public Double getBranchTotalCost() {
        var totalCost = 0D;
        if (parent != null) {
            totalCost += parent.getBranchTotalCost();
        }
        if (item != null) {
            totalCost += item.getCost();
        }

        return totalCost;
    }

    /**
     * Method for calculate total weight of current branch (sum of weights of this node and of parent nodes)
     * @return Total weight of current branch
     */
    public Double getBranchTotalWeight() {
        var totalWeight = 0D;
        if (parent != null) {
            totalWeight += parent.getBranchTotalWeight();
        }
        if (item != null) {
            totalWeight += item.getWeight();
        }

        return totalWeight;
    }

    /**
     * Method for get all leaf nodes, which parent is current node
     * @return List of all leaf nodes in current tree
     */
    public List<PackerTreeNode<T>> getAllLeafNodes() {
        if (children.isEmpty()) {
            return Collections.singletonList(this);
        }
        return children
                .stream()
                .map(PackerTreeNode::getAllLeafNodes)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Method for get entire branch with current item
     * @return all parents and this branch
     */
    public List<PackerTreeNode<T>> getBranch() {
        final var result = new ArrayList<PackerTreeNode<T>>();
        result.add(this);
        if (parent != null) {
            result.addAll(parent.getBranch());
        }
        return result;
    }
}
