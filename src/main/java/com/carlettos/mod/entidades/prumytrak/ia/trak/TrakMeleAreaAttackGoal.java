package com.carlettos.mod.entidades.prumytrak.ia.trak;

import java.util.EnumSet;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;

import net.minecraft.entity.ai.goal.Goal;

public class TrakMeleAreaAttackGoal extends Goal{
	private PrumTrakEntity entity;
	private double rango;
	private byte aps;
	private byte currentTime;
	
	public TrakMeleAreaAttackGoal(PrumTrakEntity entity, double rango, byte time) {
		this.entity = entity;
		this.rango = rango;
		this.aps = time;
		setMutexFlags(EnumSet.noneOf(Goal.Flag.class));
	}

	@Override
	public boolean shouldExecute() {
		return this.entity.getAttackTarget() != null && this.entity.getAttackTarget().getDistance(this.entity) <= this.rango;
	}
	
	@Override
	public void resetTask() {
		this.currentTime = 0;
		this.entity.setAtacandoAMeleAOE(false);
	}
	
	@Override
	public void tick() {
		if(--this.currentTime <= 0) {
			this.entity.setAtacandoAMeleAOE(true);
			this.entity.ataqueEnArea(this.rango);
			this.currentTime = this.aps;
		} else {
			this.entity.setAtacandoAMeleAOE(false);
		}
	}
}
