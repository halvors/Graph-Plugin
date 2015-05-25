package com.calclavia.graph.api;

import nova.core.component.Component;

import java.util.Set;

/**
 * A node is a object with connections in a graph structure.
 */
public abstract class Node<CONNECTION extends Node<?>> extends Component {

	/**
	 * Gets a list of getNodes connected to this node.
	 */
	public abstract Set<CONNECTION> connections();
}
