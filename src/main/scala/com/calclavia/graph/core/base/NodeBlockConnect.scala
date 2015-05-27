package com.calclavia.graph.core.base

import java.util.{Map => JMap, Optional, Set => JSet}

import com.calclavia.graph.api.Node
import nova.core.block.Block
import nova.core.util.Direction
import nova.core.util.transform.vector.Vector3i
import nova.core.world.World

import scala.collection.convert.wrapAll._

/**
 * A node that connects to adjacent blocks.
 * @author Calclavia
 */
trait NodeBlockConnect[N <: Node[N]] extends NodeConnect[N] {
	protected val block: Block
	//TODO: Remove
	protected var connectedMap: JMap[N, Direction] = null

	//TODO: Expose to Java side
	protected var getConnections: () => JSet[N] = () => {
		val adjacentBlocks: Map[Direction, Optional[Block]] = getAdjacentBlocks
		val adjacentNodes: Map[Direction, N] =
			adjacentBlocks
				.filter { case (k, v) => v.isPresent && v.get.getClass().isAssignableFrom(compareClass) }
				.map { case (k, v) => (k, getNodeFromBlock(v.get(), k)) }

		connectedMap = adjacentNodes
			.filter { case (k, v) => canConnect(v, k) }
			.filter { case (k, v) => v.asInstanceOf[NodeConnect[N]].canConnect(this.asInstanceOf[N], k.opposite) }
			.map(_.swap)
			.asInstanceOf[JMap[N, Direction]]

		connectedMask = connectedMap.values
			.map(_.ordinal)
			.map(i => 1 << i)
			.foldLeft(0)((a, b) => a | b)

		connectedMap.keySet
	}

	def setConnectionHandler(conFunction: () => JSet[N]): this.type = {
		getConnections = conFunction
		return this
	}

	def connections: JSet[N] = getConnections()

	protected def getAdjacentBlocks: Map[Direction, Optional[Block]] = Direction.DIRECTIONS.map(dir => (dir, world.getBlock(position + dir.toVector))).toMap

	def world: World = block.world()

	def position: Vector3i = block.transform.position

	protected def getNodeFromBlock(block: Block, from: Direction): N = block.getOp(compareClass).orElse(null.asInstanceOf[N])

	protected def compareClass: Class[N] = getClass.asInstanceOf[Class[N]]
}