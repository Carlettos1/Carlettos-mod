package com.carlettos.mod.entidades.prumytrak;

import java.util.Set;
import java.util.function.Predicate;

import com.carlettos.mod.entidades.prumytrak.ia.PrumTrakLookAtGoal;
import com.carlettos.mod.entidades.prumytrak.ia.PrumTrakLookRandomlyGoal;
import com.carlettos.mod.entidades.prumytrak.ia.PrumTrakNearestAttackableTargetGoal;
import com.carlettos.mod.entidades.prumytrak.ia.controllers.PrumTrakBodyController;
import com.carlettos.mod.entidades.prumytrak.ia.controllers.PrumTrakLookController;
import com.carlettos.mod.entidades.prumytrak.ia.prum.PrumRandomRangedAttackGoal;
import com.carlettos.mod.entidades.prumytrak.ia.prum.PrumRangedAttackGoal;
import com.carlettos.mod.entidades.prumytrak.ia.trak.TrakMeleAreaAttackGoal;
import com.carlettos.mod.entidades.prumytrak.ia.trak.TrakMeleAttackGoal;
import com.carlettos.mod.entidades.prumytrak.proyectil.PrumProyectilEntity;
import com.carlettos.mod.entidades.prumytrak.proyectil.PrumProyectilItem;
import com.carlettos.mod.listas.ListaAtributos;
import com.carlettos.mod.listas.ListaDamageSources;
import com.carlettos.mod.listas.ListaEntidades;
import com.carlettos.mod.listas.ListaItem;
import com.carlettos.mod.listas.ListaParticulas;

import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

/* 
 * Prum es el que ataca a distancia, el de su lado derecho
 * Trak es el que ataca a melé, el de su lado izquierdo
 */
public class PrumTrakEntity extends MonsterEntity implements IRangedAttackMob {
	public static final Set<Item> RUNAS_PRUM = Set.of(ListaItem.RUNA_PRUM, ListaItem.RUNA_RUDU, ListaItem.RUNA_UNK, ListaItem.RUNA_MIH);
	public static final Set<Item> RUNAS_TRAK = Set.of(ListaItem.RUNA_TRAK, ListaItem.RUNA_RUDU, ListaItem.RUNA_AMAN, ListaItem.RUNA_KEL);
	public static final Set<Item> RUNAS_PRUM_Y_TRAK = Set.of(ListaItem.RUNA_PRUM, ListaItem.RUNA_RUDU, ListaItem.RUNA_UNK, ListaItem.RUNA_MIH, ListaItem.RUNA_TRAK, ListaItem.RUNA_AMAN, ListaItem.RUNA_KEL);

