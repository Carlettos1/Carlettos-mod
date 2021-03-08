package com.carlettos.mod.entidades.prumytrak.proyectil;

import com.carlettos.mod.util.Util;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class PrumProyectilRenderer extends EntityRenderer<PrumProyectilEntity> {
	public static final ResourceLocation PRUM_PROYECTILE_TEXTURE = Util.getResLoc("textures/entity/prum_proyectil.png");
	private final PrumProyectilModel modelo = new PrumProyectilModel();
	
	public PrumProyectilRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}
	
	@Override
	public void render(PrumProyectilEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.translate(0, -1.375F, 0);
		IVertexBuilder ivertex = bufferIn.getBuffer(this.modelo.getRenderType(this.getEntityTexture(entityIn)));
		this.modelo.render(matrixStackIn, ivertex, packedLightIn, packedLightIn, packedLightIn, entityYaw, partialTicks, packedLightIn);
		this.modelo.setRotationAngles(entityIn, 0, 0, 0, entityIn.getYaw(partialTicks), entityIn.getPitch(partialTicks));
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(PrumProyectilEntity entity) {
		return PRUM_PROYECTILE_TEXTURE;
	}
}
