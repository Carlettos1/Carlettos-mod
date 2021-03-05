package com.carlettos.mod;

import java.util.List;

import com.carlettos.mod.util.Letra;
import com.carlettos.mod.util.Util;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class Runa extends Item{
	private final Letra letra;
	public Runa(Letra letra) {
		super(new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD).setNoRepair().isImmuneToFire().rarity(Rarity.UNCOMMON).maxStackSize(64));
		this.letra = letra;
	}
	
	public Letra getLetra() {
		return letra;
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("item.carlettosmod.runa." + letra.toString().toLowerCase()).mergeStyle(TextFormatting.GOLD));
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return 0;
	}
}
