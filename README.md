# Graph-API
The Graph API is a NOVA plugin that allow mods to handle graphs and nodes.


The Graph API features pre-built adjacency graphs and node classes to be used for different purposes, 
such as designing energy grids and fluid systems in the game. Examples of systems that use the Graph API
include Electrodynamic's electric circuits and mechanical systems, pipe pressure system and Minecraft's wrapped
Redstone system.

## Use case
A Graph is a representation of a set of objects where some pairs of objects are connected by links.
A Node has connections to other nodes and belongs to a graph. (See http://en.wikipedia.org/wiki/Graph_%28mathematics%29)

For example, a block wants to implement the Redstone graph.
The block will need to implement NodeProvider. The NodeProvider interface defines a Block as an object that contains a Node.

```java
class BlockMachine extends Block, implements NodeProvider {
	
	private NodeRedstone redstoneNode = nodeManager.make(NodeRedstone.class, this);
	
	public Set<Node<?>> getNodes(Direction from)
	{
		return Collections.singleton(redstoneNode);
	}
}
```

The block can then alter the node's values or read data from the node by accessing methods from the node's interface.
In this case, if we want to get the block's input redstone power, we call:

```java
redstoneNode.getWeakPower();
```

Similar cases apply for other types of nodes. For electric nodes, we can call:

```java
electricNode.getPower();
electricNode.getCurrent();
electricNode.getVoltage();
electricNode.getResistance();
```

Nodes can also have events.
```java
redstoneNode.onInputPowerChange(
	node -> {
		if (node.getWeakPower > 0)
			this.setActive(true)
		else
			this.setActive(false)
	}
)
```
