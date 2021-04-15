package com.carlettos.mod.entidades.prumytrak.trak.trakhenchman;

import com.carlettos.mod.util.Util;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class TrakHenchmanRenderer extends MobRenderer<TrakHenchmanEntity, TrakHenchmanModel>{
	public static final ResourceLocation TRAK_HENCHMAN_TEXTURE = Util.getResLoc("textures/entity/trak_henchman.png");
	
	public TrakHenchmanRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new TrakHenchmanModel(), 0.5F);
	}
	
	@Override
	public void render(TrakHenchmanEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		this.entityModel.aoe = entityIn.getAOEProgress(partialTicks);
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(TrakHenchmanEntity entity) {
		return TRAK_HENCHMAN_TEXTURE;
	}
}
