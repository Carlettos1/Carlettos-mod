package com.carlettos.mod.entidades.aman.ia;

import java.util.EnumSet;

import com.carlettos.mod.entidades.aman.IAmanSpit;
import com.carlettos.mod.listas.ListaAtributos;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.EntityPredicates;

public class AmanSpitGoal<E extends MonsterEntity & IAmanSpit> extends Goal{
	private final E entity;
	private final int intervalo;
	private int counter;
	
	public AmanSpitGoal(E entity, int intervalo) {
		this.entity = entity;
		this.intervalo = intervalo;
		this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
	}
	
	@Override
	public boolean shouldExecute() {
		LivingEntity target = entity.getAttackTarget();
		if(entity.getAttributeValue(ListaAtributos.RANGE_ATTACK_DAMAGE) == 0) {
			return false;
		} if(target == null) {
			return false;
		} else if (!target.isAlive()) {
			return false;
		} else if (!EntityPredicates.CAN_AI_TARGET.test(target)) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return this.shouldExecute();
	}
	
	@Override
	public void startExecuting() {
		this.entity.setSpitting(true);
		this.counter = 0;
	}
	
	@Override
	public void resetTask() {
		if (!EntityPredicates.CAN_AI_TARGET.test(this.entity.getAttackTarget())) {
			this.entity.setAttackTarget(null);
		}
		this.entity.setSpitting(false);
	}
	
	@Override
	public void tick() {
		this.entity.getLookController().setLookPositionWithEntity(this.entity.getAttackTarget(), 30F, 30F);;
		++this.counter;
		if(this.counter >= this.intervalo) {
			this.counter = 0;
			this.entity.spitAnimation(false);
			this.entity.spitAttack();
		}
	}
}
