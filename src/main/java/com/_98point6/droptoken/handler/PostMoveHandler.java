package com._98point6.droptoken.handler;

import com._98point6.droptoken.Runtime;
import com._98point6.droptoken.exception.ExceptionStrings;
import com._98point6.droptoken.exception.GoneException;
import com._98point6.droptoken.model.common.Game;
import com._98point6.droptoken.model.common.move.DropTokenMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostMoveHandler
{
    public static final Logger logger = LoggerFactory.getLogger(PostMoveHandler.class);

    public static int handle(String gameId, String playerId, int column)
    {
        Game curGame = Runtime.getGame(gameId);
        if (curGame.isInProgress()) {
            DropTokenMove move = new DropTokenMove.Builder()
                    .player(playerId)
                    .column(column)
                    .build();
            int ret = curGame.executeMove(move);
            if (curGame.getState().equals(Game.STATUS_DONE)) {
                Runtime.updateGame(curGame);
            }
            logger.info("Player <{}> played in column <{}> in game <{}>", playerId, column, gameId);
            return ret;
        }
        else
        {
            String exString = ExceptionStrings.getGameCompletedString(gameId);
            logger.error(exString);
            throw new GoneException(exString);
        }
    }
}
