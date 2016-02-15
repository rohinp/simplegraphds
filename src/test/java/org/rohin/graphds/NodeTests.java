package org.rohin.graphds;

import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTests {

    @Test(expected = Node.EmptyChildrenListException.class)
    public void itShouldNotAllowEmptyChildListToAccess_ByIndex(){
        //given
        INode<String> node = Node.createNode("root","d");

        //when
        node.getChildByIndex(0);
        //then
    }

    @Test(expected = Node.InvalidNodeIDException.class)
    public void itShouldGiveExceptionForInvalidChildName(){
        //given
        INode<String> node = Node.createNode("root","root");
        node = node.addChild(Node.createNode("child","c"));

        //when
        node.getChildByID("someName");
        //then
    }

    @Test(expected = Node.InvalidIndexException.class)
    public void itShouldGiveExceptionForInvalidChildIndex(){
        //given
        INode<String> node = Node.createNode("root","d");
        node = node.addChild(Node.createNode("child","c"));

        //when
        node.getChildByIndex(1);
        //then
    }

    @Test(expected = Node.InvalidIndexException.class)
    public void itShouldGiveExceptionForInvalidNegativeChildIndex(){
        //given
        INode<String> node = Node.createNode("root","d");
        node = node.addChild(Node.createNode("child","c"));

        //when
        node.getChildByIndex(-1);
        //then
    }


    @Test(expected = Node.EmptyChildrenListException.class)
    public void itShouldNotAllowEmptyChildListToAccess_ByName(){
        //given
        INode<String> node = Node.createNode("root","root");

        //when
        node.getChildByID("someName");
        //then
    }

    @Test
    public void itShouldCreateAGraphNodeHavingMultipleNodeChildren_index(){
        //given
        INode<String> nodeDS = Node.createNode("root","r");

        //when
        nodeDS = nodeDS.addChild(Node.createNode("1","c"));
        nodeDS = nodeDS.addChild(Node.createNode("2","c"));
        nodeDS = nodeDS.addChild(Node.createNode("3","c"));

        //then

        assertEquals(nodeDS.getChildByIndex(0), Node.createNode("1","c"));
        assertEquals(nodeDS.getChildByIndex(1), Node.createNode("2","c"));
        assertEquals(nodeDS.getChildByIndex(2), Node.createNode("3","c"));
    }

    @Test
    public void itShouldCreateAGraphNodeHavingMultipleNodeChildren_name(){
        //given
        INode<String> nodeDS = Node.createNode("root","root");

        //when
        nodeDS = nodeDS.addChild(Node.createNode("1","c"));
        nodeDS = nodeDS.addChild(Node.createNode("2","c"));
        nodeDS = nodeDS.addChild(Node.createNode("3","c"));
        System.out.println(nodeDS);
        //then

        assertEquals(nodeDS.getChildByID("1"), Node.createNode("1","c"));
        assertEquals(nodeDS.getChildByID("2"), Node.createNode("2","c"));
        assertEquals(nodeDS.getChildByID("3"), Node.createNode("3","c"));
    }

    @Test
    public void itShouldCheckContains_ForEmptyNodeChildren(){
        //given
        INode<String> nodeDS = Node.createNode("root","root");

        //when

        //then
        assertFalse(nodeDS.contains(Node.createNode("1","2")));
    }


    @Test
    public void itShouldCheckContains_ForNonEmptyNodeChildren(){
        //given
        INode<String> nodeDS = Node.createNode("root","root");

        nodeDS = nodeDS.addChild(Node.createNode("1","c"));
        nodeDS = nodeDS.addChild(Node.createNode("2","c"));
        //when

        //then
        assertTrue(nodeDS.contains(Node.createNode("1","2")));
    }

    @Test(expected = Node.NoDuplicateChildAllowed.class)
    public void itShouldNotAllowDuplicateNodeChild_IdentityID(){
        //given
        INode<String> nodeDS = Node.createNode("root","root");

        nodeDS = nodeDS.addChild(Node.createNode("1","c"));
        nodeDS.addChild(Node.createNode("1","c"));
        //when

        //then
    }

    @Test(expected = Node.RootAndChildNodeSameNameException.class)
    public void itShouldNotAllowRootAndChildNameSame(){
        //given
        INode<String> nodeDS = Node.createNode("root","root");

        nodeDS = nodeDS.addChild(Node.createNode("root","c"));
        nodeDS.addChild(Node.createNode("1","c"));
        //when

        //then
    }



}
