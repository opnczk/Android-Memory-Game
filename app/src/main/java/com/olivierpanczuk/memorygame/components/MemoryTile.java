package com.olivierpanczuk.memorygame.components;

import com.olivierpanczuk.memorygame.Assets;
import com.olivierpanczuk.memorygame.utils.EventFunctions;
import com.olivierpanczuk.simpleframework.Graphics;
import com.olivierpanczuk.simpleframework.Image;
import com.olivierpanczuk.simpleframework.Input.TouchEvent;

import java.util.List;

/**
 * Created by Olivier on 19/01/2016.
 */
public class MemoryTile {
    private int type =0;
    private int xCol;
    private int yLine;
    private boolean touched = false;
    private boolean found = false;
    private Image back;
    private Image front;

    public MemoryTile(int xCol, int yLine, int type){
        this.xCol = xCol;
        this.yLine = yLine;
        this.type = type;
        back = Assets.back;
        front = Assets.cards.get(type-1);
    }

    public boolean update(float deltaTime, List<TouchEvent> touchEvents, Graphics g){
        int len = touchEvents.size();
        boolean successfulTouchEvent = false;
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP && EventFunctions.inBounds(event, xCol * g.getWidth() / 4, yLine * g.getWidth() / 4, g.getWidth() / 4, g.getWidth() / 4)) {
                this.touched = true;
                successfulTouchEvent = true;
            }
        }
        return successfulTouchEvent;
    }

    public void paint(Graphics g, int size, int padding) {
        Image imgToDraw = null;

        if(touched || found) imgToDraw = front;
        else imgToDraw = back;

        g.drawImage(imgToDraw, g.getWidth()/4*xCol, g.getWidth()/4*yLine, g.getWidth()/4, g.getWidth()/4);
    }

    public void setFound(boolean b){
        this.found = b;
    }

    public void setTouched(boolean b){
        this.touched = b;
    }

    public boolean getFound(){
        return this.found;
    }

    public boolean getTouched(){
        return this.touched;
    }

    public int getType(){
        return this.type;
    }
}
