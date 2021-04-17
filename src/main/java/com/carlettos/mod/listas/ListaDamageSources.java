package com.carlettos.mod.listas;

import com.carlettos.mod.damagesources.TrakAOEDamageSource;
import com.carlettos.mod.entidades.IHasFases;
import com.carlettos.mod.entidades.prumytrak.trak.ITrakAOE;

import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;

public class ListaDamageSources {
	public static final<E extends MonsterEntity & ITrakAOE & IHasFases> DamageSource TRAK_AOE(E entity) {
		return new TrakAOEDamageSource<E>(entity);
	}
}
