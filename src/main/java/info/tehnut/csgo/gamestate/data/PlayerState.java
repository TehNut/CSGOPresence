package info.tehnut.csgo.gamestate.data;

import com.google.gson.annotations.SerializedName;

public class PlayerState {

    private final int health;
    private final int armor;
    private final boolean helmet;
    private final int flashed;
    private final int smoked;
    private final int burning;
    private final int money;
    @SerializedName("round_kills")
    private final int roundKills;
    @SerializedName("round_killhs")
    private final int headshots;
    @SerializedName("equip_value")
    private final int equippedValue;

    public PlayerState(int health, int armor, boolean helmet, int flashed, int smoked, int burning, int money, int roundKills, int headshots, int equippedValue) {
        this.health = health;
        this.armor = armor;
        this.helmet = helmet;
        this.flashed = flashed;
        this.smoked = smoked;
        this.burning = burning;
        this.money = money;
        this.roundKills = roundKills;
        this.headshots = headshots;
        this.equippedValue = equippedValue;
    }

    public int getHealth() {
        return health;
    }

    public int getArmor() {
        return armor;
    }

    public boolean hasHelmet() {
        return helmet;
    }

    public int getFlashed() {
        return flashed;
    }

    public int getSmoked() {
        return smoked;
    }

    public int getBurning() {
        return burning;
    }

    public int getMoney() {
        return money;
    }

    public int getRoundKills() {
        return roundKills;
    }

    public int getHeadshotCount() {
        return headshots;
    }

    public int getEquippedValue() {
        return equippedValue;
    }
}
