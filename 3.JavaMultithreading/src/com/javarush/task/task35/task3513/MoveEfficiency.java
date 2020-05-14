package com.javarush.task.task35.task3513;

public class MoveEfficiency implements Move,Comparable<MoveEfficiency> {

    private int numberOfEmptyTiles;
    private int score;
    private Move move;

    @Override
    public void move() {

    }

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency o) {

        if (numberOfEmptyTiles != o.numberOfEmptyTiles)
            return Integer.compare(numberOfEmptyTiles, o.numberOfEmptyTiles);
        else
            return Integer.compare(score, o.score);
    }

    public int getNumberOfEmptyTiles() {
        return numberOfEmptyTiles;
    }

    public int getScore() {
        return score;
    }
}
