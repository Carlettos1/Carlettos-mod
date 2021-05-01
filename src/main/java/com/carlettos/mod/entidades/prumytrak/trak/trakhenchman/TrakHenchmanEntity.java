package com.carlettos.mod.entidades.prumytrak.trak.trakhenchman;

import com.carlettos.mod.entidades.dummyboi.DummyBoiEntity;
import com.carlettos.mod.entidades.prumytrak.trak.ITrakAOE;
import com.carlettos.mod.entidades.prumytrak.trak.ia.TrakAOEAttackGoal;
import com.carlettos.mod.listas.ListaAtributos;
import com.carlettos.mod.listas.ListaDamageSources;
import com.carlettos.mod.listas.ListaParticulas;
import com.carlettos.mod.util.SCAnimatePackage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public class TrakHenchmanEntity extends MonsterEntity implements ITrakAOE{
	public static final DataParameter<Boolean> AOE_AGRESSIVE = EntityDataManager.createKey(TrakHenchmanEntity.class, DataSerializers.BOOLEAN);
	public float AOEProgress;
	public float prevAOEProgress;
	public int AOEProgressInt;
	public boolean isAOEInProgress;

	public TrakHenchmanEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1D, true));
		this.goalSelector.addGoal(2, new TrakAOEAttackGoal<>(this, true, 3D));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<DummyBoiEntity>(this, DummyBoiEntity.class, 0, true, false, DummyBoiEntity.PREDICATE));
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(AOE_AGRESSIVE, false);
	}
	
	@Override
	public void baseTick() {
		super.baseTick();
		this.prevAOEProgress = this.AOEProgress;
	}
	
	@Override
	public void livingTick() {
		super.livingTick();
		this.updateAOEProgress();
	}
	
	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return false;
	}
	
	@Override
	public int getMaxAOEProgress() {
		int base = 10;
		if(this.isPotionActive(Effects.SPEED)) {
			base -= this.getActivePotionEffect(Effects.SPEED).getAmplifier() * 2;
		}
		if(this.isPotionActive(Effects.SLOWNESS)) {
			base += this.getActivePotionEffect(Effects.SLOWNESS).getAmplifier() * 6;
		}
    	return base < 0 ? 0: base;
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
		this.world.getProfiler().startSection("update aoe progress");
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
		this.world.getProfiler().endSection();
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
					entidad.attackEntityFrom(ListaDamageSources.TRAK_AOE(this), (float) this.getAttributeValue(ListaAtributos.TRAK_AOE_ATTACK_DAMAGE));
				}
			});
			//TODO: sonido
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
	
	public static AttributeModifierMap.MutableAttribute getAtributos(){
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 40D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 32D)
				.createMutableAttribute(Attributes.ARMOR, 4D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 6D)
				.createMutableAttribute(ListaAtributos.TRAK_AOE_ATTACK_DAMAGE, 5D);
	}
}
