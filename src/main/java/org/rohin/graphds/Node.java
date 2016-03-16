package org.rohin.graphds;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class Node  {

    private static class NodeWithoutChildren<T> implements INode<T> {

        private final String nodeId;
        private final T data;
        private final String tag;
        private final String description;


        public NodeWithoutChildren(String nodeId, T data, String tag, String description) {
            this.nodeId = nodeId;
            this.data = data;
            this.tag = tag;
            this.description = description;
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
        public String getTag() {
            return tag;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public List<INode<T>> getChildByTag(String tag) {
            return new ArrayList<>();
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

        private final String tag;
        private final String description;

        private List<INode<T>> acc = new ArrayList<>();

        public NodeWithChildren(INode<T> parent, INode<T> child) {
            nodeId = parent.id();
            data = parent.getData();
            children.addAll(parent.getChildren());
            children.add(child);
            this.tag = parent.getTag();
            this.description = parent.getDescription();
        }

        @Override
        public INode<T> addChild(INode<T> node) {
            return createNodeWithChildren(this,node);
        }

        @Override
        public Optional<INode<T>> getChildByID(String id) {
                return children.stream().filter(e -> e.id().equals(id)).findFirst();
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

        @Override
        public String getTag() {
            return tag;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public List<INode<T>> getChildByTag(String tag) {
            acc = new ArrayList<>();
            traverse(this,tag);
            return acc;
        }

        private void loop(Consumer<INode<T>> func, INode<T> node){
            if(node.haveChildren())
                node.getChildren().stream().forEach(func);
        }
        private void traverse(INode<T> node, String tag) {
            if(node.getTag().equals(tag)) acc.add(node);
            loop( e -> traverse(e,tag),node);
        }

        private void loopForLeaf(INode<T> node){
            if(!node.haveChildren())acc.add(node);
            loop(this::loopForLeaf,node);
        }

        @Override
        public String toString() {
            return nodeId + " --> [ " + children.stream().map(Object::toString).reduce( (e, a) -> a += " , "+e).get() + " ]";
        }
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

    static class Builder<T> {
        //required
        private final String nodeId;
        private final T data;

        //optional
        private String tag = "";
        private String description = "";

        public Builder(String nodeId, T data) {
            this.nodeId = nodeId;
            this.data = data;
        }

        public Builder<T> tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder<T> description(String description) {
            this.description = description;
            return this;
        }

        public INode<T> build() {
            return new NodeWithoutChildren<>(nodeId, data, tag,description);
        }
    }

    static class NoDuplicateChildAllowed extends RuntimeException{
    }

    static class RootAndChildNodeSameNameException extends RuntimeException{
    }
}
