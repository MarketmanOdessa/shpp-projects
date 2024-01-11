package com.shpp.p2p.cs.sburlachenko.assignment8;

import acm.graphics.GObject;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

public class Mouseloop extends WindowProgram {

    private static final int M = 10;
    private static final int N = 10;
    private static final double BOX_SIZE = 65;
    private static final double BOX_SPACING = 2;
    public static final int APPLICATION_WIDTH = 700;
    public static final int APPLICATION_HEIGHT = 700;
    private static final double BOX_WITH_SPACING = BOX_SIZE + BOX_SPACING;

    ArrayList<GRect> list = new ArrayList<>();
    ArrayList<GRect> list1 = new ArrayList<>();
    ArrayList<GRect> list2 = new ArrayList<>();
    ArrayList<GRect> list3 = new ArrayList<>();


    int flag = 1;

    public void run() {
        addMouseListeners();
        bricks();
        Collections.reverse(list2);
        Collections.reverse(list3);
        while (true) {
            if (flag > 0) {
                moveGo();
            }
            if (flag < 0) {
                moveRe();
            }
        }
    }

    public void bricks() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                GRect brick = new GRect(BOX_WITH_SPACING * j,
                        BOX_WITH_SPACING * i,
                        BOX_SIZE,
                        BOX_SIZE);
                if (i == 0 || i == 9 || j == 0 || j == 9) {
                    brick.setColor(Color.GREEN);
                    brick.setFilled(true);
                    add(brick);
                    if (j == 0) list.add(brick);
                    if (j != 0 && i == 9) list1.add(brick);
                    if (i != 9 && j == 9) {
                        list2.add(brick);
                    }
                    if (j != 0 && j != 9 && i == 0) {
                        list3.add(brick);
                    }
                } else {
                    brick.setColor(Color.WHITE);
                    brick.setFilled(true);
                    add(brick);
                }
            }
        }
    }

    public void moveGo() {
        oneMove(list);
        oneMove(list1);
        oneMove(list2);
        oneMove(list3);
    }

    public void moveRe() {
        twoMove(list3);
        twoMove(list2);
        twoMove(list1);
        twoMove(list);
    }

    public void oneMove(ArrayList<GRect> list) {
        for (GRect r : list) {
            r.setColor(Color.BLUE);
            r.setFilled(true);
            add(r);
            pause(200);
            remove(r);
            r.setColor(Color.GREEN);
            r.setFilled(true);
            add(r);
        }
    }

    public void twoMove(ArrayList<GRect> list) {
        for (int i = list.size() - 1; i > -1; i--) {
            GRect r = list.get(i);
            r.setColor(Color.BLUE);
            r.setFilled(true);
            add(r);
            pause(200);
            remove(r);
            r.setColor(Color.GREEN);
            r.setFilled(true);
            add(r);
        }
    }

    public void mousePressed(MouseEvent e) {
        GObject comp = getElementAt(e.getX(), e.getY());
        if (comp != null && comp.getColor().equals(Color.BLUE)) {
            System.out.println("oppa");
            flag = -flag;
        }
    }
}
