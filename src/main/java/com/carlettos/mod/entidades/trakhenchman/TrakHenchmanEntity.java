package com.carlettos.mod.entidades.trakhenchman;

import com.carlettos.mod.listas.ListaEntidades;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.world.World;

public class TrakHenchmanEntity extends MonsterEntity{

	public TrakHenchmanEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(ListaEntidades.TRAK_HENCHMAN, worldIn);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1D, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<SheepEntity>(this, SheepEntity.class, 0, true, false, (entidad) -> {return entidad.getClass().equals(SheepEntity.class);}));
	}
	
	public static AttributeModifierMap.MutableAttribute getAtributos(){
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 40D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 1D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 32D)
				.createMutableAttribute(Attributes.ARMOR, 4D);
	}
}
