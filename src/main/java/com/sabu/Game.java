package com.sabu;

import com.sabu.entities.Player;

import java.util.ArrayList;
import java.util.List;

import static com.sabu.Constants.MAX_PLAYERS;

public class Game {
    private final List<Player> players;


    public Game() {
        players = new ArrayList<>(MAX_PLAYERS);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player getPlayer(int id) {
        return players.get(id);
    }

}
