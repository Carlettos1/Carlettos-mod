package com.carlettos.mod.entidades.amanspit;

import com.carlettos.mod.listas.ListaEntidades;
import com.carlettos.mod.util.SCSpawnObjectPacket;
import com.carlettos.mod.util.Util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class AmanSpitEntity extends AbstractArrowEntity{

	public AmanSpitEntity(EntityType<AmanSpitEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public AmanSpitEntity(World worldIn) {
		super(ListaEntidades.AMAN_SPIT, worldIn);
	}
	
	public AmanSpitEntity(World worldIn, double x, double y, double z) {
		super(ListaEntidades.AMAN_SPIT, x, y, z, worldIn);
	}
	
	@Override
	protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
		super.func_230299_a_(p_230299_1_);
		this.remove();
	}
	
	@Override
	protected void func_225516_i_() {
		super.func_225516_i_();
		Util.LOG.info("func_225516_i_ no debería usarse");
	}
	
	@Override
	protected ItemStack getArrowStack() {
		return ItemStack.EMPTY;
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		Entity entity = this.func_234616_v_();
		return new SCSpawnObjectPacket(this, entity == null ? 0 : entity.getEntityId());
	}
	
	@Override
	public void tick() {
		super.tick();
	}
}
