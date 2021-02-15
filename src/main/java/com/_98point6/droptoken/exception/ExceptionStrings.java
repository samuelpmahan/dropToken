package com._98point6.droptoken.exception;

public class ExceptionStrings
{
    public static String getPlayerNotFoundString(String gameId, String playerId)
    {
        return String.format("Player <%s> not found in game <%s>", gameId, playerId);
    }

    public static String getGameNotFoundString(String gameId)
    {
        return String.format("Game <%s> not found", gameId);
    }

    public static String getGameAlreadyExistsString(String gameId)
    {
        return String.format("Game <%s> already exists", gameId);
    }

    public static String getGameCompletedString(String gameId)
    {
        return String.format("Could not modify game <%s>; it is already complete", gameId);
    }

    public static String getStartGreaterThanUntilString()
    {
        return String.format("Start value should be less than or equal to until value");
    }

    public static String getMovesDontExistString()
    {
        return String.format("Requested move(s) do not exist");
    }

    public static String getOutOfTurnString(String playerId)
    {
        return String.format("Player <%s> tried to play but it wasn't their turn", playerId);
    }

    public static String getInvalidColumnString(int columnId)
    {
        return String.format("Column <%d> does not exist in this game", columnId);
    }

    public static String getFullColumnString(int columnId)
    {
        return String.format("Column <%d> is full, try another", columnId);
    }

    public static String getTooSmallString(String name, int min)
    {
        return String.format("Param <%s> is too small, minimum is <%d>", name, min);
    }

    public static String getIndistinctPlayerListString()
    {
        return "All player names must be distinct";
    }
}
