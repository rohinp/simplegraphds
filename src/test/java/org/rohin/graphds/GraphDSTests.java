package org.rohin.graphds;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GraphDSTests {

    @Test(expected = Graph.EmptyGraphException.class)
    public void itShouldCreateAnEmptyGraph(){
        //given
        IGraph<String> graphDS = Graph.createAcyclicGraph();
        //when
        graphDS.traverse();
        //then
    }

    @Test
    public void itShouldAllowToAddNodeAndChildNode(){
        //given
        IGraph<String> graph = Graph.createAcyclicGraph();

        //when
        graph.addNode("root1","data");
        graph.addChildNode("root1","child1","data");

        //then
        assertEquals(graph.getRoot().get(0).id(),"root1");
        assertEquals(graph.getChildOf("root1").get(0).id(),"child1");

    }
}
