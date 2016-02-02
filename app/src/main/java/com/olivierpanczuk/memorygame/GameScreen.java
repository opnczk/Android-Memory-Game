package com.olivierpanczuk.memorygame;

import android.graphics.Color;
import android.graphics.Paint;

import com.olivierpanczuk.memorygame.components.MemoryGrid;
import com.olivierpanczuk.simpleframework.Game;
import com.olivierpanczuk.simpleframework.Graphics;
import com.olivierpanczuk.simpleframework.Input.TouchEvent;
import com.olivierpanczuk.simpleframework.Screen;
import com.olivierpanczuk.simpleframework.FileIO;
import com.olivierpanczuk.simpleframework.implementation.AndroidFileIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Olivier on 19/01/2016.
 */

public class GameScreen extends Screen {
    private float timeSinceLastStateChange = 0;
    private long runningTime = 0;
    private long startTime = 0;
    private long highScore;

    enum GameState {
        Ready, Running, Paused, Win
    }
    GameState state = GameState.Ready;

    MemoryGrid grid;
    Paint paint;

    public GameScreen(Game game, int level) {
        super(game);

        paint = new Paint();
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        grid = new MemoryGrid(level);
        setHighScoreValue(level);
    }

    private void setHighScoreValue(int level){
        FileIO fileHelper = game.getFileIO();
        String filename = "";
        if(level == MemoryGrid.EASY){
            filename = "easy.score";
        }else if(level == MemoryGrid.MEDIUM){
            filename = "medium.score";
        }else{
            filename = "difficult.score";
        }
        FileInputStream fis = null;
        String rs = "";
        try {
            fis = (FileInputStream) fileHelper.readFile(filename);
            int content;
            while ((content = fis.read()) != -1) {
                rs += (char) content;
            }
            highScore = Long.valueOf(rs);
        } catch (IOException e) {
            if(e.getClass() == java.io.FileNotFoundException.class){
                writeHighScore(filename, "0");
            }else {
                e.printStackTrace();
            }
        }
    }

    private void writeHighScore(String filename, String data) {
        FileIO fileHelper = game.getFileIO();
        FileOutputStream fos = null;
        try {
            fos = (FileOutputStream) fileHelper.writeFile(filename);
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        timeSinceLastStateChange += deltaTime;
        if (state == GameState.Ready)
            updateReady(touchEvents);
        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if(state == GameState.Win)
            updateWin(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if (touchEvents.size() > 0) {
            startTime = System.currentTimeMillis();
            state = GameState.Running;
            timeSinceLastStateChange = 0;
        }
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        runningTime = System.currentTimeMillis() - startTime;
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            grid.update(deltaTime, touchEvents, game.getGraphics());
        }
        if (grid.getNbFound() == grid.getNbPairs()) {
            state = GameState.Win;
            timeSinceLastStateChange = 0;
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                this.state = GameState.Running;
                timeSinceLastStateChange = 0;
            }
        }
    }

    private void updateWin(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP && timeSinceLastStateChange > 0.5f) {
                    if(highScore == 0 || (runningTime < highScore)){
                        /*HighScore Save*/
                        int level = grid.getLevel();
                        String filename = "";
                        if(level == MemoryGrid.EASY)filename = "easy.score";
                        else if(level == MemoryGrid.MEDIUM) filename = "medium.score";
                        else filename = "difficult.score";
                        writeHighScore(filename,""+runningTime);
                    }
                    nullify();
                    game.setScreen(new MainMenuScreen(game));
                    return;
            }
        }
    }

    @Override
    public void paint(float deltaTime) {
        if (state == GameState.Ready)
            drawReady();
        if (state == GameState.Running)
            drawRunning();
        if (state == GameState.Paused)
            drawPaused();
        if (state == GameState.Win)
            drawWin();
    }

    private void drawReady() {
        Graphics g = game.getGraphics();
        drawRunning();
        g.drawARGB(155, 0, 0, 0);
        g.drawString("Touch a tile to begin !",g.getWidth()/2, g.getHeight()/2, paint);
    }

    private void drawRunning() {
        Graphics g = game.getGraphics();
        g.clearScreen(Color.BLACK);
        grid.paint(g);
        g.drawString("Time: "+String.format("%02d:%02d", (int) ((runningTime / (1000*60)) % 60), (int) (runningTime/ 1000) % 60 ), 150, g.getHeight() - 40, paint);
        g.drawString("HighScore: "+String.format("%02d:%02d", (int) ((highScore / (1000*60)) % 60), (int) (highScore/ 1000) % 60 ), 550, g.getHeight() - 40, paint);
    }

    private void drawPaused() {
        Graphics g = game.getGraphics();
        // Darken the entire screen so you can display the Paused screen.
        drawRunning();
        g.drawARGB(155, 0, 0, 0);
        g.drawString("Game Paused", g.getWidth() / 2, g.getHeight() / 2, paint);
    }

    private void drawWin() {
        Graphics g = game.getGraphics();
        drawRunning();
        g.drawARGB(155, 0, 0, 0);
        g.drawString("You Won !", g.getWidth() / 2, g.getHeight() / 2, paint);
        g.drawString("Your Score : "+String.format("%02d:%02d", (int) ((runningTime / (1000*60)) % 60), (int) (runningTime/ 1000) % 60 ), g.getWidth()/2, g.getHeight()/2 + 50, paint);
    }

    @Override
    public void pause() {
        if (state == GameState.Running) {
            state = GameState.Paused;
            timeSinceLastStateChange = 0;
        }
    }

    @Override
    public void resume() {

    }

    private void nullify(){
        this.grid = null;
        this.paint = null;

        System.gc();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        if(state == GameState.Running)pause();
        else if(state == GameState.Win){
            if(highScore == 0 || (runningTime < highScore)){
                        /*HighScore Save*/
                int level = grid.getLevel();
                String filename = "";
                if(level == MemoryGrid.EASY)filename = "easy.score";
                else if(level == MemoryGrid.MEDIUM) filename = "medium.score";
                else filename = "difficult.score";
                writeHighScore(filename,""+runningTime);
            }
            nullify();
            game.setScreen(new MainMenuScreen(game));
        }
        else game.setScreen(new MainMenuScreen(game));
    }
}