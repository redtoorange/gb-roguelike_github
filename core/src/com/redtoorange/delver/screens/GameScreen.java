package com.redtoorange.delver.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.redtoorange.delver.utility.Constants;


public abstract class GameScreen implements Screen {
	protected Stage stage;
	protected Skin skin;

	@Override
	public abstract void show();

	@Override
	public abstract void render( float delta );

	protected void initUI(){
		stage = new Stage();
		skin = new Skin(Gdx.files.internal( "UI/uiskin.json" ));
	}

	protected void clearScreen()  {
		Color c = Constants.CLEAR_COLOR;
		Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void pause(){}

	@Override
	public void resume(){}

	@Override
	public void hide(){}

	@Override
	public void dispose(){}

	@Override
	public void resize( int width, int height ){
		stage.getViewport().setScreenSize( width, height );
	}
}
