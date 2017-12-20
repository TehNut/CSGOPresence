package info.tehnut.csgo.gamestate;

import info.tehnut.csgo.gamestate.data.GameState;

public class EventUpdateState {

    private final GameState gameState;

    public EventUpdateState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean isUser() {
        return gameState.getPlayer() == null || gameState.getPlayer().getSteamId().equals(gameState.getProvider().getSteamId());
    }
}
