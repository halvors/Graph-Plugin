package com.calclavia.graph.api.energy;

import nova.core.block.Block;
import nova.core.util.Direction;

/**
 * An electric node that acts as a component in an electric circuit.
 * Constructor requirement: Provider (An instance of {@link Block}
 * @author Calclavia
 */
public abstract class NodeElectricComponent extends NodeElectric {

	public NodeElectricComponent(Block provider) {
		super(provider);
	}

	public abstract void setPositives(Direction... dirs);

	public abstract void setNegatives(Direction... dirs);

	@Override
	public final String getID() {
		return "electricComponent";
	}
}
