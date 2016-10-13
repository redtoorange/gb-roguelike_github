package com.redtoorange.delver.utility.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.redtoorange.delver.entities.Character;
import com.redtoorange.delver.entities.inventory.types.Item;
import com.redtoorange.delver.utility.Constants;
import com.redtoorange.delver.utility.TileType;

public class Tile{
    private int itemsPresent;
    private Array<Item> items;

    private Character occupier;
    private int worldPositionX, worldPositionY;
    private boolean flagged = false;
    private TileType type = TileType.GRASS;
    private boolean passable = true;
    private TextureRegion texReg;
    private float drawWidth = 16, drawHeight = 16;

    public Tile(int worldPositionX, int worldPositionY, float drawWidth, float drawHeight, TextureRegion s, TileType type){
        this.worldPositionX = worldPositionX;
        this.worldPositionY = worldPositionY;

        this.type = type;

        if (type == TileType.WALL)
            passable = false;

        texReg = s;

        this.drawWidth = drawWidth;
        this.drawHeight = drawHeight;

        items = new Array<Item>( );
        itemsPresent = 0;
    }

    public boolean hasRoom(){
        return items.size < Constants.MAX_ITEMS_IN_TILE;
    }

    public boolean addItem(Item i){
        items.add( i );
        i.setTile( this );

        return true;
    }

    public Item removeItem(){
        Item i = items.pop();
        items.shrink();

        return i;
    }

    public void draw(SpriteBatch batch){
        Color bColor = batch.getColor();

        if (flagged)
            batch.setColor(1, 0, 0, 1);

        batch.draw( texReg, worldPositionX, worldPositionY, drawWidth, drawHeight);

        if (flagged)
            batch.setColor(bColor);
    }

    public String toString(){
        String s = "";
        if (occupier != null)
            s += occupier.getName();
        else
            s += "No Character";

        s += ": " + type.toString() + " At " + worldPositionX + ", " + worldPositionY;
        return s;
    }

    public boolean hasItems(){
        return (items.size > 0);
    }

    public void setOccupier(Character c){
        occupier = c;
    }

    public void flag(boolean val){
        flagged = val;
    }

    public Character getOccupier(){
        return occupier;
    }

    public boolean occupied(){
        return occupier != null;
    }

    public int getWorldPositionX(){
        return worldPositionX;
    }

    public int getWorldPositionY(){
        return worldPositionY;
    }

    public TileType getType(){
        return type;
    }

    public boolean isPassable(){
        return ( passable && !occupied( ) );
    }
}

