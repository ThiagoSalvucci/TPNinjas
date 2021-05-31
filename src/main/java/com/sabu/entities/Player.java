package com.sabu.entities;

public class Player {
    private String name;
    private Board board;


    public Player(String name, Board board) {
        this.name = name;
        this.board = board;
    }
    public Player(String name){
        this.name = name;
    }

    public void validate() {
    }

    public String getName() {
        return name;
    }
    public Board getBoard() {
        return board;
    }


}
