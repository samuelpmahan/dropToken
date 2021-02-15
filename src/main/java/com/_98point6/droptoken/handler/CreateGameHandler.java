package com._98point6.droptoken.handler;

import com._98point6.droptoken.Runtime;
import com._98point6.droptoken.model.common.Game;

import java.util.List;

public class CreateGameHandler {

    public static String handle(List<String> players, int columns, int rows)
    {
        Game newGame = new Game.Builder()
                .players(players)
                .columns(columns)
                .rows(rows)
                .build();

        Runtime.addNewGame(newGame);

        return newGame.getId();
    }

}
