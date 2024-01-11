package com.shpp.p2p.cs.sburlachenko.assignment8;

import acm.graphics.GObject;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class Mouseloop2 extends WindowProgram {
    private static final int M = 10;
    private static final int N = 10;

    private static final int NUM_OF_BOXIES = 36;
    private static final double BOX_SIZE = 65;
    private static final double BOX_SPACING = 2;
    public static final int APPLICATION_WIDTH = 700;
    public static final int APPLICATION_HEIGHT = 700;
    private static final double BOX_WITH_SPACING = BOX_SIZE + BOX_SPACING;

    Map<Integer, GRect> map = new HashMap<>();
    int flag = 1;

    public void run() {
        addMouseListeners();
        bricks();
        moveBox();
    }

    private void moveBox() {
        while (true) {
            while (flag > 0) {
                for (int i = 1; i < NUM_OF_BOXIES + 1; i++) {
                    GRect b = map.get(i);
                    b.setColor(Color.BLUE);
                    b.setFilled(true);
                    pause(100);
                    b.setColor(Color.GREEN);
                    b.setFilled(true);
                    if (flag < 0) break;
                    GRect r = map.get(NUM_OF_BOXIES - i + 1);
                    r.setColor(Color.RED);
                    r.setFilled(true);
                    pause(100);
                    r.setColor(Color.GREEN);
                    r.setFilled(true);
                }
            }
            while (flag < 0) {
                for (int i = 36; i > 0; i--) {
                    GRect b = map.get(i);
                    b.setColor(Color.BLUE);
                    b.setFilled(true);
                    pause(100);
                    b.setColor(Color.GREEN);
                    b.setFilled(true);
                    if (flag > 0) break;
                    GRect r = map.get(NUM_OF_BOXIES - i + 1);
                    r.setColor(Color.RED);
                    r.setFilled(true);
                    pause(100);
                    r.setColor(Color.GREEN);
                    r.setFilled(true);
                }
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        GObject comp = getElementAt(e.getX(), e.getY());
        if (comp != null && comp.getColor().equals(Color.BLUE)) {
            System.out.println("oppa");
            flag = -flag;
        }
    }

    public void bricks() {
        for (int i = 0; i < M; i++) {
            GRect brick = new GRect(0,
                    BOX_WITH_SPACING * i,
                    BOX_SIZE,
                    BOX_SIZE);
            brick.setColor(Color.GREEN);
            brick.setFilled(true);
            add(brick);
            map.put(36 - i, brick);
        }
        for (int i = 0; i < M; i++) {
            GRect brick = new GRect(BOX_WITH_SPACING * (N - 1),
                    BOX_WITH_SPACING * i,
                    BOX_SIZE,
                    BOX_SIZE);
            brick.setColor(Color.GREEN);
            brick.setFilled(true);
            add(brick);
            map.put(i + 9, brick);
        }
        for (int j = 1; j < N - 1; j++) {
            GRect brick = new GRect(BOX_WITH_SPACING * j,
                    0,
                    BOX_SIZE,
                    BOX_SIZE);
            brick.setColor(Color.GREEN);
            brick.setFilled(true);
            add(brick);
            map.put(j, brick);
        }
        for (int j = 1; j < N - 1; j++) {
            GRect brick = new GRect(BOX_WITH_SPACING * j,
                    BOX_WITH_SPACING * (N - 1),
                    BOX_SIZE,
                    BOX_SIZE);
            brick.setColor(Color.GREEN);
            brick.setFilled(true);
            add(brick);
            map.put(27 - j, brick);
        }
    }
}
