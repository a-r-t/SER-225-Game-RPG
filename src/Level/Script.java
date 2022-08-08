package Level;

import Event.*;

public class Script {

    protected Event event;
    protected boolean isActive = false;

    public Script() { this.event = loadEvent(); }

    public Script(Event event) {
        this.event = event;
    }

    public void update() {
        ScriptState scriptState = event.execute();
        if (scriptState == ScriptState.COMPLETED) {
            this.isActive = false;
        }
    }

    public boolean isActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    public void setMap(Map map) {
        this.event.setMap(map);
    }

    public void setPlayer(Player player) {
        this.event.setPlayer(player);
    }

    protected Event loadEvent() { return null; }

}
