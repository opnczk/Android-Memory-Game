package com.olivierpanczuk.memorygame.components;

import android.graphics.Paint;

import com.olivierpanczuk.memorygame.Assets;
import com.olivierpanczuk.memorygame.GameScreen;
import com.olivierpanczuk.memorygame.utils.EventFunctions;
import com.olivierpanczuk.simpleframework.Graphics;
import com.olivierpanczuk.simpleframework.Image;
import com.olivierpanczuk.simpleframework.Input;

import java.util.List;

/**
 * Created by Olivier on 20/01/2016.
 */
public class ClickableButton {
    private final String text;
    private Image defaultImg;
    private Image pressedImg;
    private int height = 0;
    private int width = 0;
    private int x = 0;
    private int y = 0;
    private boolean pressed;

    public ClickableButton(String text){
        this.text = text;
        this.defaultImg = Assets.button;
        this.pressedImg = Assets.pressedButton;
    }

    public boolean update(float deltaTime, List<Input.TouchEvent> touchEvents){
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_UP && EventFunctions.inBounds(event, x, y, width, height)) {
                    pressed = true;
                    return true;
            }else if(event.type == Input.TouchEvent.TOUCH_DOWN && EventFunctions.inBounds(event, x, y, width, height)){
                pressed = true;
            }
        }
        return false;
    }

    public void paint(Graphics g,Paint paint){
        if(!pressed)g.drawImage(defaultImg, x, y, width, height);
        else g.drawImage(pressedImg, x, y, width, height);
        g.drawString(text, x + width/2, y + height/2, paint);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
