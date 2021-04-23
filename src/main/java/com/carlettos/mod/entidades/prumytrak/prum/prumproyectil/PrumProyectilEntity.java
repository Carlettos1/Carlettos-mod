package com.carlettos.mod.entidades.prumytrak.prum.prumproyectil;

import com.carlettos.mod.entidades.interfaces.IHasFases;
import com.carlettos.mod.listas.ListaDamageSources;
import com.carlettos.mod.listas.ListaEntidades;
import com.carlettos.mod.listas.ListaParticulas;
import com.carlettos.mod.listas.ListaSerializers;
import com.carlettos.mod.util.SCSpawnObjectPacket;

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
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
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

public class PrumProyectilEntity extends AbstractArrowEntity{
	public static final DataParameter<Vector3d> TARGET_POSITION= EntityDataManager.createKey(PrumProyectilEntity.class, ListaSerializers.VECTOR3D);
	public static final DataParameter<Boolean> HAS_TARGET = EntityDataManager.createKey(PrumProyectilEntity.class, DataSerializers.BOOLEAN);

	public PrumProyectilEntity(EntityType<? extends AbstractArrowEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public PrumProyectilEntity(World worldIn, double x, double y, double z) {
		 super(ListaEntidades.PRUM_PROYECTIL, x, y, z, worldIn);
	}
	
	public PrumProyectilEntity(World worldIn, LivingEntity shooter) {
		super(ListaEntidades.PRUM_PROYECTIL, shooter, worldIn);
	}
	
	public PrumProyectilEntity(World worldIn, LivingEntity shooter, LivingEntity target) {
		super(ListaEntidades.PRUM_PROYECTIL, shooter, worldIn);
		this.dataManager.set(TARGET_POSITION, target.getPositionVec());
		this.dataManager.set(HAS_TARGET, true);
	}
	
	public Vector3d getTargetPos() {
		return this.dataManager.get(TARGET_POSITION);
	}
	
	public boolean hasTarget() {
		return this.dataManager.get(HAS_TARGET);
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(TARGET_POSITION, Vector3d.ZERO);
		this.dataManager.register(HAS_TARGET, false);
	}
	
	@Override
	protected void onEntityHit(EntityRayTraceResult raytrace) {
		Entity objetivo = raytrace.getEntity();
		double motion = this.getMotion().length();      
		int i = MathHelper.ceil(MathHelper.clamp(motion * this.getDamage(), 0.0D, 2.147483647E9D));
		Entity shooter = this.func_234616_v_();
		DamageSource source;
		if(shooter == null) {
			source = ListaDamageSources.PRUM_PROYECTIL(this, this);
		} else {
			source = ListaDamageSources.PRUM_PROYECTIL(this, shooter);
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
	
	@Override
	protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
		this.remove(); //onBlockHit
	}
	
	@Override
	protected void func_225516_i_() {
		this.remove(); //groundTick
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		if (!this.world.isRemote) {
          	this.setFlag(6, this.isGlowing());
        }
        this.baseTick();
		
		boolean flag = this.getNoClip();
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
		if(!blockstate.isAir(this.world, blockpos) && !flag) {
			VoxelShape voxels = blockstate.getCollisionShape(this.world, blockpos);
			if(!voxels.isEmpty()) {
				Vector3d pos = this.getPositionVec();
				for (AxisAlignedBB aabb : voxels.toBoundingBoxList()) {
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
		
		for(double i = 0; i < 10; i++) {
			this.world.addParticle(ListaParticulas.PRUM_PARTICULA, this.getPosX() + velx * i / 10D, this.getPosY() + vely * i / 10D + 0.125F, this.getPosZ() + velz * i / 10D, -velx, -vely + 0.2D, -velz);
		}
		double xfinal = this.getPosX() + velx;
		double yfinal = this.getPosY() + vely;
		double zfinal = this.getPosZ() + velz;
		float horizontalmal = MathHelper.sqrt(velx * velx + velz * velz);
		if(flag) {            
			this.rotationYaw = (float)(MathHelper.atan2(-velx, -velz) * (double)(180F / (float)Math.PI));
		} else {
            this.rotationYaw = (float)(MathHelper.atan2(velx, velz) * (double)(180F / (float)Math.PI));
		}
		this.rotationPitch = (float)(MathHelper.atan2(vely, horizontalmal) * 180D / Math.PI);
		this.rotationYaw = func_234614_e_(this.prevRotationYaw, this.rotationYaw);
		this.rotationPitch = func_234614_e_(this.prevRotationPitch, this.rotationPitch);
		if (this.isInWater()) {
			for (int j = 0; j < 4; ++j) {
				this.world.addParticle(ParticleTypes.BUBBLE, xfinal - velx * 0.25D, yfinal - vely * 0.25D, zfinal - velz * 0.25D, velx, vely, velz);
			}
		}
		
		Vector3d vector3d4 = this.getMotion();
		
		if(this.hasTarget()) {
			Vector3d target = this.getTargetPos();
			Vector3d radioVector = new Vector3d(target.getX() - this.getPosX(), target.getY() - this.getPosY(), target.getZ() - this.getPosZ());
			
			vector3d4 = vector3d4.add(radioVector).normalize().scale(this.getMotion().length());
		}
		this.setMotion(vector3d4.x, vector3d4.y, vector3d4.z);
		this.setPosition(xfinal, yfinal, zfinal);
		this.doBlockCollisions();
	}

	@Override
	protected ItemStack getArrowStack() {
		return ItemStack.EMPTY;
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		Entity entity = this.func_234616_v_();
		return new SCSpawnObjectPacket(this, entity == null ? 0 : entity.getEntityId());
	}
}
