package com.calclavia.graph.api.energy;

import com.calclavia.graph.api.Node;
import nova.core.block.Block;

/**
 * An abstract interface extended by NodeElectricComponent and NodeElectricJunction.
 * This interface is NOT registered.
 * @author Calclavia
 */
public abstract class NodeElectric extends Node<NodeElectric> {

	public final Block provider;

	public NodeElectric(Block provider) {
		this.provider = provider;
	}

	/**
	 * @return The resistance of the electric component in ohms.
	 */
	public abstract double resistance();

	/**
	 * @return The voltage (potential difference) of the component in volts.
	 */
	public abstract double voltage();

	/**
	 * @return The current of the component in amperes.
	 */
	public abstract double current();

	/**
	 * @return The power dissipated in the component.
	 */
	public double power() {
		return current() * voltage();
	}
}
