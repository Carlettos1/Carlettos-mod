package com.carlettos.mod.entidades.prumytrak.proyectil;

import com.carlettos.mod.listas.ListaEntidades;
import com.carlettos.mod.listas.ListaItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
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
	public void handleStatusUpdate(byte id) {
		System.out.println(this.getPosX() + ", " + this.getPosY() + ", " + this.getPosZ());
		this.world.addParticle(ParticleTypes.END_ROD, this.getPosX(), this.getPosY(), this.getPosZ(), 0, 0, 0);
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
