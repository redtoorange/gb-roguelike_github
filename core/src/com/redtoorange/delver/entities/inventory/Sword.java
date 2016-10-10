package com.redtoorange.delver.entities.inventory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.redtoorange.delver.Tile;

public class Sword extends Item{
	public Sword( String name, float weight, TextureRegion texReg, float x, float y, float scale, Tile tile ) {
		super( name, weight, texReg, x, y, scale, tile );
	}

	@Override
	public void draw( SpriteBatch batch ) {
		super.draw( batch );
	}
}
