package com.olivierpanczuk.simpleframework;

/*
* Note from Olivier Panczuk : 21/01/2016
* Framework inspired and modified partly by the one exposed in "Beginning Android Games Development 2nd Edition"(by Mario Zechner and Robert Green)
* and in the Kilobolt series tutorials.
* */

public interface Game {

    public Audio getAudio();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public void dispose();

    public Screen getCurrentScreen();

    public Screen getInitScreen();
}
