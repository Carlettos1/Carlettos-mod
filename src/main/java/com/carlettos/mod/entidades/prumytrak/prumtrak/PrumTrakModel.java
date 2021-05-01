package com.carlettos.mod.entidades.prumytrak.prumtrak;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

//Modelo hecho con Blockbench 3.8.3
public class PrumTrakModel extends EntityModel<PrumTrakEntity> {
	public float aoe;
	public float range;
	private float tamañofemur = 16F;
	
	private final ModelRenderer piernaderecha;
	private final ModelRenderer piernaderechaarriba;
	private final ModelRenderer piernaderechaabajo;
	private final ModelRenderer piernaizquierda;
	private final ModelRenderer piernaizquierdaarriba;
	private final ModelRenderer piernaizquierdaabajo;
	private final ModelRenderer torso;
	private final ModelRenderer brazoderecho;
	private final ModelRenderer brazoderechoabajo;
	private final ModelRenderer brazoderechoarriba;
	private final ModelRenderer brazoizquierdo;
	private final ModelRenderer brazoizquierdoarriba;
	private final ModelRenderer brazoizquieroabajo;
	private final ModelRenderer cabezaizquierda;
	private final ModelRenderer ornamento;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cabezaderecha;
	private final ModelRenderer cejaizquierda_r1;
	private final ModelRenderer cejaderecha_r1;
	private final ModelRenderer barbacabezaderecha;

	public PrumTrakModel() {
		textureWidth = 256;
		textureHeight = 256;

		piernaderecha = new ModelRenderer(this);
		piernaderecha.setRotationPoint(-7.0F, -8.0F, 0.0F);

		piernaderechaarriba = new ModelRenderer(this);
		piernaderechaarriba.setRotationPoint(0.0F, 0.0F, 0.0F);
		piernaderecha.addChild(piernaderechaarriba);
		piernaderechaarriba.setTextureOffset(0, 186).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 16.0F, 10.0F, 0.0F, false);

