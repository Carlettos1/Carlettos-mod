package com.carlettos.mod.entidades.prumytrak;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;

public class PrumTrakLookAtGoal extends LookAtGoal{
	protected final PrumTrakEntity entity;
	protected int lookTime;
	protected final EntityPredicate predicate;

	public PrumTrakLookAtGoal(PrumTrakEntity entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
		super(entityIn, watchTargetClass, maxDistance);
		this.entity = entityIn;
		this.predicate = new EntityPredicate().setDistance(maxDistance).allowFriendlyFire().allowInvulnerable().setSkipAttackChecks();
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
		this.entity.getLookController().setLookPosition(this.closestEntity.getPosX(), this.closestEntity.getPosYEye(), this.closestEntity.getPosZ());
		((PrumTrakLookController)this.entity.getLookController()).setLookPositionCabeza2(this.closestEntity.getPosX(), this.closestEntity.getPosYEye(), this.closestEntity.getPosZ());
		--this.lookTime;
	}
}
