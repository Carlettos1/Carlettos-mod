package com.carlettos.mod.entidades.prum.prumproyectil;

import com.carlettos.mod.listas.ListaEntidades;
import com.carlettos.mod.listas.ListaParticulas;
import com.carlettos.mod.util.SCSpawnObjectPacket;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
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
	public static final DataParameter<BlockPos> TARGET_BLOCKPOS = EntityDataManager.createKey(PrumProyectilEntity.class, DataSerializers.BLOCK_POS);
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
		this.dataManager.set(HAS_TARGET, true);
		this.dataManager.set(TARGET_BLOCKPOS, target.getPosition());
	}
	
	public BlockPos getTargetPos() {
		return this.dataManager.get(TARGET_BLOCKPOS);
	}
	
	public boolean hasTarget() {
		return this.dataManager.get(HAS_TARGET);
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(TARGET_BLOCKPOS, new BlockPos(0, 0, 0));
		this.dataManager.register(HAS_TARGET, false);
	}
	
	@Override
	protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
		super.onEntityHit(p_213868_1_); //TODO: reescribir esto para cambiar damageSources y comportamientos
	}
	
	@Override
	protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
		this.remove(); //onBlockHit
	}
	
	@Override
	protected void func_225516_i_() {
		this.remove();
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
			BlockPos target = this.getTargetPos();
			Vector3d radioVector = new Vector3d(target.getX() - this.getPosX(), 2 * (target.getY() - this.getPosY()), target.getZ() - this.getPosZ()).normalize().scale((1 + this.ticksExisted / 10D));
			
			vector3d4 = vector3d4.add(radioVector);
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
