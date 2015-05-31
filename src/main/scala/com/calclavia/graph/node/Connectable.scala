package com.calclavia.graph.node

import nova.core.event.EventBus
import nova.core.util.Direction

/**
 * @author Calclavia
 */
trait Connectable[N <: Node[N]] extends Node[N] {

	var connectionMask: Int = 0x3F
	var connectListener = new EventBus[ConnectEvent]()
	protected var connectedMask: Int = 0x0
	private var _canConnect = (node: N, dir: Direction) => (connectionMask & (1 << dir.ordinal())) != 0

	def canConnect(node: N, dir: Direction): Boolean = _canConnect.apply(node, dir)

	def canConnect_=(f: (N, Direction) => Boolean) {
		_canConnect = f
	}

	class ConnectEvent {

	}

}
