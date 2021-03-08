package com.carlettos.mod.listas;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class ListaAtributos {
	public static final Attribute AOE_ATTACK_DAMAGE;
	
	static {
		AOE_ATTACK_DAMAGE = new RangedAttribute("attribute.name.trak.aoe_attack_damage", 2D, 0D, 128D).setShouldWatch(true);
	}
}
