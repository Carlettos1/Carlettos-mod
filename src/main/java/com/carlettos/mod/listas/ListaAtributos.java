package com.carlettos.mod.listas;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class ListaAtributos {
	public static final Attribute TRAK_AOE_ATTACK_DAMAGE;
	public static final Attribute RANGE_ATTACK_DAMAGE;
	public static final Attribute AMAN_EGG_COUNT;
	public static final Attribute EFFECT_AMPLIFIER;
	public static final Attribute CANTIDAD_PROYECTILES;
	
	static {
		TRAK_AOE_ATTACK_DAMAGE = new RangedAttribute("attribute.name.trak.aoe_attack_damage", 2D, 0D, 128D).setShouldWatch(true);
		RANGE_ATTACK_DAMAGE = new RangedAttribute("attribute.name.generic.range_attack_damage", 0D, 0D, 128D).setShouldWatch(true);
		AMAN_EGG_COUNT = new RangedAttribute("attribute.name.aman.egg_count", 0D, 0D, 10D).setShouldWatch(true);
		EFFECT_AMPLIFIER = new RangedAttribute("attribute.name.generic.effect_amplifier", 0D, 0D, 255D).setShouldWatch(true);
		CANTIDAD_PROYECTILES = new RangedAttribute("attribute.name.generic.cantidad_proyectiles", 0D, 0D, 255D).setShouldWatch(true);
	}
}
