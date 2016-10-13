package com.redtoorange.delver.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.redtoorange.delver.MainGame;

public class PauseScreen implements Screen{
    Stage stage;
    Skin skin;

    MainGame game;

    PlayingScreen playingScreen;
    Table table;
    TextButton quitButton;

    private boolean initialized = false;
    public PauseScreen(MainGame game){
        this.game = game;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));

        table = new Table(skin);
        Label label = new Label("PAUSED!", skin, "default");
        table.add(label);

        quitButton = new TextButton("Quit?", skin, "default");
        quitButton.setProgrammaticChangeEvents(false);
        table.row();
        table.add(quitButton);

        quitButton.addListener( new QuitButtonListener(game));


        stage.addActor(table);
    }


    class QuitButtonListener extends ChangeListener{
        @Override
        public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.getScreenByType(MainGame.ScreenType.START));
        }

        private MainGame game;

        public QuitButtonListener(MainGame game){
            this.game = game;
        }
    }

    @Override
    public void show() {
        if(!initialized){
            initialized = true;

            playingScreen = (PlayingScreen)game.getScreenByType(MainGame.ScreenType.PLAYING);
            stage.setViewport(playingScreen.getViewport());
        }

        Gdx.input.setInputProcessor(stage);

        float x = stage.getCamera().position.x;
        float y = stage.getCamera().position.y;


        table.setPosition(x, y);

        System.out.println("Button Shown");
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

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
