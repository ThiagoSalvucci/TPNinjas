package com.sabu.entities;

import com.sabu.entities.pieces.Mark;

public class Player {
    private String name;
    private volatile Board board;
    private volatile Board enemyBoard;

    public Player(String name, Board board) {
        this.name = name;
        this.board = board;
        enemyBoard = new Board();
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setEnemyBoard(Board enemyBoard) {
        this.enemyBoard = enemyBoard;
    }

    public Board getEnemyBoard() {
        return enemyBoard;
    }
}
