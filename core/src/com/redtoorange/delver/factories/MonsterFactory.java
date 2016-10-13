package com.redtoorange.delver.factories;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.redtoorange.delver.utility.Map.Map;
import com.redtoorange.delver.utility.Map.Tile;
import com.redtoorange.delver.entities.Monster;
import com.redtoorange.delver.utility.Dice;

public class MonsterFactory {

    private static String avatarSheet = "gbaTileSet.png";
    private static TextureRegion rat = new TextureRegion(new Texture(Gdx.files.internal(avatarSheet)), 0, 608, 32, 32);

    public static Monster buildZombie(Map map, Tile t){
        return new Monster(Dice.D6, Dice.D4, "Rat", rat, 16, t.getWorldPositionX(), t.getWorldPositionY(), map);
    }
}
