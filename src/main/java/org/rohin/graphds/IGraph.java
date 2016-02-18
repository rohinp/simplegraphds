package org.rohin.graphds;

import java.util.List;

public interface IGraph<T> {
    void traverse();

    void addNode(String nodeId, T data);

    void addChildNode(String parentNodeId, String childNodeId, T data);

    List<INode<T>> getRoots();

    List<INode<T>> getChildOf(String parentNodeId);
}
