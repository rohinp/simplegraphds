package org.rohin.graphds;

import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class NodeTests {

    @Test
    public void itShouldNotAllowEmptyChildListToAccess_ByIndex(){
        //given
        INode<String> node = new Node.Builder("root","d").build();

        //when
        assertEquals(Optional.empty(),node.getChildByIndex(0));
        //then
    }

    @Test
    public void itShouldGiveEmptyForInvalidChildName(){
        //given
        INode<String> node = new Node.Builder("root","root").build();
        node = node.addChild(new Node.Builder("child","c").build());

        //when
        assertEquals(Optional.empty(),node.getChildByID("someName"));
        //then
    }

    @Test
    public void itShouldGiveEmptyForInvalidChildIndex(){
        //given
        INode<String> node = new Node.Builder("root","d").build();
        node = node.addChild(new Node.Builder("child","c").build());

        //when
        assertEquals(Optional.empty(),node.getChildByIndex(1));
        //then
    }

    @Test
    public void itShouldGiveEmptyForInvalidNegativeChildIndex(){
        //given
        INode<String> node = new Node.Builder("root","d").build();
        node = node.addChild(new Node.Builder("child","c").build());

        //when
        assertEquals(Optional.empty(),node.getChildByIndex(-1));
        //then
    }


    @Test
    public void itShouldNotAllowEmptyChildListToAccess_ByName(){
        //given
        INode<String> node = new Node.Builder("root","root").build();

        //when
        assertEquals(Optional.empty(),node.getChildByID("someName"));
        //then
    }

    @Test
    public void itShouldCreateAGraphNodeHavingMultipleNodeChildren_index(){
        //given
        INode<String> nodeDS = new Node.Builder("root","r").build();

        //when
        nodeDS = nodeDS.addChild(new Node.Builder("1","c").build());
        nodeDS = nodeDS.addChild(new Node.Builder("2","c").build());
        nodeDS = nodeDS.addChild(new Node.Builder("3","c").build());

        //then

        assertEquals(nodeDS.getChildByIndex(0).get(), new Node.Builder("1","c").build());
        assertEquals(nodeDS.getChildByIndex(1).get(), new Node.Builder("2","c").build());
        assertEquals(nodeDS.getChildByIndex(2).get(), new Node.Builder("3","c").build());
    }

    @Test
    public void itShouldCreateAGraphNodeHavingMultipleNodeChildren_name(){
        //given
        INode<String> nodeDS = new Node.Builder("root","root").build();

        //when
        nodeDS = nodeDS.addChild(new Node.Builder("1","c").build());
        nodeDS = nodeDS.addChild(new Node.Builder("2","c").build());
        nodeDS = nodeDS.addChild(new Node.Builder("3","c").build());

        //then

        assertEquals(nodeDS.getChildByID("1").get(), new Node.Builder("1","c").build());
        assertEquals(nodeDS.getChildByID("2").get(), new Node.Builder("2","c").build());
        assertEquals(nodeDS.getChildByID("3").get(), new Node.Builder("3","c").build());
    }

    @Test
    public void itShouldCheckContains_ForEmptyNodeChildren(){
        //given
        INode<String> nodeDS = new Node.Builder("root","root").build();

        //when

        //then
        assertFalse(nodeDS.contains(new Node.Builder("1","2").build()));
    }


    @Test
    public void itShouldCheckContains_ForNonEmptyNodeChildren(){
        //given
        INode<String> nodeDS = new Node.Builder("root","root").build();

        nodeDS = nodeDS.addChild(new Node.Builder("1","c").build());
        nodeDS = nodeDS.addChild(new Node.Builder("2","c").build());
        //when

        //then
        assertTrue(nodeDS.contains(new Node.Builder("1","2").build()));
    }

    @Test(expected = Node.NoDuplicateChildAllowed.class)
    public void itShouldNotAllowDuplicateNodeChild_IdentityID(){
        //given
        INode<String> nodeDS = new Node.Builder("root","root").build();

        nodeDS = nodeDS.addChild(new Node.Builder("1","c").build());
        nodeDS.addChild(new Node.Builder("1","c").build());
        //when

        //then
    }

    @Test(expected = Node.RootAndChildNodeSameNameException.class)
    public void itShouldNotAllowRootAndChildNameSame(){
        //given
        INode<String> nodeDS = new Node.Builder("root","root").build();

        nodeDS = nodeDS.addChild(new Node.Builder("root","c").build());
        nodeDS.addChild(new Node.Builder("1","c").build());
        //when

        //then
    }

    @Test
    public void itShouldGetNoLeafForNoLeafNodes(){
        //given
        INode<String> root = new Node.Builder("root","root").build();

        INode[] expected = new INode[]{};
        //when
        List<INode<String>> actual = root.getLeafs();

        //then
        assertArrayEquals(expected,actual.toArray());

    }

    @Test
    public void itShouldGetAllLeafNodes(){
        //given
        INode<String> root = new Node.Builder("root","root").build();

        INode<String> child1 = new Node.Builder("child1","child1").build();
        INode<String> leaf1 = new Node.Builder("leaf1","leaf1").build();

        INode<String> child2 = new Node.Builder("child2","child2").build();
        INode<String> leaf2 = new Node.Builder("leaf2","leaf2").build();

        child1 = child1.addChild(leaf1);
        child2 = child2.addChild(leaf2);

        root = root.addChild(child1);
        root = root.addChild(child2);

        INode[] expected = new INode[]{leaf1, leaf2};
        //when
        List<INode<String>> actual = root.getLeafs();

        //then
        assertArrayEquals(expected,actual.toArray());

    }

    @Test
    public void itShouldGiveTagAndDescriptionToNode(){
        //given
        INode<String> root = new Node.Builder("root","root")
                                .tag("tag")
                                .description("desc")
                                .build();

        //when

        //then
        assertEquals("tag",root.getTag());
        assertEquals("desc",root.getDescription());
    }

}
