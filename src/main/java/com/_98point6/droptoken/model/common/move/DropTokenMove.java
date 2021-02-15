package com._98point6.droptoken.model.common.move;

public class DropTokenMove {


    private String type = "MOVE";
    private String player;
    private int column;

    private DropTokenMove(Builder builder)
    {
        this.player = builder.player;
        this.column = builder.column;
    }

    public String getType() {
        return type;
    }

    public String getPlayer() {
        return player;
    }

    public int getColumn() {
        return column;
    }

    public static class Builder
    {
        private String player;
        private int column;

        public Builder player(String player)
        {
            this.player = player;
            return this;
        }

        public Builder column(int column)
        {
            this.column = column;
            return this;
        }

        public DropTokenMove build()
        {
            return new DropTokenMove(this);
        }
    }
}
