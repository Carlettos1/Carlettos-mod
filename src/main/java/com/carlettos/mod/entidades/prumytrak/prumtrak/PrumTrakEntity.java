package com.carlettos.mod.entidades.prumytrak.prumtrak;

import java.util.Set;
import java.util.function.Predicate;

import com.carlettos.mod.entidades.bihead.ia.BiHeadLookRandomlyGoal;
import com.carlettos.mod.entidades.dummyboi.DummyBoiEntity;
import com.carlettos.mod.entidades.interfaces.IHasFases;
import com.carlettos.mod.entidades.prumytrak.PrumTrakMonsterEntity;
import com.carlettos.mod.entidades.prumytrak.prum.ia.PrumRangedAttackGoal;
import com.carlettos.mod.entidades.prumytrak.trak.ia.TrakAOEAttackGoal;
import com.carlettos.mod.listas.ListaAtributos;
import com.carlettos.mod.listas.ListaItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
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
import net.minecraftforge.common.ForgeHooks;

//TODO: que no despawnee
//TODO: que el agua no le haga counter
public class PrumTrakEntity extends PrumTrakMonsterEntity implements IHasFases{
	public static final Set<Item> RUNAS_PRUM = Set.of(ListaItem.RUNA_PRUM, ListaItem.RUNA_RUDU, ListaItem.RUNA_UNK, ListaItem.RUNA_MIH);
	public static final Set<Item> RUNAS_TRAK = Set.of(ListaItem.RUNA_TRAK, ListaItem.RUNA_RUDU, ListaItem.RUNA_AMAN, ListaItem.RUNA_KEL);
	public static final Set<Item> RUNAS_PRUM_Y_TRAK = Set.of(ListaItem.RUNA_PRUM, ListaItem.RUNA_RUDU, ListaItem.RUNA_UNK, ListaItem.RUNA_MIH, ListaItem.RUNA_TRAK, ListaItem.RUNA_AMAN, ListaItem.RUNA_KEL);
	public static final DataParameter<Byte> FASE = EntityDataManager.createKey(PrumTrakEntity.class, DataSerializers.BYTE);
	public static final Predicate<LivingEntity> TARGET_RUNE_PLAYER = (entidad) -> {
		if(!(entidad instanceof PlayerEntity)) {
			return false;
		}
		return ((PlayerEntity)entidad).inventory.hasAny(RUNAS_PRUM_Y_TRAK);
	};
	
	private final ServerBossInfo bossInfo = new ServerBossInfo(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS);
	
	
	public PrumTrakEntity(EntityType<? extends PrumTrakEntity> type, World worldIn) {
		super(type, worldIn, 10, 10);
		this.experienceValue = 2500;
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1D, false));
		this.goalSelector.addGoal(2, new PrumRangedAttackGoal<>(this, 20));
		this.goalSelector.addGoal(3, new TrakAOEAttackGoal<>(this, true, 7D));
		this.goalSelector.addGoal(4, new BiHeadLookRandomlyGoal<>(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<DummyBoiEntity>(this, DummyBoiEntity.class, 0, true, false, DummyBoiEntity.PREDICATE));
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(FASE, (byte) 0);
	}
	
	@Override
	public void tick() {
		super.tick();
		this.actualizarFase();
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
	
	public static AttributeModifierMap.MutableAttribute getAtributos(){
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 210D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 128D)
				.createMutableAttribute(Attributes.ARMOR, 7D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 10D)
				.createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1D)
				.createMutableAttribute(ListaAtributos.TRAK_AOE_ATTACK_DAMAGE, 8D)
				.createMutableAttribute(ListaAtributos.RANGE_ATTACK_DAMAGE, 10D);
	}
}
