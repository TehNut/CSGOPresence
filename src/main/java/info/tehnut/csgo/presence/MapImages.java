package info.tehnut.csgo.presence;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

// All the map images on the Discord API. Allows defaulting to the CS icon for unknown maps.
public enum MapImages {

    DE_CACHE,
    DE_CANALS,
    DE_COBBLE("de_cbble"),
    DE_DUST2,
    DE_INFERNO,
    DE_MIRAGE,
    DE_NUKE,
    DE_OVERPASS,
    DE_TRAIN,
    UNKNOWN("default")
    ;

    private final String mapName;

    MapImages(String mapName) {
        this.mapName = mapName;
    }

    MapImages() {
        this(null);
    }

    public String getImage() {
        return mapName == null ? name().toLowerCase(Locale.ROOT) : mapName;
    }

    private static final Map<String, MapImages> BY_NAME = new HashMap<>();

    public static MapImages getbyName(String name) {
        return BY_NAME.getOrDefault(name, UNKNOWN);
    }

    public static void initNames() {
        for (MapImages known : values())
            BY_NAME.put(known.getImage(), known);
    }
}
