package com.redtoorange.delver;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.redtoorange.delver.entities.Character;

//A single piece of a map
public class Tile
{
    private Character occupier;
    private int worldPositionX, worldPositionY;
    private boolean flagged = false;
    private Type type = Type.GRASS;
    private boolean passable = true;
    private TextureRegion texReg;
    private float drawWidth = 16, drawHeight = 16;

    public Tile(int worldPositionX, int worldPositionY, float drawWidth, float drawHeight, TextureRegion s, Type type)
    {
        this.worldPositionX = worldPositionX;
        this.worldPositionY = worldPositionY;

        this.type = type;

        if (type == Type.WALL)
            passable = false;

        texReg = s;

        this.drawWidth = drawWidth;
        this.drawHeight = drawHeight;
    }

    public Character getOccupier()
    {
        return occupier;
    }

    public boolean occupied()
    {
        return occupier != null;
    }

    public int getWorldPositionX()
    {
        return worldPositionX;
    }

    public int getWorldPositionY()
    {
        return worldPositionY;
    }

    public Type getType()
    {
        return type;
    }

    public boolean isPassable()
    {
        return (passable && !occupied());
    }

    public void draw(SpriteBatch batch)
    {
        Color bColor = batch.getColor();

        if (flagged)
            batch.setColor(1, 0, 0, 1);

        batch.draw( texReg, worldPositionX, worldPositionY, drawWidth, drawHeight);

        if (flagged)
            batch.setColor(bColor);

    }

    public String toString()
    {
        String s = "";
        if (occupier != null)
            s += occupier.getName();
        else
            s += "No Character";

        s += ": " + type.toString() + " At " + worldPositionX + ", " + worldPositionY;
        return s;
    }

    public void setOccupier(Character c)
    {
        occupier = c;
    }

    public void flag(boolean val){
        flagged = val;
    }



    public enum Type
    {
        GRASS, WALL, DIRT, SAND
    }
}

