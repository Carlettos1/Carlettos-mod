package com.carlettos.mod.entidades.aman.amanspider;

import com.carlettos.mod.listas.ListaAtributos;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

//Made with Blockbench 3.7.5
public class AmanSpiderModel extends EntityModel<AmanSpiderEntity> {
	public float spit;
	public float hatch;
	
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
	private final ModelRenderer madibula_izuiquierda_2_r1;
	private final ModelRenderer mandibula_izquierda_1_r1;
	private final ModelRenderer mandibula_derecha;
	private final ModelRenderer mandibula_derecha_2_r1;
	private final ModelRenderer mandibula_derecha_1_r1;
	private final ModelRenderer cuello;
	private final ModelRenderer body;
	private final ModelRenderer cuerpo_arriba;
	private final ModelRenderer cuerpo_abajo;
	private final ModelRenderer sarcofago;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer huevos;
	private final ModelRenderer huevo1;
	private final ModelRenderer huevo2;
	private final ModelRenderer huevo3;
	private final ModelRenderer huevo4;
	private final ModelRenderer huevo5;
	private final ModelRenderer huevo6;
	private final ModelRenderer huevo7;
	private final ModelRenderer huevo8;
	private final ModelRenderer huevo9;
	private final ModelRenderer huevo10;
	private final ModelRenderer[] huevoslst;
	private final ModelRenderer[] patas;
	private final float[] angulospatasx;
	private final float[] angulospatasy;
	private final float[] angulospatasz;

