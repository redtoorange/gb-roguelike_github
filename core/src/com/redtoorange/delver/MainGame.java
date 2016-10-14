package com.redtoorange.delver;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ArrayMap;
import com.redtoorange.delver.screens.*;

public class MainGame extends Game{
    private ArrayMap<ScreenType, GameScreen> screens;

    @Override
    public void create() {
        screens = new ArrayMap<ScreenType, GameScreen>();

        screens.put(ScreenType.PLAYING, new PlayingScreen( this ));
        screens.put(ScreenType.START, new StartScreen( this ));


        screens.put(ScreenType.PAUSE, new PauseScreen( this ));
        screens.put(ScreenType.INVENTORY, new InventoryScreen( this ));

        setScreen( screens.get(ScreenType.START ) );
    }

    public GameScreen getScreenByType(ScreenType type){
        return screens.get(type);
    }

    @Override
    public void dispose() {
        super.dispose();
        Gdx.app.exit();
    }

    public enum ScreenType{
        PLAYING, START, END, MENU, SOUND, VIDEO, PAUSE, INVENTORY
    }
}
