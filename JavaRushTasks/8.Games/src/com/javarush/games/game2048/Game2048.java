package com.javarush.games.game2048;
import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Game2048 extends Game {

    private static final int SIDE  = 4;
    private int[][] gameField = new int[SIDE][SIDE];
    private boolean isGameStopped = false;
    private int score = 0;

    @Override
    public void onKeyPress(Key key) {

        if (key == Key.SPACE) {
          isGameStopped = false;
          createGame();
          drawScene(); return;
        }

        if (!canUserMove()) {
            gameOver();
            return;
        }

        if (!isGameStopped) {
            switch (key) {
                case RIGHT: moveRight(); drawScene(); break;
                case LEFT: moveLeft(); drawScene(); break;
                case UP: moveUp(); drawScene(); break;
                case DOWN:moveDown(); drawScene(); break;
                default: break;
            }
        }
        
    }

    private void gameOver() {

        isGameStopped = true;
        showMessageDialog(Color.BLACK, "Game over", Color.WHEAT, 18);

    }

    private boolean canUserMove() {

        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                int value = gameField[y][x];
                if (value == 0 ) return true;
                if (y < SIDE-1) {
                    if (value == gameField[y+1][x]) return true;
                }
                if (y > 0) {
                    if (value == gameField[y-1][x]) return true;
                }
                if (x < SIDE-1) {
                    if (value == gameField[y][x+1]) return true;
                }
                if (x > 0) {
                    if (value == gameField[y][x-1]) return true;
                }
            }
        }

        return false;

    }

    private void rotateClockwise() {

        int[][] rotatedGameField = new int[SIDE][SIDE];

        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                rotatedGameField[x][SIDE-y-1] = gameField[y][x];
            }
        }

        gameField = rotatedGameField;


    }

    private void moveLeft() {
        boolean create = false;
        for (int y = 0; y < SIDE; y++) {
            if (compressRow(gameField[y])) create = true;
            if (mergeRow(gameField[y])) create = true;
            if (compressRow(gameField[y])) create = true;
        }
        if (create) createNewNumber();
    }

    private void moveRight() {

        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();

    }

    private void moveUp() {

        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        
    }

    private void moveDown() {

        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();

    }

    @Override
    public void initialize() {
        super.initialize();
        setScreenSize(SIDE, SIDE);
        createGame();
        drawScene();
    }

    private void createGame() {

        gameField = new int[SIDE][SIDE];
        score = 0;
        setScore(score);
        createNewNumber();
        createNewNumber();

    }

    private void win() {

        isGameStopped = true;
        showMessageDialog(Color.BLACK, "Win", Color.ALICEBLUE, 14);

    }

    private void createNewNumber() {

        if (getMaxTileValue() == 2048) {
            win();
        }

        int x = getRandomNumber(SIDE);
        int y = getRandomNumber(SIDE);

        int currentValue = gameField[y][x];

        if (currentValue == 0) {

            int numberParam = getRandomNumber(10);
            int newValue = 2;
            if (numberParam == 9 ) {
                newValue = 4;
            };

            gameField[y][x] = newValue;

        } else {
            createNewNumber();
        }

    }

    private void setCellColoredNumber(int x, int y, int value) {

        String result = Integer.toString(value);

        Color color = getColorByValue(value);

        if (value == 0) {
            result = "";
        }

        setCellValueEx(x,y, color, result);

    }

    private Color getColorByValue(int value) {

        Map<Integer, Color> colorMatches = new HashMap<>();
        colorMatches.put(0, Color.NONE);
        colorMatches.put(2, Color.ALICEBLUE);
        colorMatches.put(4, Color.GREEN);
        colorMatches.put(8, Color.RED);
        colorMatches.put(16, Color.BLUE);
        colorMatches.put(32, Color.AZURE);
        colorMatches.put(64, Color.BEIGE);
        colorMatches.put(128, Color.DARKCYAN);
        colorMatches.put(256, Color.DARKVIOLET);
        colorMatches.put(512, Color.GHOSTWHITE);
        colorMatches.put(1024, Color.LIGHTCYAN);
        colorMatches.put(2048, Color.CHOCOLATE);

        Color color = colorMatches.get(value);

        if (color == null) {
            color = Color.NONE;
        }

        return color;

    }

    private int getMaxTileValue() {

        int maxtile = 0;

        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                int currentTile = gameField[y][x];
                if (currentTile > maxtile) {
                    maxtile = currentTile;
                }
            }
        }

        return maxtile;

    }

    private boolean compressRow(int[] row) {


        int[] compressedRow = {0,0,0,0};
        int compressedIndex = 0;

        for (int index = 0; index < row.length; index++) {
            int value = row[index];
            if (value > 0) {
                compressedRow[compressedIndex] = value;
                compressedIndex++;
            }
        }

        boolean rowsWasChanged = !Arrays.equals(compressedRow, row);

        for (int index = 0; index < compressedRow.length; index++) {
            row[index] = compressedRow[index];
        }
        
        return rowsWasChanged;

    }

    private boolean mergeRow(int[] row) {

        int[] mergedRow = {0,0,0,row[row.length-1]};

        for (int index = 0; index < row.length-1; index++) {

            if (row[index]>0 && row[index+1] > 0 && row[index] == row[index+1]) {
                mergedRow[index] = row[index] + row[index+1];
                mergedRow[index+1] = 0;
                score += mergedRow[index];
                setScore(score);
                index++;
            } else {
                mergedRow[index] = row[index];
            }

        }


        boolean rowsWasChanged = !Arrays.equals(mergedRow, row);

        for (int index = 0; index < mergedRow.length; index++) {
            row[index] = mergedRow[index];
        }
        return rowsWasChanged;
    }


    private void drawScene() {
        for (int y=0;y<SIDE;y++) {
            for (int x = 0; x<SIDE; x++) {
                setCellColoredNumber(x, y, gameField[y][x]);
            }
        }
        setScore(score);
    }

}
