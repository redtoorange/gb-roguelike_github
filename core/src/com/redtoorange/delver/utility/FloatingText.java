package com.redtoorange.delver.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class FloatingText implements Disposable{
    private boolean decays = true;
    private float xSpacing = 1.5f;
    private float ySpacing = 1.25f;

    private Vector2 location;
    private float delay;
    private Color color;
    private String text;
    private BitmapFont font;
    private GlyphLayout layout;

    private boolean showing = true;

    @Override
    public void dispose() {
        font.dispose();
    }

    public FloatingText(boolean decays){
        this.decays = decays;
        float x = 0;
        float y = 0;

        location = new Vector2(x, y);
        delay = 1.0f;
        color = Color.RED;
        text = "DEBUG TEXT";
        showing = false;

        font = new BitmapFont();
        layout = new GlyphLayout(font, text);

        font.setColor(color);
    }

    public void play(float time){
        delay = time;
        showing = true;
    }

    public void setLocation(Vector2 location){
        this.location.set(location);
    }



    public void setText(String text){
        this.text = text;
        layout.setText(font, text);
    }

    public void update(){
        if(decays){
            if(showing)
                delay -= Gdx.graphics.getDeltaTime();

            if(delay < 0.0f)
                showing = false;
        }
    }

    public void draw(SpriteBatch batch){
        if(showing || !decays){
            Vector2 center = location;
            
            float xOffset = layout.width;
            float yOffset = layout.height;

            font.draw(batch, text, (location.x - xOffset) + xOffset / xSpacing, location.y + (yOffset * ySpacing));
        }
    }
}
