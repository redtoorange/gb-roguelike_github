package com.redtoorange.delver.entities.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemSheet {
	public String name;

	private float weight = 1.0f;

	private int startX;
	private int startY;

	private int width;
	private int height;

	private static String textureSource = "gbaTileSet.png";
	private static Texture texture = new Texture( textureSource );

	private TextureRegion texReg;


	//TODO: Pull from a JSON file at some point...
	public ItemSheet( String name, int startX, int startY, int width, int height){
		this.name = name;
		this.startX = startX;
		this.startY = startY;

		this.width = width;
		this.height = height;

		texReg = new TextureRegion( texture, startX, startY, width, height );
	}

	public TextureRegion getTextureRegion(){
		return texReg;
	}

	public static ItemSheet Sword = new ItemSheet("Sword", 0, 224, 32, 32);
}