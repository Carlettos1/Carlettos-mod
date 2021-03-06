package com.carlettos.mod.entidades.prumytrak;

import com.carlettos.mod.util.Util;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PrumTrakRender extends MobRenderer<PrumTrakEntity, PrumTrakModel>{
	public static final ResourceLocation PRUM_Y_TRAK_TEXTURE = Util.getResLoc("textures/entity/prum_y_trak.png");

	public PrumTrakRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new PrumTrakModel(), 1.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(PrumTrakEntity entity) {
		return PRUM_Y_TRAK_TEXTURE;
	}
}
