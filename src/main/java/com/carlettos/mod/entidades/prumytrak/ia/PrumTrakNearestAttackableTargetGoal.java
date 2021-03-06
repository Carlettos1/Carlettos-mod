package com.carlettos.mod.entidades.prumytrak.ia;

import java.util.function.Predicate;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;

public class PrumTrakNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T>{

	public PrumTrakNearestAttackableTargetGoal(MobEntity goalOwnerIn, Class<T> targetClassIn, int targetChanceIn,
			boolean checkSight, boolean nearbyOnlyIn, Predicate<LivingEntity> targetPredicate) {
		super(goalOwnerIn, targetClassIn, targetChanceIn, checkSight, nearbyOnlyIn, targetPredicate);
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		if(this.nearestTarget instanceof PlayerEntity) {
			//TODO:AAA
			PlayerEntity player = (PlayerEntity) this.nearestTarget;
			if(!player.inventory.hasAny(PrumTrakEntity.RUNAS_PRUM_Y_TRAK)) {
				return false;
			}
		}
		return super.shouldContinueExecuting();
	}
}
