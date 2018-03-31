package info.tehnut.csgo.presence;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.tehnut.csgo.gamestate.CSGOGamestate;
import info.tehnut.csgo.gamestate.config.DataType;
import info.tehnut.csgo.gamestate.config.GSConfigBuilder;
import info.tehnut.csgo.gamestate.config.GameStateConfiguration;
import info.tehnut.csgo.util.Logger;
import info.tehnut.csgo.util.Utils;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CSGOPresence {

    public static final DiscordRichPresence DISCORD_PRESENCE = new DiscordRichPresence();
    public static final String APPLICATION_ID = "390310250886070274";
    public static final Map<String, String> MAP_IMAGES = new HashMap<>();

    public static boolean discordConnected = false;
    public static boolean active = true;
    public static Thread callbackThread;

    public static void main(String... args) {
        OptionParser parser = new OptionParser();
        parser.accepts("dir", "The directory that CSGO is installed to. If provided, a default config will be printed.").withRequiredArg().ofType(String.class);
        parser.accepts("port", "The port to run the HTTP server on.").withRequiredArg().ofType(Integer.class);
        parser.accepts("help", "Prints help text and exits.");
        parser.accepts("matchSurf", "Makes an attempt to match surf maps based on their name prefix.");

        OptionSet options = parser.parse(args);

        if (options.has("help")) {
            active = false;
            try {
                parser.printHelpOn(System.out);
            } catch (Exception e) {
                Logger.DEFAULT.error("Failed to print help text.");
                e.printStackTrace();
            }
            return;
        }

        setupRPC();

        TrayHandler.tryFindSurf = options.has("matchSurf");
        String dir = options.has("dir") ? (String) options.valueOf("dir") : "";
        int port = options.has("port") ? (int) options.valueOf("port") : 1234;

        TrayHandler.init();

        try {
            URL url = new URL("https://raw.githubusercontent.com/TehNut/CSGOPresence/master/img/map_images.json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            String responseJson = Utils.toString(urlConnection.getInputStream());
            MAP_IMAGES.putAll(new Gson().fromJson(responseJson, new TypeToken<Map<String, String>>(){}.getType()));
            Logger.DEFAULT.info("Pulled map image names from remote server.");
        } catch (Exception e) {
            Logger.DEFAULT.error("Failed to pull map image names from remote server.");
        }

        try {
            if (!dir.isEmpty()) {
                GameStateConfiguration gsConfig = new GSConfigBuilder("Discord Rich Presence Integration")
                        .withLocalURI(port, null)
                        .withData(
                                DataType.PROVIDER,
                                DataType.MAP,
                                DataType.ROUND,
                                DataType.PLAYER_ID,
                                DataType.PLAYER_STATE,
                                DataType.PLAYER_WEAPONS,
                                DataType.PLAYER_MATCH_STATS
                        )
                        .withHeartbeat(2)
                        .build();

                gsConfig.print(new File(dir), true);
            }
            CSGOGamestate.initGamestate(port);
            CSGOGamestate.subscribeWatcher(new UpdateWatcher());
            Logger.DEFAULT.info("Subscribed game state watcher.");
        } catch (IOException e) {
            Logger.DEFAULT.error("Failed to initialize game state.");
            e.printStackTrace();
        }
    }

    private static void setupRPC() {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        DiscordRPC.INSTANCE.Discord_Initialize(APPLICATION_ID, handlers, true, "");
        discordConnected = true;

        if (callbackThread != null && callbackThread.isAlive())
            callbackThread.interrupt();

        callbackThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted() && active) {
                if (!Utils.isCSGORunning()) {
                    if (discordConnected) {
                        DiscordRPC.INSTANCE.Discord_UpdatePresence(null);
                        DiscordRPC.INSTANCE.Discord_RunCallbacks();
                        DiscordRPC.INSTANCE.Discord_Shutdown();
                        Logger.DEFAULT.info("CSGO not detected. Shutting down RPC.");
                        discordConnected = false;
                    }
                } else {
                    if (!discordConnected) {
                        DiscordRPC.INSTANCE.Discord_Initialize(APPLICATION_ID, handlers, true, "");
                        Logger.DEFAULT.info("CSGO detected. Initializing RPC.");
                        discordConnected = true;
                    }
                }

                if (discordConnected)
                    DiscordRPC.INSTANCE.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    DiscordRPC.INSTANCE.Discord_Shutdown();
                }
            }
        }, "RPC-Callback-Handler");
        callbackThread.start();

        DISCORD_PRESENCE.largeImageKey = "default";
        DiscordRPC.INSTANCE.Discord_UpdatePresence(DISCORD_PRESENCE);
    }

}
