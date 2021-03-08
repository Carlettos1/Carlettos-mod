package com.carlettos.mod.entidades.prumytrak.ia;

import java.util.EnumSet;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;
import com.carlettos.mod.entidades.prumytrak.ia.controllers.PrumTrakLookController;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;

public class PrumTrakLookAtGoal extends Goal{
	protected final PrumTrakEntity entity;
	protected LivingEntity closestEntity;
	protected final float maxDistance;
	protected int lookTime;
	protected final EntityPredicate predicate;
	protected final float chance;
	protected final Class<? extends LivingEntity> watchedClass;

	public PrumTrakLookAtGoal(PrumTrakEntity entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
		this.entity = entityIn;
		this.watchedClass = watchTargetClass;
		this.maxDistance = maxDistance;
		this.predicate = new EntityPredicate().setDistance(maxDistance).allowFriendlyFire().allowInvulnerable().setSkipAttackChecks();
		this.chance = 0.2F;
		this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
	}
	
	@Override
	public boolean shouldExecute() {
		if(this.entity.getRNG().nextFloat() >= this.chance) {
			return false;
		} else {
			if(this.entity.getAttackTarget() != null) {
				this.closestEntity = this.entity.getAttackTarget();
			}
			if (this.watchedClass == PlayerEntity.class) {
				this.closestEntity = this.entity.world.getClosestPlayer(this.predicate, this.entity, this.entity.getPosX(), this.entity.getPosYEye(), this.entity.getPosZ());
			} else {
				this.closestEntity = this.entity.world.func_225318_b(this.watchedClass, predicate, entity, this.entity.getPosX(), this.entity.getPosYEye(), this.entity.getPosZ(), this.entity.getBoundingBox().grow(this.maxDistance, 3D, this.maxDistance));
			}
			return this.closestEntity != null;
		}
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		if(!this.closestEntity.isAlive()) {
			return false;
		} else if(this.entity.getDistanceSq(this.closestEntity) > this.maxDistance * this.maxDistance) {
			return false;
		} else {
			return this.lookTime > 0;
		}
	}
	
	@Override
	public void startExecuting() {
		this.lookTime = 40 + this.entity.getRNG().nextInt(40);
	}
	
	@Override
	public void resetTask() {
		this.closestEntity = null;
	}
	
	@Override
	public void tick() {
		PrumTrakLookController lookController = (PrumTrakLookController) this.entity.getLookController();
		lookController.setLookPosition(this.closestEntity.getPosX(), this.closestEntity.getPosYEye(), this.closestEntity.getPosZ());
		lookController.setLookPositionCabeza2(this.closestEntity.getPosX(), this.closestEntity.getPosYEye(), this.closestEntity.getPosZ());
		--this.lookTime;
	}
}
