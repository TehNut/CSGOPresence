package info.tehnut.csgo.presence;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum GameModes {

    CASUAL(true),
    COMPETETIVE(true),
    DEATHMATCH(false),
    ARMS_RACE("gungameprogressive", false),
    DEMOLITION("gungametrbomb", true),
    WINGMAN(true),
    UNKNOWN(false),
    ;

    private static final Map<String, GameModes> BY_NAME = new HashMap<>();

    private final String internalName;
    private final boolean teamOriented;

    GameModes(String internalName, boolean teamOriented) {
        this.internalName = internalName;
        this.teamOriented = teamOriented;
    }

    GameModes(boolean teamOriented) {
        this(null, teamOriented);
    }

    public String getInternalName() {
        return internalName == null ? name().toLowerCase(Locale.ROOT) : internalName;
    }

    public String getDisplayName() {
        String[] words = name().toLowerCase(Locale.ROOT).split("_");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            words[i] = word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1, word.length());
        }

        return String.join(" ", words);
    }

    public boolean isTeamOriented() {
        return teamOriented;
    }

    public static GameModes getbyName(String name) {
        return BY_NAME.getOrDefault(name, UNKNOWN);
    }

    public static void initNames() {
        for (GameModes mode : GameModes.values())
            BY_NAME.put(mode.getInternalName(), mode);
    }
}
