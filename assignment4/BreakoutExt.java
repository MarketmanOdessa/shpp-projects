package com.shpp.p2p.cs.sburlachenko.assignment4;

import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;


public class BreakoutExt extends WindowProgram {
    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    /**
     * Dimensions of game board (usually the same)
     */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;

    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;

    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;

    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 10;

    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;

    /**
     * Width of a brick
     */
    private static final int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;

    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;

    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;

    /**
     * Number of turns
     */
    private static final int NTURNS = 3;

    GOval ball = ball();
    GRect paddle = paddle();

    int lives = NTURNS;
    int count = NBRICK_ROWS * NBRICKS_PER_ROW;

    public void run() {
        getMenuBar().setVisible(false);
        addMouseListeners();
        bricks();
        Game();
        println("GAME OVER");
    }


    public void Game() {
        RandomGenerator rg = RandomGenerator.getInstance();
        double vx = rg.nextDouble(1.0, 3.0);
        if (rg.nextBoolean(0.5)) vx = -vx;
        double vy = 3.0;
        waitForClick();
        while (lives > 0) {
            ball.move(vx, vy);
            pause(5 + count / 10.0);
            if (ball.getX() < 0) vx = -vx;
            if (ball.getX() > WIDTH - BALL_RADIUS * 2.0) vx = -vx;
            if (ball.getY() < 0) vy = -vy;
            if (ball.getY() > HEIGHT - BALL_RADIUS * 2.0) {
                lives--;
                println("OOPS");
                ball.setLocation(WIDTH / 2.0 - BALL_RADIUS, HEIGHT / 2.0 + BALL_RADIUS);
                waitForClick();
            }
            GObject collider = getCollidingObject();
            if ((collider == paddle) && (vy < HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS)) {
                if (rg.nextBoolean(0.5)) vx = rg.nextDouble(-3.0, 3.0);
                vy = -vy;
            }
            if (collider != paddle) {
                remove(collider);
                vy = -vy;
                count--;
                println(count);
                if (count == 0) {
                    println("YOU WIN");
                    lives = 0;
                }
            }
        }
    }

    public GRect paddle() {
        GRect p = new GRect(WIDTH / 2.0 - PADDLE_WIDTH / 2.0, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
        p.setColor(Color.BLACK);
        p.setFilled(true);
        add(p);
        return p;
    }

    public GOval ball() {
        GOval b = new GOval(WIDTH / 2.0 - BALL_RADIUS, HEIGHT / 2.0 + BALL_RADIUS, BALL_RADIUS * 2.0, BALL_RADIUS * 2.0);
        b.setColor(Color.BLACK);
        b.setFilled(true);
        add(b);
        return b;
    }

    public void bricks() {
        for (int i = 0; i < NBRICK_ROWS; i++) {    // Draw rows
            for (int j = 0; j < NBRICKS_PER_ROW; j++) {  // Draw bricks
                GRect brick = new GRect(((WIDTH - (NBRICKS_PER_ROW * (BRICK_WIDTH + BRICK_SEP))) / 2.0) + (BRICK_WIDTH + BRICK_SEP) * j + BRICK_SEP / 2.0,
                        ((BRICK_Y_OFFSET) + (BRICK_HEIGHT + BRICK_SEP) * i),
                        BRICK_WIDTH,
                        BRICK_HEIGHT);
                brick.setColor(colors(i));
                brick.setFilled(true);
                add(brick);
            }
        }
    }

    public Color colors(int i) {
        return switch (i) {
            case 0, 1 -> Color.RED;
            case 2, 3 -> Color.ORANGE;
            case 4, 5 -> Color.YELLOW;
            case 6, 7 -> Color.GREEN;
            case 8, 9 -> Color.CYAN;
            default -> null;
        };
    }

    public void mouseMoved(MouseEvent e) {
        paddle.setLocation(e.getX() - PADDLE_WIDTH / 2.0, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
        if (e.getX() < PADDLE_WIDTH / 2) paddle.setLocation(0, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
        if (e.getX() > WIDTH - PADDLE_WIDTH / 2)
            paddle.setLocation(e.getX() - PADDLE_WIDTH, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
    }

    private GObject getCollidingObject() {
        double x = ball.getX();
        double y = ball.getY();
        double r = BALL_RADIUS;
        if (getElementAt(x, y) != null) return getElementAt(x, y);
        if (getElementAt(x + 2 * r, y) != null) return getElementAt(x + 2 * r, y);
        if (getElementAt(x, y + 2 * r) != null) return getElementAt(x, y + 2 * r);
        if (getElementAt(x + 2 * r, y + 2 * r) != null) return getElementAt(x + 2 * r, y + 2 * r);
        return null;
    }


}