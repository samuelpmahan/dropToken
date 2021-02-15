package com._98point6.droptoken;

import com._98point6.droptoken.exception.ExceptionStrings;
import com._98point6.droptoken.model.common.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Runtime
{
    private static final Logger logger = LoggerFactory.getLogger(Runtime.class);

    private static final Map<String, Game> inProgressGames = new HashMap<>();
    private static final Map<String, Game> completeGames = new HashMap<>();

    public static void addNewGame(Game newGame)
    {
        if (isValidNewId(newGame.getId()))
        {
            inProgressGames.put(newGame.getId(), newGame);
        }
        else
        {
            // should never hit this
            String exString = ExceptionStrings.getGameAlreadyExistsString(newGame.getId());
            logger.error(exString);
            throw new UnsupportedOperationException(exString);
        }
    }

    public static List<String> getGameIdsInProgress()
    {
        return new ArrayList<>(inProgressGames.keySet());
    }

    public static Game getGame(String gameId)
    {
        if (inProgressGames.containsKey(gameId))
        {
            return inProgressGames.get(gameId);
        }
        else if (completeGames.containsKey(gameId))
        {
            return completeGames.get(gameId);
        }
        else
        {
            String exString = ExceptionStrings.getGameNotFoundString(gameId);
            logger.error(exString);
            throw new NoSuchElementException(exString);
        }
    }

    public static void updateGame(Game game)
    {
        if (game.isInProgress())
        {
            inProgressGames.put(game.getId(), game);
        }
        else
        {
            if (inProgressGames.containsKey(game.getId()))
            {
                inProgressGames.remove(game.getId());
            }
            completeGames.put(game.getId(), game);
        }
    }

    public static boolean isValidNewId(String id)
    {
        return (!completeGames.containsKey(id)
                && !inProgressGames.containsKey(id));
    }
}
