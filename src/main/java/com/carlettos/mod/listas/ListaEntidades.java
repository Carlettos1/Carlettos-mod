package com.carlettos.mod.listas;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;
import com.carlettos.mod.entidades.prumytrak.proyectil.PrumProyectilEntity;
import com.carlettos.mod.entidades.trakhenchman.TrakHenchmanEntity;
import com.carlettos.mod.util.Util;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class ListaEntidades {
	public static final EntityType<PrumTrakEntity> PRUM_Y_TRAK;
	public static final EntityType<PrumProyectilEntity> PRUM_PROYECTIL;
	public static final EntityType<TrakHenchmanEntity> TRAK_HENCHMAN;
	
	static {
		PRUM_Y_TRAK = EntityType.Builder.<PrumTrakEntity>create(PrumTrakEntity::new, EntityClassification.MONSTER).size(1.7f, 4.6f).build(Util.MOD_ID + ":prum_y_trak");
		PRUM_PROYECTIL = EntityType.Builder.<PrumProyectilEntity>create(PrumProyectilEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).trackingRange(4).func_233608_b_(10).build(Util.MOD_ID + ":prum_proyectil");
		TRAK_HENCHMAN = EntityType.Builder.<TrakHenchmanEntity>create(TrakHenchmanEntity::new, EntityClassification.MONSTER).size(1F, 1F).build(Util.MOD_ID + ":trak_henchman");
	}
}
