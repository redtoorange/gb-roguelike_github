package com.redtoorange.delver.entities.inventory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.redtoorange.delver.Tile;
import com.redtoorange.delver.entities.Character;
import com.redtoorange.delver.utility.Dice;

public class Sword extends Weapon {
	public Sword( String name, Dice damageDie, float weight, TextureRegion texReg, float x, float y, float scale, Tile tile ) {
		super( name, damageDie, weight, texReg, x, y, scale, tile );
	}

	@Override
	public void equip( Character c ) {
		super.equip( c );
	}

	@Override
	public void unequip( Character c ) {
		super.unequip( c );
	}

	@Override
	public void draw( SpriteBatch batch ) {
		super.draw( batch );
	}
}
