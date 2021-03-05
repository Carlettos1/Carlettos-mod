package com.carlettos.mod.listas;

import com.carlettos.mod.entidades.prumytrak.PrumTrakEntity;
import com.carlettos.mod.util.Util;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class ListaEntidades {
	public static final EntityType<PrumTrakEntity> prum_y_trak;
	
	static {
		prum_y_trak = EntityType.Builder.<PrumTrakEntity>create(PrumTrakEntity::new, EntityClassification.MONSTER).size(1.7f, 4.6f).build(Util.MOD_ID + ":prum_y_trak");
	}
}
