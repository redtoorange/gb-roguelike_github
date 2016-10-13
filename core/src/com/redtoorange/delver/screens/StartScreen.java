package com.redtoorange.delver.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.redtoorange.delver.MainGame;
import com.redtoorange.delver.utility.Constants;

public class StartScreen implements Screen {
    Stage stage;
    Skin skin;
    MainGame game;
    PlayingScreen playingScreen;
    Table table;
    TextButton startButton;

    private boolean initialized = false;

    public StartScreen(MainGame game){
        this.game = game;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        table = new Table(skin);

        startButton = new TextButton("Start?", skin, "default");
        startButton.setProgrammaticChangeEvents(false);
        startButton.addListener( new StartButtonListener(game));

        table.add(startButton);
        stage.addActor(table);
    }


    class StartButtonListener extends ChangeListener {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            game.setScreen(game.getScreenByType(MainGame.ScreenType.PLAYING));
        }

        private MainGame game;

        public StartButtonListener(MainGame game){
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
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta){
        stage.act(delta);
    }

    private void draw(){
        clearScreen();
        stage.draw();
    }

    private void clearScreen()  {
        Color c = Constants.CLEAR_COLOR;
        Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        System.out.println("Paused");
    }

    @Override
    public void resume() {
        System.out.println("Resumed");
    }

    @Override
    public void hide() {
        System.out.println("Hidden");
    }

    @Override
    public void dispose() {

    }
}
