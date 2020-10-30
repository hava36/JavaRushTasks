package com.javarush.task.task34.task3410.model;

import com.javarush.task.task34.task3410.controller.EventListener;

import javax.swing.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Set;

public class Model {

    public static final int FIELD_CELL_SIZE = 20;
    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel;
    private LevelLoader levelLoader;

    public Model() {
        try {
            this.levelLoader = new LevelLoader(Paths.get(getClass().getResource("../res/levels.txt").toURI()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.currentLevel = 1;

    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level) {
        this.gameObjects = levelLoader.getLevel(level);
        this.currentLevel = level;
    }

    public void restart() {
        restartLevel(this.currentLevel);
    }

    public void startNextLevel() {
        this.currentLevel++;
        restart();
    }

    public void move(Direction direction) {

        Player player = gameObjects.getPlayer();

        if (checkWallCollision(player, direction)) return;
        if (checkBoxCollisionAndMoveIfAvailable(direction)) return;

        int dx = 0;
        int dy = 0;

        switch (direction) {
            case RIGHT: dx += FIELD_CELL_SIZE; break;
            case LEFT: dx -= FIELD_CELL_SIZE; break;
            case DOWN:dy += FIELD_CELL_SIZE; break;
            case UP:dy -= FIELD_CELL_SIZE; break;
        }

        player.move(dx, dy);

        checkCompletion();

    }

    public void checkCompletion() {
        int countBoxInHomes = 0;
        Set<Home> homes = gameObjects.getHomes();
        Set<Box> boxes = gameObjects.getBoxes();
        for (Home home : homes
        ) {
            for (Box box : boxes
            ) {
                if ((home.getX() == box.getX()) && (home.getY() == box.getY())) {
                    countBoxInHomes++;
                }
            }
        }
        if (countBoxInHomes == homes.size()) {
            eventListener.levelCompleted(currentLevel);
        }
    }

    public boolean checkBoxCollisionAndMoveIfAvailable(Direction direction) {
        for (Box box : gameObjects.getBoxes()) {
            if (gameObjects.getPlayer().isCollision(box, direction)) {
                for (Box item : gameObjects.getBoxes()) {
                    if (!box.equals(item)) {
                        if (box.isCollision(item, direction)) {
                            return true;
                        }
                    }
                    if (checkWallCollision(box, direction)) {
                        return true;
                    }
                }
                int dx = direction == Direction.LEFT ? -FIELD_CELL_SIZE : (direction == Direction.RIGHT ? FIELD_CELL_SIZE : 0);
                int dy = direction == Direction.UP ? -FIELD_CELL_SIZE : (direction == Direction.DOWN ? FIELD_CELL_SIZE : 0);
                box.move(dx, dy);
            }
        }
        return false;
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        Set<Wall> walls = gameObjects.getWalls();
        for (Wall wall : walls
        ) {
            if (gameObject.isCollision(wall, direction)) {
                return true;
            }
        }
        return false;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

}
