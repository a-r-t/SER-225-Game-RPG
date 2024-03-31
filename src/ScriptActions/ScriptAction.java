package ScriptActions;

import Level.Map;
import Level.MapEntity;
import Level.Player;
import Level.ScriptState;

public abstract class ScriptAction {
    protected Map map;
    protected Player player;
    protected MapEntity entity;

    public abstract void setup();

    public abstract ScriptState execute();

    public abstract void cleanup();

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setEntity(MapEntity entity) {
        this.entity = entity;
    }
}
