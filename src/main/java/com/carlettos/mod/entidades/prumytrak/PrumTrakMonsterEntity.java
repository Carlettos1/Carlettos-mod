package com.carlettos.mod.entidades.prumytrak;

import com.carlettos.mod.entidades.bihead.BiHeadMonsterEntity;
import com.carlettos.mod.entidades.interfaces.IHasFases;
import com.carlettos.mod.entidades.prumytrak.prum.IPrumRangedAttack;
import com.carlettos.mod.entidades.prumytrak.prum.prumproyectil.PrumProyectilEntity;
import com.carlettos.mod.entidades.prumytrak.trak.ITrakAOE;
import com.carlettos.mod.listas.ListaAtributos;
import com.carlettos.mod.listas.ListaDamageSources;
import com.carlettos.mod.listas.ListaParticulas;
import com.carlettos.mod.util.SCAnimatePackage;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

/**
 * Clase intermedia, una implementación directa de ambas interfaces.
 * Hecha para no tener que reescribir tanto código.
 */
public class PrumTrakMonsterEntity extends BiHeadMonsterEntity implements IPrumRangedAttack, ITrakAOE{
	public static final DataParameter<Boolean> AOE_AGRESSIVE = EntityDataManager.createKey(PrumTrakMonsterEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Boolean> RANGED_AGRESSIVE = EntityDataManager.createKey(PrumTrakMonsterEntity.class, DataSerializers.BOOLEAN);

	public float AOEProgress;
	public float prevAOEProgress;
	public int AOEProgressInt;
	public boolean isAOEInProgress;
	
	public float rangedProgress;
	public float prevRangedProgress;
	public int rangedProgressInt;
	public boolean isRangedInProgress;

	private final int maxAOEProgress;
	private final int maxRangedProgress;
	
	public PrumTrakMonsterEntity(EntityType<? extends PrumTrakMonsterEntity> type, World world, int maxAOEProgress, int maxRangeProgress) {
		super(type, world);
		this.maxAOEProgress = maxAOEProgress;
		this.maxRangedProgress = maxRangeProgress;
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(AOE_AGRESSIVE, false);
		this.dataManager.register(RANGED_AGRESSIVE, false);	
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
	public int getMaxAOEProgress() {
		return maxAOEProgress;
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
					DamageSource damageSource = ListaDamageSources.TRAK_AOE(this);
					if(this instanceof IHasFases) {
						damageSource = ListaDamageSources.FASED_ENTITY((PrumTrakMonsterEntity & IHasFases) this, damageSource);
					}
					entidad.attackEntityFrom(damageSource, (float) this.getAttributeValue(ListaAtributos.TRAK_AOE_ATTACK_DAMAGE));
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

	@Override
	public int getMaxRangedProgress() {
		return this.maxRangedProgress;
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
			proyectil.setDamage(this.getAttributeValue(ListaAtributos.RANGE_ATTACK_DAMAGE));
			double d0 = target.getPosX() -  proyectil.getPosX();
			double d1 = target.getPosYHeight(0.5D) -  proyectil.getPosY();
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
				.createMutableAttribute(ListaAtributos.TRAK_AOE_ATTACK_DAMAGE)
				.createMutableAttribute(ListaAtributos.RANGE_ATTACK_DAMAGE);
	}
}
