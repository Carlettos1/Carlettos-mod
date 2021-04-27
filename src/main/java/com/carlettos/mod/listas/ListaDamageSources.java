package com.carlettos.mod.listas;

import com.carlettos.mod.damagesources.AmanSpitDamageSource;
import com.carlettos.mod.damagesources.FasedEntityDamageSource;
import com.carlettos.mod.damagesources.PrumProyectilDamageSource;
import com.carlettos.mod.damagesources.TrakAOEDamageSource;
import com.carlettos.mod.entidades.aman.amanspit.AmanSpitEntity;
import com.carlettos.mod.entidades.interfaces.IHasFases;
import com.carlettos.mod.entidades.prumytrak.prum.prumproyectil.PrumProyectilEntity;
import com.carlettos.mod.entidades.prumytrak.trak.ITrakAOE;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;

public class ListaDamageSources {
	public static final<E extends MonsterEntity & ITrakAOE> DamageSource TRAK_AOE(E entity) {
		return new TrakAOEDamageSource<E>(entity);
	}
	
	public static final DamageSource AMAN_SPIT(AmanSpitEntity proyectil, Entity shooter) {
		return new AmanSpitDamageSource(proyectil, shooter);
	}
	
	public static final DamageSource PRUM_PROYECTIL(PrumProyectilEntity proyectil, Entity shooter) {
		return new PrumProyectilDamageSource(proyectil, shooter);
	}
	
	public static final<E extends MonsterEntity & IHasFases> DamageSource FASED_ENTITY(E entity, DamageSource damage) {
		return new FasedEntityDamageSource<E>(entity, damage);
	}
}
