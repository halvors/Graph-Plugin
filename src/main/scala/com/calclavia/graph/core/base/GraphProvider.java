package com.calclavia.graph.core.base;

import com.calclavia.graph.api.graph.Graph;

/**
 * A node that provides a reference to the graph that it is in.
 * @author Calclavia
 */
@Deprecated
public interface GraphProvider<G extends Graph> {

	G getGraph();

	void setGraph(G graph);
}
