package com.olivierpanczuk.memorygame.utils;

import com.olivierpanczuk.simpleframework.Input;

/**
 * Created by Olivier on 20/01/2016.
 */
public class EventFunctions {

    public static boolean inBounds(Input.TouchEvent event, int x, int y, int width,
                             int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y
                && event.y < y + height - 1)
            return true;
        else
            return false;
    }
}
