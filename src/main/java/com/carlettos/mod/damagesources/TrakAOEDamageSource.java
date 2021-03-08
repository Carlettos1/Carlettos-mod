package com.carlettos.mod.damagesources;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TrakAOEDamageSource extends EntityDamageSource{
	PrumTrakEntity prumtrak;

	public TrakAOEDamageSource(PrumTrakEntity damageSourceEntityIn) {
		super("trak_aoe_damage", damageSourceEntityIn);
		prumtrak = damageSourceEntityIn;
	}
	
	@Override
	public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
		String text = "death.attack." + this.damageType + ".fase" + this.prumtrak.getFase() + ".text" + this.prumtrak.getRNG().nextInt(3);
		return new TranslationTextComponent(
				text, 
				entityLivingBaseIn.getDisplayName(), 
				this.prumtrak.getDisplayName(),
				(int)this.prumtrak.getHealth());
	}
}

