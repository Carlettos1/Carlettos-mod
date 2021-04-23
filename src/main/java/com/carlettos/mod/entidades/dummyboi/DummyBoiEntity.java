package com.carlettos.mod.entidades.dummyboi;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.HandSide;
import net.minecraft.world.World;

public class DummyBoiEntity extends LivingEntity{
	public static final Predicate<LivingEntity> PREDICATE = (entidad) -> {return entidad.isAlive();};
	
	public DummyBoiEntity(EntityType<? extends LivingEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return true;
	}
	
	@Override
	protected void damageEntity(DamageSource damageSrc, float damageAmount) {
	}
	
	@Override
	public void onKillCommand() {
		this.remove();
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return List.of(ItemStack.EMPTY);
	}

	@Override
	public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {
	}

	@Override
	public HandSide getPrimaryHand() {
		return HandSide.RIGHT;
	}
}
