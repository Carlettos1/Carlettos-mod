package com.carlettos.mod;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ListaItem {
	public static final Item bola_ender_corrupta;
	public static final Item bloque_ender_corrupto;
	
	static {
		bola_ender_corrupta = new Item(new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD).maxStackSize(16).setNoRepair());
		bloque_ender_corrupto = new BlockItem(ListaBloques.bloque_ender_corrupto, new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD).setNoRepair());
	}
}
