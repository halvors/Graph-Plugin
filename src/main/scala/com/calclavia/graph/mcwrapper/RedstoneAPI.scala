package com.calclavia.graph.mcwrapper

import com.calclavia.graph.mcwrapper.redstone.NodeRedstone
import com.resonant.lib.wrapper.WrapFunctions._
import nova.core.component.{Component, ComponentManager, ComponentProvider}
import nova.core.event.EventManager.BlockNeighborChangeEvent
import nova.core.game.Game
import nova.core.loader.{Loadable, NativeLoader}
import nova.core.util.transform.vector.Vector3i
import nova.core.world.World
import nova.wrapper.mc1710.util.WrapperEventManager
import nova.wrapper.mc1710.util.WrapperEventManager.{RedstoneConnectEvent, RedstoneEvent}

import scala.collection.convert.wrapAll._

/**
 * The Minecraft native loader
 * @author Calclavia
 */
@NativeLoader(forGame = "minecraft")
class RedstoneAPI(componentManager: ComponentManager) extends Loadable {

	override def preInit() {
		//Registers Redstone Node
		componentManager.register(
			func[Array[AnyRef], Component]((args) => {
				if (args.length > 0) {
					new NodeRedstone(args(0).asInstanceOf[ComponentProvider])
				} else {
					new NodeRedstone(null)
				}
			}))

		Game.instance.eventManager.blockNeighborChange.add(
			(evt: BlockNeighborChangeEvent) => {
				getRedstoneNodes(evt.world, evt.position).foreach(_.recache())
			}
		)

		WrapperEventManager.instance.onCanConnect.add(
			(evt: RedstoneConnectEvent) => {
				//TODO: Null may cause an issue
				evt.canConnect = getRedstoneNodes(evt.world, evt.position).exists(_.canConnect(null, evt.direction))
			}
		)

		WrapperEventManager.instance.onStrongPower.add(
			(evt: RedstoneEvent) => {
				evt.power = getRedstoneNodes(evt.world, evt.position).map(_.strongPower).foldLeft(0)(_.max(_))
			}
		)

		WrapperEventManager.instance.onWeakPower.add(
			(evt: RedstoneEvent) => {
				evt.power = getRedstoneNodes(evt.world, evt.position).map(_.weakPower).foldLeft(0)(_.max(_))
			}
		)
	}

	def getRedstoneNodes(world: World, pos: Vector3i): Set[NodeRedstone] = {
		val blockOptional = world.getBlock(pos)
		if (blockOptional.isPresent && blockOptional.get().isInstanceOf[ComponentProvider]) {
			val nodeProvider = blockOptional.get().asInstanceOf[ComponentProvider]

			return nodeProvider
				.components()
				.collect { case n: NodeRedstone => n }
				.toSet
		}

		return Set.empty
	}
}