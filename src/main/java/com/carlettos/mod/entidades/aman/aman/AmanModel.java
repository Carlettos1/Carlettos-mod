package com.carlettos.mod.entidades.aman.aman;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

//Made with Blockbench 3.7.5
public class AmanModel extends EntityModel<AmanEntity> {
	private final ModelRenderer pata1;
	private final ModelRenderer pata1_1_r1;
	private final ModelRenderer pata1_2;
	private final ModelRenderer pata1_2_r1;
	private final ModelRenderer pata1_3;
	private final ModelRenderer pata2;
	private final ModelRenderer pata2_1_r1;
	private final ModelRenderer pata2_2;
	private final ModelRenderer pata2_2_r1;
	private final ModelRenderer pata2_3;
	private final ModelRenderer pata3;
	private final ModelRenderer pata3_1_r1;
	private final ModelRenderer pata3_2;
	private final ModelRenderer pata3_2_r1;
	private final ModelRenderer pata3_3;
	private final ModelRenderer pata4;
	private final ModelRenderer pata4_1_r1;
	private final ModelRenderer pata4_2;
	private final ModelRenderer pata4_2_r1;
	private final ModelRenderer pata4_3;
	private final ModelRenderer pata5;
	private final ModelRenderer pata5_1_r1;
	private final ModelRenderer pata5_2;
	private final ModelRenderer pata5_2_r1;
	private final ModelRenderer pata5_3;
	private final ModelRenderer pata6;
	private final ModelRenderer pata6_1_r1;
	private final ModelRenderer pata6_2;
	private final ModelRenderer pata6_2_r1;
	private final ModelRenderer pata6_3;
	private final ModelRenderer pata7;
	private final ModelRenderer pata7_1_r1;
	private final ModelRenderer pata7_2;
	private final ModelRenderer pata7_2_r1;
	private final ModelRenderer pata7_3;
	private final ModelRenderer pata8;
	private final ModelRenderer pata8_1_r1;
	private final ModelRenderer pata8_2;
	private final ModelRenderer pata8_2_r1;
	private final ModelRenderer pata8_3;
	private final ModelRenderer cuerpo;
	private final ModelRenderer cabeza;
	private final ModelRenderer mandibula;
	private final ModelRenderer mandibula_izquierda;
	private final ModelRenderer mandibula_derecha;
	private final ModelRenderer cuello;
	private final ModelRenderer body;

