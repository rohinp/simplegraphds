package org.rohin.graphds;

import java.util.List;
import java.util.Optional;

interface INode<T> {
    INode<T> addChild(INode<T> INode);
    Optional<INode<T>> getChildByIndex(int index);
    Optional<INode<T>> getChildByID(String nodeId);
    boolean contains(INode<T> INode);
    T getData();
    List<INode<T>> getChildren();
    String id();
    List<INode<T>> getLeafs();
    boolean haveChildren();
    String getTag();
    String getDescription();
}
