package com.redtoorange.delver;

import com.badlogic.gdx.Game;

public class MainGame extends Game{
    @Override
    public void create() {
        setScreen( new PlayingScreen() );
    }


}
