package com.carlettos.mod.entidades.prumytrak.proyectil;

import com.carlettos.mod.util.Util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PrumProyectilItem extends ArrowItem{
	public PrumProyectilItem() {
		super(new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD));
	}
	
	@Override
	public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		return new PrumProyectilEntity(worldIn, shooter);
	}
	
	public PrumProyectilEntity createArrow(World world, LivingEntity shooter, LivingEntity target, float aceleracion) {
		return new PrumProyectilEntity(world, shooter, target, aceleracion);
	}
}
