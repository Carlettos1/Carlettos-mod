package com.carlettos.mod.listas;

import com.carlettos.mod.damagesources.TrakAOEDamageSource;
import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;

import net.minecraft.util.DamageSource;

public class ListaDamageSources {
	public static final DamageSource TRAK_AOE(PrumTrakEntity entity) {
		return new TrakAOEDamageSource(entity);
	}
}
