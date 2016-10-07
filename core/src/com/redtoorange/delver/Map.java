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

    private Hashtable<Tile.Type, TextureRegion> mapTileView;
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

    public int getTileWidth()
    {
        return tileWidth;
    }

    public int getTileHeight()
    {
        return tileHeight;
    }

    //Constructor, xMapOffset and yMapOffset are the map offsets
    public Map( FileHandle tileSet, int width, int height, int xMapOffset, int yMapOffset, int tileWidth, int tileHeight, float tileDrawWidth, float tileDrawHeight)
    {
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

    public void updateCharacters()
    {
        for (Character c : characters)
        {
            c.update();
        }
    }

    public void drawCharacters(SpriteBatch batch)
    {
        for (int i = characters.size - 1; i >= 0; i--)
        {
            characters.get(i).draw(batch);
        }
    }

    public void tickMonsters()
    {
        for (int i = 1; i < characters.size; i++)
        {
            ((Monster) characters.get(i)).setReady();
        }
    }

    public class ChanceRange
    {
        float min, max;

        public ChanceRange(float min, float max)
        {
            this.min = min;
            this.max = max;
        }

        public boolean inRange(float c)
        {
            return c > min && c < max;
        }
    }

    private void initTileView()
    {
        mapTileView = new Hashtable<Tile.Type, TextureRegion>();

        TextureRegion tile = new TextureRegion(tileSet, 0, 0, tileWidth, tileHeight);
        mapTileView.put(Tile.Type.GRASS, tile);

        tile = new TextureRegion(tileSet, 32, 32, tileWidth, tileHeight);
        mapTileView.put(Tile.Type.WALL, tile);

        tile = new TextureRegion(tileSet, 32, 0, tileWidth, tileHeight);
        mapTileView.put(Tile.Type.SAND, tile);

        tile = new TextureRegion(tileSet, 64, 0, tileWidth, tileHeight);
        mapTileView.put(Tile.Type.DIRT, tile);
    }

    //initialize the array of tileMap
    private void initTiles()
    {
        tileMap = new Tile[mapWidth][mapHeight];
        for (int i = 0; i < mapWidth; i++)
        {
            for (int k = 0; k < mapHeight; k++)
            {
                tileMap[i][k] = setTile(i, k);
            }
        }

        //Convert the tileMap into a node and tree chart
        //pathingTree = new Tree(tileMap);
    }

    private Tile setTile(int arrayX, int arrayY)
    {
        Tile.Type type = Tile.Type.GRASS;

        float seed = MathUtils.random();

        //sand
        if (sandChance.inRange(seed))
            type = Tile.Type.SAND;
            //dirt
        else if (dirtChance.inRange(seed))
            type = Tile.Type.DIRT;
            //grass
        else if (grassChance.inRange(seed))
            type = Tile.Type.GRASS;
            //wall
        else if (wallChance.inRange(seed))
            type = Tile.Type.WALL;

        if ((arrayX == 0 || arrayX == mapHeight - 1 || arrayY == 0 || arrayY == mapHeight - 1))
            type = Tile.Type.WALL;

        return new Tile((arrayX + xMapOffset) * (int)tileDrawWidth, (arrayY + yMapOffset) * (int)tileDrawHeight, tileDrawWidth, tileDrawHeight, mapTileView.get(type), type);
    }

    public Tile getRandomEmptyTile()
    {
        Tile t = tileMap[MathUtils.random(0, mapWidth - 1)][MathUtils.random(0, mapHeight - 1)];
        while (!t.isPassable() || t.occupied())
        {
            t = tileMap[MathUtils.random(0, mapWidth - 1)][MathUtils.random(0, mapHeight - 1)];
        }
        return t;
    }
    //return a specific tile based on it's world xMapOffset, yMapOffset

    /**
     * translate a point on the screen/world into the 2d array of tileMap
     *
     * @param arrayX - point on screen
     * @param arrayY - point on screen
     * @return
     */
    public Tile getTile(int arrayX, int arrayY)
    {
        int worldX = (arrayX + xMapOffset) / (int)tileDrawWidth;
        int worldY = (arrayY + yMapOffset) / (int)tileDrawHeight;

        if ((worldX >= 0 && worldX < mapWidth) &&
                (worldY >= 0 && worldY < mapHeight))
        {
            return tileMap[worldX][worldY];
        }

        return null;
    }

    //Draw each tile of the map
    public void drawMap(SpriteBatch batch)
    {
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


    public Node getNode(Tile t)
    {
        return pathingTree.getNodeFromTile(t);
    }

    @Override
    public void dispose()
    {
        tileSet.dispose();
    }
}
