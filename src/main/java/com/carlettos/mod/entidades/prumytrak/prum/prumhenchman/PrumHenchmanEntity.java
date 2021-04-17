package com.carlettos.mod.entidades.prumytrak.prum.prumhenchman;

import com.carlettos.mod.entidades.prumytrak.prum.IPrumRangedAttack;
import com.carlettos.mod.entidades.prumytrak.prum.ia.PrumRangedAttackGoal;
import com.carlettos.mod.entidades.prumytrak.prum.prumproyectil.PrumProyectilEntity;
import com.carlettos.mod.listas.ListaAtributos;
import com.carlettos.mod.util.SCAnimatePackage;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public class PrumHenchmanEntity extends MonsterEntity implements IPrumRangedAttack{
	public static final DataParameter<Boolean> RANGED_AGRESSIVE = EntityDataManager.createKey(PrumHenchmanEntity.class, DataSerializers.BOOLEAN);
	public float rangedProgress;
	public float prevRangedProgress;
	public int rangedProgressInt;
	public boolean isRangedInProgress;
	
	public PrumHenchmanEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new PrumRangedAttackGoal<>(this, 20));		
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<SheepEntity>(this, SheepEntity.class, 0, true, false, (entidad) -> {return entidad.getClass().equals(SheepEntity.class);}));
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(RANGED_AGRESSIVE, false);
	}
	
	@Override
	public void baseTick() {
		super.baseTick();
		this.prevRangedProgress = this.rangedProgress;
	}
	
	@Override
	public void livingTick() {
		super.livingTick();
		this.updateRangedProgress();
	}

	@Override
	public int getMaxRangedProgress() {
		int base = 10;
		if(this.isPotionActive(Effects.HASTE)) {
			base -= this.getActivePotionEffect(Effects.HASTE).getAmplifier() * 2;
		} 
		if(this.isPotionActive(Effects.MINING_FATIGUE)) {
			base += this.getActivePotionEffect(Effects.MINING_FATIGUE).getAmplifier() * 6;
		}
		return base < 0 ? 0: base;
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
		this.world.getProfiler().startSection("update ranged progress");
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
		this.world.getProfiler().endSection();
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
				.createMutableAttribute(Attributes.MAX_HEALTH, 40D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 1.8D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 32D)
				.createMutableAttribute(Attributes.ARMOR, 4D)
				.createMutableAttribute(ListaAtributos.RANGE_ATTACK_DAMAGE, 10D);
	}
}
