package com.redtoorange.delver.entities.inventory;

import com.badlogic.gdx.utils.Array;
import com.redtoorange.delver.entities.Character;

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
		currentWeight += i.weight;
		items.add( i );
	}

	public void removeItem(Item i){
		currentWeight -= i.weight;
		items.removeValue( i, true );
	}

	private void calculateWeight(){
		currentWeight = 0;

		for(int i = 0; i < items.size; i++){
			currentWeight += items.get( i ).weight;
		}
	}

}
