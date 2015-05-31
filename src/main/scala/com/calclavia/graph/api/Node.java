package com.calclavia.graph.api;

import java.util.Set;

/**
 * A node is a object with connections in a graph structure.
 * A node should extend Component
 */
public interface Node<CONNECTION extends Node<?>> {

	/**
	 * Gets a list of nodes connected to this node.
	 */
	Set<CONNECTION> connections();
}
