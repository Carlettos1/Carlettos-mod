package com.carlettos.mod.entidades.trakhenchman;

import java.util.EnumSet;

import com.carlettos.mod.util.IAOEMob;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector3d;

public class TrakAOEAttackGoal<E extends MonsterEntity & IAOEMob> extends Goal{
	private final E entity;
	private final boolean longMemory;
	private final int intervalo = 20;
	private final double radio;
	private Vector3d targetPos = new Vector3d(0D, 0D, 0D);
	private int delayCounter;
	private int otroCounter;
	private long time;
	
	public TrakAOEAttackGoal(E entity, boolean longMemory, double radio) {
		this.entity = entity;
		this.longMemory = longMemory;
		this.radio = radio;
		this.setMutexFlags(EnumSet.noneOf(Goal.Flag.class));
	}
	
	@Override
	public boolean shouldExecute() {
		long i = this.entity.world.getGameTime();
		if(i - this.time < intervalo) {
			return false;
		} else {
			this.time = i;
			LivingEntity target = this.entity.getAttackTarget();
			if(target == null) {
				return false;
			} else if(!target.isAlive()) {
				return false;
			} else {
				Path path = this.entity.getNavigator().getPathToEntity(target, 0);
				if(path != null) {
					return true;
				} else {
					return this.getAttackReachSqr(target) >= this.entity.getDistanceSq(target);
				}
			}
		}
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		LivingEntity target = this.entity.getAttackTarget();
		if(target == null) {
			return false;
		} else if(!target.isAlive()) {
			return false;
		} else if(!this.longMemory) {
			return !this.entity.getNavigator().noPath();
		} else if(!this.entity.isWithinHomeDistanceFromPosition(target.getPosition())) {
			return false;
		} else {
			return EntityPredicates.CAN_AI_TARGET.test(target);
		}
	}
	
	@Override
	public void startExecuting() {
		this.entity.setAOEAgressive(true);
		this.delayCounter = 0;
		this.otroCounter = 0;
	}
	
	@Override
	public void resetTask() {
		LivingEntity target = this.entity.getAttackTarget();
		if(!EntityPredicates.CAN_AI_TARGET.test(target)) {
			this.entity.setAttackTarget(null);
		}
		this.entity.setAOEAgressive(false);
	}
	
	@Override
	public void tick() {
		LivingEntity target = this.entity.getAttackTarget();
		double d0 = this.entity.getDistanceSq(target);
		this.delayCounter = Math.max(this.delayCounter - 1, 0);
		if(this.longMemory && this.delayCounter <= 0 && (this.targetPos.equals(Vector3d.ZERO) || target.getDistanceSq(this.targetPos) >= 1D || this.entity.getRNG().nextFloat() < 0.05F)) {
			this.targetPos = new Vector3d(target.getPosX(), target.getPosY(), target.getPosZ());
			this.delayCounter = 4 + this.entity.getRNG().nextInt(7);
			if(d0 > 1042D) {
				this.delayCounter += 10;
			} else if(d0 > 256D) {
				this.delayCounter += 5;
			}
		}
		this.otroCounter = Math.max(this.otroCounter - 1, 0);
		double d1 = this.getAttackReachSqr(target);
		if(d0 <= d1 && this.otroCounter <= 0) {
			this.otroCounter = 20;
			this.entity.AOEAnimation(false);
			this.entity.AOEAttack(this.radio);
		}
	}
	
	public double getAttackReachSqr(LivingEntity target) {
		return this.radio * this.radio + target.getWidth();
	}
}
