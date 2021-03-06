package com.carlettos.mod.entidades.prumytrak.proyectil;

import com.carlettos.mod.listas.ListaEntidades;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.world.World;

public class PrumProyectilPacketHandler extends SSpawnObjectPacket{
	private World world;
	public PrumProyectilPacketHandler(Entity entityIn, int typeIn) {
		super(entityIn, typeIn);
	}
	
	@Override
	public void processPacket(IClientPlayNetHandler handler) {		
		PacketThreadUtil.checkThreadAndEnqueue(this, handler, Minecraft.getInstance());
		
		this.world = ((ClientPlayNetHandler)handler).getWorld();
		Entity entity;
		
		if(getType() == ListaEntidades.prum_proyectil) {
			Entity entity2 = this.world.getEntityByID(getData());
			entity = new PrumProyectilEntity(this.world, getX(), getY(), getZ());
			if(entity2 != null) {
				((PrumProyectilEntity)entity).setShooter(entity2);
			}
		} else {
			entity = null;
		}
		
		if(entity != null) {
			entity.setPacketCoordinates(getX(), getY(), getZ());
			entity.moveForced(getX(), getY(), getZ());
			entity.rotationPitch = (float)(getPitch() * 360) / 256.0F;
			entity.rotationYaw = (float)(getYaw() * 360) / 256.0F;
			entity.setEntityId(getEntityID());
			entity.setUniqueId(getUniqueId());
			if(this.world instanceof ClientWorld) {
				((ClientWorld)this.world).addEntity(getEntityID(), entity); //return false
			}
		}
	}
}
