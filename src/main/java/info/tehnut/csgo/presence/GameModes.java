package info.tehnut.csgo.presence;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum GameModes {

    CASUAL_DEFUSAL(true),
    COMPETETIVE_DEFUSAL(true),
    DEATHMATCH(false),
    // TODO - The others
    UNKNOWN(false),
    ;

    private static final Map<String, GameModes> BY_NAME = new HashMap<>();

    private final String name;
    private final boolean teamOriented;

    GameModes(String name, boolean teamOriented) {
        this.name = name;
        this.teamOriented = teamOriented;
    }

    GameModes(boolean teamOriented) {
        this(null, teamOriented);
    }

    public String getName() {
        return name == null ? name().toLowerCase(Locale.ROOT) : name;
    }

    public boolean isTeamOriented() {
        return teamOriented;
    }

    public static GameModes getbyName(String name) {
        return BY_NAME.getOrDefault(name, UNKNOWN);
    }

    public static void initNames() {
        for (GameModes mode : GameModes.values())
            BY_NAME.put(mode.getName(), mode);
    }
}
