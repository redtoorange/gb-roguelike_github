package com.redtoorange.delver.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.redtoorange.delver.MainGame;

public class PauseScreen extends GameScreen{
    private MainGame game;
    private PlayingScreen playingScreen;
    private Table rootTable;
    private TextButton quitButton;
    private boolean initialized = false;

    public PauseScreen(MainGame game){
        this.game = game;
        initUI();
    }

    protected void initUI() {
        super.initUI();

        rootTable = new Table(skin);
        Label label = new Label("Paused", skin, "default");
        label.setAlignment( Align.center);
        rootTable.add(label).width(75);

        rootTable.row();

        rootTable.debug();

        quitButton = new TextButton("Main Menu", skin, "default");
        quitButton.setProgrammaticChangeEvents(false);
        rootTable.add(quitButton).width(100);

        quitButton.addListener( new ChangeScreenEvent(game, MainGame.ScreenType.START));
        stage.addActor( rootTable );
    }

    @Override
    public void show() {
        if(!initialized){
            initialized = true;

            playingScreen = (PlayingScreen)game.getScreenByType(MainGame.ScreenType.PLAYING);
        }

        stage.setViewport(playingScreen.getViewport());
        realignMainTable();
    }

    private void realignMainTable() {
        Gdx.input.setInputProcessor(stage);

        rootTable.setPosition(
                stage.getCamera().position.x - rootTable.getWidth()/2,
                stage.getCamera().position.y - rootTable.getHeight()/2
        );
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(game.getScreenByType(MainGame.ScreenType.PLAYING));
        }

        stage.act(delta);
    }

    private void draw(){
        stage.draw();
    }

}
