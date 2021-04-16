package com.carlettos.mod.util;

import java.io.IOException;

import com.carlettos.mod.entidades.aman.IAmanEggHatch;
import com.carlettos.mod.entidades.aman.IAmanSpit;
import com.carlettos.mod.entidades.prumytrak.bihead.IBiHead;
import com.carlettos.mod.entidades.prumytrak.prum.IPrumRangedAttack;
import com.carlettos.mod.entidades.prumytrak.trak.ITrakAOE;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;

public class SCAnimatePackage implements IPacket<IClientPlayNetHandler>{
	public static final int TRAK_AOE_ANIMATION_ID = 10;
	public static final int PRUM_RANGED_ATTACK_ANIMATION_ID = 11;
	public static final int AMAN_SPIT_ANIMATION_ID = 0;
	public static final int AMAN_EGG_HATCH_ANIMATION_ID = 1;
	public static final int GIRO_SEGUNDA_CABEZA_ANIMATION_ID = 2;
	
	private int entityId;
	private int animationId;
	
	public SCAnimatePackage(Entity entity, int animationId) {
		this.entityId = entity.getEntityId();
		this.animationId = animationId;
	}
	
	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		this.entityId = buf.readVarInt();
		this.animationId = buf.readVarInt();
	}

	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
		buf.writeVarInt(this.entityId);
		buf.writeVarInt(this.animationId);
	}

	@Override
	public void processPacket(IClientPlayNetHandler handler) {
		PacketThreadUtil.checkThreadAndEnqueue(this, handler, Minecraft.getInstance());
		Entity entity = ((ClientPlayNetHandler)handler).getWorld().getEntityByID(this.getEntityId());
		if(entity != null) {
			switch (this.getAnimationId()) {
			case TRAK_AOE_ANIMATION_ID:
				((ITrakAOE)entity).AOEAnimation(false);
				break;
			case PRUM_RANGED_ATTACK_ANIMATION_ID:
				((IPrumRangedAttack)entity).rangedAnimation(false);
				break;
			case AMAN_EGG_HATCH_ANIMATION_ID:
				((IAmanEggHatch)entity).hatchAnimation(false);
				break;
			case AMAN_SPIT_ANIMATION_ID:
				((IAmanSpit)entity).spitAnimation(false);
				break;
			case GIRO_SEGUNDA_CABEZA_ANIMATION_ID:
				((IBiHead<?>)entity).girarSegundaCabezaAnimation(false);
				break;
			}
		}
	}

	public int getEntityId() {
		return this.entityId;
	}
	
	public int getAnimationId() {
		return this.animationId;
	}
}
