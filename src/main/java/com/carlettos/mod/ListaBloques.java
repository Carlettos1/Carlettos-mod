package com.carlettos.mod;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class ListaBloques {
	public static final Block bloque_ender_corrupto;
	
	static {
		bloque_ender_corrupto = new Block(Properties
				.create(Material.IRON, MaterialColor.IRON)
				.setRequiresTool()
				.hardnessAndResistance(5, 6)
				.sound(SoundType.METAL)
				.jumpFactor(1.2f));
	}
}
