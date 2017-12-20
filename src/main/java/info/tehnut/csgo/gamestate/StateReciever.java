package info.tehnut.csgo.gamestate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import info.tehnut.csgo.gamestate.data.GameState;
import info.tehnut.csgo.util.IOUtils;

import java.io.IOException;

public class StateReciever implements HttpHandler {

    private static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();
    private static GameState oldState;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = IOUtils.toString(httpExchange.getRequestBody());
        GameState gameState = GSON.fromJson(response, GameState.class);
        System.out.println("Received state update from " + gameState.getProvider().getName() + ". Posting to watchers.");

        for (IStateUpdateWatcher watcher : CSGOGamestate.UPDATE_WATCHERS)
            watcher.handleUpdatedState(gameState, oldState);

        oldState = gameState;
    }
}
