package com.carlettos.mod;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Util.MOD_ID)
public class CarlettosMod {
	public CarlettosMod() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, modid = Util.MOD_ID)
	public static class Registros{
		
		@SubscribeEvent
		public static void items(RegistryEvent.Register<Item> event) {
			ListaItem.bola_ender_corrupta.setRegistryName(Util.getResLoc("bola_ender_corrupta"));
			ListaItem.bloque_ender_corrupto.setRegistryName(Util.getResLoc("bloque_ender_corrupto"));
			event.getRegistry().register(ListaItem.bola_ender_corrupta);
			event.getRegistry().register(ListaItem.bloque_ender_corrupto);
		}
		
		@SubscribeEvent
		public static void bloques(RegistryEvent.Register<Block> evento) {
			ListaBloques.bloque_ender_corrupto.setRegistryName(Util.getResLoc("bloque_ender_corrupto"));
			evento.getRegistry().register(ListaBloques.bloque_ender_corrupto);
		}
	}
}
