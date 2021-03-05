package com.carlettos.mod.entidades.prumytrak;

import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;

public class PrumTrakLookRandomlyGoal extends LookRandomlyGoal{
	private PrumTrakEntity entity;
	private double lookX1;
	private double lookX2;
	private double lookZ1;
	private double lookZ2;
	private int idleTime;

	public PrumTrakLookRandomlyGoal(PrumTrakEntity entidad) {
		super(entidad);
		this.entity = entidad;
	}
	
	@Override
	public boolean shouldExecute() {
		return this.entity.getRNG().nextFloat() < 0.02F;
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return this.idleTime >= 0;
	}

	@Override
	public void startExecuting() {
		double d0 = (2 * Math.PI) * entity.getRNG().nextDouble();
		double d1 = (2 * Math.PI) * entity.getRNG().nextDouble();
		this.lookX1 = Math.cos(d0);
		this.lookZ1 = Math.sin(d0);
		this.lookX2 = Math.cos(d1);
		this.lookZ2 = Math.sin(d1);
		this.idleTime = 20 + this.entity.getRNG().nextInt(20);
	}

	@Override
	public void tick() {
		--this.idleTime;
		LookController lookController = entity.getLookController();
		if (lookController instanceof PrumTrakLookController) {
			PrumTrakLookController ptlc = (PrumTrakLookController) lookController;
			ptlc.setLookPosition(
					this.entity.getPosX() + this.lookX1,
					this.entity.getPosYEye(),
					this.entity.getPosZ() + this.lookZ1);
			ptlc.setLookPositionCabeza2(
					this.entity.getPosX() + this.lookX2, 
					this.entity.getPosYEye(), 
					this.entity.getPosZ() + this.lookZ2);
		} else {
			lookController.setLookPosition(
					this.entity.getPosX() + this.lookX1,
					this.entity.getPosYEye(),
					this.entity.getPosZ() + this.lookZ1);
		}
	}
}
