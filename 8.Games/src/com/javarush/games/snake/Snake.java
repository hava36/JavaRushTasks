package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake extends GameObject {

    private Direction direction;
    public boolean isAlive;
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";

    private List<GameObject> snakeParts = new ArrayList<>();

    public Snake(int x, int y) {

        super(x, y);

        GameObject object1 = new GameObject(x, y);
        GameObject object2 = new GameObject(x + 1, y);
        GameObject object3 = new GameObject(x + 2, y);

        snakeParts.add(object1);
        snakeParts.add(object2);
        snakeParts.add(object3);

        direction = Direction.LEFT;

        isAlive = true;

    }

    public void setDirection(Direction direction) {

        switch (this.direction) {
            case LEFT:
            case RIGHT:
                if (snakeParts.get(0).x == snakeParts.get(1).x) return;
                break;
            case UP:
            case DOWN:
                if (snakeParts.get(0).y == snakeParts.get(1).y) return;
                break;
        }
        this.direction = direction;



        if (direction == Direction.RIGHT && this.direction == Direction.LEFT) return;
        if (direction == Direction.LEFT && this.direction == Direction.RIGHT) return;
        if (direction == Direction.UP && this.direction == Direction.DOWN) return;
        if (direction == Direction.DOWN && this.direction == Direction.UP) return;

        this.direction = direction;
    }

    public void draw(Game game) {
        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject part = snakeParts.get(i);
            Color color = Color.BLACK;
            if (!isAlive) color = Color.RED;

            if (i == 0) game.setCellValueEx(part.x, part.y, Color.NONE, HEAD_SIGN, color, 75);
            else game.setCellValueEx(part.x, part.y, Color.NONE, BODY_SIGN, color, 75);
        }
    }

    public int getLength() {
        return snakeParts.size();
    }

    public void move(Apple apple) {

        GameObject newHead = createNewHead();

        if (newHead.x < 0 || newHead.x >= SnakeGame.WIDTH
                || newHead.y < 0 || newHead.y >= SnakeGame.HEIGHT) {

            isAlive = false;

        } else {

            if (checkCollision(newHead)) {
                isAlive = false;
            } else {
                snakeParts.add(0, newHead);

                if (newHead.x == apple.x && newHead.y == apple.y) {
                    apple.isAlive = false;
                } else removeTail();
            }

        }
    }

    public boolean checkCollision(GameObject gameObject) {

        boolean collision = false;

        for (GameObject part : snakeParts) {

            if (gameObject.x == part.x && gameObject.y == part.y) {
                collision = true;
            }

        }

        return collision;

    }


    public GameObject createNewHead() {

        int headX = snakeParts.get(0).x;
        int headY = snakeParts.get(0).y;

        if (direction == Direction.LEFT) headX -= 1;
        else if (direction == Direction.RIGHT) headX += 1;
        else if (direction == Direction.UP) headY -= 1;
        else if (direction == Direction.DOWN) headY += 1;

        return new GameObject(headX, headY);

    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

}
