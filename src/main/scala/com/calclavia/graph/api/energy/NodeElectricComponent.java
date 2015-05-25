package com.calclavia.graph.api.energy;

import nova.core.block.Block;
import nova.core.util.Direction;

/**
 * An electric node that acts as a component in an electric circuit.
 * Constructor requirement: Provider (An instance of {@link Block}
 * @author Calclavia
 */
public interface NodeElectricComponent extends NodeElectric {

	void setPositives(Direction... dirs);

	void setNegatives(Direction... dirs);

	@Override
	default String getID() {
		return "electricComponent";
	}
}