	public AmanModel() {
		textureWidth = 256;
		textureHeight = 256;

		pata1 = new ModelRenderer(this);
		pata1.setRotationPoint(-6.0F, 4.0F, -2.0F);
		setRotationAngle(pata1, 0.7854F, -0.7854F, -0.8727F);
		

		pata1_1_r1 = new ModelRenderer(this);
		pata1_1_r1.setRotationPoint(-3.0F, -4.0F, 0.0F);
		pata1.addChild(pata1_1_r1);
		setRotationAngle(pata1_1_r1, 0.0F, 0.0F, -0.3927F);
		pata1_1_r1.setTextureOffset(112, 0).addBox(-2.0F, -18.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata1_2 = new ModelRenderer(this);
		pata1_2.setRotationPoint(-5.0F, -9.0F, 0.0F);
		pata1.addChild(pata1_2);
		setRotationAngle(pata1_2, 0.0F, 0.0F, 0.7854F);
		

		pata1_2_r1 = new ModelRenderer(this);
		pata1_2_r1.setRotationPoint(-4.0F, 4.0F, 0.0F);
		pata1_2.addChild(pata1_2_r1);
		setRotationAngle(pata1_2_r1, 0.0F, 0.0F, 0.7854F);
		pata1_2_r1.setTextureOffset(112, 28).addBox(-13.0F, -1.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata1_3 = new ModelRenderer(this);
		pata1_3.setRotationPoint(-8.0F, 8.0F, 0.0F);
		pata1_2.addChild(pata1_3);
		setRotationAngle(pata1_3, 0.0F, 0.0F, 0.5236F);
		pata1_3.setTextureOffset(112, 56).addBox(-17.0F, 13.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata2 = new ModelRenderer(this);
		pata2.setRotationPoint(6.0F, 4.0F, -2.0F);
		setRotationAngle(pata2, 0.7854F, 0.7854F, 0.8727F);
		

		pata2_1_r1 = new ModelRenderer(this);
		pata2_1_r1.setRotationPoint(3.0F, -4.0F, 0.0F);
		pata2.addChild(pata2_1_r1);
		setRotationAngle(pata2_1_r1, 0.0F, 0.0F, 0.3927F);
		pata2_1_r1.setTextureOffset(96, 0).addBox(-2.0F, -18.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata2_2 = new ModelRenderer(this);
		pata2_2.setRotationPoint(5.0F, -9.0F, 0.0F);
		pata2.addChild(pata2_2);
		setRotationAngle(pata2_2, 0.0F, 0.0F, -0.7854F);
		

		pata2_2_r1 = new ModelRenderer(this);
		pata2_2_r1.setRotationPoint(4.0F, 4.0F, 0.0F);
		pata2_2.addChild(pata2_2_r1);
		setRotationAngle(pata2_2_r1, 0.0F, 0.0F, -0.7854F);
		pata2_2_r1.setTextureOffset(96, 28).addBox(9.0F, -1.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata2_3 = new ModelRenderer(this);
		pata2_3.setRotationPoint(8.0F, 8.0F, 0.0F);
		pata2_2.addChild(pata2_3);
		setRotationAngle(pata2_3, 0.0F, 0.0F, -0.5236F);
		pata2_3.setTextureOffset(97, 56).addBox(13.0F, 13.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata3 = new ModelRenderer(this);
		pata3.setRotationPoint(-6.0F, 4.0F, -2.0F);
		setRotationAngle(pata3, 0.3491F, -0.6981F, -0.5236F);
		

		pata3_1_r1 = new ModelRenderer(this);
		pata3_1_r1.setRotationPoint(-3.0F, -5.0F, 0.0F);
		pata3.addChild(pata3_1_r1);
		setRotationAngle(pata3_1_r1, 0.0F, 0.0F, -0.3927F);
		pata3_1_r1.setTextureOffset(80, 0).addBox(-2.0F, -18.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata3_2 = new ModelRenderer(this);
		pata3_2.setRotationPoint(-5.0F, -10.0F, 0.0F);
		pata3.addChild(pata3_2);
		setRotationAngle(pata3_2, 0.0F, 0.0F, 0.4363F);
		

		pata3_2_r1 = new ModelRenderer(this);
		pata3_2_r1.setRotationPoint(-4.0F, 4.0F, 0.0F);
		pata3_2.addChild(pata3_2_r1);
		setRotationAngle(pata3_2_r1, 0.0F, 0.0F, 0.7854F);
		pata3_2_r1.setTextureOffset(80, 28).addBox(-14.0F, -6.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata3_3 = new ModelRenderer(this);
		pata3_3.setRotationPoint(-8.0F, 8.0F, 0.0F);
		pata3_2.addChild(pata3_3);
		setRotationAngle(pata3_3, 0.0F, 0.0F, 0.3491F);
		pata3_3.setTextureOffset(80, 56).addBox(-18.0F, 5.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata4 = new ModelRenderer(this);
		pata4.setRotationPoint(6.0F, 4.0F, -2.0F);
		setRotationAngle(pata4, 0.3491F, 0.6981F, 0.5236F);
		

		pata4_1_r1 = new ModelRenderer(this);
		pata4_1_r1.setRotationPoint(3.0F, -5.0F, 0.0F);
		pata4.addChild(pata4_1_r1);
		setRotationAngle(pata4_1_r1, 0.0F, 0.0F, 0.3927F);
		pata4_1_r1.setTextureOffset(64, 0).addBox(-2.0F, -18.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata4_2 = new ModelRenderer(this);
		pata4_2.setRotationPoint(5.0F, -10.0F, 0.0F);
		pata4.addChild(pata4_2);
		setRotationAngle(pata4_2, 0.0F, 0.0F, -0.4363F);
		

		pata4_2_r1 = new ModelRenderer(this);
		pata4_2_r1.setRotationPoint(4.0F, 4.0F, 0.0F);
		pata4_2.addChild(pata4_2_r1);
		setRotationAngle(pata4_2_r1, 0.0F, 0.0F, -0.7854F);
		pata4_2_r1.setTextureOffset(64, 28).addBox(10.0F, -6.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata4_3 = new ModelRenderer(this);
		pata4_3.setRotationPoint(8.0F, 8.0F, 0.0F);
		pata4_2.addChild(pata4_3);
		setRotationAngle(pata4_3, 0.0F, 0.0F, -0.3491F);
		pata4_3.setTextureOffset(64, 56).addBox(14.0F, 5.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata5 = new ModelRenderer(this);
		pata5.setRotationPoint(-6.0F, 4.0F, -1.0F);
		setRotationAngle(pata5, -0.1745F, 0.2618F, -0.3491F);
		

		pata5_1_r1 = new ModelRenderer(this);
		pata5_1_r1.setRotationPoint(-3.0F, -5.0F, 0.0F);
		pata5.addChild(pata5_1_r1);
		setRotationAngle(pata5_1_r1, 0.0F, 0.0F, -0.3927F);
		pata5_1_r1.setTextureOffset(48, 0).addBox(-2.0F, -18.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata5_2 = new ModelRenderer(this);
		pata5_2.setRotationPoint(-5.0F, -10.0F, 0.0F);
		pata5.addChild(pata5_2);
		setRotationAngle(pata5_2, 0.0F, 0.0F, 0.4363F);
		

		pata5_2_r1 = new ModelRenderer(this);
		pata5_2_r1.setRotationPoint(-4.0F, 4.0F, 0.0F);
		pata5_2.addChild(pata5_2_r1);
		setRotationAngle(pata5_2_r1, 0.0F, 0.0F, 0.7854F);
		pata5_2_r1.setTextureOffset(48, 28).addBox(-14.0F, -6.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata5_3 = new ModelRenderer(this);
		pata5_3.setRotationPoint(-8.0F, 8.0F, 0.0F);
		pata5_2.addChild(pata5_3);
		setRotationAngle(pata5_3, 0.0F, 0.0F, 0.3491F);
		pata5_3.setTextureOffset(48, 56).addBox(-18.0F, 5.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata6 = new ModelRenderer(this);
		pata6.setRotationPoint(6.0F, 4.0F, -1.0F);
		setRotationAngle(pata6, -0.1745F, -0.2618F, 0.3491F);
		

		pata6_1_r1 = new ModelRenderer(this);
		pata6_1_r1.setRotationPoint(3.0F, -5.0F, 0.0F);
		pata6.addChild(pata6_1_r1);
		setRotationAngle(pata6_1_r1, 0.0F, 0.0F, 0.3927F);
		pata6_1_r1.setTextureOffset(32, 0).addBox(-2.0F, -18.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata6_2 = new ModelRenderer(this);
		pata6_2.setRotationPoint(5.0F, -10.0F, 0.0F);
		pata6.addChild(pata6_2);
		setRotationAngle(pata6_2, 0.0F, 0.0F, -0.4363F);
		

		pata6_2_r1 = new ModelRenderer(this);
		pata6_2_r1.setRotationPoint(4.0F, 4.0F, 0.0F);
		pata6_2.addChild(pata6_2_r1);
		setRotationAngle(pata6_2_r1, 0.0F, 0.0F, -0.7854F);
		pata6_2_r1.setTextureOffset(32, 28).addBox(10.0F, -6.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata6_3 = new ModelRenderer(this);
		pata6_3.setRotationPoint(8.0F, 8.0F, 0.0F);
		pata6_2.addChild(pata6_3);
		setRotationAngle(pata6_3, 0.0F, 0.0F, -0.3491F);
		pata6_3.setTextureOffset(32, 56).addBox(14.0F, 5.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata7 = new ModelRenderer(this);
		pata7.setRotationPoint(-7.0F, 4.0F, 2.0F);
		setRotationAngle(pata7, -0.8727F, 0.8727F, -0.8727F);
		

		pata7_1_r1 = new ModelRenderer(this);
		pata7_1_r1.setRotationPoint(-3.0F, -5.0F, 0.0F);
		pata7.addChild(pata7_1_r1);
		setRotationAngle(pata7_1_r1, 0.0F, 0.0F, -0.3927F);
		pata7_1_r1.setTextureOffset(16, 0).addBox(-2.0F, -18.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata7_2 = new ModelRenderer(this);
		pata7_2.setRotationPoint(-5.0F, -10.0F, 0.0F);
		pata7.addChild(pata7_2);
		setRotationAngle(pata7_2, 0.0F, 0.0F, 0.7854F);
		

		pata7_2_r1 = new ModelRenderer(this);
		pata7_2_r1.setRotationPoint(-4.0F, 4.0F, 0.0F);
		pata7_2.addChild(pata7_2_r1);
		setRotationAngle(pata7_2_r1, 0.0F, 0.0F, 0.7854F);
		pata7_2_r1.setTextureOffset(16, 28).addBox(-13.0F, -1.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata7_3 = new ModelRenderer(this);
		pata7_3.setRotationPoint(-8.0F, 8.0F, 0.0F);
		pata7_2.addChild(pata7_3);
		setRotationAngle(pata7_3, 0.0F, 0.0F, 0.5236F);
		pata7_3.setTextureOffset(16, 56).addBox(-17.0F, 13.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata8 = new ModelRenderer(this);
		pata8.setRotationPoint(7.0F, 4.0F, 2.0F);
		setRotationAngle(pata8, -0.8727F, -0.8727F, 0.8727F);
		

		pata8_1_r1 = new ModelRenderer(this);
		pata8_1_r1.setRotationPoint(3.0F, -5.0F, 0.0F);
		pata8.addChild(pata8_1_r1);
		setRotationAngle(pata8_1_r1, 0.0F, 0.0F, 0.3927F);
		pata8_1_r1.setTextureOffset(0, 0).addBox(-2.0F, -18.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata8_2 = new ModelRenderer(this);
		pata8_2.setRotationPoint(5.0F, -10.0F, 0.0F);
		pata8.addChild(pata8_2);
		setRotationAngle(pata8_2, 0.0F, 0.0F, -0.7854F);
		

		pata8_2_r1 = new ModelRenderer(this);
		pata8_2_r1.setRotationPoint(4.0F, 4.0F, 0.0F);
		pata8_2.addChild(pata8_2_r1);
		setRotationAngle(pata8_2_r1, 0.0F, 0.0F, -0.7854F);
		pata8_2_r1.setTextureOffset(0, 28).addBox(9.0F, -1.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		pata8_3 = new ModelRenderer(this);
		pata8_3.setRotationPoint(8.0F, 8.0F, 0.0F);
		pata8_2.addChild(pata8_3);
		setRotationAngle(pata8_3, 0.0F, 0.0F, -0.5236F);
		pata8_3.setTextureOffset(0, 56).addBox(13.0F, 13.0F, -2.0F, 4.0F, 24.0F, 4.0F, 0.0F, false);

		cuerpo = new ModelRenderer(this);
		cuerpo.setRotationPoint(0.0F, 31.0F, 0.0F);
		

		cabeza = new ModelRenderer(this);
		cabeza.setRotationPoint(0.0F, -8.0F, -4.0F);
		cuerpo.addChild(cabeza);
		cabeza.setTextureOffset(182, 62).addBox(-8.0F, -29.0F, -18.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
		cabeza.setTextureOffset(152, 106).addBox(-3.0F, -30.0F, -13.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);

		mandibula = new ModelRenderer(this);
		mandibula.setRotationPoint(0.0F, 8.5F, -20.0F);
		cabeza.addChild(mandibula);
		

		mandibula_izquierda = new ModelRenderer(this);
		mandibula_izquierda.setRotationPoint(-1.5F, -6.5F, -12.0F);
		mandibula.addChild(mandibula_izquierda);
		mandibula_izquierda.setTextureOffset(0, 93).addBox(-2.5F, -21.5F, 12.0F, 3.0F, 7.0F, 2.0F, 0.0F, false);

		mandibula_derecha = new ModelRenderer(this);
		mandibula_derecha.setRotationPoint(1.5F, -6.5F, -12.0F);
		mandibula.addChild(mandibula_derecha);
		mandibula_derecha.setTextureOffset(0, 84).addBox(-0.5F, -21.5F, 12.0F, 3.0F, 7.0F, 2.0F, 0.0F, false);

		cuello = new ModelRenderer(this);
		cuello.setRotationPoint(0.0F, -8.0F, -1.5F);
		cuerpo.addChild(cuello);
		cuello.setTextureOffset(192, 40).addBox(-6.0F, -27.0F, -4.5F, 12.0F, 12.0F, 10.0F, 0.0F, false);
		cuello.setTextureOffset(124, 107).addBox(-3.0F, -28.0F, -2.5F, 6.0F, 1.0F, 6.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -8.0F, 1.0F);
		cuerpo.addChild(body);
		body.setTextureOffset(168, 0).addBox(-10.0F, -29.0F, 3.0F, 20.0F, 16.0F, 24.0F, 0.0F, false);
		body.setTextureOffset(61, 107).addBox(-3.0F, -24.0F, 27.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(93, 107).addBox(-3.0F, -30.0F, 12.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(AmanEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		pata1.render(matrixStack, buffer, packedLight, packedOverlay);
		pata2.render(matrixStack, buffer, packedLight, packedOverlay);
		pata3.render(matrixStack, buffer, packedLight, packedOverlay);
		pata4.render(matrixStack, buffer, packedLight, packedOverlay);
		pata5.render(matrixStack, buffer, packedLight, packedOverlay);
		pata6.render(matrixStack, buffer, packedLight, packedOverlay);
		pata7.render(matrixStack, buffer, packedLight, packedOverlay);
		pata8.render(matrixStack, buffer, packedLight, packedOverlay);
		cuerpo.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
