package com._98point6.droptoken.model.api;

import com._98point6.droptoken.model.common.move.DropTokenMove;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GetMovesResponse {
    private List<GetMoveResponse> moves;

    public GetMovesResponse() {}

    private GetMovesResponse(Builder builder) {
        this.moves = Preconditions.checkNotNull(builder.moves);
    }

    public List<GetMoveResponse> getMoves() {
        return moves;
    }

    public static class Builder {
        private List<GetMoveResponse> moves;

        public Builder moves(List<GetMoveResponse> moves) {
            this.moves = moves;
            return this;
        }

        public Builder dropTokenMoves(List<DropTokenMove> moves) {
            this.moves = new ArrayList<>();
            moves.forEach((m) -> this.moves.add(new GetMoveResponse.Builder().move(m).build()));
            return this;
        }

        public Builder fromPrototype(GetMovesResponse prototype) {
            moves = prototype.moves;
            return this;
        }

        public GetMovesResponse build() {
            return new GetMovesResponse(this);
        }
    }
}
