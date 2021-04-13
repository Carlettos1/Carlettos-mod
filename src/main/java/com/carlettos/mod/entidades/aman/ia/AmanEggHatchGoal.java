package com.carlettos.mod.entidades.aman.ia;

import java.util.EnumSet;

import com.carlettos.mod.entidades.aman.AmanEntity;
import com.carlettos.mod.entidades.interfaces.IAmanEggHatch;
import com.carlettos.mod.listas.ListaAtributos;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.EntityPredicates;

public class AmanEggHatchGoal<E extends MonsterEntity & IAmanEggHatch> extends Goal {
	private final E entity;
	private final int intervalo;
	private int counter;
	
	public AmanEggHatchGoal(E entity, int intervalo) {
		this.entity = entity;
		this.intervalo = intervalo;
		this.setMutexFlags(EnumSet.noneOf(Goal.Flag.class));
	}
	
	@Override
	public boolean shouldExecute() {
		LivingEntity target = entity.getAttackTarget();
		if(entity.getAttribute(ListaAtributos.AMAN_EGG_COUNT).getValue() == 0) {
			return false;
		} else if (target == null) {
			return false;
		} else if (!target.isAlive()){
			return false;
		} else if (!EntityPredicates.CAN_AI_TARGET.test(target)) {
			return false;
		} else if(entity.getHealth() < entity.getMaxHealth()) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return this.shouldExecute();
	}
	
	@Override
	public void startExecuting() {
		this.entity.setHatching(true);
		this.counter = 0;
	}
	
	@Override
	public void resetTask() {
		if(!EntityPredicates.CAN_AI_TARGET.test(this.entity.getAttackTarget())) {
			this.entity.setAttackTarget(null);
		}
		this.entity.setHatching(false);
	}
	
	@Override
	public void tick() {
		++this.counter;
		if(this.counter >= this.intervalo) {
			this.counter = 0;
			this.entity.hatchAnimation(false);
			if(entity instanceof AmanEntity) {
				//TODO: fases de la Aman
			} else {
				this.entity.hatch(1);
				this.entity.getAttribute(ListaAtributos.AMAN_EGG_COUNT).applyPersistentModifier(new AttributeModifier("Egg Hatched", -1, Operation.ADDITION));
			}
		}
	}
}
