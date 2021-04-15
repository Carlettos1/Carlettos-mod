package com.carlettos.mod.entidades.prumytrak.prumtrakhenchman;

import com.carlettos.mod.util.Util;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PrumTrakHenchmanRenderer extends MobRenderer<PrumTrakHenchmanEntity, PrumTrakHenchmanModel>{
	public static final ResourceLocation PRUM_TRAK_HENCHMAN_TEXTURE = Util.getResLoc("textures/entity/prum_trak_henchman.png");
	
	public PrumTrakHenchmanRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new PrumTrakHenchmanModel(), 0.5F);
	}
	
	@Override
	public void render(PrumTrakHenchmanEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(PrumTrakHenchmanEntity entity) {
		return PRUM_TRAK_HENCHMAN_TEXTURE;
	}
}
