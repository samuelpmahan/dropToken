package com._98point6.droptoken.model.common.board;

import com._98point6.droptoken.exception.ExceptionStrings;
import com._98point6.droptoken.model.common.move.DropTokenMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropTokenBoard {
    private  static final Logger logger = LoggerFactory.getLogger(DropTokenBoard.class);

    protected String[][] board;
    private int numCells;
    private int[] lowestAvailableCellByColumn;

    private DropTokenBoard(int rows, int columns)
    {
        this.numCells = rows * columns;
        this.board = new String[columns][rows];
        // inits each array location to 0 bc of the int primitive
        this.lowestAvailableCellByColumn = new int[columns];
    }

    public String executeMoveOnBoard(DropTokenMove move)
    {
        int column = move.getColumn();
        String playerId = move.getPlayer();
        if (isValidColumn(column))
        {
            if (!isColumnFull(column))
            {
                int row = lowestAvailableCellByColumn[column];
                board[column][row] = playerId;
                lowestAvailableCellByColumn[column]++;
                return String.valueOf(row);
            }
            else
            {
                String exString = ExceptionStrings.getFullColumnString(column);
                logger.error(exString);
                throw new IllegalArgumentException(exString);
            }
        }
        else
        {
            String exString = ExceptionStrings.getInvalidColumnString((column));
            logger.error(exString);
            throw new IllegalArgumentException(exString);
        }
    }

    public boolean isValidCell(int row, int column)
    {
        return isValidColumn(column) && isValidRow(row);
    }

    public String getCell(int row, int column)
    {
        if (isValidCell(row, column))
        {
            return board[column][row];
        }
        return null;
    }

    public int getNumCells() {
        return numCells;
    }

    public boolean isValidColumn(int column)
    {
        return column > -1 && column < board.length;
    }

    public boolean isValidRow(int row)
    {
        return row > -1 && row < board[0].length;
    }

    //returns true (full) when row is invalid ("lowest" is outside board)
    public boolean isColumnFull(int column)
    {
        return !isValidRow(lowestAvailableCellByColumn[column]);
    }

    public static class Builder
    {
        private int rows;
        private int columns;

        public Builder rows(int rows)
        {
            this.rows = rows;
            return this;
        }

        public Builder columns(int columns)
        {
            this.columns = columns;
            return this;
        }

        public DropTokenBoard build()
        {
            return new DropTokenBoard(rows, columns);
        }

    }
}
