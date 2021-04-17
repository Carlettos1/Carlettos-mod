package com.carlettos.mod.damagesources;

import com.carlettos.mod.entidades.IHasFases;
import com.carlettos.mod.entidades.prumytrak.trak.ITrakAOE;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TrakAOEDamageSource<E extends MonsterEntity & ITrakAOE & IHasFases> extends EntityDamageSource{
	E entity;

	public TrakAOEDamageSource(E damageSourceEntityIn) {
		super("trak_aoe_damage", damageSourceEntityIn);
		entity = damageSourceEntityIn;
	}
	
	@Override
	public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
		String text = "death.attack." + this.damageType + ".fase" + this.entity.getFase() + ".text" + this.entity.getRNG().nextInt(3);
		return new TranslationTextComponent(
				text, 
				entityLivingBaseIn.getDisplayName(), 
				this.entity.getDisplayName(),
				(int)this.entity.getHealth());
	}
}

