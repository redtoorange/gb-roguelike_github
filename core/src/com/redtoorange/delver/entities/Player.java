package com.redtoorange.delver.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.redtoorange.delver.Map;
import com.redtoorange.delver.Tile;
import com.redtoorange.delver.utility.Dice;
import com.redtoorange.delver.utility.FloatingText;

public class Player extends Character
{
    private Sound bumpSound;
    private Music deathMusic;
    private FloatingText combatText;
    private boolean monstersShouldTick;
    private float delay = .25f;
    private float timeElapsed = 0.f;

    public Player(TextureRegion tr, float scale, float x, float y, Map map){
        super("Player", tr, scale, x, y, map);
        bumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Blip.wav"));
        deathMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Death.wav"));
        deathMusic.setLooping(false);

        combatText = new FloatingText(true);
        attackDamage = Dice.D12;

        hitPoints = 20;
        maxHitPoints = hitPoints;
        updateHealthBar();
    }

    //@override
    public void update()
    {
        if (!monstersShouldTick)
        {
            super.update();
            combatText.update();
        }

        if (monstersShouldTick)
        {
            timeElapsed += Gdx.graphics.getDeltaTime();
            if (timeElapsed >= delay)
            {
                currentMap.tickMonsters();
                monstersShouldTick = false;
                timeElapsed = 0;
            }
        }

        if (dying && !deathMusic.isPlaying())
            super.die();
    }

    protected void handleInput()
    {
        if (Gdx.input.isKeyPressed(Input.Keys.W))
        {
            move(0, 1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S))
        {
            move(0, -1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            move(1, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            move(-1, 0);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            passTurn();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.H))
        {
            heal(5);
            passTurn();
        }
    }

    private void passTurn()
    {
        monstersShouldTick = true;
    }

    public void move(int deltaX, int deltaY){
        int targetX = MathUtils.round(positionX + (deltaX * scale));
        int targetY = MathUtils.round(positionY + (deltaY * scale));
        Tile targetTile = currentMap.getTile(targetX, targetY);

        if (ableToMove() && spaceOpen(targetTile))
        {
            completeMove(targetTile, targetX, targetY);
        } else if (ableToMove() && targetTile.occupied())
        {
            Character monster = targetTile.getOccupier();
            if (monster.getClass().equals(Monster.class))
            {
                if (attack(monster))
                    combatText.setText("HIT!");
                else
                    combatText.setText("MISS!");
                combatText.setLocation(new Vector2(targetX, targetY));
                combatText.play(.25f);
            }
        } else
        {
            bumpSound.play();

            combatText.setLocation(new Vector2(targetX, targetY));
            combatText.setText("Blocked!");
            combatText.play(.25f);
        }
        monstersShouldTick = true;

    }

    private void completeMove(Tile target, int targetX, int targetY)
    {
        currentTile.setOccupier(null);

        currentTile = target;
        currentTile.setOccupier(this);
        positionX = targetX;
        positionY = targetY;
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        super.draw(batch);
        combatText.draw(batch);
    }

    @Override
    public void dispose()
    {
        super.dispose();
        combatText.dispose();
        bumpSound.dispose();
        deathMusic.dispose();
    }

    private boolean dying = false;

    @Override
    public void die()
    {
        deathMusic.play();
        dying = true;
    }
}
