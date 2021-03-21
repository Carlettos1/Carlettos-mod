package com.carlettos.mod.entidades.prumytrak.ia.prum;

import java.util.EnumSet;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;

public class PrumRangedAttackGoal extends Goal{
	private PrumTrakEntity entity;
	private byte attackTime;
	private byte aps;
	private float rango;

	public PrumRangedAttackGoal(PrumTrakEntity attacker, double movespeed, byte attackTime, float ditanciaAtaque) {
		this.entity = attacker;
		this.aps = attackTime;
		this.rango = ditanciaAtaque;
		this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
	}
	
	@Override
	public boolean shouldExecute() {
		LivingEntity entidad = this.entity.getAttackTarget();
		if(entidad != null && entidad.isAlive()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void resetTask() {
		this.attackTime = 0;
		this.entity.setAtacandoARango(false);
	}
	
	@Override
	public void tick() {
		LivingEntity target = this.entity.getAttackTarget();
		double d0 = this.entity.getDistance(target);
		boolean flag = this.entity.getEntitySenses().canSee(target);
		
		this.entity.getLookController().setLookPositionWithEntity(target, 30F, 30F);
		if(--this.attackTime <= 0) {
			if(!flag) {
				return;
			}
			this.entity.setAtacandoARango(true);
			double f = d0 / this.rango;
			double f1 = MathHelper.clamp(f, 0.1D, 1D);
			this.entity.attackEntityWithRangedAttack(target, (float)f1);
			this.attackTime = aps;
		} else {
			this.entity.setAtacandoARango(false);
		}
	}
}
