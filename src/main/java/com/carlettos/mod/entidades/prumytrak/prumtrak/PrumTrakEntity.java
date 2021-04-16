package com.carlettos.mod.entidades.prumytrak.prumtrak;

import com.carlettos.mod.entidades.IHasFases;
import com.carlettos.mod.entidades.prum.prumproyectil.PrumProyectilEntity;
import com.carlettos.mod.entidades.prumytrak.prum.IPrumRangedAttack;
import com.carlettos.mod.entidades.prumytrak.prum.ia.PrumRangedAttackGoal;
import com.carlettos.mod.entidades.prumytrak.trak.ITrakAOE;
import com.carlettos.mod.entidades.prumytrak.trak.ia.TrakAOEAttackGoal;
import com.carlettos.mod.listas.ListaAtributos;
import com.carlettos.mod.listas.ListaParticulas;
import com.carlettos.mod.util.SCAnimatePackage;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public class PrumTrakEntity extends MonsterEntity implements IPrumRangedAttack, ITrakAOE, IHasFases{
	public static final DataParameter<Boolean> AOE_AGRESSIVE = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Boolean> RANGED_AGRESSIVE = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Byte> FASE = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.BYTE);
	
	public float AOEProgress;
	public float prevAOEProgress;
	public int AOEProgressInt;
	public boolean isAOEInProgress;
	
	public float rangedProgress;
	public float prevRangedProgress;
	public int rangedProgressInt;
	public boolean isRangedInProgress;
	
	public PrumTrakEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new PrumRangedAttackGoal<>(this, 10));
		this.goalSelector.addGoal(2, new TrakAOEAttackGoal<>(this, true, 7));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<SheepEntity>(this, SheepEntity.class, 0, true, false, (entidad) -> {return entidad.getClass().equals(SheepEntity.class);}));
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(AOE_AGRESSIVE, false);
		this.dataManager.register(RANGED_AGRESSIVE, false);
	}
	
	@Override
	public void tick() {
		super.tick();
		this.actualizarFase();
	}
	
	@Override
	public void baseTick() {
		super.baseTick();
		this.prevAOEProgress = this.AOEProgress;
		this.prevRangedProgress = this.rangedProgress;
	}
	
	@Override
	public void livingTick() {
		super.livingTick();
		this.updateAOEProgress();
		this.updateRangedProgress();
	}

	@Override
	public void actualizarFase() {
		this.world.getProfiler().startSection("updatefase");
		double ratio = getHealth() / getMaxHealth();
		if(ratio > 2F/3F) {
		} else if (ratio > 1F/3F) {
			if(this.getFase() < 2) {
				this.setFase((byte)2);
				this.getAttribute(Attributes.ARMOR).applyPersistentModifier(new AttributeModifier("bonus segunda fase", 4D, AttributeModifier.Operation.ADDITION));
				this.getAttribute(Attributes.ATTACK_DAMAGE).applyPersistentModifier(new AttributeModifier("bonus segunda fase", 4D, AttributeModifier.Operation.ADDITION));
				this.getAttribute(ListaAtributos.RANGE_ATTACK_DAMAGE).applyPersistentModifier(new AttributeModifier("bonus segunda fase", 10D, AttributeModifier.Operation.ADDITION));
				this.getAttribute(ListaAtributos.TRAK_AOE_ATTACK_DAMAGE).applyPersistentModifier(new AttributeModifier("bonus segunda fase", 9D, AttributeModifier.Operation.ADDITION));
			}
		} else {
			if(this.getFase() < 3) {
				this.setFase((byte)3);
				this.getAttribute(Attributes.ARMOR).applyPersistentModifier(new AttributeModifier("bonus tercera fase", 5D, AttributeModifier.Operation.ADDITION));
				this.getAttribute(Attributes.ATTACK_DAMAGE).applyPersistentModifier(new AttributeModifier("bonus tercera fase", 5D, AttributeModifier.Operation.ADDITION));
				this.getAttribute(ListaAtributos.RANGE_ATTACK_DAMAGE).applyPersistentModifier(new AttributeModifier("bonus tercera fase", 11D, AttributeModifier.Operation.ADDITION));
				this.getAttribute(ListaAtributos.TRAK_AOE_ATTACK_DAMAGE).applyPersistentModifier(new AttributeModifier("bonus tercera fase", 10D, AttributeModifier.Operation.ADDITION));
			}
		}
		this.world.getProfiler().endSection();
	}

	@Override
	public byte getFase() {
		return this.dataManager.get(FASE);
	}

	@Override
	public void setFase(byte fase) {
		this.dataManager.set(FASE, fase);
	}
	
	@Override
	public int getMaxAOEProgress() {
		return 10;
	}
	
	@Override
	public float getAOEProgress(float partialTick) {
	      float f = this.AOEProgress - this.prevAOEProgress;
	      if (f < 0.0F) {
	         ++f;
	      }
	      return this.prevAOEProgress + f * partialTick;
	}
	
	@Override
	public void updateAOEProgress() {
		int i = this.getMaxAOEProgress();
		if(this.isAOEInProgress) {
			++this.AOEProgressInt;
			if(this.AOEProgressInt >= i) {
				this.AOEProgressInt = 0;
				this.isAOEInProgress = false;
			}
		} else {
			this.AOEProgressInt = 0;
		}
		this.AOEProgress = (float)this.AOEProgressInt / (float)i;
	}
	
	@Override
	public void AOEAnimation(boolean updateSelf) {
		if(!this.isAOEInProgress || this.AOEProgressInt >= this.getMaxAOEProgress() / 2 || this.AOEProgressInt < 0) {
			this.AOEProgressInt = -1;
			this.isAOEInProgress = true;
			if(this.world instanceof ServerWorld) {
				SCAnimatePackage scanimate = new SCAnimatePackage(this, SCAnimatePackage.TRAK_AOE_ANIMATION_ID);
				ServerChunkProvider scp = ((ServerWorld)this.world).getChunkProvider();
				if(updateSelf) {
					scp.sendToTrackingAndSelf(this, scanimate);
				} else {
					scp.sendToAllTracking(this, scanimate);
				}
			}
		}
	}

	@Override
	public void AOEAttack(double radio) {
		if(!this.world.isRemote) {
			for(int i = 0; i < radio * radio * radio * 4; i++) {
				double x = 2D * radio * (0.5D - this.getRNG().nextDouble());
				double y = radio * this.getRNG().nextDouble();
				double z = 2D * radio * (0.5D - this.getRNG().nextDouble());
				((ServerWorld)this.world).spawnParticle(ListaParticulas.TRAK_PARTICULA, this.getPosX() + x, this.getPosY() + y, this.getPosZ() + z, 1, 0D, 0D, 0D, 0.1D);
			}
			this.world.getEntitiesInAABBexcluding(this, getBoundingBox().grow(radio), (entidad) -> {return entidad instanceof LivingEntity;}).forEach((entidad) -> {
				if(entidad.isAlive()) {
					entidad.attackEntityFrom(DamageSource.GENERIC, (float) this.getAttributeValue(ListaAtributos.TRAK_AOE_ATTACK_DAMAGE));
				}
			});
		}
	}
	
	@Override
	public boolean isAOEAgressive() {
		return this.dataManager.get(AOE_AGRESSIVE);
	}
	
	@Override
	public void setAOEAgressive(boolean aoed) {
		this.dataManager.set(AOE_AGRESSIVE, aoed);
	}

	@Override
	public int getMaxRangedProgress() {
		return 10;
	}

	@Override
	public float getRangedProgress(float partialTick) {
		float f = this.rangedProgress - this.prevRangedProgress;
		if(f < 0.0F) {
			++f;
		}
		return this.prevRangedProgress + f * partialTick;
	}

	@Override
	public void updateRangedProgress() {
		int i = this.getMaxRangedProgress();
		if(this.isRangedInProgress) {
			++this.rangedProgressInt;
			if(this.rangedProgressInt >= i) {
				this.rangedProgressInt = 0;
				this.isRangedInProgress = false;
			}
		} else {
			this.rangedProgressInt = 0;
		}
		this.rangedProgress = (float)this.rangedProgressInt / (float)i;
	}

	@Override
	public void rangedAnimation(boolean updateSelf) {
		if(!this.isRangedInProgress || this.rangedProgressInt >= this.getMaxRangedProgress() / 2 || this.rangedProgressInt < 0) {
			this.rangedProgressInt = -1;
			this.isRangedInProgress = true;
			if(this.world instanceof ServerWorld) {
				SCAnimatePackage scanimate = new SCAnimatePackage(this, SCAnimatePackage.PRUM_RANGED_ATTACK_ANIMATION_ID);
				ServerChunkProvider scp = ((ServerWorld)this.world).getChunkProvider();
				if(updateSelf) {
					scp.sendToTrackingAndSelf(this, scanimate);
				} else {
					scp.sendToAllTracking(this, scanimate);
				}
			}
		}
	}

	@Override
	public void rangedAttack(LivingEntity target) {
		if(this.world instanceof ServerWorld) {
			PrumProyectilEntity proyectil = new PrumProyectilEntity(this.world, this, target);
			double d0 = target.getPosX() -  proyectil.getPosX();
			double d1 = target.getPosYHeight(0.333333D) -  proyectil.getPosY();
			double d2 = target.getPosZ() -  proyectil.getPosZ();
			proyectil.shoot(d0, d1, d2, 2F, 1F);
			this.world.addEntity(proyectil);
			//TODO: SONIDO
		}
	}

	@Override
	public boolean isRangedAgressive() {
		return this.dataManager.get(RANGED_AGRESSIVE);
	}

	@Override
	public void setRangedAgressive(boolean ranged) {
		this.dataManager.set(RANGED_AGRESSIVE, ranged);
	}
	
	public static AttributeModifierMap.MutableAttribute getAtributos(){
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 210D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 1D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 128D)
				.createMutableAttribute(Attributes.ARMOR, 7D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 10D)
				.createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1D)
				.createMutableAttribute(ListaAtributos.TRAK_AOE_ATTACK_DAMAGE, 8D)
				.createMutableAttribute(ListaAtributos.RANGE_ATTACK_DAMAGE, 10D);
	}
}
