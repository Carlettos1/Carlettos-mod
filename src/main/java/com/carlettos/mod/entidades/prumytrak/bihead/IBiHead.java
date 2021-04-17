package com.carlettos.mod.entidades.prumytrak.bihead;

import com.carlettos.mod.entidades.prumytrak.bihead.controllers.BiHeadLookController;
import com.carlettos.mod.util.Util;

import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/* 
 * Las clases que implementen esta interfaz, deben sustituir el lookcontroller por
 * un BiHeadLookController.
 * 
 * variables usadas: prevProgress, progress, progressInt e isInProgress.
 * prevProgress y progress son porcentuales (van de 0 a 1).
 * progressInt es la cantidad de ticks que lleva ejecutándose (véase {@code getMaxProgress}).
 * isInProgress es un boolean para saber si debe ejecutarse o no.
 * 
 * Importante: en el baseTick debe hacerse que {@code prevProgress = progress;}
 * y registrar el DataParameter para ayudar en el EntityModel.
 */
public interface IBiHead<E extends MonsterEntity & IBiHead<E>> {
	
	/**
	 * Hecho para no sobreescribir el getLookController. 
	 * Pero funciona de la misma manera.
	 */
	BiHeadLookController<E> getLookController2();

	/**
	 * Obtiene el DataParameter.
	 */
	public Vector3f getPrevSecondHeadLookingPos();

	/**
	 * Cambia el DataParameter, generalmente lo hace el LookController.
	 */
	public void setPrevSecondHeadLookingPos(Vector3f v3f);

	/**
	 * Obtiene el DataParameter.
	 */
	public Vector3f getSecondHeadLookingPos();
	
	/**
	 * Cambia el DataParameter, generalmente lo hace el LookController.
	 */
	public void setSecondHeadLookingPos(Vector3f v3f);
	
	/**
	 * Maneja los ticks de la segunda cabeza, en caso de que se necesite.
	 */
	public void secondHeadTick();
	
	/**
	 * Lerpea el looking pos.
	 */
	public default Vector3f getSecondHeadLookingPos(float pct) {
		return pct == 1F ? getSecondHeadLookingPos() : Util.vectorLerp(pct, this.getPrevSecondHeadLookingPos(), this.getSecondHeadLookingPos());
	}
	
	/**
	 * Retorna la cantidad de ticks que debe durar la animación. 
	 * Puede tomar en cuenta efectos de poción o lo que sea necesario.
	 */
	public int getMaxGirarSegundaCabezaProgress();
	
	/**
	 * Generalmente es la forma {@code prevProgress + partialTick * (progress - prevProgress)}, 
	 * o sea, retorna el punto {@code partialTick} entre el progreso actual y el anterior.
	 */
	public float getGirarSegundaCabezaProgress(float partialTick);
	
	/**
	 * Actualiza el progressInt y el progress. 
	 * Si el progressInt es mayor o igual al maxProgress, debe regresar a 0
	 * y hacer que el isInProgress vuelva a false.
	 * 
	 * Importante: Se utiliza en el {@code livingTick();}
	 */
	public void updateGirarSegundaCabezaProgress();
	
	/**
	 * Manda un IPacket al ServerChunkProvider, desde el server, para trackear la animación, pero,
	 * solo lo hace si la animación no está iniciada, o si se puede interrumpir.
	 */	
	public void girarSegundaCabezaAnimation(boolean updateSelf);
	
	/**
	 * Retorna el DataParameter, generalmente lo usa el EntityModel.
	 */
	public boolean isGirandoSegundaCabeza();
	
	/**
	 * Cambia el DataParameter, generalmente lo hace el Goal.
	 */
	public void setGirandoSegundaCabeza(boolean girando);

	/**
	 * Obtiene el yaw en radianes para ser usado por el modelo.
	 */
	@OnlyIn(Dist.CLIENT)
	public float getSegundaCabezaYaw(float partialTick);
	
	/**
	 * Obtiene el pitch en radianes para ser usado por el modelo.
	 */
	@OnlyIn(Dist.CLIENT)
	public float getSegundaCabezaPitch(float partialTick);
}
