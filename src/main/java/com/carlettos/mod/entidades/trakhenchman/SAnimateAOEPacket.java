package com.carlettos.mod.entidades.trakhenchman;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;

public class SAnimateAOEPacket implements IPacket<IClientPlayNetHandler>{
	private int entityId;
	
	public SAnimateAOEPacket(Entity entity) {
		this.entityId = entity.getEntityId();
	}
	
	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		this.entityId = buf.readVarInt();
	}

	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
		buf.writeVarInt(this.entityId);
	}

	@Override
	public void processPacket(IClientPlayNetHandler handler) {
		PacketThreadUtil.checkThreadAndEnqueue(this, handler, Minecraft.getInstance());
		Entity entity = ((ClientPlayNetHandler)handler).getWorld().getEntityByID(this.getEntityId());
		if(entity != null) {
			((TrakHenchmanEntity)entity).AOEAnimation(false);
		}
	}

	public int getEntityId() {
		return entityId;
	}
}
