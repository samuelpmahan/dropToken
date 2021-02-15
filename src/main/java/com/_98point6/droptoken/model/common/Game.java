package com._98point6.droptoken.model.common;

import com._98point6.droptoken.Runtime;
import com._98point6.droptoken.exception.ExceptionStrings;
import com._98point6.droptoken.model.common.board.DropTokenBoard;
import com._98point6.droptoken.model.common.move.DropTokenMove;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


public class Game {
    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    private String id;
    private final List<String> players;
    private final List<DropTokenMove> moves;
    private final DropTokenBoard gameBoard;
    private String winner;
    private String state;
    private int nextMoveIndex;
    private final NInARowWinChecker winChecker;

    private static final int NUM_TO_WIN = 4;

    private Game(Builder builder)
    {
        // to ensure uniqueness, collision rate should be negligible w/ 2.8E12 possible IDs
        do {
            this.id = RandomStringUtils.randomAlphanumeric(8);
        }
        while (!Runtime.isValidNewId(this.id));
        this.players = builder.players;
        this.moves = new ArrayList<>();
        this.gameBoard = new DropTokenBoard.Builder().rows(builder.rows).columns(builder.columns).build();
        this.winner = null;
        this.state = STATUS_IN_PROGRESS;
        this.nextMoveIndex = 0;
        this.winChecker = new NInARowWinChecker(NUM_TO_WIN);
    }

    public boolean isInProgress()
    {
        return this.state.equals(STATUS_IN_PROGRESS);
    }

    public String getId()
    {
        return id;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void removePlayer(String playerId)
    {
        if (players.contains(playerId))
        {
            players.remove(playerId);
            if (players.size() == 1)
            {
                markWinner(players.get(0));
            }
            else
            {
                // removing end player will cause "next" player to be first in line
                if (nextMoveIndex >= players.size())
                {
                    nextMoveIndex = 0;
                }
            }
        }
    }

    public List<DropTokenMove> getMoves() {
        return moves;
    }

    public String getState() {
        return state;
    }

    public String getWinner() {
        return winner;
    }

    public String getNextPlayer()
    {
        return players.get(nextMoveIndex);
    }

    public void incrementNextPlayer()
    {
        nextMoveIndex++;
        if (nextMoveIndex >= players.size())
        {
            nextMoveIndex = 0;
        }
    }

    public int executeMove(DropTokenMove move)
    {
        String playerId = move.getPlayer();
        if (this.getPlayers().contains(playerId))
        {
            if (this.getNextPlayer().equals(playerId))
            {
                int column = move.getColumn();
                String rowString = gameBoard.executeMoveOnBoard(move);
                Integer row = Integer.parseInt(rowString);
                logger.debug("Landed in row: {}", row);
                //win check, update if winner or draw
                Optional<String> winner = winChecker.checkForWin(gameBoard, row, column);
                if (winner.isPresent())
                {
                    this.markWinner(winner.get());
                }
                //update move list
                moves.add(move);
                if (moves.size() == gameBoard.getNumCells())
                {
                    this.markTie();
                }
                incrementNextPlayer();
                return moves.size() - 1;
            }
            else
            {
                String exString = ExceptionStrings.getOutOfTurnString(playerId);
                logger.error(exString);
                throw new IllegalStateException(exString);
            }

        }
        else
        {
            String exString = ExceptionStrings.getPlayerNotFoundString(id, playerId);
            logger.error(exString);
            throw new NoSuchElementException(exString);
        }
    }

    private void markWinner(String playerId)
    {
        this.winner = playerId;
        this.state = STATUS_DONE;
    }

    private void markTie()
    {
        this.winner = null;
        this.state = STATUS_DONE;
    }

    public static class Builder {
        private List<String> players;
        private int rows;
        private int columns;

        public Builder players(List<String> players) {
            this.players = players;
            return this;
        }

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

        public void validate()
        {
            String exString = null;
            if (rows < 1)
            {
                exString = ExceptionStrings.getTooSmallString("rows", 1);
            }
            if (columns < 1 )
            {
                exString = ExceptionStrings.getTooSmallString("columns", 1);
            }
            if (players.size() < 2)
            {
                exString = ExceptionStrings.getTooSmallString("num_players", 2);
            }
            if (players.size() > new HashSet<>(players).size())
            {
                exString = ExceptionStrings.getIndistinctPlayerListString();
            }
            if (exString != null)
            {

                throw new IllegalArgumentException(exString);
            }
        }

        public Game build()
        {
            this.validate();
            return new Game(this);
        }
    }

    public static String STATUS_DONE = "DONE";
    public static String STATUS_IN_PROGRESS = "IN_PROGRESS";
}
