package com.carlettos.mod.entidades.prumytrak.prum;

import net.minecraft.entity.LivingEntity;

/**
 * Rregistrar el DataParameter para ayudar en el EntityModel.
 */
public interface IPrumRandomRangedAttack {
	
	/**
	 * Efectúa la acción, generalmente un ataque.
	 */
	public void randomRangedAttack(LivingEntity target);
	
	/**
	 * Retorna el DataParameter, generalmente lo usa el EntityModel.
	 */
	public boolean isRandomRangedAgressive();
	
	/**
	 * Cambia el DataParameter, generalmente lo hace el Goal.
	 */
	public void setRandomRangedAgressive(boolean ranged);
}
