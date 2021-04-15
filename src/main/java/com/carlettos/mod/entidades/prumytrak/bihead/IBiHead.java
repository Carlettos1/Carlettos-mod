package com.carlettos.mod.entidades.prumytrak.bihead;

import com.carlettos.mod.entidades.prumytrak.bihead.controllers.BiHeadLookController;
import com.carlettos.mod.util.Util;

import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.vector.Vector3f;

/* 
 * Las clases que implementen esta interfaz, deben sustituir el lookcontroller por
 * un BiHeadLookController.
 */
public interface IBiHead<E extends MonsterEntity & IBiHead<E>> {
	BiHeadLookController<E> getLookController2();
	
	public Vector3f getPrevSecondHeadLookingPos();
	
	public void setPrevSecondHeadLookingPos(Vector3f v3f);
	
	public Vector3f getSecondHeadLookingPos();
	
	public void setSecondHeadLookingPos(Vector3f v3f);
	
	public default Vector3f getSecondHeadLookingPos(float partialTick) {
		return partialTick == 1F ? getSecondHeadLookingPos() : Util.vectorLerp(partialTick, this.getPrevSecondHeadLookingPos(), this.getSecondHeadLookingPos());
	}
}
