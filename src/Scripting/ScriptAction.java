package Scripting;

import Level.Map;
import Level.Player;
import Level.ScriptState;

public abstract class ScriptAction {
    protected Map map;
    protected Player player;

    public abstract void setup();

    public abstract ScriptState execute();

    public abstract void cleanup();

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
