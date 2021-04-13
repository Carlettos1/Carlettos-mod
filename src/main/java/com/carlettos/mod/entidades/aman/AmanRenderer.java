package com.carlettos.mod.entidades.aman;

import com.carlettos.mod.util.Util;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class AmanRenderer extends MobRenderer<AmanEntity, AmanModel>{
	public static final ResourceLocation AMAN_TEXTURE = Util.getResLoc("textures/entity/aman.png");
	
	public AmanRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new AmanModel(), 0.5F);
	}

	@Override
	public ResourceLocation getEntityTexture(AmanEntity entity) {
		return AMAN_TEXTURE;
	}
}
