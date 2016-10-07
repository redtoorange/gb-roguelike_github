package com.redtoorange.delver.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.redtoorange.delver.Map;
import com.redtoorange.delver.Tile;
import com.redtoorange.delver.utility.Dice;
import com.redtoorange.delver.utility.pathFinding.Node;
import com.redtoorange.delver.utility.pathFinding.Path;

public class Monster extends Character implements Disposable {
    private boolean debugPath = false;
    private boolean canTick = false;
    private int speed = 0;  //number of turns to wait before an action
                            // 0 = act each turn
                            // 1 = act every-other turn
    private int currentAction;
    private Player target;

    public Monster(Dice hitPointDie, Dice attackDie, String name, TextureRegion tr, float scale, float x, float y, Map map) {
        super(name, tr, scale, x, y, map);
        attackDamage = attackDie;

        hitPoints = hitPointDie.roll();
        maxHitPoints = hitPoints;
        updateHealthBar();

        currentAction = speed;
        target = (Player)currentMap.getCharacterAt( 0 );
    }

    @Override
    protected void handleInput() {
        if(target == null)
            return;

        Path p = currentMap.pathingTree.aStarSearch( currentTile, target.currentTile );
        Tile nextTile = p.getNext();

        if(debugPath)
            drawDebugPath(p);

        if(nextTile == target.currentTile)
            attack( target );
        else
            completeMove( nextTile );
    }

    private void drawDebugPath(Path p) {
        currentMap.clearFlags( );
        for(Node n : p.nodePath)
            n.tile.flag(true);
    }

    public void move(int deltaX, int deltaY){ }

    private void completeMove(Tile destination){
        currentTile.setOccupier(null);

        currentTile = destination;
        currentTile.setOccupier(this);

        positionX = currentTile.getWorldPositionX();
        positionY = currentTile.getWorldPositionY();
    }

    public void setReady(){
        canTick = true;
    }
    @Override
    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            if(debugPath){
                currentMap.clearFlags();
                debugPath = false;
            }
            else
                debugPath = true;
        }

        if (!canTick)
            return;

        if (currentAction > 0) {
            currentAction--;
        } else {
            super.update();
            currentAction = MathUtils.random(0, speed);
        }
        canTick = false;
    }

    @Override
    public void die() {
        currentMap.clearFlags();
        super.die();
    }
}
