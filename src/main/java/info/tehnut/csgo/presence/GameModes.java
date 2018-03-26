package info.tehnut.csgo.presence;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum GameModes {

    CASUAL(IScoreHandler.TEAM_ORIENTED),
    COMPETITIVE(IScoreHandler.TEAM_ORIENTED),
    DEATHMATCH(IScoreHandler.NOT_TEAM_ORIENTED),
    ARMS_RACE("gungameprogressive", IScoreHandler.NOT_TEAM_ORIENTED), // TODO Look for way to calculate progress through weapon tree
    DEMOLITION("gungametrbomb", IScoreHandler.TEAM_ORIENTED),
    WINGMAN(IScoreHandler.TEAM_ORIENTED),
    SURF(IScoreHandler.NOT_TEAM_ORIENTED),
    UNKNOWN(IScoreHandler.NOT_TEAM_ORIENTED),
    ;

    private static final Map<String, GameModes> BY_NAME = new HashMap<>();
    static {
        for (GameModes mode : GameModes.values())
            BY_NAME.put(mode.getInternalName(), mode);
    }

    private final String internalName;
    private final IScoreHandler scoreHandler;

    GameModes(String internalName, IScoreHandler scoreHandler) {
        this.internalName = internalName;
        this.scoreHandler = scoreHandler;
    }

    GameModes(IScoreHandler scoreHandler) {
        this(null, scoreHandler);
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

    public IScoreHandler getScoreHandler() {
        return scoreHandler;
    }

    public static GameModes getbyName(String name) {
        return BY_NAME.getOrDefault(name, UNKNOWN);
    }
}
