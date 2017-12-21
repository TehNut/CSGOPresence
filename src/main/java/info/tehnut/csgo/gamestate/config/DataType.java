package info.tehnut.csgo.gamestate.config;

import java.util.Locale;

public enum DataType {

    PROVIDER,
    MAP,
    ROUND,
    PLAYER_ID,
    PLAYER_STATE,
    PLAYER_WEAPONS,
    PLAYER_MATCH_STATS,
    ALLPLAYERS_ID,
    ALLPLAYERS_STATE,
    ALLPLAYERS_MATCH_STATS,
    ALLPLAYERS_WEAPONS,
    ALLPLAYERS_POSITION,
    PHASE_COUNTDOWNS,
    ;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ROOT);
    }
}
