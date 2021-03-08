package com.carlettos.mod.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carlettos.mod.listas.ListaItem;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Util {
	public static final Logger LOG = LogManager.getLogger();
	public static final String MOD_ID = "carlettosmod";
	public static final ItemGroup GRUPO_CARLETTOS_MOD = new ItemGroup(-1, "carlettosmodtab") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ListaItem.BOLA_ENDER_CORRUPTA);
		}
	};
	
	public static ResourceLocation getResLoc(String nombre) {
		return new ResourceLocation(MOD_ID, nombre);
	}
}
