package com.redtoorange.delver.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.redtoorange.delver.MainGame;
import com.redtoorange.delver.utility.Constants;


public class InventoryScreen implements Screen {
    private MainGame game;
    private Stage stage;
    private Skin skin;
    private Table mainTable;

    private boolean initialized = false;

    public InventoryScreen(MainGame game){
        this.game = game;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
    }

    @Override
    public void show() {
        if(!initialized){
            initialized = true;
            initUI();
        }
        realignMainTable();
    }

    private void realignMainTable() {
        Gdx.input.setInputProcessor(stage);

        mainTable.setPosition(
                stage.getCamera().position.x - mainTable.getWidth()/2,
                stage.getCamera().position.y - mainTable.getHeight()/2
        );
    }

    private void initUI(){
        stage.setViewport(
                ((PlayingScreen)
                        game.getScreenByType(MainGame.ScreenType.PLAYING)).getViewport()
        );

        mainTable = new Table(skin);
        mainTable.setSize(Constants.GB_RES_WIDTH, Constants.GB_RES_HEIGHT);
        stage.addActor(mainTable);



        Label inventoryLabel = new Label("Inventory", skin, "default");
        inventoryLabel.setAlignment(Align.center);

        mainTable.add(inventoryLabel).center().top();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta){
        stage.act(delta);

        if(exitMenuScreen())
            game.setScreen(game.getScreenByType(MainGame.ScreenType.PLAYING));
    }

    private boolean exitMenuScreen() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.I) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            return true;
        else
            return false;
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
