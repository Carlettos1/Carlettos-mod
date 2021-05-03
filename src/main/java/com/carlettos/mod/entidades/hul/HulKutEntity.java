package com.carlettos.mod.entidades.hul;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import com.carlettos.mod.listas.ListaEntidades;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class HulKutEntity extends MonsterEntity{
	public Set<HulKutEntity> indefiniciones = new HashSet<>();

	public HulKutEntity(EntityType<? extends HulKutEntity> type, World worldIn) {
		super(type, worldIn);
		this.indefiniciones.add(this);
	}

	public HulKutEntity(World worldIn, HulKutEntity hulkut) {
		super(ListaEntidades.HUL_KUT, worldIn);
		this.indefiniciones.add(hulkut);
		hulkut.indefiniciones.add(this);
		this.indefiniciones.addAll(hulkut.indefiniciones);
		for (HulKutEntity hk : this.indefiniciones) {
			hk.indefiniciones.add(this);
		}
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(super.attackEntityFrom(source, amount)) {
			int real = this.getRNG().nextInt(this.indefiniciones.size());
			HulKutEntity[] hks = this.indefiniciones.toArray(new HulKutEntity[0]);
			HulKutEntity hkreal = hks[real];
			this.indefiniciones.remove(hkreal);
			this.indefiniciones.forEach((hk) -> {hk.remove();});
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new QuantumFadingGoal(this));
	}
	
	public void quantificar() {
		HulKutEntity hulkut = new HulKutEntity(this.world, this);
		this.indefiniciones.add(hulkut);
		hulkut.setPosition(this.getPosX() + (1 - 2 * this.getRNG().nextDouble()), this.getPosY(), this.getPosZ() + (1 - 2 * this.getRNG().nextDouble()));
		this.world.addEntity(hulkut);
	}
	
	public static AttributeModifierMap.MutableAttribute getAtributos(){
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 40D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 32D)
				.createMutableAttribute(Attributes.ARMOR, 4D);
	}
	
	private class QuantumFadingGoal extends Goal{
		private HulKutEntity entity;
		private int counter;
		public QuantumFadingGoal(HulKutEntity entity) {
			this.entity = entity;
			this.setMutexFlags(EnumSet.noneOf(Goal.Flag.class));
		}
		
		@Override
		public boolean shouldExecute() {
			return true;
		}
		
		@Override
		public void tick() {
			if(++counter >= 100) {
				this.counter = 0;
				this.entity.quantificar();
			}
		}
	}
}
