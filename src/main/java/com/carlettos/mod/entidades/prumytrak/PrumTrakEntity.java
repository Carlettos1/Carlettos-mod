package com.carlettos.mod.entidades.prumytrak;

import com.carlettos.mod.listas.ListaEntidades;

import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class PrumTrakEntity extends MonsterEntity {
	public static final DataParameter<Float> RENDER_YAW_OFFSET = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.FLOAT);
	public static final DataParameter<Float> PREV_RENDER_YAW_OFFSET = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.FLOAT);
	public static final DataParameter<Float> ROTATION_PITCH = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.FLOAT);
	public static final DataParameter<Float> PREV_ROTATION_PITCH = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.FLOAT);
	public static final DataParameter<Float> ROTATION_YAW_HEAD = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.FLOAT);
	public static final DataParameter<Float> PREV_ROTATION_YAW_HEAD = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.FLOAT);

	public PrumTrakEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(ListaEntidades.prum_y_trak, worldIn);
		this.lookController = new PrumTrakLookController(this);
		this.setRotationYawHead(this.rotationYaw);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new PrumTrakLookRandomlyGoal(this));
		this.goalSelector.addGoal(2, new PrumTrakLookAtGoal(this, PlayerEntity.class, 20));
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(PrumTrakEntity.RENDER_YAW_OFFSET, 0F);
		this.dataManager.register(PrumTrakEntity.PREV_RENDER_YAW_OFFSET, 0F);
		this.dataManager.register(PrumTrakEntity.ROTATION_PITCH, 0F);
		this.dataManager.register(PrumTrakEntity.PREV_ROTATION_PITCH, 0F);
		this.dataManager.register(PrumTrakEntity.ROTATION_YAW_HEAD, 0F);
		this.dataManager.register(PrumTrakEntity.PREV_ROTATION_YAW_HEAD, 0F);
	}
	
	public float getRenderYawOffset() {
		return this.dataManager.get(RENDER_YAW_OFFSET);
	}
	
	public void setRenderYawOffSet(float yawOffset) {
		this.dataManager.set(RENDER_YAW_OFFSET, yawOffset);
	}
	
	public float getPrevRenderYawOffset() {
		return this.dataManager.get(PREV_RENDER_YAW_OFFSET);
	}
	
	public void setPrevRenderYawOffSet(float yawOffset) {
		this.dataManager.set(PREV_RENDER_YAW_OFFSET, yawOffset);
	}
	
	public float getRotationPitch() {
		return this.dataManager.get(ROTATION_PITCH);
	}
	
	public void setRotationPitch(float pitch) {
		this.dataManager.set(ROTATION_PITCH, pitch);
	}
	
	public float getPrevRotationPitch() {
		return this.dataManager.get(PREV_ROTATION_PITCH);
	}
	
	public void setPrevRotationPitch(float pitch) {
		this.dataManager.set(PREV_ROTATION_PITCH, pitch);
	}
	
	public float getRotationYawHeadCabeza2() {
		return this.dataManager.get(ROTATION_YAW_HEAD);
	}
	
	public void setRotationYawHeadCabeza2(float yaw) {
		this.dataManager.set(ROTATION_YAW_HEAD, yaw);
	}
	
	public float getPrevRotationYawHead() {
		return this.dataManager.get(PREV_ROTATION_YAW_HEAD);
	}
	
	public void setPrevRotationYawHead(float yaw) {
		this.dataManager.set(PREV_ROTATION_YAW_HEAD, yaw);
	}

	@Override
	protected BodyController createBodyController() {
		return new PrumTrakBodyController(this);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		this.world.getProfiler().startSection("prumTrakEntityBaseTick");
		this.setPrevRotationPitch(this.getRotationPitch());
		this.setPrevRenderYawOffSet(this.getRenderYawOffset());
		this.setPrevRotationYawHead(this.getRotationYawHead());
		this.world.getProfiler().endSection();
	}

	@Override
	public void tick() {
		super.tick();
		double d0 = this.getPosX() - this.prevPosX;
		double d1 = this.getPosZ() - this.prevPosZ;
		float f = (float) (d0 * d0 + d1 * d1);
		float f1 = this.getRenderYawOffset();
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
		float f13 = MathHelper.wrapDegrees(f1 - this.getRenderYawOffset());
		this.setRenderYawOffSet(this.getRenderYawOffset() + f13 * 0.3F);
		float f14 = MathHelper.wrapDegrees(this.rotationYaw - this.getRenderYawOffset());
		if (f14 < -75.0F) {
			f14 = -75.0F;
		}

		if (f14 >= 75.0F) {
			f14 = 75.0F;
		}

		this.setRenderYawOffSet(this.rotationYaw - f14); 
		if (f14 * f14 > 2500.0F) {
			this.setRenderYawOffSet(this.getRenderYawOffset() + f14 * 0.2F);
		}
		this.world.getProfiler().endSection();
		this.world.getProfiler().startSection("rangeeCheck2");

		while (this.getRenderYawOffset() - this.getPrevRenderYawOffset() < -180.0F) {
			this.setPrevRenderYawOffSet(this.getPrevRenderYawOffset() - 360F);
		}

		while (this.getRenderYawOffset() - this.getPrevRenderYawOffset() >= 180.0F) {
			this.setPrevRenderYawOffSet(this.getPrevRenderYawOffset() + 360F);
		}

		while (this.getRotationPitch() - this.getPrevRotationPitch() < -180.0F) {
			this.setPrevRotationPitch(this.getPrevRotationPitch() - 360.0F);
		}

		while (this.getRotationPitch() - this.getPrevRotationPitch() >= 180.0F) {
			this.setPrevRotationPitch(this.getPrevRotationPitch() + 360.0F);
		}

		while (this.getRotationYawHead() - this.getPrevRotationYawHead() < -180.0F) {
			this.setPrevRotationYawHead(this.getPrevRotationYawHead() - 360.0F);
		}

		while (this.getRotationYawHead() - this.getPrevRotationYawHead() >= 180.0F) {
			this.setPrevRotationYawHead(this.getPrevRotationYawHead() + 360.0F);
		}
		this.world.getProfiler().endSection();
	}

	@Override
	public void lookAt(Type anchor, Vector3d target) {
		super.lookAt(anchor, target);
		
		this.setRotationPitch(this.rotationPitch);
		this.setRotationYawHeadCabeza2(this.rotationYaw);
		this.setPrevRotationPitch(this.getRotationPitch());
		
		this.setPrevRotationYawHead(this.getRotationYawHead());
		this.setRenderYawOffSet(this.getRotationYawHead());
		this.setPrevRenderYawOffSet(this.getRenderYawOffset());
	}

	@Override
	public void rotateTowards(double yaw, double pitch) {
		super.rotateTowards(yaw, pitch);
		double d0 = pitch * 0.15;
		this.setRotationPitch((float) ((double) this.getRotationPitch() + d0));
		this.setRotationPitch(MathHelper.clamp(this.getRotationPitch(), -90.0F, 90.0F)); 
		this.setPrevRotationPitch((float) ((double) this.getPrevRotationPitch() + d0));
		this.setPrevRotationPitch(MathHelper.clamp(this.getPrevRotationPitch(), -90.0F, 90.0F)); 
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
		this.setRotationPitch(this.updateRotation(this.getRotationPitch(), f1, maxPitchIncrease));
	}

	public Vector2f getPitchYawCabeza2() {
		return new Vector2f(this.getRotationPitch(), this.rotationYaw);
	}

	public float getPitchCabeza2(float partialTicks) {
		return partialTicks == 1.0F ? this.getRotationPitch()
				: MathHelper.lerp(partialTicks, this.getPrevRotationPitch(), this.getRotationPitch());
	}

	public Vector3d getLookVecCabeza2() {
		return this.getVectorForRotation(this.getRotationPitch(), this.rotationYaw);
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
		this.setRotationPitch(this.rotationPitch);
	}
	
	public void setPositionAndRotationAmbasCabezas(double x, double y, double z, float yaw, float pitch, float yaw2, float pitch2) {
		super.setPositionAndRotation(x, y, z, yaw, pitch);
		this.setRotationPitch(MathHelper.clamp(pitch2, -90.0F, 90.0F) % 360.0F);
		this.setPrevRotationPitch(this.getRotationPitch());
	}

	@Override
	public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
		super.setPositionAndRotation(x, y, z, yaw, pitch);
		this.setRotationPitch(MathHelper.clamp(pitch, -90.0F, 90.0F) % 360.0F);
		this.setPrevRotationPitch(this.getRotationPitch());
	}

	@Override
	public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
		super.setLocationAndAngles(x, y, z, yaw, pitch);
		this.setRotationPitch(pitch);
	}

	public static AttributeModifierMap.MutableAttribute getAtributos() {
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 100)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 128)
				.createMutableAttribute(Attributes.ARMOR, 10)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 20);
	}
}
