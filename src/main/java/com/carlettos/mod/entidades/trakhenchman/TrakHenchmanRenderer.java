package com.carlettos.mod.entidades.trakhenchman;

import com.carlettos.mod.util.Util;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class TrakHenchmanRenderer extends MobRenderer<TrakHenchmanEntity, TrakHenchmanModel>{
	public static final ResourceLocation TRAK_HENCHMAN_TEXTURE = Util.getResLoc("textures/entity/trak_henchman.png");
	
	public TrakHenchmanRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new TrakHenchmanModel(), 0.5F);
	}

	@Override
	public ResourceLocation getEntityTexture(TrakHenchmanEntity entity) {
		return TRAK_HENCHMAN_TEXTURE;
	}
}
