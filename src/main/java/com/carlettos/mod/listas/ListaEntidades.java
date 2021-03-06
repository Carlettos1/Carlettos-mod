package com.carlettos.mod.listas;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;
import com.carlettos.mod.entidades.prumytrak.proyectil.PrumProyectilEntity;
import com.carlettos.mod.util.Util;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class ListaEntidades {
	public static final EntityType<PrumTrakEntity> prum_y_trak;
	public static final EntityType<PrumProyectilEntity> prum_proyectil;
	
	static {
		prum_y_trak = EntityType.Builder.<PrumTrakEntity>create(PrumTrakEntity::new, EntityClassification.MONSTER).size(1.7f, 4.6f).build(Util.MOD_ID + ":prum_y_trak");
		prum_proyectil = EntityType.Builder.<PrumProyectilEntity>create(PrumProyectilEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).trackingRange(4).func_233608_b_(10).build(Util.MOD_ID + ":prum_proyectil");
	}
}