		piernaderechaabajo = new ModelRenderer(this);
		piernaderechaabajo.setRotationPoint(0.0F, 16.0F, 0.0F);
		piernaderecha.addChild(piernaderechaabajo);
		piernaderechaabajo.setTextureOffset(0, 218).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 16.0F, 10.0F, 0.0F, false);
		piernaderechaabajo.setTextureOffset(0, 247).addBox(-5.0F, 12.0F, -10.0F, 10.0F, 4.0F, 5.0F, 0.0F, false);

		piernaizquierda = new ModelRenderer(this);
		piernaizquierda.setRotationPoint(7.0F, -8.0F, 0.0F);

		piernaizquierdaarriba = new ModelRenderer(this);
		piernaizquierdaarriba.setRotationPoint(0.0F, 0.0F, 0.0F);
		piernaizquierda.addChild(piernaizquierdaarriba);
		piernaizquierdaarriba.setTextureOffset(216, 186).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 16.0F, 10.0F, 0.0F, false);

		piernaizquierdaabajo = new ModelRenderer(this);
		piernaizquierdaabajo.setRotationPoint(0.0F, 16.0F, 0.0F);
		piernaizquierda.addChild(piernaizquierdaabajo);
		piernaizquierdaabajo.setTextureOffset(216, 217).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 16.0F, 10.0F, 0.0F, false);
		piernaizquierdaabajo.setTextureOffset(226, 247).addBox(-5.0F, 12.0F, -10.0F, 10.0F, 4.0F, 5.0F, 0.0F, false);

		torso = new ModelRenderer(this);
		torso.setRotationPoint(0.0F, -24.0F, 0.0F);
		torso.setTextureOffset(140, 214).addBox(-12.0F, -16.0F, -5.0F, 24.0F, 32.0F, 10.0F, 0.0F, false);
		torso.setTextureOffset(82, 232).addBox(-12.0F, -6.0F, -7.0F, 24.0F, 22.0F, 2.0F, 0.0F, false);
		torso.setTextureOffset(82, 207).addBox(-11.0F, -4.0F, -8.0F, 22.0F, 20.0F, 1.0F, 0.0F, false);
		torso.setTextureOffset(135, 186).addBox(-11.0F, -4.0F, 5.0F, 22.0F, 20.0F, 1.0F, 0.0F, false);

		brazoderecho = new ModelRenderer(this);
		brazoderecho.setRotationPoint(-12.0F, -35.0F, 0.0F);

		brazoderechoabajo = new ModelRenderer(this);
		brazoderechoabajo.setRotationPoint(-4.0F, 14.0F, 0.0F);
		brazoderecho.addChild(brazoderechoabajo);
		brazoderechoabajo.setTextureOffset(0, 152).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 18.0F, 8.0F, 0.0F, false);

		brazoderechoarriba = new ModelRenderer(this);
		brazoderechoarriba.setRotationPoint(0.0F, 0.0F, 0.0F);
		brazoderecho.addChild(brazoderechoarriba);
		brazoderechoarriba.setTextureOffset(0, 119).addBox(-8.0F, -4.0F, -4.0F, 8.0F, 18.0F, 8.0F, 0.0F, false);
		brazoderechoarriba.setTextureOffset(0, 90).addBox(-7.0F, -3.0F, -5.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);
		brazoderechoarriba.setTextureOffset(16, 94).addBox(-9.0F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F, 0.0F, false);
		brazoderechoarriba.setTextureOffset(0, 110).addBox(-7.0F, -5.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
		brazoderechoarriba.setTextureOffset(0, 100).addBox(-7.0F, -3.0F, 4.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);

		brazoizquierdo = new ModelRenderer(this);
		brazoizquierdo.setRotationPoint(12.0F, -35.0F, 0.0F);

		brazoizquierdoarriba = new ModelRenderer(this);
		brazoizquierdoarriba.setRotationPoint(0.0F, 0.0F, 0.0F);
		brazoizquierdo.addChild(brazoizquierdoarriba);
		brazoizquierdoarriba.setTextureOffset(224, 126).addBox(0.0F, -4.0F, -4.0F, 8.0F, 18.0F, 8.0F, 0.0F, false);
		brazoizquierdoarriba.setTextureOffset(242, 100).addBox(1.0F, -3.0F, -5.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);
		brazoizquierdoarriba.setTextureOffset(242, 111).addBox(8.0F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F, 0.0F, false);
		brazoizquierdoarriba.setTextureOffset(215, 114).addBox(1.0F, -5.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
		brazoizquierdoarriba.setTextureOffset(220, 100).addBox(1.0F, -3.0F, 4.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);

		brazoizquieroabajo = new ModelRenderer(this);
		brazoizquieroabajo.setRotationPoint(4.0F, 14.0F, 0.0F);
		brazoizquierdo.addChild(brazoizquieroabajo);
		brazoizquieroabajo.setTextureOffset(224, 156).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 18.0F, 8.0F, 0.0F, false);

		cabezaizquierda = new ModelRenderer(this);
		cabezaizquierda.setRotationPoint(7.0F, -40.0F, 0.0F);
		cabezaizquierda.setTextureOffset(208, 70).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, 0.0F, true);
		cabezaizquierda.setTextureOffset(242, 62).addBox(-3.0F, -10.0F, -7.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);

		ornamento = new ModelRenderer(this);
		ornamento.setRotationPoint(0.0F, 1.0F, -6.5F);
		cabezaizquierda.addChild(ornamento);
		ornamento.setTextureOffset(246, 50).addBox(-2.0F, 0.1F, -0.5F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		ornamento.setTextureOffset(252, 55).addBox(-0.5F, 1.1F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(2.7F, -0.3F, 0.0F);
		ornamento.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.0F, -0.7854F);
		cube_r1.setTextureOffset(248, 45).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-2.7F, -0.3F, 0.0F);
		ornamento.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, 0.7854F);
		cube_r2.setTextureOffset(248, 40).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		cabezaderecha = new ModelRenderer(this);
		cabezaderecha.setRotationPoint(-7.0F, -40.0F, 0.0F);
		cabezaderecha.setTextureOffset(0, 61).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, 0.0F, false);

		cejaizquierda_r1 = new ModelRenderer(this);
		cejaizquierda_r1.setRotationPoint(2.5F, -8.5F, -6.5F);
		cabezaderecha.addChild(cejaizquierda_r1);
		setRotationAngle(cejaizquierda_r1, 0.0F, 0.0F, -0.7854F);
		cejaizquierda_r1.setTextureOffset(11, 54).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		cejaderecha_r1 = new ModelRenderer(this);
		cejaderecha_r1.setRotationPoint(-2.5F, -8.5F, -6.5F);
		cabezaderecha.addChild(cejaderecha_r1);
		setRotationAngle(cejaderecha_r1, 0.0F, 0.0F, 0.7854F);
		cejaderecha_r1.setTextureOffset(0, 55).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		barbacabezaderecha = new ModelRenderer(this);
		barbacabezaderecha.setRotationPoint(0.0F, 0.0F, -7.0F);
		cabezaderecha.addChild(barbacabezaderecha);
		barbacabezaderecha.setTextureOffset(22, 54).addBox(-4.0F, -1.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		barbacabezaderecha.setTextureOffset(30, 51).addBox(-3.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		barbacabezaderecha.setTextureOffset(37, 50).addBox(-2.0F, -1.0F, 0.0F, 1.0F, 7.0F, 1.0F, 0.0F, false);
		barbacabezaderecha.setTextureOffset(7, 41).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 9.0F, 1.0F, 0.0F, false);
		barbacabezaderecha.setTextureOffset(0, 42).addBox(0.0F, -1.0F, 0.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		barbacabezaderecha.setTextureOffset(14, 39).addBox(1.0F, -1.0F, 0.0F, 1.0F, 10.0F, 1.0F, 0.0F, false);
		barbacabezaderecha.setTextureOffset(21, 41).addBox(2.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		barbacabezaderecha.setTextureOffset(29, 43).addBox(3.0F, -1.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setLivingAnimations(PrumTrakEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		this.cabezaizquierda.rotateAngleX = entityIn.getSegundaCabezaPitch(partialTick);
		this.cabezaizquierda.rotateAngleY = entityIn.getSegundaCabezaYaw(partialTick);
	}

	@Override
	public void setRotationAngles(PrumTrakEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		this.cabezaderecha.rotateAngleX = headPitch * (float) Math.PI / 180f;
		this.cabezaderecha.rotateAngleY = netHeadYaw * (float) Math.PI / 180f;
	
		this.torso.rotateAngleY = 0F;

		this.brazoderecho.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
		this.brazoderecho.rotateAngleY = 0F;
		this.brazoderecho.rotateAngleZ = 0F;
		
		this.brazoizquierdo.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
		this.brazoizquierdo.rotateAngleY = 0F;
		this.brazoizquierdo.rotateAngleZ = 0F;

		this.brazoizquieroabajo.rotateAngleX = 0F;
		this.brazoizquieroabajo.rotateAngleY = 0F;
		this.brazoizquieroabajo.rotateAngleZ = 0F;
		
		this.piernaderecha.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.piernaderecha.rotateAngleY = 0F;
		this.piernaderecha.rotateAngleZ = 0F;
		
		this.piernaizquierda.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.piernaizquierda.rotateAngleY = 0F;
		this.piernaizquierda.rotateAngleZ = 0F;
		
		if(!(this.swingProgress <= 0F)) {
			float f = this.swingProgress;
			this.torso.rotateAngleY = (float) Math.toRadians(15F * f);
			this.brazoizquierdo.rotateAngleX = (float) Math.toRadians(-90F * f);
			this.brazoizquieroabajo.rotateAngleZ = (float) Math.toRadians(45F * f);
		}
		
		if(!(this.range <= 0F)) {
			this.brazoderecho.rotateAngleX = -80F * (1 - MathHelper.sqrt(this.range)) * (float) Math.PI / 180f;
			this.brazoderecho.rotateAngleY = -10F * this.range * (float) Math.PI / 180f;
			this.brazoderecho.rotateAngleZ = -10F * this.range * (float) Math.PI / 180f;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
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
		torso.render(matrixStack, buffer, packedLight, packedOverlay);
		brazoderecho.render(matrixStack, buffer, packedLight, packedOverlay);
		brazoizquierdo.render(matrixStack, buffer, packedLight, packedOverlay);
		cabezaizquierda.render(matrixStack, buffer, packedLight, packedOverlay);
		cabezaderecha.render(matrixStack, buffer, packedLight, packedOverlay);
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
