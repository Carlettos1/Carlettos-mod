package com.carlettos.mod.entidades.prumytrak;

import java.util.Set;
import java.util.function.Predicate;

import com.carlettos.mod.entidades.prumytrak.ia.PrumTrakBodyController;
import com.carlettos.mod.entidades.prumytrak.ia.PrumTrakLookAtGoal;
import com.carlettos.mod.entidades.prumytrak.ia.PrumTrakLookController;
import com.carlettos.mod.entidades.prumytrak.ia.PrumTrakLookRandomlyGoal;
import com.carlettos.mod.entidades.prumytrak.ia.PrumTrakNearestAttackableTargetGoal;
import com.carlettos.mod.entidades.prumytrak.proyectil.PrumProyectilItem;
import com.carlettos.mod.listas.ListaEntidades;
import com.carlettos.mod.listas.ListaItem;

import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

/* 
 * Prum es el que ataca a distancia, el de su lado derecho
 * Trak es el que ataca a melé, el de su lado izquierdo
 */
public class PrumTrakEntity extends MonsterEntity implements IRangedAttackMob{
	public static final Set<Item> RUNAS_PRUM = Set.of(ListaItem.runa_prum, ListaItem.runa_rudu, ListaItem.runa_unk, ListaItem.runa_mih);
	public static final Set<Item> RUNAS_TRAK = Set.of(ListaItem.runa_trak, ListaItem.runa_rudu, ListaItem.runa_aman, ListaItem.runa_kel);
	public static final Set<Item> RUNAS_PRUM_Y_TRAK = Set.of(ListaItem.runa_prum, ListaItem.runa_rudu, ListaItem.runa_unk, ListaItem.runa_mih, ListaItem.runa_trak, ListaItem.runa_aman, ListaItem.runa_kel);
	
	public static final Predicate<LivingEntity> TARGET_RUNE_PLAYER = (entidad) -> {
		if(!(entidad instanceof PlayerEntity)) {
			return false;
		}
		PlayerEntity player = (PlayerEntity) entidad;
		return player.inventory.hasAny(RUNAS_PRUM_Y_TRAK);
	};
	
