package info.tehnut.csgo.presence;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import info.tehnut.csgo.gamestate.CSGOGamestate;
import info.tehnut.csgo.gamestate.config.DataType;
import info.tehnut.csgo.gamestate.config.GSConfigBuilder;
import info.tehnut.csgo.gamestate.config.GameStateConfiguration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.File;
import java.io.IOException;

public class CSGOPresence {

    public static final DiscordRichPresence DISCORD_PRESENCE = new DiscordRichPresence();
    public static final String APPLICATION_ID = "390310250886070274";

    public static boolean active = true;
    public static Thread callbackThread;

    public static void main(String... args) {
        OptionParser parser = new OptionParser();
        parser.accepts("dir", "The directory that CSGO is installed to. If provided, a default config will be printed.").withRequiredArg().ofType(String.class);
        parser.accepts("port", "The port to run the HTTP server on.").withRequiredArg().ofType(Integer.class);
        parser.accepts("help", "Prints help text and exits.");

        OptionSet options = parser.parse(args);

        if (options.has("help")) {
            active = false;
            try {
                parser.printHelpOn(System.out);
            } catch (Exception e) {
                System.out.println("Failed to print help text.");
                e.printStackTrace();
            }
            return;
        }

        setupRPC();

        String dir = options.has("dir") ? (String) options.valueOf("dir") : "";
        int port = options.has("port") ? (int) options.valueOf("port") : 1234;

        MapImages.initNames();
        GameModes.initNames();

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
                        .withTimeout(15)
                        .build();

                gsConfig.print(new File(dir), true);
            }
            CSGOGamestate.initGamestate(port);
            CSGOGamestate.subscribeWatcher(new UpdateWatcher());
        } catch (IOException e) {
            System.out.println("Failed to initialize game state.");
            e.printStackTrace();
        }
    }

    private static void setupRPC() {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        DiscordRPC.INSTANCE.Discord_Initialize(APPLICATION_ID, handlers, true, "");

        callbackThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted() && active) {
                DiscordRPC.INSTANCE.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    DiscordRPC.INSTANCE.Discord_Shutdown();
                }
            }
        }, "RPC-Callback-Handler");
        callbackThread.start();
    }

}
