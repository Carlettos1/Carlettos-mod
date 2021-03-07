package com.carlettos.mod.entidades.prumytrak.ia.prum;

import java.util.EnumSet;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;

public class PrumRandomRangedAttackGoal extends RangedAttackGoal{
	private PrumTrakEntity entity;
	private int attackTime;
	private int aps;
	private float rango;
	
	public PrumRandomRangedAttackGoal(PrumTrakEntity attacker, double movespeed, int maxAttackTime,
			float maxAttackDistanceIn) {
		super(attacker, movespeed, maxAttackTime, maxAttackDistanceIn);
		this.entity = attacker;
		this.aps = maxAttackTime;
		this.rango = maxAttackDistanceIn;
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
		System.out.println(attackTime);
		if(--this.attackTime <= 0) {
			this.entity.attackRandomly(10 * this.entity.getFase());
			this.attackTime = aps;
		}
	}
}