	public static final DataParameter<Float> RENDER_YAW_OFFSET_CABEZA2 = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.FLOAT);
	public static final DataParameter<Float> PREV_RENDER_YAW_OFFSET_CABEZA2 = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.FLOAT);
	public static final DataParameter<Float> ROTATION_PITCH_CABEZA2 = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.FLOAT);
	public static final DataParameter<Float> PREV_ROTATION_PITCH_CABEZA2 = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.FLOAT);
	public static final DataParameter<Float> ROTATION_YAW_HEAD_CABEZA2 = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.FLOAT);
	public static final DataParameter<Float> PREV_ROTATION_YAW_HEAD_CABEZA2 = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.FLOAT);
	
	public PrumTrakEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(ListaEntidades.prum_y_trak, worldIn);
		this.lookController = new PrumTrakLookController(this);
		this.setRotationYawHead(this.rotationYaw);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new PrumTrakLookRandomlyGoal(this));
		this.goalSelector.addGoal(2, new PrumTrakLookAtGoal(this, PlayerEntity.class, 20));
		this.goalSelector.addGoal(4, new RangedAttackGoal(this, 1.8D, 20, 32F));
		
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new PrumTrakNearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, TARGET_RUNE_PLAYER));
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(PrumTrakEntity.RENDER_YAW_OFFSET_CABEZA2, 0F);
		this.dataManager.register(PrumTrakEntity.PREV_RENDER_YAW_OFFSET_CABEZA2, 0F);
		this.dataManager.register(PrumTrakEntity.ROTATION_PITCH_CABEZA2, 0F);
		this.dataManager.register(PrumTrakEntity.PREV_ROTATION_PITCH_CABEZA2, 0F);
		this.dataManager.register(PrumTrakEntity.ROTATION_YAW_HEAD_CABEZA2, 0F);
		this.dataManager.register(PrumTrakEntity.PREV_ROTATION_YAW_HEAD_CABEZA2, 0F);
	}
	
	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		AbstractArrowEntity abstractArrowEntity = ((PrumProyectilItem)ListaItem.prum_proyectil).createArrow(this.world, new ItemStack(ListaItem.prum_proyectil), this);
		double d0 = target.getPosX() - this.getPosX();
		double d1 = target.getPosYHeight(0.3333333D) - abstractArrowEntity.getPosY();
		double d2 = target.getPosZ() - this.getPosZ();
		double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
		abstractArrowEntity.shoot(d0, d1 + d3 * 0.2D, d2, 1.6F, 2);
		this.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1, 1);
		this.world.addEntity(abstractArrowEntity);
	}
	
	public float getRenderYawOffsetCabeza2() {
		return this.dataManager.get(RENDER_YAW_OFFSET_CABEZA2);
	}
	
	public void setRenderYawOffSetCabeza2(float yawOffset) {
		this.dataManager.set(RENDER_YAW_OFFSET_CABEZA2, yawOffset);
	}
	
	public float getPrevRenderYawOffsetCabeza2() {
		return this.dataManager.get(PREV_RENDER_YAW_OFFSET_CABEZA2);
	}
	
	public void setPrevRenderYawOffSetCabeza2(float yawOffset) {
		this.dataManager.set(PREV_RENDER_YAW_OFFSET_CABEZA2, yawOffset);
	}
	
	public float getRotationPitchCabeza2() {
		return this.dataManager.get(ROTATION_PITCH_CABEZA2);
	}
	
	public void setRotationPitchCabeza2(float pitch) {
		this.dataManager.set(ROTATION_PITCH_CABEZA2, pitch);
	}
	
	public float getPrevRotationPitchCabeza2() {
		return this.dataManager.get(PREV_ROTATION_PITCH_CABEZA2);
	}
	
	public void setPrevRotationPitchCabeza2(float pitch) {
		this.dataManager.set(PREV_ROTATION_PITCH_CABEZA2, pitch);
	}
	
	public float getRotationYawHeadCabeza2() {
		return this.dataManager.get(ROTATION_YAW_HEAD_CABEZA2);
	}
	
	public void setRotationYawHeadCabeza2(float yaw) {
		this.dataManager.set(ROTATION_YAW_HEAD_CABEZA2, yaw);
	}
	
	public float getPrevRotationYawHeadCabeza2() {
		return this.dataManager.get(PREV_ROTATION_YAW_HEAD_CABEZA2);
	}
	
	public void setPrevRotationYawHeadCabeza2(float yaw) {
		this.dataManager.set(PREV_ROTATION_YAW_HEAD_CABEZA2, yaw);
	}

	@Override
	protected BodyController createBodyController() {
		return new PrumTrakBodyController(this);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		this.world.getProfiler().startSection("prumTrakEntityBaseTick");
		this.setPrevRotationPitchCabeza2(this.getRotationPitchCabeza2());
		this.setPrevRenderYawOffSetCabeza2(this.getRenderYawOffsetCabeza2());
		this.setPrevRotationYawHeadCabeza2(this.getRotationYawHead());
		this.world.getProfiler().endSection();
	}

	@Override
	public void tick() {
		super.tick();
		double d0 = this.getPosX() - this.prevPosX;
		double d1 = this.getPosZ() - this.prevPosZ;
		float f = (float) (d0 * d0 + d1 * d1);
		float f1 = this.getRenderYawOffsetCabeza2();
		if (f > 0.0025000002F) {
			float f4 = (float) MathHelper.atan2(d1, d0) * 180f / (float) Math.PI - 90F;
			float f5 = MathHelper.abs(MathHelper.wrapDegrees(this.rotationYaw) - f4);
			if (95F < f5 && f5 < 265F) {
				f1 = f4 - 180F;
			} else {
				f1 = f4;
			}
		}
		if (this.swingProgress > 0F) {
			f1 = this.rotationYaw;
		}
		this.world.getProfiler().startSection("secondHeadTurn");
		float f13 = MathHelper.wrapDegrees(f1 - this.getRenderYawOffsetCabeza2());
		this.setRenderYawOffSetCabeza2(this.getRenderYawOffsetCabeza2() + f13 * 0.3F);
		float f14 = MathHelper.wrapDegrees(this.rotationYaw - this.getRenderYawOffsetCabeza2());
		if (f14 < -75.0F) {
			f14 = -75.0F;
		}

		if (f14 >= 75.0F) {
			f14 = 75.0F;
		}

		this.setRenderYawOffSetCabeza2(this.rotationYaw - f14); 
		if (f14 * f14 > 2500.0F) {
			this.setRenderYawOffSetCabeza2(this.getRenderYawOffsetCabeza2() + f14 * 0.2F);
		}
		this.world.getProfiler().endSection();
		this.world.getProfiler().startSection("rangeeCheck2");

		while (this.getRenderYawOffsetCabeza2() - this.getPrevRenderYawOffsetCabeza2() < -180.0F) {
			this.setPrevRenderYawOffSetCabeza2(this.getPrevRenderYawOffsetCabeza2() - 360F);
		}

		while (this.getRenderYawOffsetCabeza2() - this.getPrevRenderYawOffsetCabeza2() >= 180.0F) {
			this.setPrevRenderYawOffSetCabeza2(this.getPrevRenderYawOffsetCabeza2() + 360F);
		}

		while (this.getRotationPitchCabeza2() - this.getPrevRotationPitchCabeza2() < -180.0F) {
			this.setPrevRotationPitchCabeza2(this.getPrevRotationPitchCabeza2() - 360.0F);
		}

		while (this.getRotationPitchCabeza2() - this.getPrevRotationPitchCabeza2() >= 180.0F) {
			this.setPrevRotationPitchCabeza2(this.getPrevRotationPitchCabeza2() + 360.0F);
		}

		while (this.getRotationYawHead() - this.getPrevRotationYawHeadCabeza2() < -180.0F) {
			this.setPrevRotationYawHeadCabeza2(this.getPrevRotationYawHeadCabeza2() - 360.0F);
		}

		while (this.getRotationYawHead() - this.getPrevRotationYawHeadCabeza2() >= 180.0F) {
			this.setPrevRotationYawHeadCabeza2(this.getPrevRotationYawHeadCabeza2() + 360.0F);
		}
		this.world.getProfiler().endSection();
	}

	@Override
	public void lookAt(Type anchor, Vector3d target) {
		super.lookAt(anchor, target);
		
		this.setRotationPitchCabeza2(this.rotationPitch);
		this.setRotationYawHeadCabeza2(this.rotationYaw);
		this.setPrevRotationPitchCabeza2(this.getRotationPitchCabeza2());
		
		this.setPrevRotationYawHeadCabeza2(this.getRotationYawHead());
		this.setRenderYawOffSetCabeza2(this.getRotationYawHead());
		this.setPrevRenderYawOffSetCabeza2(this.getRenderYawOffsetCabeza2());
	}

	@Override
	public void rotateTowards(double yaw, double pitch) {
		super.rotateTowards(yaw, pitch);
		double d0 = pitch * 0.15;
		this.setRotationPitchCabeza2((float) ((double) this.getRotationPitchCabeza2() + d0));
		this.setRotationPitchCabeza2(MathHelper.clamp(this.getRotationPitchCabeza2(), -90.0F, 90.0F)); 
		this.setPrevRotationPitchCabeza2((float) ((double) this.getPrevRotationPitchCabeza2() + d0));
		this.setPrevRotationPitchCabeza2(MathHelper.clamp(this.getPrevRotationPitchCabeza2(), -90.0F, 90.0F)); 
	}

	@Override
	public void faceEntity(Entity entityIn, float maxYawIncrease, float maxPitchIncrease) {
		super.faceEntity(entityIn, maxYawIncrease, maxPitchIncrease);
		double d0 = entityIn.getPosX() - this.getPosX();
		double d2 = entityIn.getPosZ() - this.getPosZ();
		double d1;
		if (entityIn instanceof LivingEntity) {
			LivingEntity livingentity = (LivingEntity) entityIn;
			d1 = livingentity.getPosYEye() - this.getPosYEye();
		} else {
			d1 = (entityIn.getBoundingBox().minY + entityIn.getBoundingBox().maxY) / 2.0D - this.getPosYEye();
		}

		double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
		float f1 = (float) (-(MathHelper.atan2(d1, d3) * (double) (180F / (float) Math.PI)));
		this.setRotationPitchCabeza2(this.updateRotation(this.getRotationPitchCabeza2(), f1, maxPitchIncrease));
	}

	public Vector2f getPitchYawCabeza2() {
		return new Vector2f(this.getRotationPitchCabeza2(), this.rotationYaw);
	}

	public float getPitchCabeza2(float partialTicks) {
		return partialTicks == 1.0F ? this.getRotationPitchCabeza2()
				: MathHelper.lerp(partialTicks, this.getPrevRotationPitchCabeza2(), this.getRotationPitchCabeza2());
	}

	public Vector3d getLookVecCabeza2() {
		return this.getVectorForRotation(this.getRotationPitchCabeza2(), this.rotationYaw);
	}

	private float updateRotation(float angle, float targetAngle, float maxIncrease) {
		float f = MathHelper.wrapDegrees(targetAngle - angle);
		if (f > maxIncrease) {
			f = maxIncrease;
		}

		if (f < -maxIncrease) {
			f = -maxIncrease;
		}

		return angle + f;
	}


	@Override
	protected void setRotation(float yaw, float pitch) {
		super.setRotation(yaw, pitch);
		this.setRotationPitchCabeza2(this.rotationPitch);
	}
	
	public void setPositionAndRotationAmbasCabezas(double x, double y, double z, float yaw, float pitch, float yaw2, float pitch2) {
		super.setPositionAndRotation(x, y, z, yaw, pitch);
		this.setRotationPitchCabeza2(MathHelper.clamp(pitch2, -90.0F, 90.0F) % 360.0F);
		this.setPrevRotationPitchCabeza2(this.getRotationPitchCabeza2());
	}

	@Override
	public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
		super.setPositionAndRotation(x, y, z, yaw, pitch);
		this.setRotationPitchCabeza2(MathHelper.clamp(pitch, -90.0F, 90.0F) % 360.0F);
		this.setPrevRotationPitchCabeza2(this.getRotationPitchCabeza2());
	}

	@Override
	public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
		super.setLocationAndAngles(x, y, z, yaw, pitch);
		this.setRotationPitchCabeza2(pitch);
	}

	public static AttributeModifierMap.MutableAttribute getAtributos() {
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 100)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 1.8F)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 128)
				.createMutableAttribute(Attributes.ARMOR, 10)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 20);
	}
}
