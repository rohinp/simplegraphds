package org.rohin.graphds;

import java.util.List;

interface INode<T> {
    INode<T> addChild(INode<T> INode);
    INode<T> getChildByIndex(int index);
    INode<T> getChildByID(String nodeId);
    boolean contains(INode<T> INode);
    T getData();
    List<INode<T>> getChildren();
    String id();
    List<INode<T>> getLeafs();
    boolean haveChildren();
}
