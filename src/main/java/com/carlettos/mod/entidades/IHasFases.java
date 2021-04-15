package com.carlettos.mod.entidades;

public interface IHasFases {
	/* 
	 * Actualiza y activa los efectos de cambiar de fase. Debe usarse en el método tick();
	 */
	void actualizarFase();
	
	byte getFase();
	
	void setFase(byte fase);
}
