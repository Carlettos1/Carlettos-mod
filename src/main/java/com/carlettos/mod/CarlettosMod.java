package com.carlettos.mod;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;
import com.carlettos.mod.entidades.prumytrak.PrumTrakRender;
import com.carlettos.mod.entidades.prumytrak.particulas.PrumProyectilParticle;
import com.carlettos.mod.entidades.prumytrak.proyectil.PrumProyectilEntity;
import com.carlettos.mod.entidades.prumytrak.proyectil.PrumProyectilRenderer;
import com.carlettos.mod.listas.ListaBloques;
import com.carlettos.mod.listas.ListaEntidades;
import com.carlettos.mod.listas.ListaFeatures;
import com.carlettos.mod.listas.ListaItem;
import com.carlettos.mod.listas.ListaParticulas;
import com.carlettos.mod.util.Util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Util.MOD_ID)
public class CarlettosMod {
	public CarlettosMod() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void commonSetup(FMLCommonSetupEvent evento) {
		evento.enqueueWork(() -> {
			GlobalEntityTypeAttributes.put(ListaEntidades.prum_y_trak, PrumTrakEntity.getAtributos().create());
		});
	}
	
	public void clientSetup(FMLClientSetupEvent evento) {
		RenderingRegistry.<PrumTrakEntity>registerEntityRenderingHandler(ListaEntidades.prum_y_trak, PrumTrakRender::new);
		RenderingRegistry.<PrumProyectilEntity>registerEntityRenderingHandler(ListaEntidades.prum_proyectil, PrumProyectilRenderer::new);
	}

	@SubscribeEvent
	public static void biomasCargando(BiomeLoadingEvent ble) {
		ble.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> ListaFeatures.ore_bloque_ender_corrupto);
	}
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Util.MOD_ID, value = Dist.CLIENT)
	public static class RegistrosCliente {
		@SuppressWarnings("resource")
		@SubscribeEvent
		public static void particulas(ParticleFactoryRegisterEvent evento) {
			Minecraft.getInstance().particles.registerFactory(ListaParticulas.prum_proyectil.getType(), PrumProyectilParticle.Factory::new);
		}
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Util.MOD_ID)
	public static class Registros {

		@SubscribeEvent
		public static void items(RegistryEvent.Register<Item> event) {
			ListaItem.bola_ender_corrupta.setRegistryName(Util.getResLoc("bola_ender_corrupta"));
			ListaItem.bloque_ender_corrupto.setRegistryName(Util.getResLoc("bloque_ender_corrupto"));
			ListaItem.prum_y_trak_spawn_egg.setRegistryName(Util.getResLoc("prum_y_trak_spawn_egg"));
			ListaItem.prum_proyectil.setRegistryName(Util.getResLoc("prum_proyectil"));
			
			ListaItem.runa_aman.setRegistryName(Util.getResLoc("runa_aman"));
			ListaItem.runa_dur.setRegistryName(Util.getResLoc("runa_dur"));
			ListaItem.runa_ersa.setRegistryName(Util.getResLoc("runa_ersa"));
			ListaItem.runa_fen.setRegistryName(Util.getResLoc("runa_fen"));
			ListaItem.runa_hul.setRegistryName(Util.getResLoc("runa_hul"));
			ListaItem.runa_ior.setRegistryName(Util.getResLoc("runa_ior"));
			ListaItem.runa_kel.setRegistryName(Util.getResLoc("runa_kel"));
			ListaItem.runa_lir.setRegistryName(Util.getResLoc("runa_lir"));
			ListaItem.runa_mih.setRegistryName(Util.getResLoc("runa_mih"));
			ListaItem.runa_nak.setRegistryName(Util.getResLoc("runa_nak"));
			ListaItem.runa_oshi.setRegistryName(Util.getResLoc("runa_oshi"));
			ListaItem.runa_prum.setRegistryName(Util.getResLoc("runa_prum"));
			ListaItem.runa_rudu.setRegistryName(Util.getResLoc("runa_rudu"));
			ListaItem.runa_sila.setRegistryName(Util.getResLoc("runa_sila"));
			ListaItem.runa_trak.setRegistryName(Util.getResLoc("runa_trak"));
			ListaItem.runa_unk.setRegistryName(Util.getResLoc("runa_unk"));
			
			event.getRegistry().register(ListaItem.bola_ender_corrupta);
			event.getRegistry().register(ListaItem.bloque_ender_corrupto);
			event.getRegistry().register(ListaItem.prum_y_trak_spawn_egg);
			event.getRegistry().register(ListaItem.prum_proyectil);
			
			event.getRegistry().register(ListaItem.runa_aman);
			event.getRegistry().register(ListaItem.runa_dur);
			event.getRegistry().register(ListaItem.runa_ersa);
			event.getRegistry().register(ListaItem.runa_fen);
			event.getRegistry().register(ListaItem.runa_hul);
			event.getRegistry().register(ListaItem.runa_ior);
			event.getRegistry().register(ListaItem.runa_kel);
			event.getRegistry().register(ListaItem.runa_lir);
			event.getRegistry().register(ListaItem.runa_mih);
			event.getRegistry().register(ListaItem.runa_nak);
			event.getRegistry().register(ListaItem.runa_oshi);
			event.getRegistry().register(ListaItem.runa_prum);
			event.getRegistry().register(ListaItem.runa_rudu);
			event.getRegistry().register(ListaItem.runa_sila);
			event.getRegistry().register(ListaItem.runa_trak);
			event.getRegistry().register(ListaItem.runa_unk);
		}

		@SubscribeEvent
		public static void bloques(RegistryEvent.Register<Block> evento) {
			ListaBloques.bloque_ender_corrupto.setRegistryName(Util.getResLoc("bloque_ender_corrupto"));
			evento.getRegistry().register(ListaBloques.bloque_ender_corrupto);
		}

		@SubscribeEvent
		public static void features(RegistryEvent.Register<Feature<?>> evento) {
			//evento.getRegistry().register(ListaFeatures.ore_bloque_ender_corrupto.feature);
		}
		
		@SubscribeEvent
		public static void entidades(RegistryEvent.Register<EntityType<?>> evento) {
			ListaEntidades.prum_y_trak.setRegistryName(Util.getResLoc("prum_y_trak"));
			ListaEntidades.prum_proyectil.setRegistryName(Util.getResLoc("prum_proyectil"));
			evento.getRegistry().register(ListaEntidades.prum_y_trak);
			evento.getRegistry().register(ListaEntidades.prum_proyectil);
		}
		
		@SubscribeEvent
		public static void particulas(RegistryEvent.Register<ParticleType<?>> evento) {
			ListaParticulas.prum_proyectil.setRegistryName(Util.getResLoc("prum_proyectil"));
			evento.getRegistry().register(ListaParticulas.prum_proyectil);
		}
	}
}
