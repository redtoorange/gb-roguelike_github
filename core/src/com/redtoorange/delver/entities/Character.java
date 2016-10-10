package com.redtoorange.delver.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.redtoorange.delver.utility.Dice;
import com.redtoorange.delver.Map;
import com.redtoorange.delver.Tile;
import com.redtoorange.delver.utility.FloatingText;

abstract public class Character implements Disposable {
    protected String sliceSoundFile = "sounds/Slice.wav";
    protected String missSoundFile = "sounds/Miss.wav";
    protected FloatingText healthBar;
    protected Sound attackSound;
    protected Sound missSound;
    protected String name;
    protected int maxHitPoints;
    protected int hitPoints = 10;
    protected int armorClass = 10;
    protected Dice attackDamage = Dice.D4;
    protected boolean canMove;
    protected Sprite sprite;
    protected float positionX;
    protected float positionY;
    protected Tile currentTile;
    protected Map currentMap;
    protected float scale = 1.0f;

    public Character(String name, TextureRegion tr, float scale, float positionX, float positionY, Map map) {
        attackSound = Gdx.audio.newSound(Gdx.files.internal(sliceSoundFile));
        missSound = Gdx.audio.newSound(Gdx.files.internal(missSoundFile));

        this.name = name;
        this.scale = scale;

        this.positionX = positionX;
        this.positionY = positionY;

        currentMap = map;
        currentTile = map.getTile(MathUtils.round(positionX), MathUtils.round(positionY));
        currentTile.setOccupier(this);

        sprite = new Sprite(tr);
        sprite.setSize(scale, scale);
        sprite.setPosition(positionX, positionY);

        canMove = true;
        maxHitPoints = hitPoints;

        healthBar = new FloatingText(false);
        updateHealthBar();
    }

    public void update() {
        handleInput();
        updateSprite();
        updateHealthBar();
    }

    protected void updateHealthBar() {
        healthBar.setText(hitPoints + "");
        healthBar.setLocation(new Vector2(positionX + sprite.getWidth() / 2.0f, positionY + sprite.getHeight() / 2.0f));
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);

        if(currentMap.showHealth)
            healthBar.draw(batch);
    }

    public boolean attack(Character target) {
        int toHitRoll = Dice.D20.roll();

        if (toHitRoll > target.getArmorClass()) {
            Gdx.app.debug(name, "Hit " + target.getName() + " with a " + toHitRoll + "!");
            dealDamage(target);
            return true;
        }

        missSound.play();
        Gdx.app.debug(name, toHitRoll + " not enought to beat AC" + target.getArmorClass());
        return false;
    }

    public void dealDamage(Character target) {
        int amount = attackDamage.roll();
        attackSound.play(amount / attackDamage.maxDamage());

        Gdx.app.debug(getName(), target.getName() + " was hit for " + amount + " damage.");
        target.takeDamage(amount);
    }

    public void takeDamage(int amount) {
        hitPoints -= amount;
        updateHealthBar();

        if (hitPoints <= 0) {
            hitPoints = 0;
            die();
        }
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

    @Override
    public void dispose() {
        attackSound.dispose();
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

    public int getArmorClass() {
        return armorClass;
    }

    protected boolean spaceOpen(Tile target) {
        return target.isPassable();
    }

    protected abstract void handleInput();

    public abstract void move(int deltaX, int deltaY);

    public boolean ableToMove() {
        return canMove;
    }

    protected void updateSprite() {
        sprite.setPosition(positionX, positionY);
    }
}
