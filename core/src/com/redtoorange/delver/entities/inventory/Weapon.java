package com.redtoorange.delver.entities.inventory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.redtoorange.delver.Tile;
import com.redtoorange.delver.entities.Character;
import com.redtoorange.delver.utility.Dice;

public class Weapon extends Item implements Equipable {
	protected Dice damageDie;

	public Weapon( String name, Dice damageDie, float weight, TextureRegion texReg, float x, float y, float scale, Tile tile ) {
		super( name, weight, texReg, x, y, scale, tile );

		this.damageDie = damageDie;
	}

	@Override
	public void draw( SpriteBatch batch ) {
		super.draw( batch );
	}

	@Override
	public void equip( Character c) {
		c.setAttackDamage( damageDie );
	}

	@Override
	public void unequip(Character c) {
		c.setAttackDamage( c.getDefaultAttackDie() );
	}
}
