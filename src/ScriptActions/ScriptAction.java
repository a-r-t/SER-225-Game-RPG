package ScriptActions;

import Level.Map;
import Level.MapEntity;
import Level.Player;
import Level.ScriptState;

public abstract class ScriptAction {
    protected Map map;
    protected Player player;
    protected MapEntity entity;
    protected ScriptActionOutputManager outputManager;

    public void setup() {}

    public ScriptState execute() {
        return ScriptState.COMPLETED;
    }

    public void cleanup() {}

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setEntity(MapEntity entity) {
        this.entity = entity;
    }

    public void setOutputManager(ScriptActionOutputManager outputManager) {
        this.outputManager = outputManager;
    }
}
