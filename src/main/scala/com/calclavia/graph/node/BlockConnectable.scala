package com.calclavia.graph.node

import java.util.function.Supplier
import java.util.{Optional, Set => JSet}

import nova.core.block.Block
import nova.core.util.Direction
import nova.core.util.transform.vector.Vector3i
import nova.core.world.World

import scala.collection.convert.wrapAll._

/**
 * A node that connects to adjacent blocks.
 * @author Calclavia
 */
trait BlockConnectable[N <: Node[N]] extends Connectable[N] {

	/**
	 * @return The block assosiated with this node.
	 */
	protected def block: Block

	//TODO: Expose to Java side
	protected var connectionFunction = () => {
		val adjacentBlocks: Map[Direction, Optional[Block]] = this.adjacentBlocks
		val adjacentNodes: Map[Direction, N] =
			adjacentBlocks
				.filter { case (k, v) => v.isPresent && v.get.getClass().isAssignableFrom(compareClass) }
				.map { case (k, v) => (k, getNodeFromBlock(v.get(), k)) }

		val connectedMap = adjacentNodes
			.filter { case (k, v) => canConnect(v, k) }
			.filter { case (k, v) => v.asInstanceOf[Connectable[N]].canConnect(this.asInstanceOf[N], k.opposite) }
			.map(_.swap)

		connectedMask = connectedMap.values
			.map(_.ordinal)
			.map(i => 1 << i)
			.foldLeft(0)((a, b) => a | b)

		connectedMap.keySet
	}

	def setConnections(f: Supplier[Set[N]]) {
		connectionFunction = () => f.get()
	}

	def setConnections(f: () => Set[N]) {
		connectionFunction = () => f.apply()
	}

	def connections: JSet[N] = connectionFunction()

	/**
	 * @return The set of blocks adjacent to this block
	 */
	protected def adjacentBlocks: Map[Direction, Optional[Block]] = Direction.DIRECTIONS.map(dir => (dir, world.getBlock(position + dir.toVector))).toMap

	protected def getNodeFromBlock(block: Block, from: Direction): N = block.getOp(compareClass).orElse(null.asInstanceOf[N])

	def world: World = block.world()

	def position: Vector3i = block.transform.position

	protected def compareClass: Class[N] = getClass.asInstanceOf[Class[N]]
}