	public AmanSpiderModel() {
		textureWidth = 128;
		textureHeight = 128;

		pata1 = new ModelRenderer(this);
		pata1.setRotationPoint(-3.0F, 14.0F, -3.0F);
		setRotationAngle(pata1, 0.7854F, -0.7854F, -0.8727F);
		

		pata1_1_r1 = new ModelRenderer(this);
		pata1_1_r1.setRotationPoint(-3.0F, -5.0F, 0.0F);
		pata1.addChild(pata1_1_r1);
		setRotationAngle(pata1_1_r1, 0.0F, 0.0F, -0.3927F);
		pata1_1_r1.setTextureOffset(40, 50).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata1_2 = new ModelRenderer(this);
		pata1_2.setRotationPoint(-5.0F, -10.0F, 0.0F);
		pata1.addChild(pata1_2);
		setRotationAngle(pata1_2, 0.0F, 0.0F, 0.7854F);
		

		pata1_2_r1 = new ModelRenderer(this);
		pata1_2_r1.setRotationPoint(-4.0F, 4.0F, 0.0F);
		pata1_2.addChild(pata1_2_r1);
		setRotationAngle(pata1_2_r1, 0.0F, 0.0F, 0.7854F);
		pata1_2_r1.setTextureOffset(40, 36).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata1_3 = new ModelRenderer(this);
		pata1_3.setRotationPoint(-8.0F, 8.0F, 0.0F);
		pata1_2.addChild(pata1_3);
		setRotationAngle(pata1_3, 0.0F, 0.0F, 0.5236F);
		pata1_3.setTextureOffset(40, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata2 = new ModelRenderer(this);
		pata2.setRotationPoint(3.0F, 14.0F, -3.0F);
		setRotationAngle(pata2, 0.7854F, 0.7854F, 0.8727F);
		

		pata2_1_r1 = new ModelRenderer(this);
		pata2_1_r1.setRotationPoint(3.0F, -5.0F, 0.0F);
		pata2.addChild(pata2_1_r1);
		setRotationAngle(pata2_1_r1, 0.0F, 0.0F, 0.3927F);
		pata2_1_r1.setTextureOffset(8, 50).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata2_2 = new ModelRenderer(this);
		pata2_2.setRotationPoint(5.0F, -10.0F, 0.0F);
		pata2.addChild(pata2_2);
		setRotationAngle(pata2_2, 0.0F, 0.0F, -0.7854F);
		

		pata2_2_r1 = new ModelRenderer(this);
		pata2_2_r1.setRotationPoint(4.0F, 4.0F, 0.0F);
		pata2_2.addChild(pata2_2_r1);
		setRotationAngle(pata2_2_r1, 0.0F, 0.0F, -0.7854F);
		pata2_2_r1.setTextureOffset(8, 36).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata2_3 = new ModelRenderer(this);
		pata2_3.setRotationPoint(8.0F, 8.0F, 0.0F);
		pata2_2.addChild(pata2_3);
		setRotationAngle(pata2_3, 0.0F, 0.0F, -0.5236F);
		pata2_3.setTextureOffset(8, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata3 = new ModelRenderer(this);
		pata3.setRotationPoint(-2.0F, 14.0F, -2.0F);
		setRotationAngle(pata3, 0.1745F, -0.2618F, -0.3491F);
		

		pata3_1_r1 = new ModelRenderer(this);
		pata3_1_r1.setRotationPoint(-3.0F, -5.0F, 0.0F);
		pata3.addChild(pata3_1_r1);
		setRotationAngle(pata3_1_r1, 0.0F, 0.0F, -0.3927F);
		pata3_1_r1.setTextureOffset(40, 50).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata3_2 = new ModelRenderer(this);
		pata3_2.setRotationPoint(-5.0F, -10.0F, 0.0F);
		pata3.addChild(pata3_2);
		setRotationAngle(pata3_2, 0.0F, 0.0F, 0.4363F);
		

		pata3_2_r1 = new ModelRenderer(this);
		pata3_2_r1.setRotationPoint(-4.0F, 4.0F, 0.0F);
		pata3_2.addChild(pata3_2_r1);
		setRotationAngle(pata3_2_r1, 0.0F, 0.0F, 0.7854F);
		pata3_2_r1.setTextureOffset(40, 36).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata3_3 = new ModelRenderer(this);
		pata3_3.setRotationPoint(-8.0F, 8.0F, 0.0F);
		pata3_2.addChild(pata3_3);
		setRotationAngle(pata3_3, 0.0F, 0.0F, 0.3491F);
		pata3_3.setTextureOffset(40, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata4 = new ModelRenderer(this);
		pata4.setRotationPoint(2.0F, 14.0F, -2.0F);
		setRotationAngle(pata4, 0.1745F, 0.2618F, 0.3491F);
		

		pata4_1_r1 = new ModelRenderer(this);
		pata4_1_r1.setRotationPoint(3.0F, -5.0F, 0.0F);
		pata4.addChild(pata4_1_r1);
		setRotationAngle(pata4_1_r1, 0.0F, 0.0F, 0.3927F);
		pata4_1_r1.setTextureOffset(8, 50).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata4_2 = new ModelRenderer(this);
		pata4_2.setRotationPoint(5.0F, -10.0F, 0.0F);
		pata4.addChild(pata4_2);
		setRotationAngle(pata4_2, 0.0F, 0.0F, -0.4363F);
		

		pata4_2_r1 = new ModelRenderer(this);
		pata4_2_r1.setRotationPoint(4.0F, 4.0F, 0.0F);
		pata4_2.addChild(pata4_2_r1);
		setRotationAngle(pata4_2_r1, 0.0F, 0.0F, -0.7854F);
		pata4_2_r1.setTextureOffset(8, 36).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata4_3 = new ModelRenderer(this);
		pata4_3.setRotationPoint(8.0F, 8.0F, 0.0F);
		pata4_2.addChild(pata4_3);
		setRotationAngle(pata4_3, 0.0F, 0.0F, -0.3491F);
		pata4_3.setTextureOffset(8, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata5 = new ModelRenderer(this);
		pata5.setRotationPoint(-2.0F, 14.0F, -1.0F);
		setRotationAngle(pata5, -0.1745F, 0.2618F, -0.3491F);
		

		pata5_1_r1 = new ModelRenderer(this);
		pata5_1_r1.setRotationPoint(-3.0F, -5.0F, 0.0F);
		pata5.addChild(pata5_1_r1);
		setRotationAngle(pata5_1_r1, 0.0F, 0.0F, -0.3927F);
		pata5_1_r1.setTextureOffset(40, 50).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata5_2 = new ModelRenderer(this);
		pata5_2.setRotationPoint(-5.0F, -10.0F, 0.0F);
		pata5.addChild(pata5_2);
		setRotationAngle(pata5_2, 0.0F, 0.0F, 0.4363F);
		

		pata5_2_r1 = new ModelRenderer(this);
		pata5_2_r1.setRotationPoint(-4.0F, 4.0F, 0.0F);
		pata5_2.addChild(pata5_2_r1);
		setRotationAngle(pata5_2_r1, 0.0F, 0.0F, 0.7854F);
		pata5_2_r1.setTextureOffset(40, 36).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata5_3 = new ModelRenderer(this);
		pata5_3.setRotationPoint(-8.0F, 8.0F, 0.0F);
		pata5_2.addChild(pata5_3);
		setRotationAngle(pata5_3, 0.0F, 0.0F, 0.3491F);
		pata5_3.setTextureOffset(40, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata6 = new ModelRenderer(this);
		pata6.setRotationPoint(2.0F, 14.0F, -1.0F);
		setRotationAngle(pata6, -0.1745F, -0.2618F, 0.3491F);
		

		pata6_1_r1 = new ModelRenderer(this);
		pata6_1_r1.setRotationPoint(3.0F, -5.0F, 0.0F);
		pata6.addChild(pata6_1_r1);
		setRotationAngle(pata6_1_r1, 0.0F, 0.0F, 0.3927F);
		pata6_1_r1.setTextureOffset(8, 50).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata6_2 = new ModelRenderer(this);
		pata6_2.setRotationPoint(5.0F, -10.0F, 0.0F);
		pata6.addChild(pata6_2);
		setRotationAngle(pata6_2, 0.0F, 0.0F, -0.4363F);
		

		pata6_2_r1 = new ModelRenderer(this);
		pata6_2_r1.setRotationPoint(4.0F, 4.0F, 0.0F);
		pata6_2.addChild(pata6_2_r1);
		setRotationAngle(pata6_2_r1, 0.0F, 0.0F, -0.7854F);
		pata6_2_r1.setTextureOffset(8, 36).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata6_3 = new ModelRenderer(this);
		pata6_3.setRotationPoint(8.0F, 8.0F, 0.0F);
		pata6_2.addChild(pata6_3);
		setRotationAngle(pata6_3, 0.0F, 0.0F, -0.3491F);
		pata6_3.setTextureOffset(8, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata7 = new ModelRenderer(this);
		pata7.setRotationPoint(-3.0F, 14.0F, 0.0F);
		setRotationAngle(pata7, -0.7854F, 0.7854F, -0.8727F);
		

		pata7_1_r1 = new ModelRenderer(this);
		pata7_1_r1.setRotationPoint(-3.0F, -5.0F, 0.0F);
		pata7.addChild(pata7_1_r1);
		setRotationAngle(pata7_1_r1, 0.0F, 0.0F, -0.3927F);
		pata7_1_r1.setTextureOffset(40, 50).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata7_2 = new ModelRenderer(this);
		pata7_2.setRotationPoint(-5.0F, -10.0F, 0.0F);
		pata7.addChild(pata7_2);
		setRotationAngle(pata7_2, 0.0F, 0.0F, 0.7854F);
		

		pata7_2_r1 = new ModelRenderer(this);
		pata7_2_r1.setRotationPoint(-4.0F, 4.0F, 0.0F);
		pata7_2.addChild(pata7_2_r1);
		setRotationAngle(pata7_2_r1, 0.0F, 0.0F, 0.7854F);
		pata7_2_r1.setTextureOffset(40, 36).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata7_3 = new ModelRenderer(this);
		pata7_3.setRotationPoint(-8.0F, 8.0F, 0.0F);
		pata7_2.addChild(pata7_3);
		setRotationAngle(pata7_3, 0.0F, 0.0F, 0.5236F);
		pata7_3.setTextureOffset(40, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata8 = new ModelRenderer(this);
		pata8.setRotationPoint(3.0F, 14.0F, 0.0F);
		setRotationAngle(pata8, -0.7854F, -0.7854F, 0.8727F);
		

		pata8_1_r1 = new ModelRenderer(this);
		pata8_1_r1.setRotationPoint(3.0F, -5.0F, 0.0F);
		pata8.addChild(pata8_1_r1);
		setRotationAngle(pata8_1_r1, 0.0F, 0.0F, 0.3927F);
		pata8_1_r1.setTextureOffset(8, 50).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata8_2 = new ModelRenderer(this);
		pata8_2.setRotationPoint(5.0F, -10.0F, 0.0F);
		pata8.addChild(pata8_2);
		setRotationAngle(pata8_2, 0.0F, 0.0F, -0.7854F);
		

		pata8_2_r1 = new ModelRenderer(this);
		pata8_2_r1.setRotationPoint(4.0F, 4.0F, 0.0F);
		pata8_2.addChild(pata8_2_r1);
		setRotationAngle(pata8_2_r1, 0.0F, 0.0F, -0.7854F);
		pata8_2_r1.setTextureOffset(8, 36).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		pata8_3 = new ModelRenderer(this);
		pata8_3.setRotationPoint(8.0F, 8.0F, 0.0F);
		pata8_2.addChild(pata8_3);
		setRotationAngle(pata8_3, 0.0F, 0.0F, -0.5236F);
		pata8_3.setTextureOffset(8, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		cuerpo = new ModelRenderer(this);
		cuerpo.setRotationPoint(0.0F, 19.0F, 0.0F);
		

		cabeza = new ModelRenderer(this);
		cabeza.setRotationPoint(0.0F, -8.0F, -4.0F);
		cuerpo.addChild(cabeza);
		cabeza.setTextureOffset(94, 74).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		mandibula = new ModelRenderer(this);
		mandibula.setRotationPoint(0.0F, 8.5F, 4.0F);
		cabeza.addChild(mandibula);
		

		mandibula_izquierda = new ModelRenderer(this);
		mandibula_izquierda.setRotationPoint(-1.5F, -6.5F, -12.0F);
		mandibula.addChild(mandibula_izquierda);
		

		madibula_izuiquierda_2_r1 = new ModelRenderer(this);
		madibula_izuiquierda_2_r1.setRotationPoint(-0.75F, 0.0F, -2.0F);
		mandibula_izquierda.addChild(madibula_izuiquierda_2_r1);
		setRotationAngle(madibula_izuiquierda_2_r1, 0.0F, -0.3491F, 0.0F);
		madibula_izuiquierda_2_r1.setTextureOffset(0, 0).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		mandibula_izquierda_1_r1 = new ModelRenderer(this);
		mandibula_izquierda_1_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		mandibula_izquierda.addChild(mandibula_izquierda_1_r1);
		setRotationAngle(mandibula_izquierda_1_r1, 0.0F, 0.3491F, 0.0F);
		mandibula_izquierda_1_r1.setTextureOffset(0, 0).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		mandibula_derecha = new ModelRenderer(this);
		mandibula_derecha.setRotationPoint(1.5F, -6.5F, -12.0F);
		mandibula.addChild(mandibula_derecha);
		

		mandibula_derecha_2_r1 = new ModelRenderer(this);
		mandibula_derecha_2_r1.setRotationPoint(0.75F, 0.0F, -2.0F);
		mandibula_derecha.addChild(mandibula_derecha_2_r1);
		setRotationAngle(mandibula_derecha_2_r1, 0.0F, 0.3491F, 0.0F);
		mandibula_derecha_2_r1.setTextureOffset(0, 0).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		mandibula_derecha_1_r1 = new ModelRenderer(this);
		mandibula_derecha_1_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		mandibula_derecha.addChild(mandibula_derecha_1_r1);
		setRotationAngle(mandibula_derecha_1_r1, 0.0F, -0.3491F, 0.0F);
		mandibula_derecha_1_r1.setTextureOffset(0, 0).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		cuello = new ModelRenderer(this);
		cuello.setRotationPoint(0.0F, -8.0F, -1.5F);
		cuerpo.addChild(cuello);
		cuello.setTextureOffset(106, 93).addBox(-3.0F, -3.0F, -2.5F, 6.0F, 6.0F, 5.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -8.0F, 1.0F);
		cuerpo.addChild(body);
		

		cuerpo_arriba = new ModelRenderer(this);
		cuerpo_arriba.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(cuerpo_arriba);
		cuerpo_arriba.setTextureOffset(82, 106).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 1.0F, 12.0F, 0.0F, false);
		cuerpo_arriba.setTextureOffset(0, 0).addBox(-5.0F, -3.0F, 0.0F, 10.0F, 3.0F, 1.0F, 0.0F, false);
		cuerpo_arriba.setTextureOffset(0, 0).addBox(-5.0F, -3.0F, 1.0F, 1.0F, 3.0F, 11.0F, 0.0F, false);
		cuerpo_arriba.setTextureOffset(0, 0).addBox(-4.0F, -3.0F, 11.0F, 9.0F, 3.0F, 1.0F, 0.0F, false);
		cuerpo_arriba.setTextureOffset(0, 0).addBox(4.0F, -3.0F, 1.0F, 1.0F, 3.0F, 10.0F, 0.0F, false);

		cuerpo_abajo = new ModelRenderer(this);
		cuerpo_abajo.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(cuerpo_abajo);
		

		sarcofago = new ModelRenderer(this);
		sarcofago.setRotationPoint(0.0F, 2.0F, 6.0F);
		cuerpo_abajo.addChild(sarcofago);
		sarcofago.setTextureOffset(0, 0).addBox(-5.0F, -2.0F, 5.0F, 10.0F, 4.0F, 1.0F, 0.0F, false);
		sarcofago.setTextureOffset(0, 0).addBox(-5.0F, -2.0F, -6.0F, 10.0F, 4.0F, 1.0F, 0.0F, false);
		sarcofago.setTextureOffset(0, 0).addBox(-5.0F, -2.0F, -5.0F, 1.0F, 4.0F, 10.0F, 0.0F, false);
		sarcofago.setTextureOffset(0, 0).addBox(4.0F, -2.0F, -5.0F, 1.0F, 4.0F, 10.0F, 0.0F, false);
		sarcofago.setTextureOffset(0, 0).addBox(-4.0F, 1.0F, -5.0F, 8.0F, 1.0F, 10.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 1.1F, 2.5F);
		sarcofago.addChild(cube_r1);
		setRotationAngle(cube_r1, -1.2217F, 0.0F, 0.0F);
		cube_r1.setTextureOffset(0, 0).addBox(-4.0F, -1.0F, -0.5F, 8.0F, 2.0F, 1.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, 1.1F, -2.5F);
		sarcofago.addChild(cube_r2);
		setRotationAngle(cube_r2, 1.2217F, 0.0F, 0.0F);
		cube_r2.setTextureOffset(0, 0).addBox(-4.0F, -1.0F, -0.5F, 8.0F, 2.0F, 1.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, -0.25F, 3.95F);
		sarcofago.addChild(cube_r3);
		setRotationAngle(cube_r3, -0.7854F, 0.0F, 0.0F);
		cube_r3.setTextureOffset(0, 0).addBox(-4.0F, -2.0F, -0.5F, 8.0F, 4.0F, 1.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, -0.25F, -3.95F);
		sarcofago.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.7854F, 0.0F, 0.0F);
		cube_r4.setTextureOffset(0, 0).addBox(-4.0F, -2.0F, -0.5F, 8.0F, 4.0F, 1.0F, 0.0F, false);

		huevos = new ModelRenderer(this);
		huevos.setRotationPoint(0.0F, -4.0F, -12.0F);
		cuerpo_abajo.addChild(huevos);
		

		huevo1 = new ModelRenderer(this);
		huevo1.setRotationPoint(0.1F, 4.475F, 14.85F);
		huevos.addChild(huevo1);
		setRotationAngle(huevo1, -0.7418F, 0.2182F, -0.2618F);
		huevo1.setTextureOffset(4, 111).addBox(-1.0F, -0.125F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		huevo1.setTextureOffset(4, 111).addBox(-1.0F, -0.875F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo1.setTextureOffset(4, 111).addBox(-1.0F, 0.125F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo1.setTextureOffset(4, 111).addBox(-0.5F, -0.875F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo1.setTextureOffset(4, 111).addBox(-0.5F, 0.125F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo1.setTextureOffset(4, 111).addBox(-0.5F, -1.375F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		huevo2 = new ModelRenderer(this);
		huevo2.setRotationPoint(2.6F, 4.975F, 15.45F);
		huevos.addChild(huevo2);
		setRotationAngle(huevo2, -0.7418F, -0.3054F, 0.3054F);
		huevo2.setTextureOffset(4, 111).addBox(-1.0F, -0.125F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		huevo2.setTextureOffset(4, 111).addBox(-1.0F, -0.875F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo2.setTextureOffset(4, 111).addBox(-1.0F, 0.125F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo2.setTextureOffset(4, 111).addBox(-0.5F, -0.875F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo2.setTextureOffset(4, 111).addBox(-0.5F, 0.125F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo2.setTextureOffset(4, 111).addBox(-0.5F, -1.375F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		huevo3 = new ModelRenderer(this);
		huevo3.setRotationPoint(-3.2F, 6.325F, 18.9F);
		huevos.addChild(huevo3);
		setRotationAngle(huevo3, 0.2618F, -0.5236F, 0.4363F);
		huevo3.setTextureOffset(4, 111).addBox(-1.0F, -0.125F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		huevo3.setTextureOffset(4, 111).addBox(-1.0F, -0.875F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo3.setTextureOffset(4, 111).addBox(-1.0F, 0.125F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo3.setTextureOffset(4, 111).addBox(-0.5F, -0.875F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo3.setTextureOffset(4, 111).addBox(-0.5F, 0.125F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo3.setTextureOffset(4, 111).addBox(-0.5F, -1.375F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		huevo4 = new ModelRenderer(this);
		huevo4.setRotationPoint(-2.0F, 5.875F, 16.4F);
		huevos.addChild(huevo4);
		setRotationAngle(huevo4, -0.3054F, 0.3054F, 0.0436F);
		huevo4.setTextureOffset(4, 111).addBox(-1.0F, -0.125F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		huevo4.setTextureOffset(4, 111).addBox(-1.0F, -0.875F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo4.setTextureOffset(4, 111).addBox(-1.0F, 0.125F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo4.setTextureOffset(4, 111).addBox(-0.5F, -0.875F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo4.setTextureOffset(4, 111).addBox(-0.5F, 0.125F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo4.setTextureOffset(4, 111).addBox(-0.5F, -1.375F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		huevo5 = new ModelRenderer(this);
		huevo5.setRotationPoint(2.3F, 4.575F, 21.15F);
		huevos.addChild(huevo5);
		setRotationAngle(huevo5, 0.9163F, 0.2618F, 0.0F);
		huevo5.setTextureOffset(4, 111).addBox(-1.0F, -0.125F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		huevo5.setTextureOffset(4, 111).addBox(-1.0F, -0.875F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo5.setTextureOffset(4, 111).addBox(-1.0F, 0.125F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo5.setTextureOffset(4, 111).addBox(-0.5F, -0.875F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo5.setTextureOffset(4, 111).addBox(-0.5F, 0.125F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo5.setTextureOffset(4, 111).addBox(-0.5F, -1.375F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		huevo6 = new ModelRenderer(this);
		huevo6.setRotationPoint(-2.15F, 4.275F, 21.4F);
		huevos.addChild(huevo6);
		setRotationAngle(huevo6, 0.6981F, -0.2182F, 0.0F);
		huevo6.setTextureOffset(4, 111).addBox(-1.0F, -0.125F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		huevo6.setTextureOffset(4, 111).addBox(-1.0F, -0.875F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo6.setTextureOffset(4, 111).addBox(-1.0F, 0.125F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo6.setTextureOffset(4, 111).addBox(-0.5F, -0.875F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo6.setTextureOffset(4, 111).addBox(-0.5F, 0.125F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo6.setTextureOffset(4, 111).addBox(-0.5F, -1.375F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		huevo7 = new ModelRenderer(this);
		huevo7.setRotationPoint(0.45F, 5.975F, 17.2F);
		huevos.addChild(huevo7);
		setRotationAngle(huevo7, -0.1309F, -0.6545F, 0.0873F);
		huevo7.setTextureOffset(4, 111).addBox(-1.0F, -0.125F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		huevo7.setTextureOffset(4, 111).addBox(-1.0F, -0.875F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo7.setTextureOffset(4, 111).addBox(-1.0F, 0.125F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo7.setTextureOffset(4, 111).addBox(-0.5F, -0.875F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo7.setTextureOffset(4, 111).addBox(-0.5F, 0.125F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo7.setTextureOffset(4, 111).addBox(-0.5F, -1.375F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		huevo8 = new ModelRenderer(this);
		huevo8.setRotationPoint(-2.9F, 4.175F, 14.85F);
		huevos.addChild(huevo8);
		setRotationAngle(huevo8, -1.0036F, -0.6109F, 0.829F);
		huevo8.setTextureOffset(4, 111).addBox(-1.0F, -0.125F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		huevo8.setTextureOffset(4, 111).addBox(-1.0F, -0.875F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo8.setTextureOffset(4, 111).addBox(-1.0F, 0.125F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo8.setTextureOffset(4, 111).addBox(-0.5F, -0.875F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo8.setTextureOffset(4, 111).addBox(-0.5F, 0.125F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo8.setTextureOffset(4, 111).addBox(-0.5F, -1.375F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		huevo9 = new ModelRenderer(this);
		huevo9.setRotationPoint(3.25F, 6.175F, 17.75F);
		huevos.addChild(huevo9);
		setRotationAngle(huevo9, 0.1745F, 0.5236F, -0.829F);
		huevo9.setTextureOffset(4, 111).addBox(-1.0F, -0.125F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		huevo9.setTextureOffset(4, 111).addBox(-1.0F, -0.875F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo9.setTextureOffset(4, 111).addBox(-1.0F, 0.125F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo9.setTextureOffset(4, 111).addBox(-0.5F, -0.875F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo9.setTextureOffset(4, 111).addBox(-0.5F, 0.125F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo9.setTextureOffset(4, 111).addBox(-0.5F, -1.375F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		huevo10 = new ModelRenderer(this);
		huevo10.setRotationPoint(0.5F, 5.775F, 19.75F);
		huevos.addChild(huevo10);
		setRotationAngle(huevo10, 0.5236F, -0.2182F, 0.0F);
		huevo10.setTextureOffset(4, 111).addBox(-1.0F, -0.125F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		huevo10.setTextureOffset(4, 111).addBox(-1.0F, -0.875F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo10.setTextureOffset(4, 111).addBox(-1.0F, 0.125F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		huevo10.setTextureOffset(4, 111).addBox(-0.5F, -0.875F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo10.setTextureOffset(4, 111).addBox(-0.5F, 0.125F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		huevo10.setTextureOffset(4, 111).addBox(-0.5F, -1.375F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		huevoslst = new ModelRenderer[]{huevo1, huevo2, huevo3, huevo4, huevo5, huevo6, huevo7, huevo8, huevo9, huevo10};
		patas = new ModelRenderer[]{pata1, pata2, pata3, pata4, pata5, pata6, pata7, pata8};
		angulospatasx = new float[] {pata1.rotateAngleX, pata2.rotateAngleX, pata3.rotateAngleX, pata4.rotateAngleX, pata5.rotateAngleX, pata6.rotateAngleX, pata7.rotateAngleX, pata8.rotateAngleX};
		angulospatasy = new float[] {pata1.rotateAngleY, pata2.rotateAngleY, pata3.rotateAngleY, pata4.rotateAngleY, pata5.rotateAngleY, pata6.rotateAngleY, pata7.rotateAngleY, pata8.rotateAngleY};
		angulospatasz = new float[] {pata1.rotateAngleZ, pata2.rotateAngleZ, pata3.rotateAngleZ, pata4.rotateAngleZ, pata5.rotateAngleZ, pata6.rotateAngleZ, pata7.rotateAngleZ, pata8.rotateAngleZ};
	}

	@Override
	public void setRotationAngles(AmanSpiderEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		this.cabeza.rotateAngleX = headPitch * (float)Math.PI / 180F;
		this.cabeza.rotateAngleY = netHeadYaw * (float)Math.PI / 180F;
		
		for (int i = 0; i < this.huevoslst.length; i++) {
			ModelRenderer webo = this.huevoslst[i];
			if(i < entity.getAttributeValue(ListaAtributos.AMAN_EGG_COUNT)) {
				webo.showModel = true;
			} else {
				webo.showModel = false;
			}
		}
		
		for (int i = 0; i < angulospatasx.length; i++) {
			 patas[i].rotateAngleX = angulospatasx[i];
			 patas[i].rotateAngleY = angulospatasy[i];
			 patas[i].rotateAngleZ = angulospatasz[i];
		}

		this.pata1.rotateAngleX += this.getAngulo(limbSwing, limbSwingAmount, -15F, 0F);
		this.pata1.rotateAngleY += this.getAngulo(limbSwing, limbSwingAmount, 15F, 0F);
		this.pata1.rotateAngleZ += this.getAngulo(limbSwing, limbSwingAmount, 10F, 0F);
		this.pata2.rotateAngleX += -this.getAngulo(limbSwing, limbSwingAmount, -15F, 0F);
		this.pata2.rotateAngleY += -this.getAngulo(limbSwing, limbSwingAmount, -15F, 0F);
		this.pata2.rotateAngleZ += -this.getAngulo(limbSwing, limbSwingAmount, 10F, 0F);
		
		this.pata3.rotateAngleX += this.getAngulo(limbSwing, limbSwingAmount, -12.5F, (float)Math.PI);
		this.pata3.rotateAngleY += this.getAngulo(limbSwing, limbSwingAmount, 25F, (float)Math.PI);
		this.pata3.rotateAngleZ += 0;
		this.pata4.rotateAngleX += -this.getAngulo(limbSwing, limbSwingAmount, -12.5F, (float)Math.PI);
		this.pata4.rotateAngleY += -this.getAngulo(limbSwing, limbSwingAmount, -25F, (float)Math.PI);
		this.pata4.rotateAngleZ += 0;
		
		this.pata5.rotateAngleX += this.getAngulo(limbSwing, limbSwingAmount, -10F, (float)Math.PI /2F);
		this.pata5.rotateAngleY += this.getAngulo(limbSwing, limbSwingAmount, 30F, (float)Math.PI /2F);
		this.pata5.rotateAngleZ += this.getAngulo(limbSwing, limbSwingAmount, -10F, (float)Math.PI /2F);
		this.pata6.rotateAngleX += -this.getAngulo(limbSwing, limbSwingAmount, -10F, (float)Math.PI /2F);
		this.pata6.rotateAngleY += -this.getAngulo(limbSwing, limbSwingAmount, -30F, (float)Math.PI /2F);
		this.pata6.rotateAngleZ += -this.getAngulo(limbSwing, limbSwingAmount, -10F, (float)Math.PI /2F);
		
		this.pata7.rotateAngleX += this.getAngulo(limbSwing, limbSwingAmount, -10F, (float)Math.PI * 1.5F);
		this.pata7.rotateAngleY += this.getAngulo(limbSwing, limbSwingAmount, 10F, (float)Math.PI * 1.5F);
		this.pata7.rotateAngleZ += this.getAngulo(limbSwing, limbSwingAmount, -10F, (float)Math.PI * 1.5F);
		this.pata8.rotateAngleX += -this.getAngulo(limbSwing, limbSwingAmount, -10F, (float)Math.PI * 1.5F);
		this.pata8.rotateAngleY += -this.getAngulo(limbSwing, limbSwingAmount, -10F, (float)Math.PI * 1.5F);
		this.pata8.rotateAngleZ += -this.getAngulo(limbSwing, limbSwingAmount, -10F, (float)Math.PI * 1.5F);
		
		this.mandibula_derecha.rotateAngleY = 0;
		this.mandibula_izquierda.rotateAngleY = 0;
		this.cuerpo_arriba.rotateAngleX = 0;
		this.cuerpo_abajo.rotateAngleX = 0;
		
		if(this.spit > 0) {
			float angulo = MathHelper.sin(MathHelper.sqrt(this.spit) * (float)Math.PI) * (float)Math.PI / 4F;
			this.mandibula_derecha.rotateAngleY = angulo;
			this.mandibula_izquierda.rotateAngleY = -angulo;
		}

		if(this.hatch > 0) {
			float angulo = MathHelper.sin(MathHelper.sqrt(this.hatch) * (float)Math.PI) * (float)Math.PI / 8F;
			this.cuerpo_arriba.rotateAngleX = angulo;
			this.cuerpo_abajo.rotateAngleX = -angulo;
		}
	}
	
	public float getAngulo(float limbSwing, float limbSwingAmount, float angulo, float desfase) {
		return (float) Math.toRadians(MathHelper.cos(limbSwing * 0.6662F * 2F + desfase) * limbSwingAmount * angulo);
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
