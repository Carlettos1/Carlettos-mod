package com.carlettos.mod.entidades.prumytrak.bihead.ia;

import com.carlettos.mod.entidades.prumytrak.bihead.IBiHead;

import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.MathHelper;

public class LookRandomlyGoal<E extends MonsterEntity & IBiHead<E>> extends net.minecraft.entity.ai.goal.LookRandomlyGoal{
	private final E entity;
	private float lookX;
	private float lookZ;

	public LookRandomlyGoal(E entity) {
		super(entity);
		this.entity = entity;
	}
	
	@Override
	public void startExecuting() {
		super.startExecuting();
		float d0 = (float)Math.PI * 2F * this.entity.getRNG().nextFloat();
		this.lookX = MathHelper.cos(d0);
		this.lookZ = MathHelper.sin(d0);
	}
	
	@Override
	public void tick() {
		super.tick();
		this.entity.getLookController2().setLookPositionCabeza2(lookX + (float)this.entity.getPosX(), (float)this.entity.getPosYEye(), lookZ + (float)this.entity.getPosZ());
	}
}
