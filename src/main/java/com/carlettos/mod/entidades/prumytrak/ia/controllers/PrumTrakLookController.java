package com.carlettos.mod.entidades.prumytrak.ia.controllers;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class PrumTrakLookController extends LookController {
	private PrumTrakEntity entity;
	private double posXCabeza2;
	private double posYCabeza2;
	private double posZCabeza2;

	public PrumTrakLookController(PrumTrakEntity entidad) {
		super(entidad);
		this.entity = entidad;
	}

	@Override
	public void setLookPosition(Vector3d lookVector) {
		this.setLookPosition(lookVector.x, lookVector.y, lookVector.z);
	}

	public void setLookPositionCabeza2(Vector3d lookVector) {
		this.setLookPositionCabeza2(lookVector.x, lookVector.y, lookVector.z);
	}

	@Override
	public void setLookPositionWithEntity(Entity entityIn, float deltaYaw, float deltaPitch) {
		this.setLookPosition(entityIn.getPosX(), getEyePosition(entityIn), entity.getPosZ(), deltaYaw, deltaPitch);
	}

	public void setLookPositionWithEntityCabeza2(Entity entity) {
		this.setLookPositionCabeza2(entity.getPosX(), getEyePosition(entity), entity.getPosZ());
	}

	@Override
	public void setLookPosition(double x, double y, double z) {
		this.setLookPosition(x, y, z, this.entity.getFaceRotSpeed(), this.entity.getVerticalFaceSpeed());
	}

	@Override
	public void setLookPosition(double x, double y, double z, float deltaYaw, float deltaPitch) {
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.deltaLookYaw = deltaYaw;
		this.deltaLookPitch = deltaPitch;
		this.isLooking = true;
	}

	public void setLookPositionCabeza2(double x, double y, double z) {
		this.posXCabeza2 = x;
		this.posYCabeza2 = y;
		this.posZCabeza2 = z;
		this.isLooking = true;
		// System.out.println(entity.getPosX() + ", " + entity.getPosYEye() + ", " +
		// entity.getPosZ());
		// System.out.println(x + ", " + y + ", " + z);
	}

	@Override
	public void tick() {
		if (this.shouldResetPitch()) {
			this.entity.rotationPitch = 0;
			this.entity.setRotationPitchCabeza2(0);
		}

		if (this.isLooking) {
			this.isLooking = false;

			this.entity.rotationYawHead = this.clampedRotate(this.entity.rotationYawHead, this.getTargetYaw(),
					this.deltaLookYaw);
			this.entity.setRotationYawHead(this.clampedRotate(this.entity.getRotationYawHead(),
					this.getTargetYawCabeza2(), this.deltaLookYaw));

			this.entity.rotationPitch = this.clampedRotate(this.entity.rotationPitch, this.getTargetPitch(),
					this.deltaLookPitch);
			this.entity.setRotationPitchCabeza2(this.clampedRotate(this.entity.getRotationPitchCabeza2(),
					this.getTargetPitchCabeza2(), this.deltaLookPitch));
		} else {
			this.entity.rotationYawHead = this.clampedRotate(this.entity.rotationYawHead, this.entity.renderYawOffset,
					10F);
			this.entity.setRotationYawHead(
					this.clampedRotate(this.entity.getRotationYawHead(), this.entity.getRenderYawOffsetCabeza2(), 10F));
		}

		if (!this.entity.getNavigator().noPath()) {
			this.entity.rotationYawHead = MathHelper.func_219800_b(this.entity.rotationYawHead,
					this.entity.renderYawOffset, this.entity.getHorizontalFaceSpeed());
			this.entity.setRotationYawHead(MathHelper.func_219800_b(this.entity.getRotationYawHead(),
					this.entity.getRenderYawOffsetCabeza2(), this.entity.getHorizontalFaceSpeed()));
		}
	}

	public Vector3d getLookingCabeza2() {
		return new Vector3d(this.posXCabeza2, this.posYCabeza2, this.posZCabeza2);
	}

	public Vector3d getLookingCabeza1() {
		return new Vector3d(this.posX, this.posY, this.posZ);
	}

	protected float getTargetPitchCabeza2() {
		double d0 = this.posXCabeza2 - this.entity.getPosX();
		double d1 = this.posYCabeza2 - this.entity.getPosYEye();
		double d2 = this.posZCabeza2 - this.entity.getPosZ();
		double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
		return (float) (-(MathHelper.atan2(d1, d3) * (double) (180F / (float) Math.PI)));
	}

	protected float getTargetYawCabeza2() {
		double d0 = this.posXCabeza2 - this.entity.getPosX();
		double d1 = this.posZCabeza2 - this.entity.getPosZ();
		return (float) (MathHelper.atan2(d1, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
	}

	protected static double getEyePosition(Entity entity) {
		return entity instanceof LivingEntity ? entity.getPosYEye()
				: (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2d;
	}
}
