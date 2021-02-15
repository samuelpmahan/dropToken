package com._98point6.droptoken.handler;

import com._98point6.droptoken.Runtime;
import com._98point6.droptoken.exception.ExceptionStrings;
import com._98point6.droptoken.model.common.Game;
import com._98point6.droptoken.model.common.move.DropTokenMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class GetMovesHandler {
    private static final Logger logger = LoggerFactory.getLogger(GetMovesHandler.class);

    public static DropTokenMove handle(String gameId, int index)
    {
        return handle(gameId, index, index).get(0);
    }

    public static List<DropTokenMove> handle(String gameId, int start, int until)
    {
        logger.info("Starting get moves handle");
        if (start <= until)
        {
            Game game = Runtime.getGame(gameId);
            List<DropTokenMove> moves = game.getMoves();
            logger.info("Move list size: {}", moves.size());
            if (until < moves.size()) {
                if (until == start)
                {
                    return Arrays.asList(moves.get(start));
                }
                return moves.subList(start, until + 1);
            }
            else
            {
                String exString = ExceptionStrings.getMovesDontExistString();
                logger.error(exString);
                throw new NoSuchElementException(exString);
            }
        }
        else
        {
            String exString = ExceptionStrings.getStartGreaterThanUntilString();
            logger.error(exString);
            throw new IllegalArgumentException(exString);
        }
    }
}
