package info.tehnut.csgopresence;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.google.common.eventbus.Subscribe;
import info.tehnut.csgogamestate.CSGOGamestate;
import info.tehnut.csgogamestate.EventUpdateState;
import info.tehnut.csgogamestate.data.GameMap;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;

public class CSGOPresence {

    public static final DiscordRichPresence DISCORD_PRESENCE = new DiscordRichPresence();
    public static final String APPLICATION_ID = "390310250886070274";
    public static final Thread CALLBACK_THREAD;

    public static boolean active = true;
    private static CSGOPresence presence;

    static {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        DiscordRPC.INSTANCE.Discord_Initialize(APPLICATION_ID, handlers, true, "");
        DISCORD_PRESENCE.state = "Setting things up";

        CALLBACK_THREAD = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted() && active) {
                DiscordRPC.INSTANCE.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    DiscordRPC.INSTANCE.Discord_Shutdown();
                }
            }
        }, "RPC-Callback-Handler");

        CALLBACK_THREAD.start();

        DiscordRPC.INSTANCE.Discord_UpdatePresence(DISCORD_PRESENCE);
    }

    public static void main(String... args) {
        OptionParser parser = new OptionParser();
        parser.accepts("dir", "The directory that CSGO is installed to.").withRequiredArg().ofType(String.class);
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

        String dir = options.has("dir") ? (String) options.valueOf("dir") : "";
        int port = options.has("port") ? (int) options.valueOf("port") : 1234;

        try {
            // TODO - Print default config to cfg dir
            new CSGOGamestate(port);
            CSGOGamestate.EVENT_BUS.register(new EventHandler());
        } catch (IOException e) {
            System.out.println("Failed to initialize game state.");
            e.printStackTrace();
        }
    }

    public static class EventHandler {
        @Subscribe
        public void onStateUpdate(EventUpdateState event) {
            if (event.isUser()) {
                if (event.getGameState().getMap() != null) {
                    GameMap map = event.getGameState().getMap();

                    DISCORD_PRESENCE.largeImageKey = map.getName();
                    DISCORD_PRESENCE.largeImageText = "Map: " + map.getName();
                    DISCORD_PRESENCE.details = map.getMode();

                    String scoreText = "Score: ";
                    if (map.getCounterTerrorist() != null && map.getTerrorist() != null) {
                        boolean ct = event.getGameState().getPlayer().getTeam().equalsIgnoreCase("ct");
                        scoreText += String.format(ct ? ">%d<" : "%d", map.getCounterTerrorist().getScore());
                        scoreText += " - ";
                        scoreText += String.format(!ct ? ">%d<" : "%d", map.getCounterTerrorist().getScore());
                    } else {
                        scoreText += event.getGameState().getPlayer().getMatchStats().getScore();
                    }

                    DISCORD_PRESENCE.state = scoreText;
                } else {
                    DISCORD_PRESENCE.details = "In menu";
                    DISCORD_PRESENCE.state = null;
                    DISCORD_PRESENCE.largeImageKey = null;
                }
            }

            DiscordRPC.INSTANCE.Discord_UpdatePresence(DISCORD_PRESENCE);
        }
    }
}
