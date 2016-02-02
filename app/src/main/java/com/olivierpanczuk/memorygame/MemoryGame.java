package com.olivierpanczuk.memorygame;

import com.olivierpanczuk.memorygame.LoadingScreen;
import com.olivierpanczuk.simpleframework.Screen;
import com.olivierpanczuk.simpleframework.implementation.AndroidGame;
/**
 * Created by Olivier on 19/01/2016.
 */
public class MemoryGame extends AndroidGame {
    @Override
    public Screen getInitScreen() {
        return new LoadingScreen(this);
    }

}