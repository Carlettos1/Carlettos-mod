package com.carlettos.mod.entidades.prumytrak.prum.ia;

import java.util.EnumSet;

import com.carlettos.mod.entidades.prumytrak.prum.IPrumRangedAttack;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.EntityPredicates;

public class PrumRangedAttackGoal<E extends MonsterEntity & IPrumRangedAttack> extends Goal{
	private final E entity;
	private final int intervalo;
	private int counter;
	
	public PrumRangedAttackGoal(E entity, int intervalo) {
		this.entity = entity;
		this.intervalo = intervalo;
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
	public void startExecuting() {
		this.entity.setRangedAgressive(true);
		this.counter = 0;
	}
	
	@Override
	public void resetTask() {
		LivingEntity target = this.entity.getAttackTarget();
		if(!EntityPredicates.CAN_AI_TARGET.test(target)) {
			this.entity.setAttackTarget(null);
		}
		this.entity.setRangedAgressive(false);
	}
	
	@Override
	public void tick() {
		LivingEntity target = this.entity.getAttackTarget();
		this.entity.getLookController().setLookPositionWithEntity(target, 30F, 30F);
		if(--this.counter <= 0) {
			if(!this.entity.getEntitySenses().canSee(target)) {
				return;
			}
			this.counter = this.intervalo;
			this.entity.RangedAnimation(false);
			this.entity.RangedAttack(target);
		}
		super.tick();
	}
}
