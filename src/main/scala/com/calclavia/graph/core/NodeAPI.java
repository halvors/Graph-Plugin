package com.calclavia.graph.core;

import com.calclavia.graph.core.thermal.GridThermal$;
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
		//Thermal Graph
		Game.instance.eventManager.serverStopping.add(evt -> GridThermal$.MODULE$.clear());
	}
}
