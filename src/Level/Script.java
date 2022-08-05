package Level;

import Engine.GraphicsHandler;
import GameObject.Rectangle;

public class Script {

    protected InteractEvent interactEvent;
    protected boolean isActive = false;

    public Script(InteractEvent interactEvent) {
        this.interactEvent = interactEvent;
    }

    public void update(Player player, Map map) {
        ScriptState scriptState = interactEvent.onInteract(player, map);
        if (scriptState == ScriptState.COMPLETED) {
            this.isActive = false;
        }
    }

    public boolean isActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
