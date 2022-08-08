package Level;

import Event.*;

public class Script {

    protected Event event;
    protected boolean isActive = false;

    public Script(Event event) {
        this.event = event;
    }

    public Script() {
        this.event = loadEvent();
    }

    public void update(Player player, Map map) {
        ScriptState scriptState = event.execute(player, map);
        if (scriptState == ScriptState.COMPLETED) {
            this.isActive = false;
        }
    }

    public boolean isActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    protected Event loadEvent() { return null; }
}
