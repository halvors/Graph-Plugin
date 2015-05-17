package com.calclavia.graph.api;

import nova.core.util.Direction;

import java.util.Set;

/**
 * A node provider is implemented in blocks or entities.
 * A node provider provides a node that is associated with another object.
 * @author Calclavia
 */
public interface NodeProvider {
	/**
	 * Gets a list of getNodes that this NodeProvider provides.
	 * @param from - The direction being accessed
	 * @return - A set of getNodes.
	 */
	Set<Node<?>> getNodes(Direction from);
}
