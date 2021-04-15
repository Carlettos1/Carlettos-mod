package com.carlettos.mod.entidades.prumytrak.prumtrakhenchman;

import com.carlettos.mod.entidades.prumytrak.bihead.IBiHead;
import com.carlettos.mod.entidades.prumytrak.bihead.controllers.BiHeadLookController;
import com.carlettos.mod.entidades.prumytrak.bihead.ia.LookRandomlyGoal;
import com.carlettos.mod.listas.ListaAtributos;
import com.carlettos.mod.listas.ListaSerializers;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class PrumTrakHenchmanEntity extends MonsterEntity implements IBiHead<PrumTrakHenchmanEntity>{
	public static final DataParameter<Vector3f> PREV_SECOND_HEAD_LOOKING_POS = EntityDataManager.createKey(PrumTrakHenchmanEntity.class, ListaSerializers.VECTOR3F);
	public static final DataParameter<Vector3f> SECOND_HEAD_LOOKING_POS = EntityDataManager.createKey(PrumTrakHenchmanEntity.class, ListaSerializers.VECTOR3F);
	
	public PrumTrakHenchmanEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.lookController = new BiHeadLookController<PrumTrakHenchmanEntity>(this);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new LookRandomlyGoal<PrumTrakHenchmanEntity>(this));
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(PREV_SECOND_HEAD_LOOKING_POS, new Vector3f(0, 0, 0));
		this.dataManager.register(SECOND_HEAD_LOOKING_POS, new Vector3f(0, 0, 0));
	}
	
	@Override
	public void baseTick() {
		super.baseTick();
		this.setPrevSecondHeadLookingPos(this.getSecondHeadLookingPos());
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

	@SuppressWarnings("unchecked")
	@Override
	public BiHeadLookController<PrumTrakHenchmanEntity> getLookController2() {
		return (BiHeadLookController<PrumTrakHenchmanEntity>) this.lookController;
	}
	
	@Override
	public Vector3f getPrevSecondHeadLookingPos() {
		return this.dataManager.get(PREV_SECOND_HEAD_LOOKING_POS);
	}

	@Override
	public void setPrevSecondHeadLookingPos(Vector3f v3f) {
		this.dataManager.set(PREV_SECOND_HEAD_LOOKING_POS, v3f);
	}

	@Override
	public Vector3f getSecondHeadLookingPos() {
		return this.dataManager.get(SECOND_HEAD_LOOKING_POS);
	}

	@Override
	public void setSecondHeadLookingPos(Vector3f v3f) {
		this.dataManager.set(SECOND_HEAD_LOOKING_POS, v3f);
	}
}
