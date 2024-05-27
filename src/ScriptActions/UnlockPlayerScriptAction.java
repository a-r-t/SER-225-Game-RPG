package ScriptActions;

import Level.ScriptState;

public class UnlockPlayerScriptAction extends ScriptAction {
    
    @Override
    public ScriptState execute() {
        player.unlock();
        return ScriptState.COMPLETED;
    } 
}
