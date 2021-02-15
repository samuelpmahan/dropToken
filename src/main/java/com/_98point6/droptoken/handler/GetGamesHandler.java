package com._98point6.droptoken.handler;

import com._98point6.droptoken.Runtime;

import java.util.List;

public class GetGamesHandler {
    public static List<String> handle()
    {
        return Runtime.getGameIdsInProgress();
    }
}
