package com.carlettos.mod;

import java.util.List;
import java.util.Set;

import com.carlettos.mod.entidades.prumytrak.proyectil.PrumProyectilEntity;
import com.carlettos.mod.entidades.prumytrak.proyectil.PrumProyectilItem;
import com.carlettos.mod.listas.ListaItem;
import com.carlettos.mod.util.Letra;
import com.carlettos.mod.util.Util;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
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
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(this.letra.equals(Letra.PRUM)) {
			if(!worldIn.isRemote){
				PrumProyectilEntity proyectil = (PrumProyectilEntity) ((PrumProyectilItem)ListaItem.prum_proyectil).createArrow(worldIn, new ItemStack(ListaItem.prum_proyectil), playerIn);
				proyectil.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0, 3, 1);
				
				proyectil.setDamage(proyectil.getDamage() + 2D);
				if(playerIn.inventory.hasAny(Set.of(ListaItem.runa_rudu))) {
					proyectil.setDamage(proyectil.getDamage() + 2D);
				}
				if(playerIn.inventory.hasAny(Set.of(ListaItem.runa_unk))) {
					proyectil.setDamage(proyectil.getDamage() + 2D);
				}
				if(playerIn.inventory.hasAny(Set.of(ListaItem.runa_mih))) {
					proyectil.setDamage(proyectil.getDamage() + 2D);
				}
				proyectil.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
				worldIn.addEntity(proyectil);
			}
			worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1F, 1F);
			playerIn.addStat(Stats.ITEM_USED.get(this));
			return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
		}
		return ActionResult.resultPass(playerIn.getHeldItem(handIn));
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
