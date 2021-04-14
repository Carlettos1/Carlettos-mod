package com.carlettos.mod.entidades.trak.trakhenchman;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

//Made with Blockbench 3.7.5
public class TrakHenchmanModel extends EntityModel<TrakHenchmanEntity> implements IHasArm, IHasHead{
	public float aoe;
	
	private final ModelRenderer piernaderecha;
	private final ModelRenderer piernaderechaabajo;
	private final ModelRenderer piernaizquierda;
	private final ModelRenderer piernaizquierdaabajo;
	private final ModelRenderer brazoderecho;
	private final ModelRenderer brazoizquierdo;
	private final ModelRenderer cuerpo;
	private final ModelRenderer cabeza;

	public TrakHenchmanModel() {
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
	public void setRotationAngles(TrakHenchmanEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
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
		//TODO: sitting
		//TODO: actualizar manos dependiendo del arma
		if(!(this.swingProgress <= 0F)) {
			HandSide side = entity.getPrimaryHand();
			side = entity.swingingHand == Hand.MAIN_HAND ? side : side.opposite();
			ModelRenderer mano = side == HandSide.LEFT ? this.brazoizquierdo : this.brazoderecho;
			this.cuerpo.rotateAngleY = MathHelper.sin(MathHelper.sqrt(this.swingProgress) * (float)Math.PI * 2F) * 0.2F;
			if(side == HandSide.LEFT) {
				this.cuerpo.rotateAngleY *= -1F;
			}
			
			this.brazoderecho.rotateAngleX = -MathHelper.cos(this.cuerpo.rotateAngleY) / 2F;
			this.brazoderecho.rotateAngleY += this.cuerpo.rotateAngleY;
			this.brazoderecho.rotateAngleZ = MathHelper.sin(this.cuerpo.rotateAngleY) / 2F;
			this.brazoizquierdo.rotateAngleX = MathHelper.cos(this.cuerpo.rotateAngleY) / 2F;
			this.brazoizquierdo.rotateAngleY += this.cuerpo.rotateAngleY;
			this.brazoizquierdo.rotateAngleZ = -MathHelper.sin(this.cuerpo.rotateAngleY) / 2F;
			
			float f = 1F - this.swingProgress;
			f = 1F - f * f * f * f;
			float f1 = MathHelper.sin(f * (float)Math.PI);
			float f2 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.cabeza.rotateAngleX - 0.7F) * 0.75F;
			mano.rotateAngleX = mano.rotateAngleX - f1 * 1.2F - f2;
			mano.rotateAngleY += this.cuerpo.rotateAngleY * 2F;
			mano.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		if(!(this.aoe <= 0F)) {
			float aoeProgress = this.aoe;
			float angulo = MathHelper.sin(MathHelper.sqrt(aoeProgress) * (float)Math.PI) * (float)Math.PI / 2F;
			this.piernaderecha.rotateAngleX = angulo;
			this.piernaderechaabajo.rotateAngleX = -angulo;
			this.piernaizquierda.rotateAngleX = -angulo;
			this.piernaizquierdaabajo.rotateAngleX = angulo;
			float ajuste = 4F/16F * (1 - MathHelper.cos(angulo));
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
