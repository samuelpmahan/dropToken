package com._98point6.droptoken.model.common;

import com._98point6.droptoken.model.common.board.DropTokenBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class NInARowWinChecker
{
    private static final Logger logger = LoggerFactory.getLogger(NInARowWinChecker.class);

    private final int numToWin;

    public NInARowWinChecker(int numToWin)
    {
        this.numToWin = numToWin;
    }

    public Optional<String> checkForWin(DropTokenBoard board, int row, int column)
    {
        if (board.isValidCell(row, column))
        {
            // possible dirs are Left, Right, Down, LeftDown, LeftUp, RightUp, RightDown
            for (Direction dir : Direction.values()) {
                Optional<String> winner = this.checkForWinInDirection(board, row, column, dir);
                if (winner.isPresent())
                {
                    logger.info(String.format("Found winner starting at {%d,%d} in direction: <%s>", row, column, dir.name()));
                    return winner;
                }
            }
        }
        return Optional.empty();
    }

    private Optional<String> checkForWinInDirection(DropTokenBoard board, int rowStart, int columnStart, Direction dir)
    {
        if (board.isValidCell(rowStart, columnStart))
        {
            String player = board.getCell(rowStart, columnStart);
            if (player != null)
            {
                int x_offset, y_offset, new_col, new_row;
                for (int offset = 1; offset < numToWin; offset++)
                {
                    x_offset = offset * dir.getX_dir();
                    y_offset = offset * dir.getY_dir();
                    new_col = columnStart + x_offset;
                    new_row = rowStart + y_offset;
                    if (!player.equals(board.getCell(new_row, new_col)))
                    {
                        logger.debug("Win for player {} in dir {} failed at offset: {}", player, dir.name(), offset);
                        return Optional.empty();
                    }
                }
                return Optional.of(player);
            }
        }
        return Optional.empty();
    }



    private enum Direction {
        LEFT(-1,0), RIGHT(1, 0), DOWN(0,-1),
        LEFT_UP(-1,1), LEFT_DOWN(-1,-1), RIGHT_UP(1,1), RIGHT_DOWN(1,-1);

        private int x_dir;
        private int y_dir;

        Direction(int x_dir, int y_dir)
        {
            this.x_dir = x_dir;
            this.y_dir = y_dir;
        }

        public int getX_dir() {
            return x_dir;
        }

        public int getY_dir() {
            return y_dir;
        }
    }
}
