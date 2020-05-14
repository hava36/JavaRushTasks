package com.javarush.task.task35.task3513;

import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    private Stack<Tile[][]> previousStates = new Stack();
    private Stack<Integer> previousScores = new Stack();
    private boolean isSaveNeeded = true;

    int score;
    int maxTile;

    public Model() {
        resetGameTiles();
    }

    private List<Tile> getEmptyTiles() {
        List<Tile> list = new ArrayList<>();
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[i].length; j++) {
                if (gameTiles[i][j].isEmpty()) list.add(gameTiles[i][j]);
            }
        }
        return list;
    }

    private void saveState(Tile[][] tiles) {

        Tile[][] savedTiles = new Tile[gameTiles.length][gameTiles[0].length];
        for (int i=0;i<savedTiles.length;i++){
            for (int j=0;j<savedTiles[0].length;j++){
                savedTiles[i][j]=new Tile(gameTiles[i][j].value);
            }
        }
        previousStates.push(savedTiles);
        previousScores.push(score);
        isSaveNeeded=false;

    }


    public void rollback () {

        if (previousScores.isEmpty() | previousStates.isEmpty()) return;

        score = previousScores.pop();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j].value = previousStates.peek()[i][j].value;
            }
        }
        gameTiles = previousStates.pop();

    }

    private void addTile() {
        //        receive list of empty tiles;
        List<Tile> emptyTiles = getEmptyTiles();
        if(emptyTiles.size() != 0)
            emptyTiles.get((int)(emptyTiles.size() * Math.random())).value = (Math.random() < 0.9 ? 2 : 4);
    }


    public void resetGameTiles() {
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[i].length; j++) {
                gameTiles[i][j] = new Tile();
            }
        }

        addTile();
        addTile();

    }

    public boolean hasBoardChanged() {

        int weight1 = 0;
        int weight2 = 0;

        if (!previousStates.isEmpty()) {

            Tile[][] prevGameTiles = previousStates.peek();

            for (int i = 0; i< FIELD_WIDTH; i++) {
                for (int j = 0 ; j < FIELD_WIDTH; j ++) {
                    weight1 += gameTiles[i][j].value;
                    weight2 += prevGameTiles[i][j].value;
                }
            }

        }

        return weight1 != weight2;

    }

    public MoveEfficiency getMoveEfficiency(Move move) {

        move.move();
        MoveEfficiency moveEfficiency = new MoveEfficiency(getEmptyTiles().size(), score, move);
        if (!hasBoardChanged())
            moveEfficiency = new MoveEfficiency(-1, 0, move);

        rollback();

        return moveEfficiency;

    }

    public void autoMove() {

        PriorityQueue<MoveEfficiency> priorityQueue = new PriorityQueue(4,Collections.reverseOrder());

        priorityQueue.add(getMoveEfficiency(new Move() {
            @Override
            public void move() {
                left();
            }
        }));
        priorityQueue.add(getMoveEfficiency(new Move() {
            @Override
            public void move() {
                right();
            }
        }));
        priorityQueue.add(getMoveEfficiency(new Move() {
            @Override
            public void move() {
                up();
            }
        }));
        priorityQueue.add(getMoveEfficiency(new Move() {
            @Override
            public void move() {
                down();
            }
        }));

        priorityQueue.poll().getMove().move();

    }

//  Сжатие плиток, таким образом, чтобы все пустые плитки были справа, т.е. ряд {4, 2, 0, 4}
// становится рядом {4, 2, 4, 0}

    private boolean compressTiles(Tile[] tiles) {
        boolean anyChange = false;
        for (int i = 0; i < tiles.length; i ++) {

            if (tiles[i].value == 0) {
                outer:
                for (int j = i+1; j < tiles.length; j++) {
                    if (tiles[j].value != 0) {
                        tiles[i].value = tiles[j].value;
                        tiles[j].value = 0;
                        anyChange = true;
                        break outer;
                    }
                }
            }
        }

        return anyChange;
    }
//     Слияние плиток одного номинала, т.е. ряд {4, 4, 2, 0} становится рядом {8, 2, 0, 0}.
//Обрати внимание, что ряд {4, 4, 4, 4} превратится в {8, 8, 0, 0}, а {4, 4, 4, 0} в {8, 4, 0, 0}.

    private boolean mergeTiles(Tile[] tiles) {
        boolean change = false;

        for (int i = 0; i < tiles.length; i++) {
            try {

                if (tiles[i].value > 0 && tiles[i].value == tiles[i + 1].value) {

                    tiles[i].value += tiles[i + 1].value;
// Если выполняется условие слияния плиток, проверяем является ли новое значения больше максимального
// и при необходимости меняем значение поля maxTile.
                    if (tiles[i].value > maxTile) {
                        maxTile = tiles[i].value;
                    }
//Увеличиваем значение поля score на величину веса плитки образовавшейся в результате слияния.
                    score += tiles[i].value;
                    change = true;

                    for (int j = i + 1; j < tiles.length; j++) {
                        if (j != tiles.length - 1) {
                            tiles[j].value = tiles[j + 1].value;
                        } else tiles[j].value = 0;
                    }
                }
            }catch (ArrayIndexOutOfBoundsException ex) {
                continue;
            }
        }
        return change;
    }

    public void randomMove() {

        int n = ((int) (Math.random() * 100)) % 4;

        switch (n) {
            case 0: left();
            case 1: right();
            case 2: up();
            case 3: down();
            default:break;
        }

    }

    public void left() {

        if (isSaveNeeded) saveState(gameTiles);

        boolean isChange = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i]))
                isChange = true;
        }

        if (isChange) {
            addTile();
        }

        isSaveNeeded = true;
    }

    public void right() {
        saveState(gameTiles);
        rotateToRight();
        rotateToRight();
        left();
        rotateToRight();
        rotateToRight();
    }

    public void up() {
        saveState(gameTiles);
        rotateToRight();
        rotateToRight();
        rotateToRight();
        left();
        rotateToRight();
    }

    public void down() {
        saveState(gameTiles);
        rotateToRight();
        left();
        rotateToRight();
        rotateToRight();
        rotateToRight();
    }

    private void rotateToRight() {
        Tile tmp;
        for (int i = 0; i < FIELD_WIDTH / 2; i++) {
            for (int j = i; j < FIELD_WIDTH - 1 - i; j++) {
                tmp = gameTiles[i][j];
                gameTiles[i][j] = gameTiles[FIELD_WIDTH - j - 1][i];
                gameTiles[FIELD_WIDTH - j - 1][i] = gameTiles[FIELD_WIDTH - i - 1][FIELD_WIDTH - j - 1];
                gameTiles[FIELD_WIDTH - i - 1][FIELD_WIDTH - j - 1] = gameTiles[j][FIELD_WIDTH - i - 1];
                gameTiles[j][FIELD_WIDTH - i - 1] = tmp;
            }
        }
    }


    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public boolean canMove() {

        if (!getEmptyTiles().isEmpty()) return true;

        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 1; j < gameTiles.length; j++) {
                if (gameTiles[i][j].value == gameTiles[i][j-1].value)
                    return true;
            }
        }
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 1; j < gameTiles.length; j++) {
                if (gameTiles[j][i].value == gameTiles[j-1][i]. value)return true;
            }
        }

        return false;

    }

}
