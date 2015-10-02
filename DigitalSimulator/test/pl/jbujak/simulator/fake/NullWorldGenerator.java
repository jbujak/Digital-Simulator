package pl.jbujak.simulator.fake;

import pl.jbujak.simulator.world.IWorldGenerator;
import pl.jbujak.simulator.world.World;

public class NullWorldGenerator implements IWorldGenerator {

	@Override
	public void generate(World world) {
	}
}
