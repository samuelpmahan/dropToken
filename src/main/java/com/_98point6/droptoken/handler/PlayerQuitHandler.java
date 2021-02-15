package com._98point6.droptoken.handler;

import com._98point6.droptoken.Runtime;
import com._98point6.droptoken.exception.ExceptionStrings;
import com._98point6.droptoken.exception.GoneException;
import com._98point6.droptoken.model.common.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

public class PlayerQuitHandler {
    private static final Logger logger = LoggerFactory.getLogger(PlayerQuitHandler.class);

    public static void handle(String gameId, String playerId)
    {
        Game game = Runtime.getGame(gameId);
        if (game.isInProgress())
        {
            if (game.getPlayers().contains(playerId)) {
                game.removePlayer(playerId);
                Runtime.updateGame(game);
                logger.info("Successfully removed player <{}> from game <{}>", playerId, gameId);
            }
            else
            {
                String exString = ExceptionStrings.getPlayerNotFoundString(gameId, playerId);
                logger.error(exString);
                throw new NoSuchElementException(exString);
            }
        }
        else
        {
            String exString = ExceptionStrings.getGameCompletedString(gameId);
            logger.error(exString);
            throw new GoneException(exString);
        }
    }
}
