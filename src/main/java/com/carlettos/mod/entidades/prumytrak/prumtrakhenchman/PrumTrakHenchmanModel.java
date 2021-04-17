package com.carlettos.mod.entidades.prumytrak.prumtrakhenchman;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

//Modelo hecho con Blockbench 3.8.3
public class PrumTrakHenchmanModel extends EntityModel<PrumTrakHenchmanEntity>{
	private final ModelRenderer piernaderecha;
	private final ModelRenderer piernaderechaabajo;
	private final ModelRenderer piernaizquierda;
	private final ModelRenderer piernaizquierdaabajo;
	private final ModelRenderer brazoderecho;
	private final ModelRenderer brazoizquierdo;
	private final ModelRenderer cuerpo;
	private final ModelRenderer cabeza;
	private final ModelRenderer cabezaderecha;
	private final ModelRenderer cabezaizquierda;

	public PrumTrakHenchmanModel() {
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
		

		cabezaderecha = new ModelRenderer(this);
		cabezaderecha.setRotationPoint(0.0F, 0.0F, 0.0F);
		cabeza.addChild(cabezaderecha);
		cabezaderecha.setTextureOffset(48, 56).addBox(-4.25F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		cabezaizquierda = new ModelRenderer(this);
		cabezaizquierda.setRotationPoint(2.25F, 0.0F, 0.0F);
		cabeza.addChild(cabezaizquierda);
		cabezaizquierda.setTextureOffset(48, 48).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
	}
	
	@Override
	public void setLivingAnimations(PrumTrakHenchmanEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		this.cabezaizquierda.rotateAngleX = entityIn.getSegundaCabezaPitch(partialTick);
		this.cabezaizquierda.rotateAngleY = entityIn.getSegundaCabezaYaw(partialTick);
	}

	@Override
	public void setRotationAngles(PrumTrakHenchmanEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		this.cabezaderecha.rotateAngleX = headPitch * (float)Math.PI / 180F;
		this.cabezaderecha.rotateAngleY = netHeadYaw * (float)Math.PI / 180F;
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
}
