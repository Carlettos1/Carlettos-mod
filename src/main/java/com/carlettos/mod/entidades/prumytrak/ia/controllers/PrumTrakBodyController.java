package com.carlettos.mod.entidades.prumytrak.ia.controllers;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;

import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.util.math.MathHelper;

public class PrumTrakBodyController extends BodyController {
	private PrumTrakEntity entity;
	private int rotationTickCounter;
	private float prevRenderYawHead;
	private float prevRenderYawHead_cabeza2;

	public PrumTrakBodyController(PrumTrakEntity mob) {
		super(mob);
		this.entity = mob;
	}

	@Override
	public void updateRenderAngles() {
		if (this.hasMovidoUnPoco()) {
			this.entity.renderYawOffset = this.entity.rotationYaw;
			this.entity.setRenderYawOffSetCabeza2(this.entity.rotationYaw);
			
			this.cambiarRotationYawHead();
			
			this.prevRenderYawHead = this.entity.rotationYawHead;
			this.prevRenderYawHead_cabeza2 = this.entity.getRotationYawHead();
			this.rotationTickCounter = 0;
		} else {
			if(Math.abs(this.entity.rotationYawHead - this.prevRenderYawHead) > 15
					|| Math.abs(this.entity.getRotationYawHead() - this.prevRenderYawHead_cabeza2) > 15) {
				this.rotationTickCounter = 0;
				this.prevRenderYawHead = this.entity.rotationYawHead;
				this.prevRenderYawHead_cabeza2 = this.entity.getRotationYawHead();
				this.cambiarRenderYawOffset();
			} else {
				++this.rotationTickCounter;
				if(this.rotationTickCounter > 10) {
					this.nadaRaro();
				}
			}
		}
	}

	private void cambiarRenderYawOffset() {
		this.entity.renderYawOffset = MathHelper.func_219800_b(
				this.entity.renderYawOffset, 
				this.entity.rotationYawHead, 
				(float) this.entity.getHorizontalFaceSpeed());
		
		this.entity.setRenderYawOffSetCabeza2(MathHelper.func_219800_b(
				this.entity.getRenderYawOffsetCabeza2(),
				this.entity.getRotationYawHead(), 
				(float) this.entity.getHorizontalFaceSpeed()));
	}

	private void cambiarRotationYawHead() {
		this.entity.rotationYawHead = MathHelper.func_219800_b(
				this.entity.rotationYawHead,
				this.entity.renderYawOffset,
				(float)this.entity.getHorizontalFaceSpeed());
		
		this.entity.setRotationYawHead(MathHelper.func_219800_b(
				this.entity.getRotationYawHead(),
				this.entity.getRenderYawOffsetCabeza2(), 
				(float) this.entity.getHorizontalFaceSpeed()));
	}

	private void nadaRaro() {
		int i = this.rotationTickCounter - 10;
		float f = MathHelper.clamp((float) i / 10.0F, 0.0F, 1.0F);
		float f1 = (float) this.entity.getHorizontalFaceSpeed() * (1.0F - f);
		this.entity.renderYawOffset = MathHelper.func_219800_b(
				this.entity.renderYawOffset, 
				this.entity.rotationYawHead, 
				f1);
		this.entity.setRenderYawOffSetCabeza2(MathHelper.func_219800_b(
				this.entity.getRenderYawOffsetCabeza2(),
				this.entity.getRotationYawHead(), 
				f1));
	}

	private boolean hasMovidoUnPoco() {
		double d0 = this.entity.getPosX() - this.entity.prevPosX;
		double d1 = this.entity.getPosZ() - this.entity.prevPosZ;
		return d0 * d0 + d1 * d1 > (double) 2.5000003E-7F;
	}
}
