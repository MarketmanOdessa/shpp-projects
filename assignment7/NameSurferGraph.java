package com.shpp.p2p.cs.sburlachenko.assignment7;

/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {

    public static final int YEAR_LABEL_SIZE = 20;
    public static final int RANK_LABEL_SIZE = 15;
    public static final int DECADE = 10;
    public static final Font FONT1 = new Font("Verdana", Font.PLAIN, YEAR_LABEL_SIZE);
    public static final Font FONT2 = new Font("Verdana", Font.PLAIN, RANK_LABEL_SIZE);

    ArrayList<NameSurferEntry> charts = new ArrayList<>();
    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        addComponentListener(this);
    }


    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        charts.clear();
    }

	
	/* Method: addEntry(entry) */

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        charts.add(entry);
    }


    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        removeAll();
        double columnWidth = (double) getWidth()/NDECADES;
        for (int i = 0; i < NDECADES; i++) {
            int decade = START_DECADE+i*DECADE;
            add(new GLine(i*columnWidth, 0, i*columnWidth, getHeight()));
            String labelText = decade + "";
            GLabel label = new GLabel(labelText);
            label.setFont(FONT1);
            add(label, i*columnWidth, getHeight());
        }
        add(new GLine(0,GRAPH_MARGIN_SIZE,getWidth(),GRAPH_MARGIN_SIZE));
        add(new GLine(0,getHeight()-GRAPH_MARGIN_SIZE,getWidth(),getHeight()-GRAPH_MARGIN_SIZE));
        int color = 0;
        for (NameSurferEntry nse : charts) {
            for (int i = 0; i < NDECADES-1; i++) {
               int rankA = nse.getRank(i)==0 ? 1000 : nse.getRank(i);
               int rankB = nse.getRank(i + 1)==0 ? 1000 : nse.getRank(i+1);
                GLine line = new GLine(i * columnWidth,
                        (getHeight() - 2.0 * GRAPH_MARGIN_SIZE) / MAX_RANK * rankA + GRAPH_MARGIN_SIZE,
                        (i + 1) * columnWidth,
                        (getHeight() - 2.0 * GRAPH_MARGIN_SIZE) / MAX_RANK * rankB + GRAPH_MARGIN_SIZE);
                line.setColor(colors(color));
                add(line);
            }
            for (int i = 0; i < NDECADES; i++) {
                String rankS = nse.getRank(i)==0 ? "*" : String.valueOf(nse.getRank(i));
                int rankI = nse.getRank(i)==0 ? 1000 : nse.getRank(i);
                GLabel name = new GLabel(nse.getName() + " " + rankS);
                name.setColor(colors(color));
                name.setFont(FONT2);
                add(name, i * columnWidth,
                        (getHeight()-2.0*GRAPH_MARGIN_SIZE)/MAX_RANK * rankI+GRAPH_MARGIN_SIZE);
            }
            color++;
        }
    }
    public Color colors(int i) {
        return switch (i%4) {
            case 0 -> Color.BLUE;
            case 1 -> Color.RED;
            case 2 -> Color.MAGENTA;
            case 3 -> Color.BLACK;
            default -> throw new IllegalStateException("Unexpected value: " + i % 4);
        };
    }

    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}
