package com.carlettos.mod.listas;

import com.carlettos.mod.entidades.aman.aman.AmanEntity;
import com.carlettos.mod.entidades.aman.amanspider.AmanSpiderEntity;
import com.carlettos.mod.entidades.aman.amanspit.AmanSpitEntity;
import com.carlettos.mod.entidades.dummyboi.DummyBoiEntity;
import com.carlettos.mod.entidades.hul.HulKutEntity;
import com.carlettos.mod.entidades.prumytrak.prum.prumhenchman.PrumHenchmanEntity;
import com.carlettos.mod.entidades.prumytrak.prum.prumproyectil.PrumProyectilEntity;
import com.carlettos.mod.entidades.prumytrak.prumtrak.PrumTrakEntity;
import com.carlettos.mod.entidades.prumytrak.prumtrakhenchman.PrumTrakHenchmanEntity;
import com.carlettos.mod.entidades.prumytrak.trak.trakhenchman.TrakHenchmanEntity;
import com.carlettos.mod.util.Util;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class ListaEntidades {
	public static final EntityType<DummyBoiEntity> DUMMY_BOI;
	
	public static final EntityType<AmanEntity> AMAN;
	public static final EntityType<AmanSpiderEntity> AMAN_SPIDER;
	public static final EntityType<AmanSpitEntity> AMAN_SPIT;
	
	public static final EntityType<HulKutEntity> HUL_KUT;
	
	public static final EntityType<PrumTrakEntity> PRUM_Y_TRAK;
	public static final EntityType<PrumProyectilEntity> PRUM_PROYECTIL;
	public static final EntityType<TrakHenchmanEntity> TRAK_HENCHMAN;
	public static final EntityType<PrumHenchmanEntity> PRUM_HENCHMAN;
	public static final EntityType<PrumTrakHenchmanEntity> PRUM_TRAK_HENCHMAN;
	
	static {
		DUMMY_BOI = EntityType.Builder.<DummyBoiEntity>create(DummyBoiEntity::new, EntityClassification.MISC).size(0.6F, 1.5F).build(Util.MOD_ID + ":dummy_boy");
		
		AMAN = EntityType.Builder.<AmanEntity>create(AmanEntity::new, EntityClassification.MONSTER).size(2.4F, 1.8F).build(Util.MOD_ID + ":aman");
		AMAN_SPIDER = EntityType.Builder.<AmanSpiderEntity>create(AmanSpiderEntity::new, EntityClassification.MONSTER).size(1.6F, 1.2F).build(Util.MOD_ID + ":aman_spider");
		AMAN_SPIT = EntityType.Builder.<AmanSpitEntity>create(AmanSpitEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).build(Util.MOD_ID + ":aman_spit");
		
		HUL_KUT = EntityType.Builder.<HulKutEntity>create(HulKutEntity::new, EntityClassification.MONSTER).size(0.1F, 0.1F).build(Util.MOD_ID + ":hul_kut");
		
		PRUM_Y_TRAK = EntityType.Builder.<PrumTrakEntity>create(PrumTrakEntity::new, EntityClassification.MONSTER).size(1.7f, 4.6f).build(Util.MOD_ID + ":prum_y_trak");
		PRUM_PROYECTIL = EntityType.Builder.<PrumProyectilEntity>create(PrumProyectilEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).trackingRange(4).func_233608_b_(10).build(Util.MOD_ID + ":prum_proyectil");
		TRAK_HENCHMAN = EntityType.Builder.<TrakHenchmanEntity>create(TrakHenchmanEntity::new, EntityClassification.MONSTER).size(0.5F, 1.1f).build(Util.MOD_ID + ":trak_henchman");
		PRUM_HENCHMAN = EntityType.Builder.<PrumHenchmanEntity>create(PrumHenchmanEntity::new, EntityClassification.MONSTER).size(0.5F, 1.1f).build(Util.MOD_ID + ":prum_henchman");
		PRUM_TRAK_HENCHMAN = EntityType.Builder.<PrumTrakHenchmanEntity>create(PrumTrakHenchmanEntity::new, EntityClassification.MONSTER).size(0.5F, 1.1f).build(Util.MOD_ID + ":prum_trak_henchman");
	}
}
