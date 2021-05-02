package com.carlettos.mod.entidades.hul;

import com.carlettos.mod.util.Util;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class HulKutRenderer extends MobRenderer<HulKutEntity, HulKutModel>{
	public static final ResourceLocation HUL_KUT_TEXTURE = Util.getResLoc("textures/entity/hul_kut.png");

	public HulKutRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new HulKutModel(), 0.1F);
	}

	@Override
	public ResourceLocation getEntityTexture(HulKutEntity entity) {
		return HUL_KUT_TEXTURE;
	}
}
