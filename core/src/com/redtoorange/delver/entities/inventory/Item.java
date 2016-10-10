package com.redtoorange.delver.entities.inventory;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.redtoorange.delver.Tile;
import com.redtoorange.delver.utility.Draws;

public abstract class Item implements Draws {
	protected String name;
	protected Tile tile;
	protected Sprite sprite;

	protected float weight;
	protected float x, y;
	protected float scale;

	protected boolean visible = true;

	public Item( String name, float weight, TextureRegion texReg,
				 float x, float y, float scale, Tile tile ){
		this.name = name;
		this.weight = weight;
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.tile = tile;

		sprite = new Sprite( texReg );
		sprite.setPosition( x, y );
		sprite.setScale( scale );
	}

	@Override
	public void draw( SpriteBatch batch ) {
		if(sprite != null && visible)
			sprite.draw( batch );
	}

	public void setVisible(boolean visible){
		this.visible = visible;
	}
}
