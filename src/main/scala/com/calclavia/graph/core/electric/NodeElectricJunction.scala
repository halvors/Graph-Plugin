package com.calclavia.graph.core.electric

import com.calclavia.graph.core.electric.component.Junction
import nova.core.block.Block

/**
 * Wires are getNodes in the grid that will not have different terminals, but instead can connect omni-directionally.
 * Wires will be treated as junctions and collapsed.
 * @author Calclavia
 */
class NodeElectricJunction(parent: Block) extends com.calclavia.graph.api.energy.NodeElectricJunction(parent) with NodeAbstractElectric {

	override protected val block: Block = parent
	var junction: Junction = null

	override def current: Double = voltage * voltage / resistance

	override def toString: String = {
		"ElectricJunction [" + connections.size() + ", " + BigDecimal(voltage).setScale(2, BigDecimal.RoundingMode.HALF_UP) + "V]"
	}

	override def voltage: Double = junction.voltage
}
