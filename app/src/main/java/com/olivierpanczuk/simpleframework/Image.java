package com.olivierpanczuk.simpleframework;

import com.olivierpanczuk.simpleframework.Graphics.ImageFormat;

public interface Image {
    public int getWidth();
    public int getHeight();
    public ImageFormat getFormat();
    public void dispose();
}
