package pl.jbujak.simulator.fake;

import pl.jbujak.simulator.environment.IWorldGenerator;
import pl.jbujak.simulator.environment.World;

public class NullWorldGenerator implements IWorldGenerator {

	@Override
	public void generate(World world) {
	}
}
