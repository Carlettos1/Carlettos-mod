package com.carlettos.mod.entidades.prumytrak.prumtrak;

import com.carlettos.mod.util.Util;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PrumTrakRenderer extends MobRenderer<PrumTrakEntity, PrumTrakModel>{
	public static final ResourceLocation PRUM_Y_TRAK_FASE1_TEXTURE = Util.getResLoc("textures/entity/prum_y_trak_fase1.png");
	public static final ResourceLocation PRUM_Y_TRAK_FASE2_TEXTURE = Util.getResLoc("textures/entity/prum_y_trak_fase2.png");
	public static final ResourceLocation PRUM_Y_TRAK_FASE3_TEXTURE = Util.getResLoc("textures/entity/prum_y_trak_fase3.png");

	public PrumTrakRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new PrumTrakModel(), 1.5f);
	}
	
	@Override
	public void render(PrumTrakEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		this.entityModel.aoe = entityIn.getAOEProgress(partialTicks);
		this.entityModel.range = entityIn.getRangedProgress(partialTicks);
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(PrumTrakEntity entity) {
		switch (entity.getFase()) {
		case 2:
			return PRUM_Y_TRAK_FASE2_TEXTURE;
		case 3:
			return PRUM_Y_TRAK_FASE3_TEXTURE;
		default:
			return PRUM_Y_TRAK_FASE1_TEXTURE;
		}
	}
}
