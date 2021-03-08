package com.carlettos.mod.listas;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class ListaFeatures {
	public static final ConfiguredFeature<?, ?> ORE_BLOQUE_ENDER_CORRUPTO;
	
	static {
		ORE_BLOQUE_ENDER_CORRUPTO = Feature.ORE.withConfiguration(
					new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, 
							ListaBloques.BLOQUE_ENDER_CORRUPTO.getDefaultState(), 33))
				.range(128).square().func_242731_b(20);
	}
}
