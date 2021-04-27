package com.carlettos.mod.entidades.aman.amanspit;

import com.carlettos.mod.entidades.interfaces.IHasFases;
import com.carlettos.mod.listas.ListaDamageSources;
import com.carlettos.mod.listas.ListaEntidades;
import com.carlettos.mod.util.SCSpawnObjectPacket;
import com.carlettos.mod.util.Util;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class AmanSpitEntity extends AbstractArrowEntity{
	private int amplifier = 0;

	public AmanSpitEntity(EntityType<AmanSpitEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public AmanSpitEntity(World worldIn, int amplifier) {
		super(ListaEntidades.AMAN_SPIT, worldIn);
		this.amplifier = amplifier;
	}
	
	public AmanSpitEntity(World worldIn, double x, double y, double z, int amplifier) {
		super(ListaEntidades.AMAN_SPIT, x, y, z, worldIn);
		this.amplifier = amplifier;
	}
	
	@Override
	protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
		super.func_230299_a_(p_230299_1_);
		this.remove();
	}
	
	@Override
	protected void func_225516_i_() {
		super.func_225516_i_();
		Util.LOG.info("func_225516_i_ no debería usarse");
	}
	
	@Override
	protected ItemStack getArrowStack() {
		return ItemStack.EMPTY;
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		Entity entity = this.func_234616_v_();
		return new SCSpawnObjectPacket(this, entity == null ? 0 : entity.getEntityId(), this.amplifier);
	}
	
	@Override
	protected void arrowHit(LivingEntity living) {
		living.addPotionEffect(new EffectInstance(Effects.POISON, 5 * 20, this.amplifier));
		living.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 5 * 20, this.amplifier));
	}
	
	@Override
	protected void onEntityHit(EntityRayTraceResult raytrace) {
		Entity objetivo = raytrace.getEntity();
		double motion = this.getMotion().length();      
		int i = MathHelper.ceil(MathHelper.clamp(motion * this.getDamage(), 0.0D, 2.147483647E9D));
		Entity shooter = this.func_234616_v_();
		DamageSource source;
		if(shooter == null) {
			source = ListaDamageSources.AMAN_SPIT(this, this);
		} else {
			source = ListaDamageSources.AMAN_SPIT(this, shooter);
			if(shooter instanceof IHasFases) {
				source = ListaDamageSources.FASED_ENTITY((MonsterEntity & IHasFases)shooter, source);
			}
			if(shooter instanceof LivingEntity) {
				((LivingEntity) shooter).setLastAttackedEntity(objetivo);
			}
		}
		boolean isEnderman = objetivo.getType() == EntityType.ENDERMAN;
		int k = objetivo.getFireTimer();
		if(this.isBurning() && !isEnderman) {
			objetivo.setFire(5);
		}
		
		if(objetivo.attackEntityFrom(source, i)) {
			if(isEnderman) {
				return;
			}
			
			if(objetivo instanceof LivingEntity) {
				if(!this.world.isRemote && shooter instanceof LivingEntity) {
					EnchantmentHelper.applyThornEnchantments((LivingEntity) objetivo, shooter);
					EnchantmentHelper.applyArthropodEnchantments((LivingEntity) shooter, objetivo);
				}
			}
			
			this.arrowHit((LivingEntity) objetivo);
			if(shooter != null && objetivo != shooter && objetivo instanceof PlayerEntity && shooter instanceof ServerPlayerEntity && !this.isSilent()) {
				((ServerPlayerEntity)shooter).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241770_g_, 0F));
			}
		} else {
			objetivo.forceFireTicks(k);
			this.setMotion(this.getMotion().scale(-0.1D));
			this.rotationYaw += 180F;
			this.prevRotationYaw += 180F;
			if(!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {
				this.remove();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		if(!this.world.isRemote) {
			this.setFlag(6, this.isGlowing());
		}
		this.baseTick();
		
		Vector3d motion = this.getMotion();
		if(this.prevRotationPitch == 0F && this.prevRotationYaw == 0F) {
			float f = MathHelper.sqrt(horizontalMag(motion));
	        this.rotationYaw = (float)(MathHelper.atan2(motion.x, motion.z) * (double)(180F / (float)Math.PI));
	        this.rotationPitch = (float)(MathHelper.atan2(motion.y, (double)f) * (double)(180F / (float)Math.PI));
	        this.prevRotationYaw = this.rotationYaw;
	        this.prevRotationPitch = this.rotationPitch;
		}
		BlockPos blockpos = this.getPosition();
		BlockState blockstate = this.world.getBlockState(blockpos);
		if(!blockstate.isAir(this.world, blockpos) && !this.noClip) {
			VoxelShape voxels = blockstate.getCollisionShape(this.world, blockpos);
			if(!voxels.isEmpty()) {
				Vector3d pos = this.getPositionVec();
				for(AxisAlignedBB aabb : voxels.toBoundingBoxList()) {
					if(aabb.offset(blockpos).contains(pos)) {
						this.remove();
						break;
					}
				}
			}
		}
		Vector3d pos = this.getPositionVec();
		Vector3d posfinal = pos.add(motion);
		RayTraceResult raytrace = this.world.rayTraceBlocks(new RayTraceContext(pos, posfinal, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
		if(raytrace.getType() != RayTraceResult.Type.MISS) {
			posfinal = raytrace.getHitVec();
		}
		EntityRayTraceResult entityraytraceresult = this.rayTraceEntities(pos, posfinal);
		if (entityraytraceresult != null) {
			raytrace = entityraytraceresult;
		}

		if (raytrace != null && raytrace.getType() == RayTraceResult.Type.ENTITY) {
			Entity entity = ((EntityRayTraceResult) raytrace).getEntity();
			Entity entity1 = this.func_234616_v_();
			if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity
					&& !((PlayerEntity) entity1).canAttackPlayer((PlayerEntity) entity)) {
				raytrace = null;
				entityraytraceresult = null;
			}
		}

		if (raytrace != null && raytrace.getType() != RayTraceResult.Type.MISS && !this.noClip
				&& !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytrace)) {
			this.onImpact(raytrace);
			this.isAirBorne = true;
		}
		
		raytrace = null;
		motion = this.getMotion();
		double velx = motion.x;
		double vely = motion.y;
		double velz = motion.z;
		
		double xfinal = this.getPosX() + velx;
		double yfinal = this.getPosY() + vely;
		double zfinal = this.getPosZ() + velz;
		float horizontalmal = MathHelper.sqrt(velx * velx + velz * velz);
		if(this.noClip) {            
			this.rotationYaw = (float)(MathHelper.atan2(-velx, -velz) * (double)(180F / (float)Math.PI));
		} else {
            this.rotationYaw = (float)(MathHelper.atan2(velx, velz) * (double)(180F / (float)Math.PI));
		}
		this.rotationPitch = (float)(MathHelper.atan2(vely, horizontalmal) * 180D / Math.PI);
		this.rotationYaw = func_234614_e_(this.prevRotationYaw, this.rotationYaw);
		this.rotationPitch = func_234614_e_(this.prevRotationPitch, this.rotationPitch);
		this.setPosition(xfinal, yfinal, zfinal);
		this.doBlockCollisions();
	}
}
