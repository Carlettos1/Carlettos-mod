package com.carlettos.mod.entidades.prumytrak.prumtrakhenchman;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

//Modelo hecho con Blockbench 3.8.3
public class PrumTrakHenchmanModel extends EntityModel<PrumTrakHenchmanEntity>{
	public float aoe;
	public float range;
	private float tamañofemur = 4F;
	
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
		
		if(!(this.swingProgress <= 0F)) {
			float f = this.swingProgress;
			this.cuerpo.rotateAngleY = (float) Math.toRadians(15F * f);
			this.brazoizquierdo.rotateAngleX = (float) Math.toRadians(-90F * f);
		}
		
		if(!(this.range <= 0F)) {
			this.brazoderecho.rotateAngleX = -80F * (1 - MathHelper.sqrt(this.range)) * (float) Math.PI / 180f;
			this.brazoderecho.rotateAngleY = -10F * this.range * (float) Math.PI / 180f;
			this.brazoderecho.rotateAngleZ = -10F * this.range * (float) Math.PI / 180f;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		this.piernaderecha.rotateAngleX = 0;
		this.piernaderechaabajo.rotateAngleX = 0;
		this.piernaizquierda.rotateAngleX = 0;
		this.piernaizquierdaabajo.rotateAngleX = 0;
		if(!(this.aoe <= 0F)) {
			float aoeProgress = this.aoe;
			float angulo = MathHelper.sin(MathHelper.sqrt(aoeProgress) * (float)Math.PI) * (float)Math.PI / 2F;
			this.piernaderecha.rotateAngleX = angulo;
			this.piernaderechaabajo.rotateAngleX = -angulo;
			this.piernaizquierda.rotateAngleX = -angulo;
			this.piernaizquierdaabajo.rotateAngleX = angulo;
			float ajuste = this.tamañofemur/16F * (1 - MathHelper.cos(angulo));
			matrixStack.push();
			matrixStack.translate(0, ajuste, 0);
		}
		piernaderecha.render(matrixStack, buffer, packedLight, packedOverlay);
		piernaizquierda.render(matrixStack, buffer, packedLight, packedOverlay);
		brazoderecho.render(matrixStack, buffer, packedLight, packedOverlay);
		brazoizquierdo.render(matrixStack, buffer, packedLight, packedOverlay);
		cuerpo.render(matrixStack, buffer, packedLight, packedOverlay);
		cabeza.render(matrixStack, buffer, packedLight, packedOverlay);
		if(!(this.aoe <= 0F)) {
			matrixStack.pop();
		}
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
