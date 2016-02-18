package org.rohin.graphds;

import java.util.List;

public class Graph {
    public static <T> IGraph<T> createAcyclicGraph(){
        return new EmptyGraph<>();
    }

    private Graph() {
    }

    private static class EmptyGraph<T> implements IGraph<T> {
        @Override
        public void traverse() {
            throw new EmptyGraphException();
        }

        @Override
        public void addNode(String nodeId, T data) {

        }

        @Override
        public void addChildNode(String parentNodeId, String childNodeId, T data) {

        }

        @Override
        public List<INode<T>> getRoots() {
            return null;
        }

        @Override
        public List<INode<T>> getChildOf(String parentNodeId) {
            return null;
        }
    }

    public static class EmptyGraphException extends RuntimeException{
    }
}
