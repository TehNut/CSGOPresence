package info.tehnut.csgo.gamestate.data;

import com.google.gson.annotations.SerializedName;

public class Weapon {

    private final String name;
    @SerializedName("paintkit")
    private final String finish;
    private final String type;
    private final String state;

    public Weapon(String name, String finish, String type, String state) {
        this.name = name;
        this.finish = finish;
        this.type = type;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public String getFinish() {
        return finish;
    }

    public String getType() {
        return type;
    }

    public boolean isHolstered() {
        return state.equals("holstered");
    }
}
