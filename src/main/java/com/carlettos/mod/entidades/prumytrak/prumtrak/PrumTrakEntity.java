package com.carlettos.mod.entidades.prumytrak.prumtrak;

import java.util.Set;
import java.util.function.Predicate;

import com.carlettos.mod.entidades.bihead.BiHeadMonsterEntity;
import com.carlettos.mod.entidades.bihead.ia.BiHeadLookRandomlyGoal;
import com.carlettos.mod.entidades.interfaces.IHasFases;
import com.carlettos.mod.entidades.prumytrak.prum.IPrumRangedAttack;
import com.carlettos.mod.entidades.prumytrak.prum.ia.PrumRangedAttackGoal;
import com.carlettos.mod.entidades.prumytrak.prum.prumproyectil.PrumProyectilEntity;
import com.carlettos.mod.entidades.prumytrak.trak.ITrakAOE;
import com.carlettos.mod.entidades.prumytrak.trak.ia.TrakAOEAttackGoal;
import com.carlettos.mod.listas.ListaAtributos;
import com.carlettos.mod.listas.ListaDamageSources;
import com.carlettos.mod.listas.ListaItem;
import com.carlettos.mod.listas.ListaParticulas;
import com.carlettos.mod.util.SCAnimatePackage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

//TODO: que no despawnee
//TODO: que el agua no le haga counter
public class PrumTrakEntity extends BiHeadMonsterEntity implements IPrumRangedAttack, ITrakAOE, IHasFases{
	public static final Set<Item> RUNAS_PRUM = Set.of(ListaItem.RUNA_PRUM, ListaItem.RUNA_RUDU, ListaItem.RUNA_UNK, ListaItem.RUNA_MIH);
	public static final Set<Item> RUNAS_TRAK = Set.of(ListaItem.RUNA_TRAK, ListaItem.RUNA_RUDU, ListaItem.RUNA_AMAN, ListaItem.RUNA_KEL);
	public static final Set<Item> RUNAS_PRUM_Y_TRAK = Set.of(ListaItem.RUNA_PRUM, ListaItem.RUNA_RUDU, ListaItem.RUNA_UNK, ListaItem.RUNA_MIH, ListaItem.RUNA_TRAK, ListaItem.RUNA_AMAN, ListaItem.RUNA_KEL);
	public static final DataParameter<Boolean> AOE_AGRESSIVE = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Boolean> RANGED_AGRESSIVE = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Byte> FASE = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.BYTE);
	public static final Predicate<LivingEntity> TARGET_RUNE_PLAYER = (entidad) -> {
		if(!(entidad instanceof PlayerEntity)) {
			return false;
		}
		return ((PlayerEntity)entidad).inventory.hasAny(RUNAS_PRUM_Y_TRAK);
	};
	
	private final ServerBossInfo bossInfo = new ServerBossInfo(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS);
	
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
		this.experienceValue = 2500;
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new PrumRangedAttackGoal<>(this, 10));
		this.goalSelector.addGoal(2, new TrakAOEAttackGoal<>(this, true, 7));
		this.goalSelector.addGoal(3, new BiHeadLookRandomlyGoal<>(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<SheepEntity>(this, SheepEntity.class, 0, true, false, (entidad) -> {return entidad.isAlive();}));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 0, true, false, (entidad) -> {return entidad.isAlive();}));
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(AOE_AGRESSIVE, false);
		this.dataManager.register(RANGED_AGRESSIVE, false);
		this.dataManager.register(FASE, (byte) 0);
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
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if(this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
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
	
	@Override
	public void addTrackingPlayer(ServerPlayerEntity player) {
		this.bossInfo.addPlayer(player);
	}
	
	@Override
	public void removeTrackingPlayer(ServerPlayerEntity player) {
		this.bossInfo.removePlayer(player);
	}
	
	//TODO: armadura
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
	
	//TODO: invulnerabilidad
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
	
	//TODO: AttackEntityFrom
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
	
	@Override
	protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
		ItemEntity prum = this.entityDropItem(ListaItem.RUNA_PRUM);
		ItemEntity trak = this.entityDropItem(ListaItem.RUNA_TRAK);
		if(prum != null) {
			prum.setNoDespawn();
		}
		if(trak != null) {
			trak.setNoDespawn();
		}
	}

	@Override
	public void actualizarFase() {
		this.world.getProfiler().startSection("update fase");
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
	public boolean isNonBoss() {
		return false;
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return false;
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
					entidad.attackEntityFrom(ListaDamageSources.FASED_ENTITY(this, ListaDamageSources.TRAK_AOE(this)), (float) this.getAttributeValue(ListaAtributos.TRAK_AOE_ATTACK_DAMAGE));
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
