package com.carlettos.mod.entidades.trak;

/**
 * variables usadas: prevProgress, progress, progressInt e isInProgress.
 * prevProgress y progress son porcentuales (van de 0 a 1).
 * progressInt es la cantidad de ticks que lleva ejecutándose (véase {@code getMaxProgress}).
 * isInProgress es un boolean para saber si debe ejecutarse o no.
 * 
 * Importante: en el baseTick debe hacerse que {@code prevProgress = progress;}
 * y registrar el DataParameter para ayudar en el EntityModel.
 */
public interface ITrakAOE {
	
	/**
	 * Retorna la cantidad de ticks que debe durar la animación. 
	 * Puede tomar en cuenta efectos de poción o lo que sea necesario.
	 */
	public int getMaxAOEProgress();
	
	/**
	 * Generalmente es la forma {@code prevProgress + partialTick * (progress - prevProgress)}, 
	 * o sea, retorna el punto {@code partialTick} entre el progreso actual y el anterior.
	 */
	public float getAOEProgress(float partialTick);
	
	/**
	 * Actualiza el progressInt y el progress. 
	 * Si el progressInt es mayor o igual al maxProgress, debe regresar a 0
	 * y hacer que el isInProgress vuelva a false.
	 * 
	 * Importante: Se utiliza en el {@code livingTick();}
	 */
	public void updateAOEProgress();
	
	/**
	 * Manda un IPacket al ServerChunkProvider, desde el server, para trackear la animación, pero,
	 * solo lo hace si la animación no está iniciada, o si se puede interrumpir.
	 */	
	public void AOEAnimation(boolean updateSelf);
	
	/**
	 * Efectúa la acción, generalmente un ataque.
	 */
	public void AOEAttack(double radio);
	
	/**
	 * Retorna el DataParameter, generalmente lo usa el EntityModel.
	 */
	public boolean isAOEAgressive();
	
	/**
	 * Cambia el DataParameter, generalmente lo hace el Goal.
	 */
	public void setAOEAgressive(boolean aoed);
}
