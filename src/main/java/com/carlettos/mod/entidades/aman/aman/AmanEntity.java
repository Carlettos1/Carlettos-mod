package com.carlettos.mod.entidades.aman.aman;

import com.carlettos.mod.entidades.aman.IAmanEggHatch;
import com.carlettos.mod.entidades.aman.IAmanSpit;
import com.carlettos.mod.entidades.aman.amanspit.AmanSpitEntity;
import com.carlettos.mod.entidades.aman.ia.AmanEggHatchGoal;
import com.carlettos.mod.entidades.aman.ia.AmanSpitGoal;
import com.carlettos.mod.listas.ListaAtributos;
import com.carlettos.mod.listas.ListaEntidades;
import com.carlettos.mod.util.SCAnimatePackage;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public class AmanEntity extends MonsterEntity implements IAmanEggHatch, IAmanSpit{
	public static final DataParameter<Boolean> HATCHING = EntityDataManager.createKey(AmanEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Boolean> SPITING = EntityDataManager.createKey(AmanEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Byte> FASE = EntityDataManager.createKey(AmanEntity.class, DataSerializers.BYTE);

	public float hatchingProgress;
	public float prevHatchingProgress;
	public boolean isHatchInProgress;
	public int hatchingProgressInt;
	
	public float spitProgress;
	public float prevSpitProgress;
	public boolean isSpitInProgress;
	public int spitProgressInt;
	
	public AmanEntity(EntityType<? extends AmanEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(HATCHING, false);
		this.dataManager.register(SPITING, false);
		this.dataManager.register(FASE, (byte)1);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(2, new AmanSpitGoal<>(this, 10));
		this.goalSelector.addGoal(3, new AmanEggHatchGoal<>(this, 60));
		
		this.targetSelector.addGoal(1,
				new NearestAttackableTargetGoal<SheepEntity>(this, SheepEntity.class, 0, true, false, (entidad) -> {
					return entidad.isAlive();
				}));
		this.targetSelector.addGoal(2,
				new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 0, true, false, (entidad) -> {
					return entidad.isAlive();
				}));
	}
	
	@Override
	public void tick() {
		super.tick();
		this.actualizarFase();
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
	
	private void actualizarFase() {
		double ratio = this.getHealth() / getMaxHealth();
		if(ratio > 2F/3F) {
		} else if(ratio > 1F/3F) {
			if(this.getFase() < 2) {
				this.setFase((byte)2);
				this.getAttribute(Attributes.ARMOR).applyPersistentModifier(new AttributeModifier("bonus segunda fase", 4D, AttributeModifier.Operation.ADDITION));
				this.getAttribute(ListaAtributos.RANGE_ATTACK_DAMAGE).applyPersistentModifier(new AttributeModifier("bonus segunda fase", 2D, AttributeModifier.Operation.ADDITION));
				this.getAttribute(ListaAtributos.AMAN_EGG_COUNT).applyPersistentModifier(new AttributeModifier("bonus segunda fase", 2D, AttributeModifier.Operation.ADDITION));
			}
		} else {
			if(this.getFase() < 3) {
				this.setFase((byte)3);
				this.getAttribute(Attributes.ARMOR).applyPersistentModifier(new AttributeModifier("bonus tercera fase", 5D, AttributeModifier.Operation.ADDITION));
				this.getAttribute(ListaAtributos.RANGE_ATTACK_DAMAGE).applyPersistentModifier(new AttributeModifier("bonus tercera fase", 3D, AttributeModifier.Operation.ADDITION));
				this.getAttribute(ListaAtributos.AMAN_EGG_COUNT).applyPersistentModifier(new AttributeModifier("bonus tercera fase", 3D, AttributeModifier.Operation.ADDITION));
			}
		}
	}

	public byte getFase() {
		return this.dataManager.get(FASE);
	}
	
	public void setFase(byte fase) {
		this.dataManager.set(FASE, fase);
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
			double count = this.getAttributeValue(ListaAtributos.AMAN_EGG_COUNT);
			for(int i = 0; i < count; i++) {			
				ListaEntidades.AMAN_SPIDER.spawn((ServerWorld) world, null, null, this.getPosition(), SpawnReason.REINFORCEMENT, false, false);
			}
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
	
	public static AttributeModifierMap.MutableAttribute getAtributos(){
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 40D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 1.8D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 32D)
				.createMutableAttribute(Attributes.ARMOR, 4D)
				.createMutableAttribute(ListaAtributos.RANGE_ATTACK_DAMAGE, 8D)
				.createMutableAttribute(ListaAtributos.AMAN_EGG_COUNT, 2D);
	}
}
