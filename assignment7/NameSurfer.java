package com.shpp.p2p.cs.sburlachenko.assignment7;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import com.shpp.cs.a.simple.SimpleProgram;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {
    /* Width of the JTextField */
    private final int FIELD_LENGTH = 30;
    private JTextField field;
    private JButton buttonClear;

    private NameSurferGraph graph;
    NameSurferDataBase nsdb = new NameSurferDataBase(NAMES_DATA_FILE);

    public NameSurfer() throws IOException {

    }
    /* Method: init() */
    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */
    public void init() {
        graph = new NameSurferGraph();
        add(graph);
        JLabel label = new JLabel("Name: ");
        add(label, NORTH);
        field = new JTextField(FIELD_LENGTH);
        add(field,NORTH);
        JButton buttonGraph = new JButton("Graph");
        add(buttonGraph, NORTH);
        buttonClear = new JButton("Clear");
        add(buttonClear, NORTH);
        buttonClear.setActionCommand("clearEmAll");
        field.setActionCommand("EnterPressed");
        field.addActionListener(this);
        addActionListeners();
    }

	/* Method: actionPerformed(e) */
    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("EnterPressed")) {
            if (nsdb.findEntry(field.getText())==null) System.out.println("Name not found");
            else graph.addEntry(nsdb.findEntry(field.getText()));
            graph.update();
        } else if (e.getSource() == buttonClear) {
            graph.clear();
            graph.update();
        } else {
            if (nsdb.findEntry(field.getText())==null) System.out.println("Name not found");
            else graph.addEntry(nsdb.findEntry(field.getText()));
            graph.update();
        }
    }
}
