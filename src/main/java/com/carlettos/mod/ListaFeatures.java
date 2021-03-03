package com.carlettos.mod;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class ListaFeatures {
	public static final ConfiguredFeature<?, ?> ore_bloque_ender_corrupto;
	
	static {
		ore_bloque_ender_corrupto = Feature.ORE.withConfiguration(
					new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, 
							ListaBloques.bloque_ender_corrupto.getDefaultState(), 33))
				.range(128).square().func_242731_b(20);
	}
}
