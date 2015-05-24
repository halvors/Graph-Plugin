package com.calclavia.graph.core.base;

import com.calclavia.graph.api.node.Node;
import nova.core.block.Block;
import nova.core.component.ComponentProvider;
import nova.core.util.Direction;
import nova.core.util.exception.NovaException;
import nova.core.util.transform.vector.Vector3i;
import nova.core.world.World;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A node that connects to adjacent blocks.
 * @author Calclavia
 */
public abstract class NodeBlockConnect<N extends Node<?>> extends NodeConnect<N> {

	public final ComponentProvider provider;
	//The cached connection map
	protected Map<N, Direction> connectedMap;

	public NodeBlockConnect(ComponentProvider parent) {
		this.provider = parent;
	}

	@Override
	public Set<N> connections() {

		Map<Direction, Optional<Block>> adjacentBlocks = adjacentBlocks();

		Map<Direction, N> adjacentNodes = adjacentBlocks
			.entrySet()
			.stream()
			.filter(entry -> entry.getValue().isPresent())
			.filter(entry -> entry.getValue().get().getClass().isAssignableFrom(compareClass()))
			.collect(Collectors.toMap(Map.Entry::getKey, entry -> getNodeFromBlock(entry.getValue().get(), entry.getKey())));

		//Generates a map of connections and their directions
		connectedMap = adjacentNodes
			.entrySet()
			.stream()
			.filter(entry -> canConnect(entry.getValue(), entry.getKey()))
			.filter(entry -> ((NodeConnect) entry.getValue()).canConnect((N) this, entry.getKey().opposite()))
			.collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

		//Sets the connection mask based on the connections
		connectedMask = connectedMap.values().stream().map(Enum::ordinal).map(i -> 1 << i).reduce((a, b) -> a | b).orElseGet(() -> 0);

		return connectedMap.keySet();
	}

	protected Map<Direction, Optional<Block>> adjacentBlocks() {
		return Arrays.stream(Direction.DIRECTIONS)
			.collect(Collectors.toMap(Function.identity(), dir -> world().getBlock(position().add(dir.toVector()))));
	}

	protected N getNodeFromBlock(Block block, Direction from) {
		if (block instanceof ComponentProvider) {
			return (N) block.components()
				.stream()
				.filter(n -> compareClass().isAssignableFrom(n.getClass()))
				.findFirst()
				.orElse(null);
		}

		return null;
	}

	@Override
	public ComponentProvider provider() {
		return provider;
	}

	public World world() {
		if (provider instanceof Block) {
			return ((Block) provider).world();
		}
		throw new NovaException("NodeProvider type not supported.");
	}

	public Vector3i position() {
		if (provider instanceof Block) {
			return ((Block) provider).position();
		}
		throw new NovaException("NodeProvider type not supported.");
	}

	protected <N> Class<N> compareClass() {
		return (Class<N>) getClass();
	}
}
