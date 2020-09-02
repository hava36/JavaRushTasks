package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {

    private static final int SIDE = 9;

    private GameObject[][] gameField = new GameObject[SIDE][SIDE];

    private int countMinesOnField, countFlags;
    private int countClosedTiles = SIDE * SIDE;
    private int score;

    private boolean isGameStopped;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    @Override
    public void onMouseLeftClick(int x, int y) {

        if (isGameStopped) {
            restart();
        } else {
            openTile(x,y);
        }
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        markTile(x,y);
    }


    private void createGame() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                setCellColor(x, y, Color.ORANGE);
                setCellValue(x,y, "");
            }
        }
        countMineNeighbors();
        countFlags = countMinesOnField;
        score = 0;
    }

    private void countMineNeighbors() {

        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                GameObject currentfield = gameField[y][x];
                if (currentfield.isMine) continue;
                currentfield.countMineNeighbors = 0;
                List<GameObject> neighbors = getNeighbors(currentfield);
                for (GameObject neighbor : neighbors
                ) {
                    if (neighbor.isMine) {
                        currentfield.countMineNeighbors++;
                    }
                }
            }

        }

    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.y + 1; y++) {
            for (int x = gameObject.x - 1; x <= gameObject.x + 1; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (gameField[y][x] == gameObject) {
                    continue;
                }
                result.add(gameField[y][x]);
            }
        }
        return result;
    }

    private void openTile(int x, int y) {

        GameObject cell = gameField[y][x];

        if (cell.isOpen || cell.isFlag || isGameStopped) return;

        cell.isOpen = true;
        countClosedTiles--;

        if (cell.isMine) {
            setCellValueEx(x, y, Color.RED, MINE);
            gameOver();
        } else {
            if (cell.countMineNeighbors == 0) {
                setCellValue(x,y, "");
                List<GameObject> neighbors = getNeighbors(cell);
                for (GameObject neighbor:neighbors
                ) {
                    if (!neighbor.isOpen) {
                        openTile(neighbor.x, neighbor.y);
                    }
                }
            } else {
                setCellNumber(x,y, cell.countMineNeighbors);
            }
            score += 5;
            setScore(score);
            if (countClosedTiles == countMinesOnField) {
                win();
            }
        }

        setCellColor(x,y, Color.GREEN);

    }

    private void markTile(int x, int y) {

        GameObject cell = gameField[y][x];

        if (cell.isOpen || isGameStopped) return;

        if (cell.isFlag) {
            cell.isFlag = false;
            countFlags++;
            setCellValue(x, y, "");
            setCellColor(x, y, Color.ORANGE);
        } else {
            if (countFlags > 0) {
                cell.isFlag = true;
                countFlags--;
                setCellValue(x, y, FLAG);
                setCellColor(x, y, Color.YELLOW);
            }
        }

    }

    private void win() {
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "You win", Color.ALICEBLUE, 30);
    }

    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "Game Over", Color.ALICEBLUE, 30);
    }

    private void restart() {
        isGameStopped = false;
        score = 0;
        countClosedTiles = SIDE * SIDE;
        countMinesOnField = 0;
        createGame();
        setScore(score);
    }

   }