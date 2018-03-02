package com.example.slava.rgbcircless;

/**
 * Created by Slava on 20.02.2018.
 */

public interface ICanvasView {
    void drawCircle(SimpleCircle circle);

    void redraw();

    void showMessage(String text);
}
