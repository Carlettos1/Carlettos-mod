package com.carlettos.mod.entidades.prumytrak.prum.ia;

import java.util.EnumSet;

import com.carlettos.mod.entidades.prumytrak.prum.IPrumRandomRangedAttack;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.EntityPredicates;

public class PrumRandomRangedAttackGoal<E extends MonsterEntity & IPrumRandomRangedAttack> extends Goal{
	private final E entity;
	private final int intervalo;
	private int counter;
	
	public PrumRandomRangedAttackGoal(E entity, int intervalo) {
		this.entity = entity;
		this.intervalo = intervalo;
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
	public void startExecuting() {
		this.entity.setRandomRangedAgressive(true);
		this.counter = 0;
	}
	
	@Override
	public void resetTask() {
		LivingEntity target = this.entity.getAttackTarget();
		if(!EntityPredicates.CAN_AI_TARGET.test(target)) {
			this.entity.setAttackTarget(null);
		}
		this.entity.setRandomRangedAgressive(false);
	}
	
	@Override
	public void tick() {
		LivingEntity target = this.entity.getAttackTarget();
		if(--this.counter <= 0) {
			if(!this.entity.getEntitySenses().canSee(target)) {
				return;
			}
			this.counter = this.intervalo;
			this.entity.randomRangedAttack(target);
		}
	}
}
