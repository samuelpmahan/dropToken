package com._98point6.droptoken;

import com._98point6.droptoken.handler.*;
import com._98point6.droptoken.model.api.CreateGameRequest;
import com._98point6.droptoken.model.api.CreateGameResponse;
import com._98point6.droptoken.model.api.GameStatusResponse;
import com._98point6.droptoken.model.api.GetGamesResponse;
import com._98point6.droptoken.model.api.GetMoveResponse;
import com._98point6.droptoken.model.api.GetMovesResponse;
import com._98point6.droptoken.model.api.PostMoveRequest;
import com._98point6.droptoken.model.api.PostMoveResponse;
import com._98point6.droptoken.model.common.Game;
import com._98point6.droptoken.model.common.move.DropTokenMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 *
 */
@Path("/drop_token")
@Produces(MediaType.APPLICATION_JSON)
public class DropTokenResource {
    private static final Logger logger = LoggerFactory.getLogger(DropTokenResource.class);

    public DropTokenResource() {
    }

    @GET
    public Response getGames()
    {
        List<String> games = GetGamesHandler.handle();
        GetGamesResponse response = new GetGamesResponse.Builder().games(games).build();
        return Response.ok(response).build();
    }

    @POST
    public Response createNewGame(CreateGameRequest request) {
        logger.info("request={}", request);
        String newId = CreateGameHandler.handle(request.getPlayers(), request.getColumns(), request.getRows());
        CreateGameResponse response = new CreateGameResponse.Builder().gameId(newId).build();
        return Response.ok(response).build();
    }

    @Path("/{id}")
    @GET
    public Response getGameStatus(@PathParam("id") String gameId) {
        logger.info("gameId = {}", gameId);
        Game game = GetGameStatusHandler.handle(gameId);
        GameStatusResponse response = new GameStatusResponse(game);
        return Response.ok(response).build();
    }

    @Path("/{id}/{playerId}")
    @POST
    public Response postMove(@PathParam("id")String gameId, @PathParam("playerId") String playerId, PostMoveRequest request) {
        logger.info("gameId={}, playerId={}, move={}", gameId, playerId, request);
        int numMoves = PostMoveHandler.handle(gameId, playerId, request.getColumn());
        String moveLink = String.format("%s/moves/%d", gameId, numMoves);
        PostMoveResponse response = new PostMoveResponse.Builder().moveLink(moveLink).build();
        return Response.ok(response).build();
    }

    @Path("/{id}/{playerId}")
    @DELETE
    public Response playerQuit(@PathParam("id")String gameId, @PathParam("playerId") String playerId) {
        logger.info("gameId={}, playerId={}", gameId, playerId);
        PlayerQuitHandler.handle(gameId, playerId);
        return Response.status(202).build();
    }
    @Path("/{id}/moves")
    @GET
    public Response getMoves(@PathParam("id") String gameId, @QueryParam("start") Integer start, @QueryParam("until") Integer until) {
        logger.info("gameId={}, start={}, until={}", gameId, start, until);
        List<DropTokenMove> moves = GetMovesHandler.handle(gameId, start, until);
        GetMovesResponse response = new GetMovesResponse.Builder().dropTokenMoves(moves).build();
        return Response.ok(response).build();
    }

    @Path("/{id}/moves/{moveId}")
    @GET
    public Response getMove(@PathParam("id") String gameId, @PathParam("moveId") Integer moveId) {
        logger.info("gameId={}, moveId={}", gameId, moveId);
        DropTokenMove move = GetMovesHandler.handle(gameId, moveId);
        GetMoveResponse response = new GetMoveResponse.Builder().move(move).build();
        return Response.ok(response).build();
    }

}
