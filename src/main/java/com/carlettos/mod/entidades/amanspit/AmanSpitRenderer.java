package com.carlettos.mod.entidades.amanspit;

import com.carlettos.mod.util.Util;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class AmanSpitRenderer extends EntityRenderer<AmanSpitEntity>{
	public static final ResourceLocation AMAN_SPIT_TEXTURE = Util.getResLoc("textures/entity/aman_spit.png");
	private final AmanSpitModel modelo = new AmanSpitModel();
	
	public AmanSpitRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void render(AmanSpitEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		IVertexBuilder ivertex = bufferIn.getBuffer(this.modelo.getRenderType(this.getEntityTexture(entityIn)));
		this.modelo.render(matrixStackIn, ivertex, packedLightIn, packedLightIn, packedLightIn, entityYaw, partialTicks, packedLightIn);
		this.modelo.setRotationAngles(entityIn, 0, 0, 0, entityIn.getYaw(partialTicks), entityIn.getPitch(partialTicks));
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(AmanSpitEntity entity) {
		return AMAN_SPIT_TEXTURE;
	}
}
