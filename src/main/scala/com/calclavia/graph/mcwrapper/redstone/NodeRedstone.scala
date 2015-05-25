package com.calclavia.graph.mcwrapper.redstone

import java.util.function.Consumer

import com.calclavia.graph.api
import com.calclavia.graph.api.energy
import com.calclavia.graph.core.base.NodeBlockConnect
import com.resonant.lib.wrapper.WrapFunctions._
import net.minecraft.world.World
import nova.core.block.Block
import nova.core.block.Block.NeighborChangeEvent
import nova.wrapper.mc1710.backward.world.BWWorld

/**
 * A Minecraft implementation that wraps Redstone to a Node
 * @author Calclavia
 */
//TODO: Create NodeVirtualRedstone (for MC blocks that are redstone, but don't implement NOVA)
class NodeRedstone(parent: Block) extends api.energy.NodeRedstone with NodeBlockConnect[api.energy.NodeRedstone] {

	override protected val block: Block = parent
	var init = false
	var inputStrongPower = 0
	var inputWeakPower = 0
	var inputSidedWeakPower = Array(0, 0, 0, 0, 0, 0)
	var strongPower = 0
	var weakPower = 0
	var onPowerChange: Consumer[energy.NodeRedstone] = null

	//Hook into the block's events.
	parent.neighborChangeEvent.add((evt: NeighborChangeEvent) => recache())

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

	override def getWeakPower: Int = {
		if (!init) recache()
		return inputWeakPower
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

	override def setWeakPower(power: Int) {
		weakPower = power
		world.markChange(position)
	}

}
