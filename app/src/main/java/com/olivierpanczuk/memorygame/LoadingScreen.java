package com.olivierpanczuk.memorygame;


import com.olivierpanczuk.simpleframework.Game;
import com.olivierpanczuk.simpleframework.Graphics;
import com.olivierpanczuk.simpleframework.Graphics.ImageFormat;
import com.olivierpanczuk.simpleframework.Image;
import com.olivierpanczuk.simpleframework.Screen;

import java.util.ArrayList;


public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }


    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.menu = g.newImage("menu.png", ImageFormat.RGB565);
        Assets.back = g.newImage("cards/back.png", ImageFormat.ARGB8888);
        Assets.button = g.newImage("blue_button.png", ImageFormat.ARGB8888);
        Assets.pressedButton = g.newImage("pressed_blue_button.png", ImageFormat.ARGB8888);
        Assets.cards= new ArrayList<Image>();
        for(int i = 1; i < 25; i++){
            Assets.cards.add(g.newImage("cards/card"+i+".png", ImageFormat.ARGB8888));
        }
        game.setScreen(new MainMenuScreen(game));
    }


    @Override
    public void paint(float deltaTime) {


    }


    @Override
    public void pause() {


    }


    @Override
    public void resume() {


    }


    @Override
    public void dispose() {


    }


    @Override
    public void backButton() {


    }
}