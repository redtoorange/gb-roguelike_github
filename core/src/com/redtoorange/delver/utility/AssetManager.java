package com.redtoorange.delver.utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {
	private com.badlogic.gdx.assets.AssetManager assetManager;

	public AssetManager(){
		assetManager = new com.badlogic.gdx.assets.AssetManager(  );
		assetManager.load( "gbaTileSet.png", Texture.class );
	}

	public TextureRegion getAsset(String assetName){
		return null;
	}
}
