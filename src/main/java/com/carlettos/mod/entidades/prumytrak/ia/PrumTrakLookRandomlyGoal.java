package com.carlettos.mod.entidades.prumytrak.ia;

import java.util.EnumSet;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;
import com.carlettos.mod.entidades.prumytrak.ia.controllers.PrumTrakLookController;

import net.minecraft.entity.ai.goal.Goal;

public class PrumTrakLookRandomlyGoal extends Goal {
	private PrumTrakEntity entity;
	private double lookX1;
	private double lookX2;
	private double lookZ1;
	private double lookZ2;
	private int idleTime;

	public PrumTrakLookRandomlyGoal(PrumTrakEntity entidad) {
		this.entity = entidad;
		this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
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
		PrumTrakLookController lookController = (PrumTrakLookController) entity.getLookController();
		lookController.setLookPosition(this.entity.getPosX() + this.lookX1, this.entity.getPosYEye(),
				this.entity.getPosZ() + this.lookZ1);
		lookController.setLookPositionCabeza2(this.entity.getPosX() + this.lookX2, this.entity.getPosYEye(),
				this.entity.getPosZ() + this.lookZ2);
	}
}
