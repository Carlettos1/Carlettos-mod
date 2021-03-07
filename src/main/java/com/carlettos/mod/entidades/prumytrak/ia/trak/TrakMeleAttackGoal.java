package com.carlettos.mod.entidades.prumytrak.ia.trak;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class TrakMeleAttackGoal extends MeleeAttackGoal{

	public TrakMeleAttackGoal(PrumTrakEntity creature, double speedIn, boolean useLongMemory) {
		super(creature, speedIn, useLongMemory);
	}
}
