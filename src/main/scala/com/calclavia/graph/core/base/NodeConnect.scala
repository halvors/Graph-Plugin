package com.calclavia.graph.core.base

import com.calclavia.graph.api.Node
import nova.core.util.Direction

/**
 * @author Calclavia
 */
trait NodeConnect[N <: Node[N]] extends Node[N] {
	var connectionMask: Int = 0x3F
	var connectListener = () => ()
	var _canConnect = (node: N, dir: Direction) => (connectionMask & (1 << dir.ordinal())) != 0

	protected var connectedMask: Int = 0x0

	def canConnect(node: N, dir: Direction): Boolean = {
		return _canConnect.apply(node, dir)
	}
}