package com.olivierpanczuk.memorygame.components;

import android.graphics.Color;

import com.olivierpanczuk.simpleframework.Graphics;
import com.olivierpanczuk.simpleframework.Input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Olivier on 19/01/2016.
 */
public class MemoryGrid {
    public static int EASY = 0;
    public static int MEDIUM = 1;
    public static int DIFFICULT = 2;
    private final int level;

    ArrayList<MemoryTile> tiles;
    int nbTiles = 0;
    int nbPairs = 0;
    int nbTouched = 0;
    int nbImages = 24;
    Set<MemoryTile> touchedTiles;
    Set<MemoryTile> foundTiles;

    public MemoryGrid(int difficultyLevel){
        tiles = new ArrayList<MemoryTile>();
        touchedTiles = new HashSet<MemoryTile>();
        foundTiles = new HashSet<MemoryTile>();
        fillTiles(difficultyLevel);
        this.level = difficultyLevel;
    }

    private void fillTiles(int level){
        if(level == EASY)nbTiles = 16;
        else if(level == MEDIUM)nbTiles = 20;
        else if(level == DIFFICULT)nbTiles = 24;

        nbPairs = nbTiles/2;

        List<Integer> poolPairs = new ArrayList<Integer>();
        /*Here I create a random set of nbPairs, we will duplicate them in the pool Array to choose from*/
        Random rng = new Random();
        Set<Integer> generated = new LinkedHashSet<Integer>();
        while (generated.size() < nbPairs)
        {
            Integer next = rng.nextInt(nbImages) + 1;
            generated.add(next);
        }
        /*Remember we add them twice !*/
        for(Integer integer : generated)poolPairs.add(integer);
        for(Integer integer : generated)poolPairs.add(integer);
        /*Shuffle it to randomly distribute it in the set*/
        Collections.shuffle(poolPairs);

        int i = 0;
        int y = 0;
        int z = 0;
        while( i < nbTiles){
            if(i%4 == 0 && i != 0){y++; z=0;}
            int type =  poolPairs.get(0).intValue();
            tiles.add(new MemoryTile(z, y, type));
            poolPairs.remove(0);
            z++;i++;
        }
    }

    public void update(float deltaTime, List<Input.TouchEvent> touchEvents, Graphics g){
        Set<MemoryTile> touchedInThisUpdate = new HashSet<MemoryTile>();
        for(MemoryTile tile : tiles){
            boolean successfulTouchEvent = tile.update(deltaTime, touchEvents, g);
            if(successfulTouchEvent)touchedInThisUpdate.add(tile);
            if(tile.getTouched())this.touchedTiles.add(tile);
        }
        if(this.touchedTiles.size() == 2){
            Iterator<MemoryTile> iter = this.touchedTiles.iterator();
            if(iter.next().getType() == iter.next().getType()){
                foundTiles.addAll(touchedTiles);
                for(MemoryTile tile : touchedTiles) {
                    tile.setFound(true);
                }
            }
        }
        if(this.touchedTiles.size() >= 3){
            this.touchedTiles.clear();
            for(MemoryTile tile: tiles){
                tile.setTouched(false);
            }
        }
    }

    public void paint(Graphics g) {
        for(MemoryTile tile : tiles){
            tile.paint(g, 100, 0);
        }
    }

    public int getNbPairs() {
        return nbPairs;
    }

    public int getNbFound() {
        return this.foundTiles.size()/2;
    }

    public int getLevel() {
        return level;
    }
}
