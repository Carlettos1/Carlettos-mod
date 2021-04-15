package com.carlettos.mod.entidades.prumytrak.prum.prumhenchman;

import com.carlettos.mod.util.Util;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PrumHenchmanRenderer extends MobRenderer<PrumHenchmanEntity, PrumHenchmanModel>{
	public static final ResourceLocation PRUM_HENCHMAN_TEXTURE = Util.getResLoc("textures/entity/prum_henchman.png");

	public PrumHenchmanRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new PrumHenchmanModel(), 0.5F);
	}
	
	@Override
	public void render(PrumHenchmanEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		this.entityModel.ranged = entityIn.getRangedProgress(partialTicks);
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(PrumHenchmanEntity entity) {
		return PRUM_HENCHMAN_TEXTURE;
	}
}
