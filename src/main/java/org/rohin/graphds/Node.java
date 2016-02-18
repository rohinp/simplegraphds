package org.rohin.graphds;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class Node  {

    public static <T> INode<T> createNode(String nodeId, T data){
        return new NodeWithoutChildren<>(nodeId, data);
    }

    private static <T> INode<T> createNodeWithChildren(INode<T> parent, INode<T> child){
        validate(parent, child);
        return new NodeWithChildren<>(parent,child);
    }

    private static <T> void validate(INode<T> parent, INode<T> child) {
        if(parent.contains(child))
            throw new NoDuplicateChildAllowed();
        if(parent.equals(child))
            throw new RootAndChildNodeSameNameException();
    }

    private static class NodeWithoutChildren<T> implements INode<T> {

        private final String nodeId;
        private final T data;

        private NodeWithoutChildren(String nodeId, T data) {
            this.nodeId = nodeId;
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            return this == o || !(o == null || getClass() != o.getClass()) && nodeId.equals(((NodeWithoutChildren<?>) o).nodeId);
        }

        @Override
        public int hashCode() {
            return nodeId.hashCode();
        }

        @Override
        public INode<T> addChild(INode<T> node) {
            return createNodeWithChildren(this, node);
        }

        @Override
        public Optional<INode<T>> getChildByIndex(int index) {
            return Optional.empty();
        }

        @Override
        public Optional<INode<T>> getChildByID(String nodeId) {
            return Optional.empty();
        }

        @Override
        public boolean contains(INode<T> INode) {
            return false;
        }

        @Override
        public T getData() {
            return data;
        }

        @Override
        public List<INode<T>> getChildren() {
            return new ArrayList<>();
        }

        @Override
        public String id() {
            return nodeId;
        }

        @Override
        public List<INode<T>> getLeafs() {
            return new ArrayList<>();
        }

        @Override
        public boolean haveChildren() {
            return false;
        }

        @Override
        public String toString() {
            return nodeId ;
        }
    }

    private static class NodeWithChildren<T> implements INode<T> {

        private final String nodeId;
        private final T data;
        private final List<INode<T>> children = new ArrayList<>();

        private List<INode<T>> acc = new ArrayList<>();

        public NodeWithChildren(INode<T> parent, INode<T> child) {
            nodeId = parent.id();
            data = parent.getData();
            children.addAll(parent.getChildren());
            children.add(child);
        }

        @Override
        public INode<T> addChild(INode<T> node) {
            return createNodeWithChildren(this,node);
        }

        @Override
        public Optional<INode<T>> getChildByIndex(int index) {
            if (isInRange(index))
                return Optional.of(children.get(index));
            return Optional.empty();
        }

        private boolean isInRange(int index) {
            return index >= 0 && children.size() > index;
        }

        @Override
        public Optional<INode<T>> getChildByID(String id) {
            if(children.stream().anyMatch(e -> e.id().equals(id)))
                return children.stream().filter(e -> e.id().equals(id)).findFirst();
            return Optional.empty();
        }

        @Override
        public boolean contains(INode<T> node) {
            return children.stream().anyMatch(e -> e.equals(node));
        }

        @Override
        public boolean equals(Object o) {
            return this == o || !(o == null || getClass() != o.getClass()) && nodeId.equals(((NodeWithChildren<?>) o).nodeId);

        }

        @Override
        public int hashCode() {
            return nodeId.hashCode();
        }

        @Override
        public T getData() {
            return data;
        }

        @Override
        public List<INode<T>> getChildren() {
            return children;
        }

        @Override
        public String id() {
            return nodeId;
        }

        @Override
        public List<INode<T>> getLeafs() {
            acc = new ArrayList<>();
            loopForLeaf(this);
            return acc;
        }

        @Override
        public boolean haveChildren() {
            return true;
        }

        private void loopForLeaf(INode<T> node){
            if(node.haveChildren())
                node.getChildren().stream().forEach(this::loopForLeaf);
            else
                acc.add(node);
        }

        @Override
        public String toString() {
            return nodeId + " --> [ " + children.stream().map(Object::toString).reduce( (e, a) -> a += " , "+e).get() + " ]";
        }
    }

    public static class NoDuplicateChildAllowed extends RuntimeException{
    }

    public static class RootAndChildNodeSameNameException extends RuntimeException{
    }
}
