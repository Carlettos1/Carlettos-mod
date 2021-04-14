package com.carlettos.mod.entidades.aman.aman;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

//Made with Blockbench 3.7.5
public class AmanModel extends EntityModel<AmanEntity> {

	private final ModelRenderer bb_main;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r8;

	public AmanModel() {
		textureWidth = 256;
		textureHeight = 256;

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.setTextureOffset(0, 0).addBox(-14.0F, -12.0F, 2.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 0).addBox(12.0F, -12.0F, 2.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 0).addBox(-5.0F, -12.0F, 12.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 0).addBox(3.0F, -12.0F, 12.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 0).addBox(12.0F, -12.0F, -4.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 0).addBox(-14.0F, -12.0F, -4.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 0).addBox(-5.0F, -12.0F, -14.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 0).addBox(3.0F, -12.0F, -14.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 0).addBox(-4.0F, -13.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-4.0F, -18.0F, -5.0F);
		bb_main.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.3927F, 0.0F, 0.0F);
		cube_r1.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		cube_r1.setTextureOffset(0, 0).addBox(7.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-4.0F, -18.0F, 5.0F);
		bb_main.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.3927F, 0.0F, 0.0F);
		cube_r2.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		cube_r2.setTextureOffset(0, 0).addBox(7.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(-5.0F, -18.0F, -3.0F);
		bb_main.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 0.0F, -0.3927F);
		cube_r3.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		cube_r3.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, 5.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(5.0F, -18.0F, 3.0F);
		bb_main.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, 0.0F, 0.3927F);
		cube_r4.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		cube_r4.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, -7.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(11.0F, -18.0F, 3.0F);
		bb_main.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, 0.0F, -0.3927F);
		cube_r5.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		cube_r5.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, -7.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(4.0F, -18.0F, -11.0F);
		bb_main.addChild(cube_r6);
		setRotationAngle(cube_r6, -0.3927F, 0.0F, 0.0F);
		cube_r6.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		cube_r6.setTextureOffset(0, 0).addBox(-9.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(-11.0F, -18.0F, 3.0F);
		bb_main.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.0F, 0.0F, 0.3927F);
		cube_r7.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		cube_r7.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, -7.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		cube_r8 = new ModelRenderer(this);
		cube_r8.setRotationPoint(4.0F, -18.0F, 11.0F);
		bb_main.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.3927F, 0.0F, 0.0F);
		cube_r8.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		cube_r8.setTextureOffset(0, 0).addBox(-9.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(AmanEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
