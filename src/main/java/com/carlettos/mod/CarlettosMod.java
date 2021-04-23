package com.carlettos.mod;

import com.carlettos.mod.entidades.aman.aman.AmanEntity;
import com.carlettos.mod.entidades.aman.aman.AmanRenderer;
import com.carlettos.mod.entidades.aman.amanspider.AmanSpiderEntity;
import com.carlettos.mod.entidades.aman.amanspider.AmanSpiderRenderer;
import com.carlettos.mod.entidades.aman.amanspit.AmanSpitEntity;
import com.carlettos.mod.entidades.aman.amanspit.AmanSpitRenderer;
import com.carlettos.mod.entidades.dummyboi.DummyBoiEntity;
import com.carlettos.mod.entidades.dummyboi.DummyBoiRenderer;
import com.carlettos.mod.entidades.prumytrak.prum.prumhenchman.PrumHenchmanEntity;
import com.carlettos.mod.entidades.prumytrak.prum.prumhenchman.PrumHenchmanRenderer;
import com.carlettos.mod.entidades.prumytrak.prum.prumproyectil.PrumProyectilEntity;
import com.carlettos.mod.entidades.prumytrak.prum.prumproyectil.PrumProyectilRenderer;
import com.carlettos.mod.entidades.prumytrak.prumtrak.PrumTrakEntity;
import com.carlettos.mod.entidades.prumytrak.prumtrak.PrumTrakRenderer;
import com.carlettos.mod.entidades.prumytrak.prumtrakhenchman.PrumTrakHenchmanEntity;
import com.carlettos.mod.entidades.prumytrak.prumtrakhenchman.PrumTrakHenchmanRenderer;
import com.carlettos.mod.entidades.prumytrak.trak.trakhenchman.TrakHenchmanEntity;
import com.carlettos.mod.entidades.prumytrak.trak.trakhenchman.TrakHenchmanRenderer;
import com.carlettos.mod.listas.ListaAtributos;
import com.carlettos.mod.listas.ListaBloques;
import com.carlettos.mod.listas.ListaEntidades;
import com.carlettos.mod.listas.ListaFeatures;
import com.carlettos.mod.listas.ListaItem;
import com.carlettos.mod.listas.ListaParticulas;
import com.carlettos.mod.particulas.ParticulaGenericaInmovil;
import com.carlettos.mod.util.Util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraft.world.gen.GenerationStage.Decoration;
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
			GlobalEntityTypeAttributes.put(ListaEntidades.DUMMY_BOI, LivingEntity.registerAttributes().create());
			GlobalEntityTypeAttributes.put(ListaEntidades.AMAN, AmanEntity.getAtributos().create());
			GlobalEntityTypeAttributes.put(ListaEntidades.AMAN_SPIDER, AmanSpiderEntity.getAtributos().create());
			GlobalEntityTypeAttributes.put(ListaEntidades.PRUM_Y_TRAK, PrumTrakEntity.getAtributos().create());
			GlobalEntityTypeAttributes.put(ListaEntidades.TRAK_HENCHMAN, TrakHenchmanEntity.getAtributos().create());
			GlobalEntityTypeAttributes.put(ListaEntidades.PRUM_HENCHMAN, PrumHenchmanEntity.getAtributos().create());
			GlobalEntityTypeAttributes.put(ListaEntidades.PRUM_TRAK_HENCHMAN, PrumTrakHenchmanEntity.getAtributos().create());
		});
	}
	
	public void clientSetup(FMLClientSetupEvent evento) {
		RenderingRegistry.<DummyBoiEntity>registerEntityRenderingHandler(ListaEntidades.DUMMY_BOI, DummyBoiRenderer::new);
		
		RenderingRegistry.<AmanEntity>registerEntityRenderingHandler(ListaEntidades.AMAN, AmanRenderer::new);
		RenderingRegistry.<AmanSpiderEntity>registerEntityRenderingHandler(ListaEntidades.AMAN_SPIDER, AmanSpiderRenderer::new);
		RenderingRegistry.<AmanSpitEntity>registerEntityRenderingHandler(ListaEntidades.AMAN_SPIT, AmanSpitRenderer::new);
		
		RenderingRegistry.<PrumTrakEntity>registerEntityRenderingHandler(ListaEntidades.PRUM_Y_TRAK, PrumTrakRenderer::new);
		RenderingRegistry.<TrakHenchmanEntity>registerEntityRenderingHandler(ListaEntidades.TRAK_HENCHMAN, TrakHenchmanRenderer::new);
		RenderingRegistry.<PrumHenchmanEntity>registerEntityRenderingHandler(ListaEntidades.PRUM_HENCHMAN, PrumHenchmanRenderer::new);
		RenderingRegistry.<PrumTrakHenchmanEntity>registerEntityRenderingHandler(ListaEntidades.PRUM_TRAK_HENCHMAN, PrumTrakHenchmanRenderer::new);
		RenderingRegistry.<PrumProyectilEntity>registerEntityRenderingHandler(ListaEntidades.PRUM_PROYECTIL, PrumProyectilRenderer::new);
	}

	@SubscribeEvent
	public void biomasCargando(BiomeLoadingEvent ble) {
		ble.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> ListaFeatures.ORE_BLOQUE_ENDER_CORRUPTO);
	}
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Util.MOD_ID, value = Dist.CLIENT)
	public static class RegistrosCliente {
		@SuppressWarnings("resource")
		@SubscribeEvent
		public static void particulas(ParticleFactoryRegisterEvent evento) {
			Minecraft.getInstance().particles.registerFactory(ListaParticulas.PRUM_PARTICULA, ParticulaGenericaInmovil.Factory::new);
			Minecraft.getInstance().particles.registerFactory(ListaParticulas.TRAK_PARTICULA, ParticulaGenericaInmovil.Factory::new);
		}
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Util.MOD_ID)
	public static class Registros {

		@SubscribeEvent
		public static void items(RegistryEvent.Register<Item> event) {
			ListaItem.BOLA_ENDER_CORRUPTA.setRegistryName(Util.getResLoc("bola_ender_corrupta"));
			ListaItem.BLOQUE_ENDER_CORRUPTO.setRegistryName(Util.getResLoc("bloque_ender_corrupto"));

			ListaItem.AMAN_SPAWN_EGG.setRegistryName(Util.getResLoc("aman_spawn_egg"));
			ListaItem.AMAN_SPIDER_SPAWN_EGG.setRegistryName(Util.getResLoc("aman_spider_spawn_egg"));
			ListaItem.PRUM_Y_TRAK_SPAWN_EGG.setRegistryName(Util.getResLoc("prum_y_trak_spawn_egg"));
			ListaItem.TRAK_HENCHMAN_SPAWN_EGG.setRegistryName(Util.getResLoc("trak_henchman_spawn_egg"));
			ListaItem.PRUM_HENCHMAN_SPAWN_EGG.setRegistryName(Util.getResLoc("prum_henchman_spawn_egg"));
			ListaItem.PRUM_TRAK_HENCHMAN_SPAWN_EGG.setRegistryName(Util.getResLoc("prum_trak_henchman_spawn_egg"));
			
			ListaItem.RUNA_AMAN.setRegistryName(Util.getResLoc("runa_aman"));
			ListaItem.RUNA_DUR.setRegistryName(Util.getResLoc("runa_dur"));
			ListaItem.RUNA_ERSA.setRegistryName(Util.getResLoc("runa_ersa"));
			ListaItem.RUNA_FEN.setRegistryName(Util.getResLoc("runa_fen"));
			ListaItem.RUNA_HUL.setRegistryName(Util.getResLoc("runa_hul"));
			ListaItem.RUNA_IOR.setRegistryName(Util.getResLoc("runa_ior"));
			ListaItem.RUNA_KEL.setRegistryName(Util.getResLoc("runa_kel"));
			ListaItem.RUNA_LIR.setRegistryName(Util.getResLoc("runa_lir"));
			ListaItem.RUNA_MIH.setRegistryName(Util.getResLoc("runa_mih"));
			ListaItem.RUNA_NAK.setRegistryName(Util.getResLoc("runa_nak"));
			ListaItem.RUNA_OSHI.setRegistryName(Util.getResLoc("runa_oshi"));
			ListaItem.RUNA_PRUM.setRegistryName(Util.getResLoc("runa_prum"));
			ListaItem.RUNA_RUDU.setRegistryName(Util.getResLoc("runa_rudu"));
			ListaItem.RUNA_SILA.setRegistryName(Util.getResLoc("runa_sila"));
			ListaItem.RUNA_TRAK.setRegistryName(Util.getResLoc("runa_trak"));
			ListaItem.RUNA_UNK.setRegistryName(Util.getResLoc("runa_unk"));
			
			event.getRegistry().register(ListaItem.BOLA_ENDER_CORRUPTA);
			event.getRegistry().register(ListaItem.BLOQUE_ENDER_CORRUPTO);

			event.getRegistry().register(ListaItem.AMAN_SPAWN_EGG);
			event.getRegistry().register(ListaItem.AMAN_SPIDER_SPAWN_EGG);
			event.getRegistry().register(ListaItem.PRUM_Y_TRAK_SPAWN_EGG);
			event.getRegistry().register(ListaItem.TRAK_HENCHMAN_SPAWN_EGG);
			event.getRegistry().register(ListaItem.PRUM_HENCHMAN_SPAWN_EGG);
			event.getRegistry().register(ListaItem.PRUM_TRAK_HENCHMAN_SPAWN_EGG);
			
			event.getRegistry().register(ListaItem.RUNA_AMAN);
			event.getRegistry().register(ListaItem.RUNA_DUR);
			event.getRegistry().register(ListaItem.RUNA_ERSA);
			event.getRegistry().register(ListaItem.RUNA_FEN);
			event.getRegistry().register(ListaItem.RUNA_HUL);
			event.getRegistry().register(ListaItem.RUNA_IOR);
			event.getRegistry().register(ListaItem.RUNA_KEL);
			event.getRegistry().register(ListaItem.RUNA_LIR);
			event.getRegistry().register(ListaItem.RUNA_MIH);
			event.getRegistry().register(ListaItem.RUNA_NAK);
			event.getRegistry().register(ListaItem.RUNA_OSHI);
			event.getRegistry().register(ListaItem.RUNA_PRUM);
			event.getRegistry().register(ListaItem.RUNA_RUDU);
			event.getRegistry().register(ListaItem.RUNA_SILA);
			event.getRegistry().register(ListaItem.RUNA_TRAK);
			event.getRegistry().register(ListaItem.RUNA_UNK);
		}

		@SubscribeEvent
		public static void bloques(RegistryEvent.Register<Block> evento) {
			ListaBloques.BLOQUE_ENDER_CORRUPTO.setRegistryName(Util.getResLoc("bloque_ender_corrupto"));
			evento.getRegistry().register(ListaBloques.BLOQUE_ENDER_CORRUPTO);
		}
		
		@SubscribeEvent
		public static void entidades(RegistryEvent.Register<EntityType<?>> evento) {
			ListaEntidades.DUMMY_BOI.setRegistryName(Util.getResLoc("dummy_boi"));
			ListaEntidades.AMAN_SPIT.setRegistryName(Util.getResLoc("aman_spit"));
			ListaEntidades.PRUM_PROYECTIL.setRegistryName(Util.getResLoc("prum_proyectil"));

			ListaEntidades.AMAN.setRegistryName(Util.getResLoc("aman"));
			ListaEntidades.AMAN_SPIDER.setRegistryName(Util.getResLoc("aman_spider"));
			ListaEntidades.PRUM_Y_TRAK.setRegistryName(Util.getResLoc("prum_y_trak"));
			ListaEntidades.TRAK_HENCHMAN.setRegistryName(Util.getResLoc("trak_henchman"));
			ListaEntidades.PRUM_HENCHMAN.setRegistryName(Util.getResLoc("prum_henchman"));
			ListaEntidades.PRUM_TRAK_HENCHMAN.setRegistryName(Util.getResLoc("prum_trak_henchman"));

			evento.getRegistry().register(ListaEntidades.DUMMY_BOI);
			evento.getRegistry().register(ListaEntidades.AMAN_SPIT);
			evento.getRegistry().register(ListaEntidades.PRUM_PROYECTIL);

			evento.getRegistry().register(ListaEntidades.AMAN);
			evento.getRegistry().register(ListaEntidades.AMAN_SPIDER);
			evento.getRegistry().register(ListaEntidades.PRUM_Y_TRAK);
			evento.getRegistry().register(ListaEntidades.TRAK_HENCHMAN);
			evento.getRegistry().register(ListaEntidades.PRUM_HENCHMAN);
			evento.getRegistry().register(ListaEntidades.PRUM_TRAK_HENCHMAN);
		}
		
		@SubscribeEvent
		public static void particulas(RegistryEvent.Register<ParticleType<?>> evento) {
			ListaParticulas.PRUM_PARTICULA.setRegistryName(Util.getResLoc("prum_particula"));
			ListaParticulas.TRAK_PARTICULA.setRegistryName(Util.getResLoc("trak_particula"));
			
			evento.getRegistry().register(ListaParticulas.PRUM_PARTICULA);
			evento.getRegistry().register(ListaParticulas.TRAK_PARTICULA);
		}
		
		@SubscribeEvent
		public static void atributos(RegistryEvent.Register<Attribute> evento) {
			ListaAtributos.TRAK_AOE_ATTACK_DAMAGE.setRegistryName(Util.getResLoc("trak_aoe_attack_damage"));
			ListaAtributos.RANGE_ATTACK_DAMAGE.setRegistryName(Util.getResLoc("range_attack_damage"));
			ListaAtributos.AMAN_EGG_COUNT.setRegistryName(Util.getResLoc("aman_egg_count"));

			evento.getRegistry().register(ListaAtributos.TRAK_AOE_ATTACK_DAMAGE);
			evento.getRegistry().register(ListaAtributos.RANGE_ATTACK_DAMAGE);
			evento.getRegistry().register(ListaAtributos.AMAN_EGG_COUNT);
		}
	}
}
