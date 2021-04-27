package com.carlettos.mod.damagesources;

import com.carlettos.mod.entidades.aman.amanspit.AmanSpitEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AmanSpitDamageSource extends IndirectEntityDamageSource {
	private final Entity shooter;
	
	public AmanSpitDamageSource(AmanSpitEntity source, Entity shooter) {
		super("aman_spit_damage", source, shooter);
		this.shooter = shooter;
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
				this.shooter.getDisplayName());
	}
}
