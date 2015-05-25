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
public abstract class NodeRedstone extends Node<NodeRedstone> {

	/**
	 * A callback when redstone power changes in this node.
	 * @param action - The callback method.
	 */
	public abstract void onInputPowerChange(Consumer<NodeRedstone> action);

	/**
	 * @return Gets the strong input power to this node
	 */
	public abstract int getStrongPower();

	/**
	 * Sets the block to output strong Redstone power.
	 */
	public abstract void setStrongPower(int power);

	/**
	 * @return The Redstone power powered to a specific side of this block.
	 */
	public abstract int getWeakPower(int side);

	/**
	 * @return The greatest Redstone energy indirectly powering this block.
	 */
	public abstract int getWeakPower();

	/**
	 * Sets the block to output weak Redstone power.
	 */
	public abstract void setWeakPower(int power);

	@Override
	public final String getID() {
		return "redstone";
	}
}
