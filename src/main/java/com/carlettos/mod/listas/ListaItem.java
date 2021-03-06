package com.carlettos.mod.listas;

import com.carlettos.mod.Runa;
import com.carlettos.mod.entidades.prumytrak.proyectil.PrumProyectilItem;
import com.carlettos.mod.util.Letra;
import com.carlettos.mod.util.Util;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;

public class ListaItem {
	public static final Item bola_ender_corrupta;
	public static final Item bloque_ender_corrupto;
	public static final Item prum_y_trak_spawn_egg;
	public static final Item prum_proyectil;
	
	public static final Item runa_aman;
	public static final Item runa_dur;
	public static final Item runa_ersa;
	public static final Item runa_fen;
	public static final Item runa_hul;
	public static final Item runa_ior;
	public static final Item runa_kel;
	public static final Item runa_lir;
	public static final Item runa_mih;
	public static final Item runa_nak;
	public static final Item runa_oshi;
	public static final Item runa_prum;
	public static final Item runa_rudu;
	public static final Item runa_sila;
	public static final Item runa_trak;
	public static final Item runa_unk;
	
	static {
		bola_ender_corrupta = new Item(new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD).maxStackSize(16).setNoRepair());
		bloque_ender_corrupto = new BlockItem(ListaBloques.bloque_ender_corrupto, new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD).setNoRepair());
		prum_y_trak_spawn_egg = new SpawnEggItem(ListaEntidades.prum_y_trak, 0x8E3CD7, 0xA1A1A1, new Item.Properties().group(Util.GRUPO_CARLETTOS_MOD));
		prum_proyectil = new PrumProyectilItem();
		
		runa_aman = new Runa(Letra.AMAN);
		runa_dur = new Runa(Letra.DUR);
		runa_ersa = new Runa(Letra.ERSA);
		runa_fen = new Runa(Letra.FEN);
		runa_hul = new Runa(Letra.HUL);
		runa_ior = new Runa(Letra.IOR);
		runa_kel = new Runa(Letra.KEL);
		runa_lir = new Runa(Letra.LIR);
		runa_mih = new Runa(Letra.MIH);
		runa_nak = new Runa(Letra.NAK);
		runa_oshi = new Runa(Letra.OSHI);
		runa_prum = new Runa(Letra.PRUM);
		runa_rudu = new Runa(Letra.RUDU);
		runa_sila = new Runa(Letra.SILA);
		runa_trak = new Runa(Letra.TRAK);
		runa_unk = new Runa(Letra.UNK);
	}
}
