package com.carlettos.mod.entidades.bihead.controllers;

import com.carlettos.mod.entidades.bihead.IBiHead;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.vector.Vector3f;

public class BiHeadLookController<E extends MonsterEntity & IBiHead<E>> extends LookController{
	private E entity;
	private float pos2x;
	private float pos2y;
	private float pos2z;
	
	public BiHeadLookController(E mob) {
		super(mob);
		this.entity = mob;
	}
	
	public void setLookPositionCabeza2(Vector3f look) {
		this.setLookPositionCabeza2(look.getX(), look.getY(), look.getZ());
	}
	
	public void setLookPositionCabeza2(Entity entity) {
		this.setLookPositionCabeza2((float)entity.getPosX(), (float)entity.getPosYEye(), (float)entity.getPosZ());
	}
	
	public void setLookPositionCabeza2(float x, float y, float z) {
		this.pos2x = x;
		this.pos2y = y;
		this.pos2z = z;
	}
	
	@Override
	public void tick() {
		super.tick();
		Vector3f vector = new Vector3f(this.pos2x, this.pos2y, this.pos2z);
		if(!this.entity.getSecondHeadLookingPos().equals(vector)) {
			this.entity.setPrevSecondHeadLookingPos(this.entity.getSecondHeadLookingPos());
			this.entity.setGirandoSegundaCabeza(true);
			this.entity.girarSegundaCabezaAnimation(false);
		} else {
			this.entity.setGirandoSegundaCabeza(false);
		}
		this.entity.setSecondHeadLookingPos(vector);
	}
}
