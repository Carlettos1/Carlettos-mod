package com.carlettos.mod.util;

public interface IAOEMob {
	public float getAOEProgress(float partialTick);
	public void updateAOEProgress();
	public void AOEAnimation(boolean updateSelf);
	public void AOEAttack(double radio);
	public boolean isAOEAgressive();
	public void setAOEAgressive(boolean aoed);
}
