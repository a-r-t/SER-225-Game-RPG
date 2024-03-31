package ScriptActions;

import Level.NPC;
import Level.ScriptState;

public class ChangeFlagScriptAction extends ScriptAction {
    protected String flagName;
    protected boolean flagState;

    public ChangeFlagScriptAction(String flagName, boolean flagState) {
        this.flagName = flagName;
        this.flagState = flagState;
    }

    @Override
    public void setup() {

    }

    @Override
    public ScriptState execute() {
        if (flagState) {
            this.map.getFlagManager().setFlag(flagName);
        }
        else {
            this.map.getFlagManager().unsetFlag(flagName);
        }
        return ScriptState.COMPLETED;
    }

    @Override
    public void cleanup() {

    }
    
}
