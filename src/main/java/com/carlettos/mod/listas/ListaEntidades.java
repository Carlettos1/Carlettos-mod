package com.carlettos.mod.listas;

import com.carlettos.mod.entidades.aman.AmanEntity;
import com.carlettos.mod.entidades.amanspider.AmanSpiderEntity;
import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;
import com.carlettos.mod.entidades.prumytrak.proyectil.PrumProyectilEntity;
import com.carlettos.mod.entidades.trakhenchman.TrakHenchmanEntity;
import com.carlettos.mod.util.Util;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class ListaEntidades {
	public static final EntityType<AmanEntity> AMAN;
	public static final EntityType<AmanSpiderEntity> AMAN_SPIDER;
	public static final EntityType<PrumTrakEntity> PRUM_Y_TRAK;
	public static final EntityType<PrumProyectilEntity> PRUM_PROYECTIL;
	public static final EntityType<TrakHenchmanEntity> TRAK_HENCHMAN;
	
	static {
		AMAN = EntityType.Builder.<AmanEntity>create(AmanEntity::new, EntityClassification.MONSTER).size(2F, 3F).build(Util.MOD_ID + ":aman");
		AMAN_SPIDER = EntityType.Builder.<AmanSpiderEntity>create(AmanSpiderEntity::new, EntityClassification.MONSTER).size(1.6F, 1.2F).build(Util.MOD_ID + ":aman_spider");
		
		PRUM_Y_TRAK = EntityType.Builder.<PrumTrakEntity>create(PrumTrakEntity::new, EntityClassification.MONSTER).size(1.7f, 4.6f).build(Util.MOD_ID + ":prum_y_trak");
		PRUM_PROYECTIL = EntityType.Builder.<PrumProyectilEntity>create(PrumProyectilEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).trackingRange(4).func_233608_b_(10).build(Util.MOD_ID + ":prum_proyectil");
		TRAK_HENCHMAN = EntityType.Builder.<TrakHenchmanEntity>create(TrakHenchmanEntity::new, EntityClassification.MONSTER).size(0.5F, 1.1f).build(Util.MOD_ID + ":trak_henchman");
	}
}
