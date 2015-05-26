package com.calclavia.graph.core;

import com.calclavia.graph.core.electric.NodeElectricComponent;
import com.calclavia.graph.core.electric.NodeElectricJunction;
import com.calclavia.graph.core.thermal.GridThermal$;
import nova.core.block.Block;
import nova.core.component.ComponentManager;
import nova.core.game.Game;
import nova.core.loader.Loadable;
import nova.core.loader.NovaMod;

/**
 * The main plugin loader class.
 * @author Calclavia
 */
@NovaMod(id = "nodeAPI", name = "Node API", version = "0.0.1", novaVersion = "0.0.1", isPlugin = true)
public class NodeAPI implements Loadable {

	private final ComponentManager componentManager;

	public NodeAPI(ComponentManager componentManager) {
		this.componentManager = componentManager;
	}

	@Override
	public void preInit() {
		//TODO: -1000 style points
		componentManager.register(args -> args.length > 0 ? new NodeElectricComponent((Block) args[0]) : new NodeElectricComponent(new Block() {
			@Override
			public String getID() {
				return "dummy";
			}
		}));
		componentManager.register(args -> args.length > 0 ? new NodeElectricJunction((Block) args[0]) : new NodeElectricJunction(new Block() {
			@Override
			public String getID() {
				return "dummy";
			}
		}));

		//Thermal Graph
		Game.instance.eventManager.serverStopping.add(evt -> GridThermal$.MODULE$.clear());
	}
}
