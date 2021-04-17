package com.carlettos.mod.entidades.prumytrak.prumtrakhenchman;

import com.carlettos.mod.entidades.prumytrak.bihead.BiHeadMonsterEntity;
import com.carlettos.mod.entidades.prumytrak.bihead.ia.BiHeadLookRandomlyGoal;
import com.carlettos.mod.listas.ListaAtributos;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;

public class PrumTrakHenchmanEntity extends BiHeadMonsterEntity{
	
	public PrumTrakHenchmanEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new BiHeadLookRandomlyGoal<>(this));
	}
	
	public static AttributeModifierMap.MutableAttribute getAtributos(){
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 40D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 1.8D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 32D)
				.createMutableAttribute(Attributes.ARMOR, 4D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 6D)
				.createMutableAttribute(ListaAtributos.RANGE_ATTACK_DAMAGE, 10D)
				.createMutableAttribute(ListaAtributos.TRAK_AOE_ATTACK_DAMAGE, 5D);
	}
}
