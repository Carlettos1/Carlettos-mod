package com.carlettos.mod.entidades.prumytrak.ia.prum;

import java.util.EnumSet;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.util.math.MathHelper;

public class PrumRangedAttackGoal extends RangedAttackGoal{
	private PrumTrakEntity entity;
	private int attackTime;
	private int aps;
	private float rango;

	public PrumRangedAttackGoal(PrumTrakEntity attacker, double movespeed, int attackTime, float ditanciaAtaque) {
		super(attacker, movespeed, attackTime, ditanciaAtaque);
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
	public boolean shouldContinueExecuting() {
		return this.shouldExecute();
	}
	
	@Override
	public void startExecuting() {
		// TODO: SET AGROED CABEZA 2
	}
	
	@Override
	public void resetTask() {
		//TODO: QUITAR AGROED CABEZA 2
		this.attackTime = -1;
	}
	
	@Override
	public void tick() {
		LivingEntity target = this.entity.getAttackTarget();
		double d0 = this.entity.getDistance(target);
		boolean flag = this.entity.getEntitySenses().canSee(target);
		
		this.entity.getLookController().setLookPositionWithEntity(target, 30F, 30F);
		System.out.println(attackTime);
		if(--this.attackTime <= 0) {
			if(!flag) {
				return;
			}
			
			double f = d0 / this.rango;
			double f1 = MathHelper.clamp(f, 0.1D, 1D);
			this.entity.attackEntityWithRangedAttack(target, (float)f1);
			this.attackTime = aps;
		}
	}
}
