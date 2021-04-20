package com.carlettos.mod.listas;

import com.carlettos.mod.damagesources.AmanSpitDamageSource;
import com.carlettos.mod.damagesources.PrumProyectilDamageSource;
import com.carlettos.mod.damagesources.TrakAOEDamageSource;
import com.carlettos.mod.entidades.aman.IAmanSpit;
import com.carlettos.mod.entidades.prumytrak.prum.IPrumRangedAttack;
import com.carlettos.mod.entidades.prumytrak.trak.ITrakAOE;

import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;

public class ListaDamageSources {
	public static final<E extends MonsterEntity & ITrakAOE> DamageSource TRAK_AOE(E entity) {
		return new TrakAOEDamageSource<E>(entity);
	}
	
	public static final<E extends MonsterEntity & IAmanSpit> DamageSource AMAN_SPIT(E entity) {
		return new AmanSpitDamageSource<E>(entity);
	}
	
	public static final<E extends MonsterEntity & IPrumRangedAttack> DamageSource PRUM_PROYECTIL(E entity) {
		return new PrumProyectilDamageSource<E>(entity);
	}
}
