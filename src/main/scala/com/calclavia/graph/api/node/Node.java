package com.calclavia.graph.api.node;

import nova.core.component.Component;

import java.util.Set;

/**
 * A node is a object with connections in a graph structure.
 */
public interface Node<CONNECTION extends Node<?>> extends Component {

	/**
	 * Gets a list of getNodes connected to this node.
	 */
	Set<CONNECTION> connections();
}
