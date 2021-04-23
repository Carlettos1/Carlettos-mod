package com.carlettos.mod.entidades.dummyboi;

import com.carlettos.mod.util.Util;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class DummyBoiRenderer extends LivingRenderer<DummyBoiEntity, DummyBoiModel>{
	public static final ResourceLocation DUMMY_BOI_TEXTURE = Util.getResLoc("textures/entity/dummy_boi.png");
	
	public DummyBoiRenderer(EntityRendererManager rendererManager) {
		super(rendererManager, new DummyBoiModel(), 0.5F);
	}

	@Override
	public ResourceLocation getEntityTexture(DummyBoiEntity entity) {
		return DUMMY_BOI_TEXTURE;
	}
}
