package com.javarush.games.snake;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SnakeGame extends Game {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private int turnDelay;
    private Snake snake;
    private Apple apple;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;


    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
        super.initialize();
    }

    private void createGame() {

        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        snake.draw(this);

        createNewApple();

        turnDelay = 300;
        setTurnTimer(turnDelay);

        isGameStopped = false;
        score = 0;
        setScore(score);
        drawScene();

    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    private void createNewApple() {

        boolean collision = true;

        while (collision) {

            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));

            collision = snake.checkCollision(apple);
        }

    }

    private void gameOver() {

        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "GAME OVER", Color.BLACK, 25);
    }

    private void win() {

        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "YOU WIN", Color.BLACK, 25);

    }

    @Override
    public void onKeyPress(Key key) {

        switch (key) {
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case UP:
                snake.setDirection(Direction.UP);
                break;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
            case SPACE:
                if (isGameStopped) createGame();
                break;
            default:
                break;
        }

    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);

    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!snake.isAlive) gameOver();
        if (!apple.isAlive) {
            createNewApple();
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);

        }
        if (GOAL < snake.getLength()) win();
        drawScene();
    }
}
