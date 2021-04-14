package com.carlettos.mod.entidades.prum.prumproyectil;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class PrumProyectilModel extends SegmentedModel<PrumProyectilEntity> {
	private final ModelRenderer bb_main;

	public PrumProyectilModel() {
		textureWidth = 16;
		textureHeight = 16;

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.setTextureOffset(0, 0).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 5.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(PrumProyectilEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		this.bb_main.rotateAngleX = -headPitch * ((float) Math.PI / 180F) + (float) Math.PI;
		this.bb_main.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.bb_main);
	}
}