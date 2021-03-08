package com.carlettos.mod.particulas;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;

public class ParticulaGenericaInmovil extends SpriteTexturedParticle {

	protected ParticulaGenericaInmovil(ClientWorld world, double x, double y, double z) {
		super(world, x, y, z, 0D, 0D, 0D);
		this.motionX *= 0.01F;
		this.motionY *= 0.01F;
		this.motionZ *= 0.01F;
		this.motionY *= 0.1D;
		this.particleScale *= 1.5F;
		this.maxAge = 16;
		this.canCollide = false;
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public float getScale(float scaleFactor) {
		return this.particleScale * MathHelper.clamp((this.age + scaleFactor) / this.maxAge * 32F, 0F, 1F);
	}

	@Override
	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.age++ >= this.maxAge) {
			this.setExpired();
		} else {
			this.move(this.motionX, this.motionY, this.motionZ);
			if (this.posY == this.prevPosY) {
				this.motionX *= 1.1D;
				this.motionZ *= 1.1D;
			}

			this.motionX *= (double) 0.86F;
			this.motionY *= (double) 0.86F;
			this.motionZ *= (double) 0.86F;
			if (this.onGround) {
				this.motionX *= (double) 0.7F;
				this.motionZ *= (double) 0.7F;
			}
		}
	}
	
	public static class Factory implements IParticleFactory<BasicParticleType>{
	    private final IAnimatedSprite sprite;

		public Factory(IAnimatedSprite sprite) {
			this.sprite = sprite;
		}
		
		@Override
		public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z,
				double xSpeed, double ySpeed, double zSpeed) {
			ParticulaGenericaInmovil particle = new ParticulaGenericaInmovil(worldIn, x, y, z);
			particle.selectSpriteRandomly(this.sprite);
			return particle;
		}
	}
}
