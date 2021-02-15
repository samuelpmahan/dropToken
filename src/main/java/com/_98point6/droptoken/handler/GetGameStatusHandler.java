package com._98point6.droptoken.handler;

import com._98point6.droptoken.Runtime;
import com._98point6.droptoken.model.common.Game;

public class GetGameStatusHandler {
    public static Game handle(String id)
    {
        return Runtime.getGame(id);
    }
}
