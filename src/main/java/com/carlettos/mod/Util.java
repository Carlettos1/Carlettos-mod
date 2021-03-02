package com.carlettos.mod;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Util {
	public static final String MOD_ID = "carlettosmod";
	public static final ItemGroup GRUPO_CARLETTOS_MOD = new ItemGroup(-1, "carlettosmodtab") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ListaItem.bola_ender_corrupta);
		}
	};
	
	public static ResourceLocation getResLoc(String nombre) {
		return new ResourceLocation(MOD_ID, nombre);
	}
}
