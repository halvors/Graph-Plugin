package com.calclavia.graph.api.energy;

import com.calclavia.graph.api.Node;
import nova.core.block.Block;

import java.util.function.Consumer;

/**
 * A node that can handle Redstone integer-based energy.
 *
 * Constructor requirement: Provider (An instance of {@link Block}
 * @author Calclavia
 */
public interface NodeRedstone extends Node<NodeRedstone> {

	/**
	 * A callback when redstone power changes in this node.
	 * @param action - The callback method.
	 */
	void onInputPowerChange(Consumer<NodeRedstone> action);

	/**
	 * @return Gets the strong input power to this node
	 */
	int getStrongPower();

	/**
	 * Sets the block to output strong Redstone power.
	 */
	void setStrongPower(int power);

	/**
	 * @return The Redstone power powered to a specific side of this block.
	 */
	int getWeakPower(int side);

	/**
	 * @return The greatest Redstone energy indirectly powering this block.
	 */
	int getWeakPower();

	/**
	 * Sets the block to output weak Redstone power.
	 */
	void setWeakPower(int power);
}
