package com.olivierpanczuk.memorygame;

import android.graphics.Color;
import android.graphics.Paint;

import com.olivierpanczuk.memorygame.components.ClickableButton;
import com.olivierpanczuk.memorygame.components.MemoryGrid;
import com.olivierpanczuk.memorygame.utils.EventFunctions;
import com.olivierpanczuk.simpleframework.Game;
import com.olivierpanczuk.simpleframework.Graphics;
import com.olivierpanczuk.simpleframework.Input.TouchEvent;
import com.olivierpanczuk.simpleframework.Screen;

import java.util.List;


public class MainMenuScreen extends Screen {
    private ClickableButton buttonEasy;
    private ClickableButton buttonMedium;
    private ClickableButton buttonDifficult;
    private Paint paint;

    public MainMenuScreen(Game game) {
        super(game);
        
        paint = new Paint();
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        
        buttonEasy = new ClickableButton("Easy");
        buttonMedium = new ClickableButton("Medium");
        buttonDifficult = new ClickableButton("Difficult");

        Graphics g = game.getGraphics();
        buttonEasy.setPosition(g.getWidth()/2- (g.getWidth()/4), game.getGraphics().getHeight()/2);
        buttonEasy.setSize(g.getWidth()/2, g.getHeight()/10);
        buttonMedium.setPosition(g.getWidth()/2 - (g.getWidth()/4), g.getHeight()/2 + g.getHeight()/10 + 40);
        buttonMedium.setSize(g.getWidth()/2, g.getHeight()/10);
        buttonDifficult.setPosition(g.getWidth()/2 - (g.getWidth()/4), (g.getHeight()/2 + (g.getHeight()/10)*2) + 40*2);
        buttonDifficult.setSize(g.getWidth()/2, g.getHeight()/10);
    }


    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        if(buttonEasy.update(deltaTime, touchEvents))  game.setScreen(new GameScreen(game, MemoryGrid.EASY));
        if(buttonMedium.update(deltaTime, touchEvents))  game.setScreen(new GameScreen(game, MemoryGrid.MEDIUM));
        if(buttonDifficult.update(deltaTime, touchEvents))  game.setScreen(new GameScreen(game, MemoryGrid.DIFFICULT));
    }


    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.menu, 0, 0, g.getWidth(), g.getHeight());
        buttonEasy.paint(g, paint);
        buttonMedium.paint(g, paint);
        buttonDifficult.paint(g, paint);
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
        game.dispose();
    }
}