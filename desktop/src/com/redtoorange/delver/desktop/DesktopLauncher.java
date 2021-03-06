package com.redtoorange.delver.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.redtoorange.delver.MainGame;
import com.redtoorange.delver.utility.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = Constants.TITLE;
		config.width = Constants.WIDTH;
		config.height = Constants.HEIGHT;

		new LwjglApplication(new MainGame(), config);
	}
}
