package com.carlettos.mod.entidades.prumytrak.ia;

import java.util.EnumSet;
import java.util.function.Predicate;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class PrumTrakNearestAttackableTargetGoal<T extends LivingEntity> extends TargetGoal{
	private final PrumTrakEntity entity;
	private final Class<T> targetClass;
	private LivingEntity target;
	private EntityPredicate predicate;
	
	public PrumTrakNearestAttackableTargetGoal(PrumTrakEntity goalOwnerIn, Class<T> targetClassIn,
			boolean checkSight, boolean nearbyOnlyIn, Predicate<LivingEntity> targetPredicate) {
		super(goalOwnerIn, checkSight, nearbyOnlyIn);
		this.entity = goalOwnerIn;
		this.targetClass = targetClassIn;
		this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
		this.predicate = new EntityPredicate().setDistance(this.getTargetDistance()).setCustomPredicate((entidad) -> {
			if(this.entity.getFase() != 1) {
				return true;
			}
			return targetPredicate.test(entidad);
		});
	}
	
	@Override
	public boolean shouldExecute() {
		this.findNearestTarget();
		return this.target != null;
	}
	
	protected AxisAlignedBB getVolume(double radio) {
		return this.entity.getBoundingBox().grow(radio);
	}
	
	protected void findNearestTarget() {
		if(this.targetClass != PlayerEntity.class && this.targetClass != ServerPlayerEntity.class) {
			this.target = this.entity.world.func_225318_b(this.targetClass, this.predicate, this.entity, this.entity.getPosX(), this.entity.getPosYEye(), this.entity.getPosZ(), this.getVolume(this.getTargetDistance()));
		} else {
			this.target = this.entity.world.getClosestPlayer(this.predicate, this.entity, this.entity.getPosX(), this.entity.getPosYEye(), this.entity.getPosZ());
		}
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		if(!this.predicate.canTarget(this.entity, this.target)){
			return false;
		}
		return super.shouldContinueExecuting();
	}
	
	@Override
	public void startExecuting() {
		this.entity.setAttackTarget(this.target);
		super.startExecuting();
	}
}
