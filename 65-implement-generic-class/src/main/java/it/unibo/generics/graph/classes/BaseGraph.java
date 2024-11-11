package it.unibo.generics.graph.classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.Set;

import it.unibo.generics.graph.api.Graph;

public class BaseGraph<T> implements Graph<T> {
    private final Map<T, Set<T>> nodesMap;

    public BaseGraph() {
        this.nodesMap = new HashMap<>();
    }

    @Override
    public void addNode(T node) {
        if (node == null) {
            return;
        }
        this.nodesMap.putIfAbsent(node, new HashSet<>());
    }

    @Override
    public void addEdge(T source, T target) {
        if (source == null || target == null || !this.nodesMap.containsKey(source)) {
            return;
        }
        Set<T> linkedNodes = this.linkedNodes(source);
        linkedNodes.add(target);
    }

    @Override
    public Set<T> nodeSet() {
        return this.nodesMap.keySet();
    }

    @Override
    public Set<T> linkedNodes(T node) {
        return this.nodesMap.get(node);
    }

    @Override
    public List<T> getPath(T source, T target) {
        final List<T> path = new LinkedList<>();
        path.add(source);
        if (source == target) {
            return path;
        }

        final Set<T> linkedNodes = this.linkedNodes(source);
        for (final T node : linkedNodes) {
            final List<T> partialPath = this.getPath(node, target);
            if (partialPath != null) {
                path.addAll(partialPath);
                return path;
            }
        }
        return null;
    }

}
