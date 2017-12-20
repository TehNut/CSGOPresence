package info.tehnut.csgo.gamestate;

import info.tehnut.csgo.gamestate.data.GameState;

public interface IStateUpdateWatcher {

    /**
     * Called whenever a state update has been received.
     *
     * Subscribe with {@link CSGOGamestate#subscribeWatcher(IStateUpdateWatcher)}. A subscribed watcher can also be
     * un-subscribed with {@link CSGOGamestate#unsubscribeWatcher(IStateUpdateWatcher)}.
     *
     * @param newState - The new state as sent by the game. Nonnull.
     * @param oldState - The last state sent. Nullable.
     */
    void handleUpdatedState(GameState newState, GameState oldState);
}
