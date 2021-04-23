package com.carlettos.mod.entidades.aman.amanspider;

import com.carlettos.mod.entidades.aman.IAmanEggHatch;
import com.carlettos.mod.entidades.aman.IAmanSpit;
import com.carlettos.mod.entidades.aman.amanspit.AmanSpitEntity;
import com.carlettos.mod.entidades.aman.ia.AmanEggHatchGoal;
import com.carlettos.mod.entidades.aman.ia.AmanSpitGoal;
import com.carlettos.mod.entidades.dummyboi.DummyBoiEntity;
import com.carlettos.mod.listas.ListaAtributos;
import com.carlettos.mod.listas.ListaEntidades;
import com.carlettos.mod.util.SCAnimatePackage;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public class AmanSpiderEntity extends MonsterEntity implements IAmanEggHatch, IAmanSpit {
	public static final DataParameter<Boolean> HATCHING = EntityDataManager.createKey(AmanSpiderEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Boolean> SPITING = EntityDataManager.createKey(AmanSpiderEntity.class, DataSerializers.BOOLEAN);
	
	public float hatchingProgress;
	public float prevHatchingProgress;
	public boolean isHatchInProgress;
	public int hatchingProgressInt;
	
	public float spitProgress;
	public float prevSpitProgress;
	public boolean isSpitInProgress;
	public int spitProgressInt;

	private AmanEggHatchGoal<AmanSpiderEntity> hatchGoal;
	private AmanSpitGoal<AmanSpiderEntity> spitGoal;

	public AmanSpiderEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public AmanSpiderEntity(World worldIn) {
		this(ListaEntidades.AMAN_SPIDER, worldIn);
	}

	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
			ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
		spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		switch (reason) {
		case REINFORCEMENT:
			if (spawnDataIn == null) {
				spawnDataIn = new AmanSpiderEntity.GroupData(this.world.getRandom().nextFloat() >= 0.5F, false);
			}
		default:
			if (spawnDataIn == null) {
				// numero que hace que el 50% de las arañas no tengan nada y el otro 50% tenga
				// algo.
				float f = 1F - 1F / MathHelper.SQRT_2;
				spawnDataIn = new AmanSpiderEntity.GroupData(this.world.getRandom().nextFloat() >= f,
						this.world.getRandom().nextFloat() >= f);
			}
			if (spawnDataIn instanceof AmanSpiderEntity.GroupData) {
				if (((AmanSpiderEntity.GroupData) spawnDataIn).isHatch) {
					AttributeModifier attributeModifier = new AttributeModifier("Random Spawn Start",
							MathHelper.clamp(this.world.getRandom().nextInt(10) + 1, 2, 10), Operation.ADDITION);
					this.getAttribute(ListaAtributos.AMAN_EGG_COUNT).applyPersistentModifier(attributeModifier);
				} else {
					this.goalSelector.removeGoal(this.hatchGoal);
				}
				if (((AmanSpiderEntity.GroupData) spawnDataIn).isSpit) {
					AttributeModifier attributeModifier = new AttributeModifier("Spawn Start", 8D, Operation.ADDITION);
					this.getAttribute(ListaAtributos.RANGE_ATTACK_DAMAGE).applyPersistentModifier(attributeModifier);
				} else {
					this.goalSelector.removeGoal(this.spitGoal);
				}
			}
			break;
		}
		return spawnDataIn;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		this.prevHatchingProgress = this.hatchingProgress;
		this.prevSpitProgress = this.spitProgress;
	}

	@Override
	public void livingTick() {
		super.livingTick();
		this.updateHatchProgress();
		this.updateSpitProgress();
	}

	@Override
	protected void registerGoals() {
		this.hatchGoal = new AmanEggHatchGoal<>(this, 60);
		this.spitGoal = new AmanSpitGoal<>(this, 20);
		
		this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(2, this.hatchGoal);
		this.goalSelector.addGoal(3, this.spitGoal);
		this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 0.8D, 1));
		
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<DummyBoiEntity>(this, DummyBoiEntity.class, 0, true, false, DummyBoiEntity.PREDICATE));
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(HATCHING, false);
		this.dataManager.register(SPITING, false);
	}

	public static AttributeModifierMap.MutableAttribute getAtributos() {
		return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 40D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 64D)
				.createMutableAttribute(Attributes.ARMOR, 4D)
				.createMutableAttribute(ListaAtributos.RANGE_ATTACK_DAMAGE)
				.createMutableAttribute(ListaAtributos.AMAN_EGG_COUNT);
	}

	@Override
	public int getMaxSpitProgress() {
		return 10;
	}

	@Override
	public float getSpitProgress(float partialTick) {
		float f = this.spitProgress - this.prevSpitProgress;
		if (f < 0F) {
			++f;
		}
		return this.prevSpitProgress + f * partialTick;
	}

	@Override
	public void updateSpitProgress() {
		this.world.getProfiler().startSection("update spit progress");
		int i = this.getMaxSpitProgress();
		if (this.isSpitInProgress) {
			++this.spitProgressInt;
			if (this.spitProgressInt >= i) {
				this.spitProgress = 0;
				this.isSpitInProgress = false;
			}
		} else {
			this.spitProgressInt = 0;
		}
		this.spitProgress = (float) this.spitProgressInt / (float) i;
		this.world.getProfiler().endSection();
	}

	@Override
	public void spitAnimation(boolean updateSelf) {
		if (!this.isSpitInProgress || this.spitProgressInt >= this.getMaxSpitProgress() / 2
				|| this.spitProgressInt < 0) {
			this.spitProgressInt = -1;
			this.isSpitInProgress = true;
			if (this.world instanceof ServerWorld) {
				SCAnimatePackage scanimate = new SCAnimatePackage(this, SCAnimatePackage.AMAN_SPIT_ANIMATION_ID);
				ServerChunkProvider scp = ((ServerWorld) this.world).getChunkProvider();
				if (updateSelf) {
					scp.sendToTrackingAndSelf(this, scanimate);
				} else {
					scp.sendToAllTracking(this, scanimate);
				}
			}
		}
	}

	@Override
	public void spitAttack() {
		if (!this.world.isRemote) {
			AmanSpitEntity spit = new AmanSpitEntity(this.world);
			spit.setLocationAndAngles(this.getPosX(), this.getPosYEye(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
			double dx = this.getAttackTarget().getPosX() - this.getPosX();
			double dy = this.getAttackTarget().getPosYEye() - this.getPosYEye();
			double dz = this.getAttackTarget().getPosZ() - this.getPosZ();
			spit.setDamage(this.getAttributeValue(ListaAtributos.RANGE_ATTACK_DAMAGE));
			spit.shoot(dx, dy, dz, 2F, 1F);
			this.world.addEntity(spit);
			//TODO: sonido
		}
	}

	@Override
	public boolean isSpitting() {
		return this.dataManager.get(SPITING);
	}

	@Override
	public void setSpitting(boolean spiting) {
		this.dataManager.set(SPITING, spiting);
	}

	@Override
	public int getMaxHatchProgress() {
		return 20;
	}

	@Override
	public float getHatchProgress(float partialTick) {
		float f = this.hatchingProgress - this.prevHatchingProgress;
		if (f < 0.0F) {
			++f;
		}
		return this.prevHatchingProgress + f * partialTick;
	}

	@Override
	public void updateHatchProgress() {
		this.world.getProfiler().startSection("update hatch progress");
		int i = this.getMaxHatchProgress();
		if (this.isHatchInProgress) {
			++this.hatchingProgressInt;
			if (this.hatchingProgressInt >= i) {
				this.hatchingProgressInt = 0;
				this.isHatchInProgress = false;
			}
		} else {
			this.hatchingProgressInt = 0;
		}
		this.hatchingProgress = (float) this.hatchingProgressInt / (float) i;
		this.world.getProfiler().endSection();
	}

	@Override
	public void hatchAnimation(boolean updateSelf) {
		if (!this.isHatchInProgress || this.hatchingProgressInt >= this.getMaxHatchProgress() / 2
				|| this.hatchingProgressInt < 0) {
			this.hatchingProgressInt = -1;
			this.isHatchInProgress = true;
			if (this.world instanceof ServerWorld) {
				SCAnimatePackage scAnimatePackage = new SCAnimatePackage(this, SCAnimatePackage.AMAN_EGG_HATCH_ANIMATION_ID);
				ServerChunkProvider scp = ((ServerWorld) this.world).getChunkProvider();
				if (updateSelf) {
					scp.sendToTrackingAndSelf(this, scAnimatePackage);
				} else {
					scp.sendToAllTracking(this, scAnimatePackage);
				}
			}
		}
	}

	@Override
	public void hatch(int amount) {
		if (!this.world.isRemote) {
			ListaEntidades.AMAN_SPIDER.spawn((ServerWorld) world, null, null, this.getPosition(), SpawnReason.REINFORCEMENT, false, false);
		}
	}

	@Override
	public boolean isHatching() {
		return this.dataManager.get(HATCHING);
	}

	@Override
	public void setHatching(boolean hatching) {
		this.dataManager.set(HATCHING, hatching);
	}

	public static class GroupData implements ILivingEntityData {
		public final boolean isSpit;
		public final boolean isHatch;

		public GroupData(boolean isRango, boolean isHatch) {
			this.isHatch = isHatch;
			this.isSpit = isRango;
		}
	}
}
