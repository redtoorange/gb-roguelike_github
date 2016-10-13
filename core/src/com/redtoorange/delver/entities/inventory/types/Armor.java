package com.redtoorange.delver.entities.inventory.types;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.redtoorange.delver.utility.Map.Tile;
import com.redtoorange.delver.entities.Character;

public class Armor extends Item implements Equipable {
	@Override
	public void equip( Character c ) {

	}

	@Override
	public void unequip( Character c ) {

	}

	public Armor( String name, float weight, TextureRegion texReg, float x, float y, float scale, Tile tile ) {
		super( name, weight, texReg, x, y, scale, tile );
	}

	@Override
	public void draw( SpriteBatch batch ) {
		super.draw( batch );
	}
}