	public static final Predicate<LivingEntity> TARGET_RUNE_PLAYER = (entidad) -> {
		if (!(entidad instanceof PlayerEntity)) {
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
	public static final DataParameter<Byte> FASE = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.BYTE);
	public static final DataParameter<Byte> ATAQUE_RANGO = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.BYTE);
	public static final DataParameter<Byte> ATAQUE_RANGO_RANDOM = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.BYTE);
	public static final DataParameter<Byte> ATAQUE_MELE = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.BYTE);
	public static final DataParameter<Byte> ATAQUE_MELE_AOE = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.BYTE);
	
	private final ServerBossInfo bossInfo = new ServerBossInfo(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS);

	public PrumTrakEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(ListaEntidades.PRUM_Y_TRAK, worldIn);
		this.lookController = new PrumTrakLookController(this);
		this.setRotationYawHead(this.rotationYaw);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new PrumTrakLookRandomlyGoal(this));
		this.goalSelector.addGoal(2, new PrumTrakLookAtGoal(this, PlayerEntity.class, 20));
		this.goalSelector.addGoal(3, new TrakMeleAreaAttackGoal(this, 7D, (byte)20));
		//this.goalSelector.addGoal(4, new TrakMeleAttackGoal(this, 1D, true));
		//this.goalSelector.addGoal(5, new PrumRangedAttackGoal(this, 1D, 20, 32F));
		//this.goalSelector.addGoal(6, new PrumRandomRangedAttackGoal(this, 1D, 20, 32F));
		
		this.targetSelector.addGoal(1, new PrumTrakNearestAttackableTargetGoal<>(this, PlayerEntity.class, true, false, TARGET_RUNE_PLAYER));
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
		this.dataManager.register(PrumTrakEntity.FASE, (byte)1);
		this.dataManager.register(PrumTrakEntity.ATAQUE_RANGO, (byte)0);
		this.dataManager.register(PrumTrakEntity.ATAQUE_RANGO_RANDOM, (byte)0);
		this.dataManager.register(PrumTrakEntity.ATAQUE_MELE, (byte)0);
		this.dataManager.register(PrumTrakEntity.ATAQUE_MELE_AOE, (byte)0);
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
	}

	@Override
	public void addTrackingPlayer(ServerPlayerEntity player) {
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void removeTrackingPlayer(ServerPlayerEntity player) {
		this.bossInfo.removePlayer(player);
	}

	@Override
	public void setCustomName(ITextComponent name) {
		super.setCustomName(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	@Override
	protected void updateAITasks() {
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	}
	
	public void ataqueEnArea(double radio) {
		if(!this.world.isRemote) {
			for(int i = 0; i < radio * radio * radio * 4; i++) {
				double x = 2D * radio * (0.5D - this.getRNG().nextDouble());
				double y = radio * this.getRNG().nextDouble();
				double z = 2D * radio * (0.5D - this.getRNG().nextDouble());
				((ServerWorld)this.world).spawnParticle(ListaParticulas.TRAK_PARTICULA, this.getPosX() + x, this.getPosY() + y, this.getPosZ() + z, 0, 0, 0, 0, 0.05D);
			}
			this.world.getEntitiesInAABBexcluding(this, getBoundingBox().grow(radio), null).forEach((entidad) -> {
				entidad.attackEntityFrom(ListaDamageSources.TRAK_AOE(this), (float) this.getAttributeValue(ListaAtributos.AOE_ATTACK_DAMAGE));
			});;
		}
	}
	
	@Override
    public void checkDespawn() {
    }
	
	public void attackRandomly(int quantity) {
		for(;quantity > 0; quantity--) {
			PrumProyectilEntity proyectil = ((PrumProyectilItem)ListaItem.PRUM_PROYECTIL).createArrow(this.world, this, this.getAttackTarget(), 0.5F);
			double phi = this.getRNG().nextDouble() * 2D * Math.PI;
			double theta = this.getRNG().nextDouble() * Math.PI;
			double x = Math.sin(theta) * Math.cos(phi);
			double y = Math.cos(theta);
			double z = Math.sin(theta) * Math.sin(phi);
			proyectil.shoot(x, y, z, 1.6F, 0);
			this.world.addEntity(proyectil);
		}
		this.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1, 1); //TODO: Sonido
	}

	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		PrumProyectilEntity proyectil = ((PrumProyectilItem) ListaItem.PRUM_PROYECTIL).createArrow(this.world, this, this.getAttackTarget(), 0.5F);
		double d0 = target.getPosX() - proyectil.getPosX();
		double d1 = target.getPosYHeight(0.3333333D) - proyectil.getPosY();
		double d2 = target.getPosZ() - proyectil.getPosZ();
		proyectil.shoot(d0, d1, d2, 2F, 0);
		this.world.addEntity(proyectil);
		this.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1, 1); //TODO: Sonido
	}

	public byte getAtacandoARango() {
		return this.dataManager.get(PrumTrakEntity.ATAQUE_RANGO);
	}
	
	public void setAtacandoARango(byte fase) {
		this.dataManager.set(PrumTrakEntity.ATAQUE_RANGO, fase);
	}

	public byte getAtacandoARangoRandom() {
		return this.dataManager.get(PrumTrakEntity.ATAQUE_RANGO_RANDOM);
	}
	
	public void setAtacandoARangoRandom(byte fase) {
		this.dataManager.set(PrumTrakEntity.ATAQUE_RANGO_RANDOM, fase);
	}

	public byte getAtacandoAMele() {
		return this.dataManager.get(PrumTrakEntity.ATAQUE_MELE);
	}
	
	public void setAtacandoAMele(byte fase) {
		this.dataManager.set(PrumTrakEntity.ATAQUE_MELE, fase);
	}

	public byte getAtacandoAMeleAOE() {
		return this.dataManager.get(PrumTrakEntity.ATAQUE_MELE_AOE);
	}
	
	public void setAtacandoAMeleAOE(byte fase) {
		this.dataManager.set(PrumTrakEntity.ATAQUE_MELE_AOE, fase);
	}
	
	public byte getFase() {
		return this.dataManager.get(FASE);
	}
	
	public void setFase(byte fase) {
		this.dataManager.set(FASE, fase);
	}

	public float getRenderYawOffsetCabeza2() {
		return this.dataManager.get(RENDER_YAW_OFFSET_CABEZA2);
	}

	public void setRenderYawOffsetCabeza2(float yawOffset) {
		this.dataManager.set(RENDER_YAW_OFFSET_CABEZA2, yawOffset);
	}

	public float getPrevRenderYawOffsetCabeza2() {
		return this.dataManager.get(PREV_RENDER_YAW_OFFSET_CABEZA2);
	}

	public void setPrevRenderYawOffsetCabeza2(float yawOffset) {
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
		this.setPrevRenderYawOffsetCabeza2(this.getRenderYawOffsetCabeza2());
		this.setPrevRotationYawHeadCabeza2(this.getRotationYawHeadCabeza2());
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
		this.setRenderYawOffsetCabeza2(this.getRenderYawOffsetCabeza2() + f13 * 0.3F);
		float f14 = MathHelper.wrapDegrees(this.rotationYaw - this.getRenderYawOffsetCabeza2());
		if (f14 < -75.0F) {
			f14 = -75.0F;
		}

		if (f14 >= 75.0F) {
			f14 = 75.0F;
		}

		this.setRenderYawOffsetCabeza2(this.rotationYaw - f14);
		if (f14 * f14 > 2500.0F) {
			this.setRenderYawOffsetCabeza2(this.getRenderYawOffsetCabeza2() + f14 * 0.2F);
		}
		this.world.getProfiler().endSection();
		this.world.getProfiler().startSection("rangeeCheckSecondHead");

		while (this.getRenderYawOffsetCabeza2() - this.getPrevRenderYawOffsetCabeza2() < -180.0F) {
			this.setPrevRenderYawOffsetCabeza2(this.getPrevRenderYawOffsetCabeza2() - 360F);
		}

		while (this.getRenderYawOffsetCabeza2() - this.getPrevRenderYawOffsetCabeza2() >= 180.0F) {
			this.setPrevRenderYawOffsetCabeza2(this.getPrevRenderYawOffsetCabeza2() + 360F);
		}

		while (this.getRotationPitchCabeza2() - this.getPrevRotationPitchCabeza2() < -180.0F) {
			this.setPrevRotationPitchCabeza2(this.getPrevRotationPitchCabeza2() - 360.0F);
		}

		while (this.getRotationPitchCabeza2() - this.getPrevRotationPitchCabeza2() >= 180.0F) {
			this.setPrevRotationPitchCabeza2(this.getPrevRotationPitchCabeza2() + 360.0F);
		}

		while (this.getRotationYawHeadCabeza2() - this.getPrevRotationYawHeadCabeza2() < -180.0F) {
			this.setPrevRotationYawHeadCabeza2(this.getPrevRotationYawHeadCabeza2() - 360.0F);
		}

		while (this.getRotationYawHeadCabeza2() - this.getPrevRotationYawHeadCabeza2() >= 180.0F) {
			this.setPrevRotationYawHeadCabeza2(this.getPrevRotationYawHeadCabeza2() + 360.0F);
		}
		this.world.getProfiler().endSection();
		
		this.world.getProfiler().startSection("updatefase");
		double ratio = getHealth() / getMaxHealth();
		if(ratio > 2F/3F) {
		} else if (ratio > 1F/3F) {
			if(this.getFase() < 2) {
				this.setFase((byte)2);
				this.getAttribute(Attributes.ARMOR).applyPersistentModifier(new AttributeModifier("bonus segunda fase", 4D, AttributeModifier.Operation.ADDITION));
				this.getAttribute(ListaAtributos.AOE_ATTACK_DAMAGE).applyPersistentModifier(new AttributeModifier("bonus segunda fase", 9D, AttributeModifier.Operation.ADDITION));
			}
		} else {
			if(this.getFase() < 3) {
				this.setFase((byte)3);
				this.getAttribute(Attributes.ARMOR).applyPersistentModifier(new AttributeModifier("bonus tercera fase", 5D, AttributeModifier.Operation.ADDITION));
				this.getAttribute(ListaAtributos.AOE_ATTACK_DAMAGE).applyPersistentModifier(new AttributeModifier("bonus segunda fase", 10D, AttributeModifier.Operation.ADDITION));
			}
		}
		this.world.getProfiler().endSection();
		//TODO: mover esto a un goal.
		if(this.isInWater()) {
			if(this.getAttackTarget() != null) {
				this.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 2, 3));
				LivingEntity target = this.getAttackTarget();
				this.setMotion(new Vector3d(target.getPosX() - this.getPosX(), 0, target.getPosZ() - this.getPosZ()).normalize());
				this.jump();
			}
		}
	}

	@Override
	public void lookAt(Type anchor, Vector3d target) {
		super.lookAt(anchor, target);

		this.setRotationPitchCabeza2(this.rotationPitch);
		this.setRotationYawHeadCabeza2(this.rotationYaw);
		this.setPrevRotationPitchCabeza2(this.getRotationPitchCabeza2());

		this.setPrevRotationYawHeadCabeza2(this.getRotationYawHeadCabeza2());
		this.setRenderYawOffsetCabeza2(this.getRotationYawHeadCabeza2());
		this.setPrevRenderYawOffsetCabeza2(this.getRenderYawOffsetCabeza2());
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

	public float getPitchCabeza2(float partialTicks) {
		return partialTicks == 1.0F ? this.getRotationPitchCabeza2()
				: MathHelper.lerp(partialTicks, this.getPrevRotationPitchCabeza2(), this.getRotationPitchCabeza2());
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
	}

	public void setPositionAndRotationAmbasCabezas(double x, double y, double z, float yaw, float pitch, float yaw2,
			float pitch2) {
		super.setPositionAndRotation(x, y, z, yaw, pitch);
		this.setRotationPitchCabeza2(MathHelper.clamp(pitch2, -90.0F, 90.0F) % 360.0F);
		this.setPrevRotationPitchCabeza2(this.getRotationPitchCabeza2());
	}

	@Override
	public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
		super.setPositionAndRotation(x, y, z, yaw, pitch);
	}

	@Override
	public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
		super.setLocationAndAngles(x, y, z, yaw, pitch);
	}

	@Override
	public boolean isNonBoss() {
		return false;
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return false;
	}
	
	@Override
	protected float applyArmorCalculations(DamageSource source, float damage) {
		this.damageArmor(source, damage);
		double armadura = this.getTotalArmorValue();
		if(armadura >= 0) {
			damage *= 5 / (5 + armadura);
		} else {
			damage *= (2 - 5 / (5 - armadura));
		}
		return damage;
	}
	
	@Override
	public boolean isInvulnerableTo(DamageSource source) {
		if(source == DamageSource.OUT_OF_WORLD) {
			return false;
		}
		if(source.isCreativePlayer()) {
			return false;
		}
		if(source.isFireDamage()) {
			return true;
		}
		if(source == DamageSource.ANVIL) {
			return true;
		}
		if(source == DamageSource.FALLING_BLOCK) {
			return true;
		}
		if(source.getTrueSource() instanceof PrumTrakEntity) {
			return true;
		}
		return this.isInvulnerable();
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(isInvulnerableTo(source)) {
			return false;
		} else if(amount < 5F) {
			return false;
		} else if(!ForgeHooks.onLivingAttack(this, source, amount)) {
			return false;
		} else if(this.world.isRemote) {
			return false;
		} else if(this.getShouldBeDead()) {
			return false;
		} else {
			this.idleTime = 0;
			this.limbSwingAmount = 1.5F;
			boolean flag1 = true;
			
			if(this.hurtResistantTime > 10F) {
				if(amount <= this.lastDamage) {
					return false;
				}
				this.damageEntity(source, amount - this.lastDamage);
				this.lastDamage = amount;
				flag1 = false;
			} else {
				this.lastDamage = amount;
				this.hurtResistantTime = 15;
				this.damageEntity(source, amount);
				this.maxHurtTime = 10;
				this.hurtTime = 10;
			}
			this.attackedAtYaw = 0F;
			Entity entity1 = source.getTrueSource();
			if(entity1 != null) {
				if(entity1 instanceof LivingEntity) {
					this.setRevengeTarget((LivingEntity)entity1);
				}
				if(entity1 instanceof PlayerEntity) {
					this.recentlyHit = 100;
					this.attackingPlayer = (PlayerEntity)entity1;
				} else if(entity1 instanceof TameableEntity) {
					TameableEntity tameable = (TameableEntity)entity1;
					if(tameable.isTamed()) {
						this.recentlyHit = 100;
						LivingEntity living = tameable.getOwner();
						if (living != null && living.getType().equals(EntityType.PLAYER)) {
							this.attackingPlayer = (PlayerEntity) living;
						} else {
							this.attackingPlayer = null;
						}
					}
				}
			}
			if(flag1) {
				if(source instanceof EntityDamageSource && ((EntityDamageSource)source).getIsThornsDamage()) {
					this.world.setEntityState(this, (byte)33);
				} else {
					byte b0;
					if(source == DamageSource.DROWN) {
						b0 = 36;
					} else if(source == DamageSource.SWEET_BERRY_BUSH) {
						b0 = 44;
					} else {
						b0 = 2;
					}
					this.world.setEntityState(this, b0);
				}
				if(source != DamageSource.DROWN) {
					this.markVelocityChanged();
				}
				if(entity1 != null) {
					double d1 = entity1.getPosX() - this.getPosX();
	                double d0;
	                for(d0 = entity1.getPosZ() - this.getPosZ(); d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
	                	d1 = (Math.random() - Math.random()) * 0.01D;
	                }
	                this.attackedAtYaw = (float)(MathHelper.atan2(d0, d1) * (double)(180F / (float)Math.PI) - (double)this.rotationYaw);
	                this.applyKnockback(0.4F, d1, d0);
				} else {
					this.attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);
				}
			}
			
			if (this.getShouldBeDead()) {
				if(flag1) {
					this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getSoundPitch());
				}
				this.onDeath(source);
			} else if (flag1) {
				this.playHurtSound(source);
			}
			return true;
		}
	}

	public static AttributeModifierMap.MutableAttribute getAtributos() {
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 210D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 1D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 128D)
				.createMutableAttribute(Attributes.ARMOR, 7D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 20D)
				.createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1D)
				.createMutableAttribute(ListaAtributos.AOE_ATTACK_DAMAGE, 8D);
	}
}
