package cz.spsmb.dijkstra.algorithm;

import java.util.HashSet;
import java.util.Set;

public class Graph {

    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public String toStringWithDistance() {
        StringBuilder a = new StringBuilder();
        for (Node node: nodes) {
            int distance = 0;
            if (node.getDistance() < Integer.MAX_VALUE) {
                distance = node.getDistance();
            }
            a.append(node + " = " + distance + " ; ");
        }
        return a.toString();
    }

    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                '}';
    }
}