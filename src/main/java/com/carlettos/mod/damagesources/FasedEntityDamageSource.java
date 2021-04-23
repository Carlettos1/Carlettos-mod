package com.carlettos.mod.damagesources;

import com.carlettos.mod.entidades.interfaces.IHasFases;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FasedEntityDamageSource<E extends MonsterEntity & IHasFases> extends EntityDamageSource{
	private final E entity;
	private final DamageSource damage;
	
	public FasedEntityDamageSource(E entity, DamageSource base) {
		super("fased_entity_damage", entity);
		this.entity = entity;
		this.damage = base;
	}
	
	@Override
	public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
		ITextComponent text = this.damage.getDeathMessage(entityLivingBaseIn);
		String key;
		if(text instanceof TranslationTextComponent) {
			key = ((TranslationTextComponent) text).getKey();
		} else {
			key = "death.attack." + this.damageType;
		}
		key += ".fase_" + this.entity.getFase();
		return new TranslationTextComponent(
				key,
				entityLivingBaseIn.getDisplayName(),
				this.entity.getDisplayName());
	}
}
