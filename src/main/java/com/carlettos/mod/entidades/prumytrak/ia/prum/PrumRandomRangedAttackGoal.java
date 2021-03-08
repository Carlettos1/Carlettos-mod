package com.carlettos.mod.entidades.prumytrak.ia.prum;

import java.util.EnumSet;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class PrumRandomRangedAttackGoal extends Goal{
	private PrumTrakEntity entity;
	private int attackTime;
	private int aps;
	
	public PrumRandomRangedAttackGoal(PrumTrakEntity attacker, double movespeed, int maxAttackTime,
			float maxAttackDistanceIn) {
		this.entity = attacker;
		this.aps = maxAttackTime;
		this.setMutexFlags(EnumSet.noneOf(Goal.Flag.class));
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
		if(--this.attackTime <= 0) {
			this.entity.attackRandomly(10 * this.entity.getFase());
			this.attackTime = aps;
		}
	}
}
