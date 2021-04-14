package com.carlettos.mod.entidades.aman.amanspider;

import com.carlettos.mod.util.Util;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class AmanSpiderRenderer extends MobRenderer<AmanSpiderEntity, AmanSpiderModel>{
	public static final ResourceLocation AMAN_SPIDER_TEXTURE = Util.getResLoc("textures/entity/aman_spider.png");
	
	public AmanSpiderRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new AmanSpiderModel(), 0.5F);
	}
	
	@Override
	public void render(AmanSpiderEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		this.entityModel.hatch = entityIn.getHatchProgress(partialTicks);
		this.entityModel.spit = entityIn.getSpitProgress(partialTicks);
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(AmanSpiderEntity entity) {
		return AMAN_SPIDER_TEXTURE;
	}
}
