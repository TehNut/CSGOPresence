package info.tehnut.csgogamestate.data;

public class GameState {

    private final Provider provider;
    private final GameMap map;
    private final Round round;
    private final Player player;

    public GameState(Provider provider, GameMap map, Round round, Player player) {
        this.provider = provider;
        this.map = map;
        this.round = round;
        this.player = player;
    }

    public Provider getProvider() {
        return provider;
    }

    public GameMap getMap() {
        return map;
    }

    public Round getRound() {
        return round;
    }

    public Player getPlayer() {
        return player;
    }
}
