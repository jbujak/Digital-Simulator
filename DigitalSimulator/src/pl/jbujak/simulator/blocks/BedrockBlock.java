package pl.jbujak.simulator.blocks;

import pl.jbujak.simulator.environment.Direction;

public class BedrockBlock extends Block {
	
	public BedrockBlock() {
		this.blockType = BlockType.BEDROCK;
		this.isSolid = true;
		this.isTransparent = false;
	}
	
	public static String getTextureName(Direction face){
		return "bedrock.png";
	}
}
