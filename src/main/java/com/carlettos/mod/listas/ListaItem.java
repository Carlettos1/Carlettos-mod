package com.carlettos.mod.listas;

import com.carlettos.mod.items.Runa;
import com.carlettos.mod.util.Letra;
import com.carlettos.mod.util.Util;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;

public class ListaItem {
	public static final Item BOLA_ENDER_CORRUPTA;
	public static final Item BLOQUE_ENDER_CORRUPTO;

	public static final Item DUMMY_BOI_SPAWN_EGG;
	public static final Item AMAN_SPAWN_EGG;
	public static final Item AMAN_SPIDER_SPAWN_EGG;
	
	public static final Item HUL_KUT_SPAWN_EGG;
	
	public static final Item PRUM_Y_TRAK_SPAWN_EGG;
	public static final Item TRAK_HENCHMAN_SPAWN_EGG;
	public static final Item PRUM_HENCHMAN_SPAWN_EGG;
	public static final Item PRUM_TRAK_HENCHMAN_SPAWN_EGG;
	
	public static final Item RUNA_AMAN;
	public static final Item RUNA_DUR;
	public static final Item RUNA_ERSA;
	public static final Item RUNA_FEN;
	public static final Item RUNA_HUL;
	public static final Item RUNA_IOR;
	public static final Item RUNA_KEL;
	public static final Item RUNA_LIR;
	public static final Item RUNA_MIH;
	public static final Item RUNA_NAK;
	public static final Item RUNA_OSHI;
	public static final Item RUNA_PRUM;
	public static final Item RUNA_RUDU;
	public static final Item RUNA_SILA;
	public static final Item RUNA_TRAK;
	public static final Item RUNA_UNK;
	
	static {
		BOLA_ENDER_CORRUPTA = new Item(new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD).maxStackSize(16).setNoRepair());
		BLOQUE_ENDER_CORRUPTO = new BlockItem(ListaBloques.BLOQUE_ENDER_CORRUPTO, new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD).setNoRepair());
		
		DUMMY_BOI_SPAWN_EGG = new SpawnEggItem(ListaEntidades.DUMMY_BOI, 0x804000, 0x808080, new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD));
		AMAN_SPAWN_EGG = new SpawnEggItem(ListaEntidades.AMAN, 0xffff00, 0x670000, new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD));
		AMAN_SPIDER_SPAWN_EGG = new SpawnEggItem(ListaEntidades.AMAN_SPIDER, 0x404040, 0xFF4040, new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD));
		
		HUL_KUT_SPAWN_EGG = new SpawnEggItem(ListaEntidades.HUL_KUT, 0x000000, 0xff0000, new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD));
		
		PRUM_Y_TRAK_SPAWN_EGG = new SpawnEggItem(ListaEntidades.PRUM_Y_TRAK, 0x8E3CD7, 0xA1A1A1, new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD));
		TRAK_HENCHMAN_SPAWN_EGG = new SpawnEggItem(ListaEntidades.TRAK_HENCHMAN, 0x000000, 0x0F0F0F, new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD));
		PRUM_HENCHMAN_SPAWN_EGG = new SpawnEggItem(ListaEntidades.PRUM_HENCHMAN, 0x101010, 0x0F0F0F, new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD));
		PRUM_TRAK_HENCHMAN_SPAWN_EGG = new SpawnEggItem(ListaEntidades.PRUM_TRAK_HENCHMAN, 0x202020, 0x0F0F0F, new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD));

		RUNA_AMAN = new Runa(Letra.AMAN);
		RUNA_DUR = new Runa(Letra.DUR);
		RUNA_ERSA = new Runa(Letra.ERSA);
		RUNA_FEN = new Runa(Letra.FEN);
		RUNA_HUL = new Runa(Letra.HUL);
		RUNA_IOR = new Runa(Letra.IOR);
		RUNA_KEL = new Runa(Letra.KEL);
		RUNA_LIR = new Runa(Letra.LIR);
		RUNA_MIH = new Runa(Letra.MIH);
		RUNA_NAK = new Runa(Letra.NAK);
		RUNA_OSHI = new Runa(Letra.OSHI);
		RUNA_PRUM = new Runa(Letra.PRUM);
		RUNA_RUDU = new Runa(Letra.RUDU);
		RUNA_SILA = new Runa(Letra.SILA);
		RUNA_TRAK = new Runa(Letra.TRAK);
		RUNA_UNK = new Runa(Letra.UNK);
	}
}
