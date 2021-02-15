package com._98point6.droptoken.model.api;

import com._98point6.droptoken.model.common.Game;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public class GameStatusResponse {
    private List<String> players;
    private Integer moves;
    private String winner;
    private String state;

    public GameStatusResponse() {}

    private GameStatusResponse(Builder builder) {
        this.players = Preconditions.checkNotNull(builder.players);
        this.moves = Preconditions.checkNotNull(builder.moves);
        this.winner = builder.winner;
        this.state = Preconditions.checkNotNull(builder.state);
    }

    public GameStatusResponse(Game game)
    {
        this.players = game.getPlayers();
        this.moves = game.getMoves().size();
        this.state = game.getState();
        if (this.state.equals(Game.STATUS_DONE))
        {
            if (StringUtils.isEmpty(game.getWinner()))
            {
                this.winner = "";
            }
            else
            {
                this.winner = game.getWinner();
            }
        }
    }

    public List<String> getPlayers() {
        return players;
    }

    public Integer getMoves() {
        return moves;
    }

    public Optional<String> getWinner() {
        return Optional.ofNullable(winner);
    }

    public String getState() {
        return state;
    }

    public static class Builder {
        private List<String> players;
        private Integer moves;
        private String winner;
        private String state;

        public Builder players(List<String> players) {
            this.players = players;
            return this;
        }

        public Builder moves(Integer moves) {
            this.moves = moves;
            return this;
        }

        public Builder winner(String winner) {
            this.winner = winner;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder fromPrototype(GameStatusResponse prototype) {
            players = prototype.players;
            moves = prototype.moves;
            winner = prototype.winner;
            state = prototype.state;
            return this;
        }

        public GameStatusResponse build() {
            return new GameStatusResponse(this);
        }
    }
}
