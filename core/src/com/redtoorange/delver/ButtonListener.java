package com.redtoorange.delver;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class ButtonListener extends ChangeListener {
	@Override
	public void changed( ChangeEvent event, Actor actor ) {
		Gdx.app.log("", "Hello World!");
	}
}
