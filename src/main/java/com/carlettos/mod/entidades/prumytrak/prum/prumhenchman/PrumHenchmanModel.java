package com.carlettos.mod.entidades.prumytrak.prum.prumhenchman;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

//Made with Blockbench 3.7.5
public class PrumHenchmanModel extends EntityModel<PrumHenchmanEntity> implements IHasArm, IHasHead {
	public float ranged;
	
	private final ModelRenderer piernaderecha;
	private final ModelRenderer piernaderechaabajo;
	private final ModelRenderer piernaizquierda;
	private final ModelRenderer piernaizquierdaabajo;
	private final ModelRenderer brazoderecho;
	private final ModelRenderer brazoizquierdo;
	private final ModelRenderer cuerpo;
	private final ModelRenderer cabeza;

	public PrumHenchmanModel() {
		textureWidth = 64;
		textureHeight = 64;

		piernaderecha = new ModelRenderer(this);
		piernaderecha.setRotationPoint(-2.0F, 16.0F, 0.0F);
		piernaderecha.setTextureOffset(0, 58).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		piernaderechaabajo = new ModelRenderer(this);
		piernaderechaabajo.setRotationPoint(0.0F, 4.0F, 0.0F);
		piernaderecha.addChild(piernaderechaabajo);
		piernaderechaabajo.setTextureOffset(0, 52).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		piernaizquierda = new ModelRenderer(this);
		piernaizquierda.setRotationPoint(2.0F, 16.0F, 0.0F);
		piernaizquierda.setTextureOffset(0, 46).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		piernaizquierdaabajo = new ModelRenderer(this);
		piernaizquierdaabajo.setRotationPoint(0.0F, 4.0F, 0.0F);
		piernaizquierda.addChild(piernaizquierdaabajo);
		piernaizquierdaabajo.setTextureOffset(0, 40).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		brazoderecho = new ModelRenderer(this);
		brazoderecho.setRotationPoint(-3.0F, 9.0F, 0.0F);
		brazoderecho.setTextureOffset(24, 54).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);

		brazoizquierdo = new ModelRenderer(this);
		brazoizquierdo.setRotationPoint(3.0F, 9.0F, 0.0F);
		brazoizquierdo.setTextureOffset(16, 54).addBox(0.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);

		cuerpo = new ModelRenderer(this);
		cuerpo.setRotationPoint(0.0F, 12.0F, 0.0F);
		cuerpo.setTextureOffset(32, 54).addBox(-3.0F, -4.0F, -1.0F, 6.0F, 8.0F, 2.0F, 0.0F, false);

		cabeza = new ModelRenderer(this);
		cabeza.setRotationPoint(0.0F, 8.0F, 0.0F);
		cabeza.setTextureOffset(48, 56).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(PrumHenchmanEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		this.cabeza.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.cabeza.rotateAngleY = netHeadYaw * (float)Math.PI / 180F;
		
		this.cuerpo.rotateAngleY = 0F;

		this.brazoderecho.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
		this.brazoderecho.rotateAngleY = 0F;
		this.brazoderecho.rotateAngleZ = 0F;
		
		this.brazoizquierdo.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
		this.brazoizquierdo.rotateAngleY = 0F;
		this.brazoizquierdo.rotateAngleZ = 0F;

		this.piernaderecha.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.piernaderecha.rotateAngleY = 0F;
		this.piernaderecha.rotateAngleZ = 0F;

		this.piernaizquierda.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.piernaizquierda.rotateAngleY = 0F;
		this.piernaizquierda.rotateAngleZ = 0F;

		if(!(this.ranged <= 0F)) {			
			this.brazoderecho.rotateAngleX = -80F * (1 - MathHelper.sqrt(this.ranged)) * (float) Math.PI / 180f;
			this.brazoderecho.rotateAngleY = -10F * this.ranged * (float) Math.PI / 180f;
			this.brazoderecho.rotateAngleZ = -10F * this.ranged * (float) Math.PI / 180f;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		piernaderecha.render(matrixStack, buffer, packedLight, packedOverlay);
		piernaizquierda.render(matrixStack, buffer, packedLight, packedOverlay);
		brazoderecho.render(matrixStack, buffer, packedLight, packedOverlay);
		brazoizquierdo.render(matrixStack, buffer, packedLight, packedOverlay);
		cuerpo.render(matrixStack, buffer, packedLight, packedOverlay);
		cabeza.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public ModelRenderer getModelHead() {
		return this.cabeza;
	}

	@Override
	public void translateHand(HandSide sideIn, MatrixStack matrixStackIn) {
		switch (sideIn) {
		case RIGHT:
			this.brazoderecho.translateRotate(matrixStackIn);
			break;
		case LEFT:
			this.brazoizquierdo.translateRotate(matrixStackIn);
			break;
		}
	}
}
