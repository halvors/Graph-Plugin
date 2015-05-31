package com.calclavia.graph.graph

import java.util.{List => JList, Set => JSet}

import com.calclavia.graph.node.Node
import com.resonant.lib.math.matrix.AdjacencyMatrix

import scala.collection.JavaConversions._

/**
 * A graph that contains getNodes, each with its ability to connect to other getNodes.
 * @author Calclavia
 */
abstract class GraphConnect[N <: Node[_]] extends Graph[N] {
	var adjMat: AdjacencyMatrix[N] = null
	protected var nodes = Set.empty[N]

	def add(node: N) {
		nodes += node
	}

	def remove(node: N) {
		nodes -= node
	}

	//TODO: Collection?
	override def getNodes: JList[N] = nodes.toList

	/**
	 * Find all nodes that are connected to a node
	 */
	def findAll(node: N, builder: Set[N] = Set.empty): Set[N] = node.connections().filterNot(builder.contains(_)).flatMap(n => findAll(n.asInstanceOf[N], builder + node)).toSet

	def build() {
		adjMat = new AdjacencyMatrix[N](nodes, nodes)

		for (node <- nodes) {
			for (con <- node.connections) {
				if (nodes.contains(con)) {
					adjMat(node, con.asInstanceOf[N]) = true
				}
			}
		}
	}
}