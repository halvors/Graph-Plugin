package com.calclavia.graph.mcwrapper.redstone

import java.util.function.Consumer

import com.calclavia.graph.api
import com.calclavia.graph.api.energy
import com.calclavia.graph.core.base.NodeBlockConnect
import net.minecraft.world.World
import nova.core.block.Block
import nova.core.component.ComponentProvider
import nova.wrapper.mc1710.backward.world.BWWorld

/**
 * A Minecraft implementation that wraps Redstone to a Node
 * @author Calclavia
 */
//TODO: Create NodeVirtualRedstone (for MC blocks that are redstone, but don't implement NOVA)
class NodeRedstone(parent: ComponentProvider) extends NodeBlockConnect[api.energy.NodeRedstone](parent) with api.energy.NodeRedstone {

	var init = false

	var inputStrongPower = 0
	var inputWeakPower = 0
	var inputSidedWeakPower = Array(0, 0, 0, 0, 0, 0)

	var strongPower = 0
	var weakPower = 0

	var onPowerChange: Consumer[energy.NodeRedstone] = null

	override def onInputPowerChange(action: Consumer[energy.NodeRedstone]): Unit = {
		onPowerChange = action
	}

	override def getStrongPower: Int = {
		if (!init) recache()
		return inputStrongPower
	}

	override def setStrongPower(power: Int) {
		strongPower = power
		world.markChange(position)
	}

	override def getWeakPower(side: Int): Int = {
		if (!init) recache()
		return inputSidedWeakPower(side)
	}

	/**
	 * Recaches the Redstone state.
	 */
	def recache() {
		init = true
		var hasChanged = false

		val newInputStrongPower = mcWorld.getBlockPowerInput(position.xi, position.yi, position.zi)
		if (inputStrongPower != newInputStrongPower) {
			inputStrongPower = newInputStrongPower
			hasChanged = true
		}

		val newInputWeakPower = mcWorld.getStrongestIndirectPower(position.xi, position.yi, position.zi)
		if (inputWeakPower != newInputWeakPower) {
			inputWeakPower = newInputWeakPower
			hasChanged = true
		}

		val newInputSidedWeakPower = (0 until 6).map(mcWorld.getIndirectPowerLevelTo(position.xi, position.yi, position.zi, _)).toArray
		if (inputSidedWeakPower != newInputSidedWeakPower) {
			inputSidedWeakPower = newInputSidedWeakPower
			hasChanged = true
		}

		if (hasChanged) {
			onPowerChange.accept(this)
		}
	}

	def mcWorld: World = block.world().asInstanceOf[BWWorld].world()

	def block: Block = parent.asInstanceOf[Block]

	override def getWeakPower: Int = {
		if (!init) recache()
		return inputWeakPower
	}

	override def setWeakPower(power: Int) {
		weakPower = power
		world.markChange(position)
	}

}
