package com.redtoorange.delver.entities.inventory;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.redtoorange.delver.utility.Map.Tile;
import com.redtoorange.delver.entities.inventory.types.Weapon;
import com.redtoorange.delver.utility.Dice;

public class WeaponFactory {
	private static Array<String> name = new Array<String>(  );
	private static Array<String> filePath = new Array<String>(  );

	private static String folder = "JSONFiles/";
	private static boolean weaponsPopulated;

	private static void populateWeapons(){
		name.add( "sword" );
		filePath.add( "sword.JSON" );

		name.add( "axe" );
		filePath.add( "axe.JSON" );
	}


	public static Weapon buildFromJSON(String type, Tile tile){
		if(!weaponsPopulated)
			populateWeapons();

		int index = 0;
		if(name.contains( type, false)){
			index = name.indexOf( type, false );
		}
		else{
			Gdx.app.log( "WeaponFactory", "Weapon type " + type + " not found." );
			return null;
		}

		JsonReader reader = new JsonReader();

		JsonValue value = reader.parse( Gdx.files.internal( folder + filePath.get( index ) ) );
		TextureRegion texReg = parseTextureRegion(value.get( "TextureRegion" ));
		Dice damageDie = parseDice( value.getInt( "damageDiceCount" ), value.getInt( "damageDiceSides" ) );

		Weapon wep = new Weapon( value.getString( "name" ), damageDie, value.getFloat( "weight" ), texReg, tile.getWorldPositionX(), tile.getWorldPositionY(),
				value.getFloat( "scale" ), tile);

		return wep;
	}

	public static Dice parseDice(int count, int sides){
		return new Dice(count, sides);
	}

	private static TextureRegion parseTextureRegion(JsonValue value){
		FileHandle file = Gdx.files.internal( value.getString( "Texture" ) );

		TextureRegion texReg = new TextureRegion( new Texture( file ),
				value.getInt( "x" ), value.getInt( "y" ), value.getInt( "width" ), value.getInt( "height" ));


		return texReg;
	}



}
