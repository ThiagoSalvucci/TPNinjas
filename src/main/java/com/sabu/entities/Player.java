package com.sabu.entities;

public class Player {
    private String name;
    private volatile Board board;

    public Player(String name, Board board) {
        this.name = name;
        this.board = board;
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
}
