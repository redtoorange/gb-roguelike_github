package com.redtoorange.delver;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ArrayMap;
import com.redtoorange.delver.screens.PauseScreen;
import com.redtoorange.delver.screens.PlayingScreen;
import com.redtoorange.delver.screens.StartScreen;

public class MainGame extends Game{
    private ArrayMap<ScreenType, Screen> screens;
    @Override
    public void create() {
        screens = new ArrayMap<ScreenType, Screen>();

        screens.put(ScreenType.PLAYING, new PlayingScreen( this ));
        screens.put(ScreenType.START, new StartScreen( this ));
        screens.put(ScreenType.PAUSE, new PauseScreen( this ));

        setScreen( screens.get(ScreenType.START) );
    }

    public Screen getScreenByType(ScreenType type){
        return screens.get(type);
    }

    @Override
    public void dispose() {
        super.dispose();

        Gdx.app.exit();
    }

    public enum ScreenType{
        PLAYING, START, END, MENU, SOUND, VIDEO, PAUSE
    }
}
