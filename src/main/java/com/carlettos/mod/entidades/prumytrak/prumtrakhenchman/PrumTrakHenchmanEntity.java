package com.carlettos.mod.entidades.prumytrak.prumtrakhenchman;

import com.carlettos.mod.entidades.bihead.ia.BiHeadLookRandomlyGoal;
import com.carlettos.mod.entidades.dummyboi.DummyBoiEntity;
import com.carlettos.mod.entidades.prumytrak.PrumTrakMonsterEntity;
import com.carlettos.mod.entidades.prumytrak.prum.ia.PrumRangedAttackGoal;
import com.carlettos.mod.entidades.prumytrak.trak.ia.TrakAOEAttackGoal;
import com.carlettos.mod.listas.ListaAtributos;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;

public class PrumTrakHenchmanEntity extends PrumTrakMonsterEntity{
	
	public PrumTrakHenchmanEntity(EntityType<? extends PrumTrakHenchmanEntity> type, World worldIn) {
		super(type, worldIn, 40, 20);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1D, false));
		this.goalSelector.addGoal(2, new PrumRangedAttackGoal<>(this, 20));
		this.goalSelector.addGoal(3, new TrakAOEAttackGoal<>(this, true, 3D));
		this.goalSelector.addGoal(4, new BiHeadLookRandomlyGoal<>(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<DummyBoiEntity>(this, DummyBoiEntity.class, 0, true, false, DummyBoiEntity.PREDICATE));
	}
	
	public static AttributeModifierMap.MutableAttribute getAtributos(){
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 40D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 32D)
				.createMutableAttribute(Attributes.ARMOR, 4D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 6D)
				.createMutableAttribute(ListaAtributos.TRAK_AOE_ATTACK_DAMAGE, 5D)
				.createMutableAttribute(ListaAtributos.RANGE_ATTACK_DAMAGE, 7D);
	}
}
