package com.redtoorange.delver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.redtoorange.delver.entities.Character;
import com.redtoorange.delver.entities.Monster;
import com.redtoorange.delver.utility.ChanceRange;
import com.redtoorange.delver.utility.TileType;
import com.redtoorange.delver.utility.pathFinding.Node;
import com.redtoorange.delver.utility.pathFinding.Tree;

import java.util.Hashtable;

public class Map implements Disposable{
    public boolean showHealth = false;
    public Tree pathingTree;
    private Array<Character> characters;
    private ChanceRange sandChance = new ChanceRange(0.1f, 0.3f);
    private ChanceRange dirtChance = new ChanceRange(0.3f, 0.6f);
    private ChanceRange grassChance = new ChanceRange(0.6f, 1f);
    private ChanceRange wallChance = new ChanceRange(0.0f, 0.1f);

    private Hashtable<TileType, TextureRegion> mapTileView;
    private int xMapOffset, yMapOffset;
    private int mapWidth, mapHeight;
    private int tileWidth, tileHeight;
    private float tileDrawWidth, tileDrawHeight;

    private Tile[][] tileMap;
    private Texture tileSet;

    public float cost(Tile a, Tile b){
        if(!b.isPassable())
            return 999999;
        else if(b.occupied())
            return 10;
        else return 1;
    }

    public void clearFlags(){
        for(int x = 0; x < tileMap.length; x++){
            for(int y = 0; y < tileMap[x].length; y++){
                tileMap[x][y].flag(false);
            }
        }
    }

    public Map( FileHandle tileSet, int width, int height, int xMapOffset, int yMapOffset, int tileWidth, int tileHeight, float tileDrawWidth, float tileDrawHeight){
        this.tileDrawWidth = tileDrawWidth;
        this.tileDrawHeight = tileDrawHeight;

        this.tileSet = new Texture( tileSet );
        characters = new Array<Character>();

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        this.xMapOffset = xMapOffset;
        this.yMapOffset = yMapOffset;

        this.mapWidth = width;
        this.mapHeight = height;

        initTileView();
        initTiles();   //build the actual array

        pathingTree = new Tree(tileMap, this);
    }



    public void updateCharacters(){
        for (Character c : characters)
        {
            c.update();
        }
    }

    public void drawCharacters(SpriteBatch batch){
        for (int i = characters.size - 1; i >= 0; i--)
        {
            characters.get(i).draw(batch);
        }
    }

    public void tickMonsters(){
        for (int i = 1; i < characters.size; i++)
        {
            ((Monster) characters.get(i)).setReady();
        }
    }

    private void initTileView(){
        mapTileView = new Hashtable<TileType, TextureRegion>();

        TextureRegion tile = new TextureRegion(tileSet, 0, 0, tileWidth, tileHeight);
        mapTileView.put(TileType.GRASS, tile);

        tile = new TextureRegion(tileSet, 32, 32, tileWidth, tileHeight);
        mapTileView.put(TileType.WALL, tile);

        tile = new TextureRegion(tileSet, 32, 0, tileWidth, tileHeight);
        mapTileView.put(TileType.SAND, tile);

        tile = new TextureRegion(tileSet, 64, 0, tileWidth, tileHeight);
        mapTileView.put(TileType.DIRT, tile);
    }

    private void initTiles(){
        tileMap = new Tile[mapWidth][mapHeight];
        for (int i = 0; i < mapWidth; i++)
        {
            for (int k = 0; k < mapHeight; k++)
            {
                tileMap[i][k] = setTile(i, k);
            }
        }
    }

    private Tile setTile(int arrayX, int arrayY){
        TileType type = TileType.GRASS;

        float seed = MathUtils.random();

        //sand
        if (sandChance.inRange(seed))
            type = TileType.SAND;
            //dirt
        else if (dirtChance.inRange(seed))
            type = TileType.DIRT;
            //grass
        else if (grassChance.inRange(seed))
            type = TileType.GRASS;
            //wall
        else if (wallChance.inRange(seed))
            type = TileType.WALL;

        if ((arrayX == 0 || arrayX == mapHeight - 1 || arrayY == 0 || arrayY == mapHeight - 1))
            type = TileType.WALL;

        return new Tile((arrayX + xMapOffset) * (int)tileDrawWidth, (arrayY + yMapOffset) * (int)tileDrawHeight, tileDrawWidth, tileDrawHeight, mapTileView.get(type), type);
    }

    public Tile getRandomEmptyTile(){
        Tile t = tileMap[MathUtils.random(0, mapWidth - 1)][MathUtils.random(0, mapHeight - 1)];
        while (!t.isPassable() || t.occupied())
        {
            t = tileMap[MathUtils.random(0, mapWidth - 1)][MathUtils.random(0, mapHeight - 1)];
        }
        return t;
    }

    public Tile getTile(int x, int y, boolean worldSpace){
        int worldX;
        int worldY;

        if(worldSpace){
            worldX = (x + xMapOffset) / (int)tileDrawWidth;
            worldY = (y + yMapOffset) / (int)tileDrawHeight;
        }
        else{
            worldX = x;
            worldY = y;
        }

        if ((worldX >= 0 && worldX < mapWidth) &&
                (worldY >= 0 && worldY < mapHeight))
        {
            return tileMap[worldX][worldY];
        }

        return null;
    }

    public Tile getTile(int arrayX, int arrayY){
        int worldX = (arrayX + xMapOffset) / (int)tileDrawWidth;
        int worldY = (arrayY + yMapOffset) / (int)tileDrawHeight;


        if ((worldX >= 0 && worldX < mapWidth) &&
                (worldY >= 0 && worldY < mapHeight))
        {
            return tileMap[worldX][worldY];
        }

        return null;
    }

    public void drawMap(SpriteBatch batch){
        if(Gdx.input.isKeyJustPressed(Input.Keys.O))
            showHealth = !showHealth;

        for (int x = 0; x < mapWidth; x++)
        {
            for (int y = 0; y < mapHeight; y++)
            {
                tileMap[x][y].draw(batch);
            }
        }
    }

    public void addCharacter(Character c)
    {
        characters.add(c);
    }

    public void removeCharacter(Character c)
    {
        characters.removeValue(c, true);
        c.dispose();
    }

    public Character getCharacterAt(int i)
    {
        return characters.get(i);
    }

    public int indexOfCharacter(Character c)
    {
        return characters.indexOf(c, true);
    }

    public Node getNode(Tile t)
    {
        return pathingTree.getNodeFromTile(t);
    }

    @Override
    public void dispose()
    {
        tileSet.dispose();
    }

    public int getTileWidth()
    {
        return tileWidth;
    }

    public int getTileHeight()
    {
        return tileHeight;
    }
}
