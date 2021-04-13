package com.carlettos.mod.entidades.interfaces;

/**
 * variables usadas: prevProgress, progress, progressInt e isInProgress.
 * prevProgress y progress son porcentuales (van de 0 a 1).
 * progressInt es la cantidad de ticks que lleva ejecutándose (véase {@code getMaxProgress}).
 * isInProgress es un boolean para saber si debe ejecutarse o no.
 * 
 * Importante: en el baseTick debe hacerse que {@code prevProgress = progress;}
 * y registrar el DataParameter para ayudar en el EntityModel.
 */
public interface IAmanEggHatch {
	
	/**
	 * Retorna la cantidad de ticks que debe durar la animación. 
	 * Puede tomar en cuenta efectos de poción o lo que sea necesario.
	 */
	public int getMaxHatchProgress();
	
	/**
	 * Generalmente es la forma {@code prevProgress + partialTick * (progress - prevProgress)}, 
	 * o sea, retorna el punto {@code partialTick} entre el progreso actual y el anterior.
	 */
	public float getHatchProgress(float partialTick);
	
	/**
	 * Actualiza el progressInt y el progress. 
	 * Si el progressInt es mayor o igual al maxProgress, debe regresar a 0
	 * y hacer que el isInProgress vuelva a false.
	 * 
	 * Importante: Se utiliza en el {@code livingTick();}
	 */
	public void updateHatchProgress();

	/**
	 * Manda un IPacket al ServerChunkProvider, desde el server, para trackear la animación, pero,
	 * solo lo hace si la animación no está iniciada, o si se puede interrumpir.
	 */		
	public void hatchAnimation(boolean updateSelf);
	
	/**
	 * Efectúa la acción, generalmente un ataque.
	 */
	public void hatch(int amount);
	
	/**
	 * Retorna el DataParameter, generalmente lo usa el EntityModel.
	 */
	public boolean isHatching();
	
	/**
	 * Cambia el DataParameter, generalmente lo hace el Goal.
	 */
	public void setHatching(boolean hatching);
}
