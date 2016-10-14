package com.redtoorange.delver.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.redtoorange.delver.MainGame;
import com.redtoorange.delver.utility.Constants;


public class InventoryScreen extends GameScreen {
    private MainGame game;
    private Table rootTable;
    private boolean initialized = false;

    public InventoryScreen(MainGame game){
        this.game = game;
        initUI();
    }

    protected void initUI(){
        super.initUI();

        stage.setViewport(
                ((PlayingScreen)
                        game.getScreenByType(MainGame.ScreenType.PLAYING)).getViewport()
        );

        rootTable = new Table(skin);
        rootTable.setSize(Constants.GB_RES_WIDTH, Constants.GB_RES_HEIGHT);

        stage.addActor( rootTable );

        Label inventoryLabel = new Label("Inventory", skin, "default");
        inventoryLabel.setAlignment(Align.center);

        rootTable.add(inventoryLabel).expand().center().top();
    }

    @Override
    public void show() {
        if(!initialized){
            initialized = true;
        }

        refreshUI();
        realignMainTable();
    }

    private void refreshUI(){}

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
}
