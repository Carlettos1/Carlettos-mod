package com.carlettos.mod.util;

import javax.annotation.Nullable;

import com.carlettos.mod.entidades.aman.amanspit.AmanSpitEntity;
import com.carlettos.mod.entidades.prumytrak.prum.prumproyectil.PrumProyectilEntity;
import com.carlettos.mod.listas.ListaEntidades;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.world.World;

public class SCSpawnObjectPacket extends SSpawnObjectPacket{
	private int amplifier;
	
	public SCSpawnObjectPacket(Entity entity, int type, @Nullable int amplifier) {
		super(entity, type);
		this.amplifier = amplifier;
	}
	@Override
	public void processPacket(IClientPlayNetHandler handler) {
		PacketThreadUtil.checkThreadAndEnqueue(this, handler, Minecraft.getInstance());
		World world = ((ClientPlayNetHandler)handler).getWorld();
		Entity entity;
		
		if(getType() == ListaEntidades.AMAN_SPIT) {
			Entity entity1 = world.getEntityByID(this.getData());
			entity = new AmanSpitEntity(world, this.getX(), this.getY(), this.getZ(), this.getAmplifier());
			if(entity1 != null) {
				((AmanSpitEntity)entity).setShooter(entity1);
			}
		} else if (getType() == ListaEntidades.PRUM_PROYECTIL){
			Entity entity2 = world.getEntityByID(this.getData());
			entity = new PrumProyectilEntity(world, this.getX(), this.getY(), this.getZ());
			if(entity2 != null) {
				((PrumProyectilEntity)entity).setShooter(entity2);
			}
		} else {
			entity = null;
		}
		
		if(entity != null) {
			entity.setPacketCoordinates(getX(), getY(), getZ());
			entity.moveForced(getX(), getY(), getZ());
			entity.rotationPitch = (float)getPitch() * 360F / 256F;
			entity.rotationYaw = (float)getYaw() * 360F / 256F;
			entity.setEntityId(getEntityID());
			entity.setUniqueId(getUniqueId());
			if(world instanceof ClientWorld) {
				((ClientWorld)world).addEntity(getEntityID(), entity);
			}
		}
	}
	
	public int getAmplifier() {
		return amplifier;
	}
}
