package info.tehnut.csgo.gamestate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import info.tehnut.csgo.gamestate.data.GameState;
import info.tehnut.csgo.util.IOUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StateReciever implements HttpHandler {

    private static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();
    private static final DateFormat FORMAT = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = IOUtils.toString(httpExchange.getRequestBody());
        GameState gameState = GSON.fromJson(response, GameState.class);
        System.out.println(FORMAT.format(new Date()) + " | Received state update from " + gameState.getProvider().getName() + ". Posting to watchers.");

        for (IStateUpdateWatcher watcher : CSGOGamestate.UPDATE_WATCHERS)
            watcher.handleUpdatedState(gameState);

        httpExchange.sendResponseHeaders(200, 0);
    }
}
