package ScriptActions;

import Level.ScriptState;

public class LockPlayerScriptAction extends ScriptAction {

    @Override
    public ScriptState execute() {
        player.lock();
        return ScriptState.COMPLETED;
    }
}
