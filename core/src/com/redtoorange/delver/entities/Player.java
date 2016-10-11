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
import com.redtoorange.delver.entities.inventory.Inventory;
import com.redtoorange.delver.entities.inventory.types.Item;
import com.redtoorange.delver.utility.AbilityScore;
import com.redtoorange.delver.utility.Dice;
import com.redtoorange.delver.utility.FloatingText;

public class Player extends Character   {
    // xxxxxxxxx Disposal Needed xxx
    private Sound bumpSound;
    private Music deathMusic;
    private FloatingText combatText;
    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    private boolean dying = false;
    private boolean monstersShouldTick;
    private float delay = .1f;
    private float timeElapsed = 0.f;
    private String bumpSoundFile = "sounds/Blip.wav";
    private String deathMusicFile = "sounds/Death.wav";
    private Inventory inventory;

    public Player(TextureRegion tr, float scale, float x, float y, Map map){
        super("Player", tr, scale, x, y, map);

        bumpSound = Gdx.audio.newSound(Gdx.files.internal(bumpSoundFile));
        deathMusic = Gdx.audio.newMusic(Gdx.files.internal(deathMusicFile));
        deathMusic.setLooping(false);

        combatText = new FloatingText(true);
        hitDie = Dice.D12;
        characterStats.getAbilityScore( AbilityScore.Type.CON ).setValue( 18 );
        inventory = new Inventory( this );

        initHealth();
    }

    @Override
    protected void handleInput()
    {
        if (Gdx.input.isKeyPressed(Input.Keys.W))   {
            timeElapsed = 0;
            move(0, 1);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S))  {
            move(0, -1);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D))    {
            move(1, 0);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A))    {
            move(-1, 0);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))    {
            passTurn();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.H))    {
            heal(5);
            passTurn();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.PERIOD))    {
            attemptToPickUp();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.COMMA))    {
            attemptToDrop();
        }
    }

    @Override
    public void update(float deltaTime)    {
        if (!monstersShouldTick)    {
            super.update(deltaTime);
            combatText.update();
        }

        if (monstersShouldTick) {
            timeElapsed += deltaTime;
            if (timeElapsed >= delay)   {
                currentMap.tickMonsters();
                monstersShouldTick = false;
                timeElapsed = 0;
            }
        }

        if (dying && !deathMusic.isPlaying())
            super.die();
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        combatText.draw(batch);
    }

    //xxxxxxxxxxxxxx Inventory Shit xxxxxxxxxxxxxxxxxxxx

    private void attemptToPickUp(){
        if(currentTile != null && currentTile.hasItems()){
            Item i = currentTile.removeItem();
            inventory.addItem( i );
            Gdx.app.log( "Pickup", i.getName() );
        }
        Gdx.app.log( "", "Attack Die: " + attackDamage.toString() );
    }

    private void attemptToDrop(){
        if(currentTile != null){
            Item i = inventory.getItem( 0 );

            if(i != null && currentTile.hasRoom()){
                currentTile.addItem( i );
                inventory.removeItem( i );
                Gdx.app.log( "Dropped", i.getName() );
            }
        }
        Gdx.app.log( "", "Attack Die: " + attackDamage.toString() );
    }
    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    private void passTurn()
    {
        monstersShouldTick = true;
    }

    public void move(int deltaX, int deltaY){
        int targetX = MathUtils.round(positionX + (deltaX * scale));
        int targetY = MathUtils.round(positionY + (deltaY * scale));
        Tile targetTile = currentMap.getTile(targetX, targetY);

        if (ableToMove() && spaceOpen(targetTile))  {
            completeMove(targetTile, targetX, targetY);
        }
        else if (ableToMove() && targetTile.occupied()) {
            Character monster = targetTile.getOccupier();

            if (monster.getClass().equals(Monster.class))   {
                if (attack(monster))
                    combatText.setText("HIT!");
                else
                    combatText.setText("MISS!");

                combatText.setLocation(new Vector2(targetX, targetY));
                combatText.play(.15f);
            }
        }
        else    {
            bumpSound.play();

            combatText.setLocation(new Vector2(targetX, targetY));
            combatText.setText("Blocked!");
            combatText.play(.15f);
        }

        monstersShouldTick = true;
    }

    private void completeMove(Tile target, int targetX, int targetY)    {
        currentTile.setOccupier(null);
        currentTile = target;
        currentTile.setOccupier(this);

        positionX = targetX;
        positionY = targetY;
    }

    @Override
    public void dispose()   {
        super.dispose();
        combatText.dispose();
        bumpSound.dispose();
        deathMusic.dispose();
    }

    @Override
    public void die()   {
        deathMusic.setVolume( 0.25F );
        deathMusic.play();
        dying = true;
    }
}
