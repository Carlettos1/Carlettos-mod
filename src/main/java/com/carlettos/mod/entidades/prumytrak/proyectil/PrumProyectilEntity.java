package com.carlettos.mod.entidades.prumytrak.proyectil;

import com.carlettos.mod.listas.ListaEntidades;
import com.carlettos.mod.listas.ListaItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PrumProyectilEntity extends AbstractArrowEntity{

	public PrumProyectilEntity(EntityType<? extends AbstractArrowEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public PrumProyectilEntity(World worldIn, double x, double y, double z) {
		 super(ListaEntidades.prum_proyectil, x, y, z, worldIn);
	}
	
	public PrumProyectilEntity(World worldIn, LivingEntity shooter) {
		super(ListaEntidades.prum_proyectil, shooter, worldIn);
	}
	
	@Override
	protected void func_225516_i_() {
		this.remove();
	}
	
	
	@Override
	public void tick() {
		if(this.world.isRemote) {
			for (int i = 0; i < 3; i++) {
	            float f1 = this.rand.nextFloat() * ((float)Math.PI * 2F);
	            float f2 = MathHelper.sqrt(this.rand.nextFloat()) * 0.2F;
	            float f3 = MathHelper.cos(f1) * f2;
	            float f4 = MathHelper.sin(f1) * f2;
				this.world.addParticle(ParticleTypes.PORTAL, this.getPosX() + f3, this.getPosY(), this.getPosZ() + f4, 0, 0, 0);
			}
		}
		super.tick();
	}

	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(ListaItem.prum_proyectil);
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		Entity entity = this.func_234616_v_();
		return new PrumProyectilPacketHandler(this, entity == null ? 0 : entity.getEntityId());
	}
}
