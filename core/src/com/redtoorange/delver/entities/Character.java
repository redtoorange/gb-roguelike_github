package com.redtoorange.delver.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.redtoorange.delver.utility.Draws;
import com.redtoorange.delver.utility.*;
import com.redtoorange.delver.Map;
import com.redtoorange.delver.Tile;

abstract public class Character implements Disposable, Draws, Updates {
    //xxxx Disposal Needed xxxx
    protected Sound attackSound;
    protected Sound missSound;
    protected FloatingText healthBar;
    //xxxxxxxxxxxxxxxxxxxxxxxxx

    protected Sprite sprite;
    protected Tile currentTile;
    protected Map currentMap;
    protected StatBlock characterStats;

    protected String sliceSoundFile = "sounds/Slice.wav";
    protected String missSoundFile  = "sounds/Miss.wav";
    protected String name;

    protected int maxHitPoints;
    protected int hitPoints;
    protected int level = 1;

    protected Dice hitDie = Dice.D8;
    protected Dice attackDamage = Dice.D4;

    protected float positionX;
    protected float positionY;
    protected float scale = 1.0f;

    protected boolean canMove = true;
    protected boolean dying = false;

    public Character(String name, TextureRegion tr, float scale, float positionX, float positionY, Map map) {
        characterStats = new StatBlock(  );

        attackSound = Gdx.audio.newSound(Gdx.files.internal(sliceSoundFile));
        missSound   = Gdx.audio.newSound(Gdx.files.internal(missSoundFile));

        this.name   = name;
        this.scale  = scale;
        this.positionX = positionX;
        this.positionY = positionY;

        currentMap  = map;
        currentTile = map.getTile(MathUtils.round(positionX), MathUtils.round(positionY));
        currentTile.setOccupier(this);

        sprite = new Sprite(tr);
        sprite.setSize(scale, scale);
        sprite.setPosition(positionX, positionY);

        initHealth( );
    }

    //Can be called by child to update hitpoints
    //int[] hitDice = int[20];  //Populate with each hitdie when rolled
    protected void initHealth() {
        if(level == 1)
            hitPoints = hitDie.maxDamage();
        else
            hitPoints = hitDie.roll();

        hitPoints       += characterStats.getBonus( AbilityScore.Type.CON ) * level;
        maxHitPoints    = hitPoints;

        if(healthBar == null)
            healthBar = new FloatingText(false);

        updateHealthBar();
    }

    protected abstract void handleInput();

    //Called via Map only when allowed to move
    public void update(float deltaTime) {
        handleInput();
        updateSprite();
    }

    //Called via Engine every Frame (via map reference)
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);

        if(currentMap.showHealth){
            updateHealthBar();
            healthBar.draw(batch);
        }
    }

    protected void updateHealthBar() {
        healthBar.setText(hitPoints + "");
        healthBar.setLocation(new Vector2(positionX + sprite.getWidth() / 2.0f, positionY + sprite.getHeight() / 2.0f));
    }


    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx Combat Shit xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    public boolean attack(Character target) {
        if (Dice.D20.roll() > target.getArmorClass()) {
            dealDamage(target);
            return true;
        }

        missSound.play();
        return false;
    }

    public void dealDamage(Character target) {
        int amount = attackDamage.roll() + characterStats.getBonus( AbilityScore.Type.STR );
        attackSound.play(amount / (float)attackDamage.maxDamage());

        target.takeDamage(amount);
    }

    public void takeDamage(int amount) {
        hitPoints -= amount;
        updateHealthBar();

        if (hitPoints <= 0) {
            dying = true;
            die( );  //Will call dispose
        }
    }

    public int getArmorClass() {
        return Constants.BASE_ARMOR + characterStats.getBonus( AbilityScore.Type.DEX ); // + Armor or something...
    }

    public void heal(int amount) {
        hitPoints += amount;

        if (hitPoints > maxHitPoints)
            hitPoints = maxHitPoints;

        updateHealthBar();
    }

    public void die() {
        healthBar.dispose();
        currentTile.setOccupier(null);
        currentMap.removeCharacter(this);
    }
    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx


    //Clean-up
    @Override
    public void dispose() {
        attackSound.dispose();
        missSound.dispose();
        healthBar.dispose();
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name + " (" + positionX + ", " + positionY + ")";
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    protected boolean spaceOpen(Tile target) {
        return target.isPassable();
    }

    public abstract void move(int deltaX, int deltaY);

    public boolean ableToMove() {
        return canMove;
    }

    protected void updateSprite() {
        sprite.setPosition(positionX, positionY);
    }
}
