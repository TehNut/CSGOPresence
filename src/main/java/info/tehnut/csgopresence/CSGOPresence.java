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

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Locale;

public class CSGOPresence {

    public static final DiscordRichPresence DISCORD_PRESENCE = new DiscordRichPresence();
    public static final String APPLICATION_ID = "390310250886070274";

    public static boolean active = true;
    public static Thread callbackThread;

    public static void main(String... args) {
        OptionParser parser = new OptionParser();
        parser.accepts("dir", "The directory that CSGO is installed to. Currently unused.").withRequiredArg().ofType(String.class);
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

        try {
            // TODO - Print default config to cfg dir
            new CSGOGamestate(port);
            CSGOGamestate.EVENT_BUS.register(new EventHandler());
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

    public static class EventHandler {
        @Subscribe
        public void onStateUpdate(EventUpdateState event) {
            if (event.isUser()) {
                if (event.getGameState().getMap() != null) { // We're in a match of some kind
                    GameMap map = event.getGameState().getMap();

                    DISCORD_PRESENCE.largeImageKey = map.getName();
                    DISCORD_PRESENCE.largeImageText = "Map: " + map.getName();
                    DISCORD_PRESENCE.details = capitalize(map.getMode()); // deathmatch -> Deathmatch

                    String scoreText = "Score: ";
                    if (map.getCounterTerrorist() != null && map.getTerrorist() != null) { // If we're in a team-based mode (ie: Defusal)
                        boolean ct = event.getGameState().getPlayer().getTeam().equalsIgnoreCase("ct");
                        // This will format the score as CT - T and place ><'s around the team the user is on
                        scoreText += String.format(ct ? ">%d<" : "%d", map.getCounterTerrorist().getScore());
                        scoreText += " - ";
                        scoreText += String.format(!ct ? ">%d<" : "%d", map.getCounterTerrorist().getScore());
                    } else { // Non-team-based (ie: Death match)
                        scoreText += event.getGameState().getPlayer().getMatchStats().getScore();
                    }

                    DISCORD_PRESENCE.state = scoreText;
                } else { // We're not in a match. Must be in the main menu... or at least a menu of some sort... probably
                    DISCORD_PRESENCE.details = "In menu";
                    DISCORD_PRESENCE.state = null;
                    DISCORD_PRESENCE.largeImageKey = null;
                    DISCORD_PRESENCE.largeImageText = null;
                }
            }

            DiscordRPC.INSTANCE.Discord_UpdatePresence(DISCORD_PRESENCE);
        }

        @Nonnull
        private static String capitalize(String input) {
            return input.substring(0, 1).toUpperCase(Locale.ROOT) + input.substring(1, input.length());
        }
    }
}
