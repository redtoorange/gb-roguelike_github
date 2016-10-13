package com.redtoorange.delver.entities.inventory.types;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.redtoorange.delver.utility.Map.Tile;
import com.redtoorange.delver.utility.Draws;

public abstract class Item implements Draws {
	protected String name;
	private Tile tile;
	protected Sprite sprite;

	protected float weight;
	private float x;
	private float y;
	protected float scale;
	protected boolean visible = true;

	public Item( String name, float weight, TextureRegion texReg,
				 float x, float y, float scale, Tile tile ){
		this.name = name;
		this.weight = weight;
		this.setX( x );
		this.setY( y );
		this.scale = scale;
		this.tile = tile;

		sprite = new Sprite( texReg );
		sprite.setScale( scale );
		sprite.setOrigin( 0, 0 );
		sprite.setPosition( x, y );

		tile.addItem( this );
	}

	@Override
	public void draw( SpriteBatch batch ) {
		if(sprite != null && visible)
			sprite.draw( batch );
	}

	public float getWeight(){
		return weight;
	}

	public void setVisible(boolean visible){
		this.visible = visible;
	}

	public float getX() {
		return x;
	}

	public void setX( float x ) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY( float y ) {
		this.y = y;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile( Tile tile ) {
		this.tile = tile;

		setX( tile.getWorldPositionX() );
		setY( tile.getWorldPositionY() );

		sprite.setPosition( x, y );
	}

	public String getName(){
		return name;
	}
}
