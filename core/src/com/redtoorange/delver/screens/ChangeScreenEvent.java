package com.redtoorange.delver.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.redtoorange.delver.MainGame;

public class ChangeScreenEvent extends ChangeListener{
	private MainGame game;
	private MainGame.ScreenType nextScreen;

	public ChangeScreenEvent(MainGame game, MainGame.ScreenType nextScreen){
		this.game = game;
		this.nextScreen = nextScreen;
	}

	@Override
	public void changed( ChangeEvent event, Actor actor ) {
		game.setScreen( game.getScreenByType( nextScreen ) );
	}
}