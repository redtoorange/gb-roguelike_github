package com.redtoorange.delver.entities.inventory;

import com.badlogic.gdx.utils.Array;
import com.redtoorange.delver.entities.Character;
import com.redtoorange.delver.entities.inventory.types.Equipable;
import com.redtoorange.delver.entities.inventory.types.Item;

public class Inventory {

	private Character owner;
	private Array<Item> items;
	private float currentWeight = 0.0f;
	private float maxWeight = 0.0f;

	public Inventory(Character owner){
		this.owner = owner;
		items = new Array<Item>(  );
	}

	public void addItem(Item i){
		currentWeight += i.getWeight();
		items.add( i );
		i.setVisible( false );


		if(i instanceof Equipable ){
			( (Equipable) i ).equip(owner);
		}
	}

	public void removeItem(Item i){
		if(i instanceof Equipable){
			( (Equipable) i ).unequip(owner);
		}

		currentWeight -= i.getWeight();
		items.removeValue( i, true );
		i.setVisible( true );
	}

	public Item getItem(int i){
		if(i < 0 || i > items.size - 1)
			return null;

		return items.get( i );
	}



	private void calculateWeight(){
		currentWeight = 0;

		for(int i = 0; i < items.size; i++){
			currentWeight += items.get( i ).getWeight();
		}
	}
}
