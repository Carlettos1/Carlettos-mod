package com.carlettos.mod.damagesources;

import com.carlettos.mod.entidades.prumytrak.prum.IPrumRangedAttack;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class PrumProyectilDamageSource<E extends MonsterEntity & IPrumRangedAttack> extends EntityDamageSource {
	private final E entity;
	
	public PrumProyectilDamageSource(E damageSourceEntityIn) {
		super("prum_proyectil_damage", damageSourceEntityIn);
		this.entity = damageSourceEntityIn;
	}
	
	/**
	 * %1$s = entidad muriendo
	 * %2$s = agresor
	 */
	@Override
	public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
		String text = "death.attack." + this.damageType;
		return new TranslationTextComponent(
				text, 
				entityLivingBaseIn.getDisplayName(), 
				this.entity.getDisplayName());
	}
}
