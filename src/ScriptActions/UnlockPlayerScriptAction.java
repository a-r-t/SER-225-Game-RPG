package ScriptActions;

import Level.PlayerState;
import Level.ScriptState;

public class UnlockPlayerScriptAction extends ScriptAction {
    
    @Override
    public ScriptState execute() {
        player.setPlayerState(PlayerState.STANDING);
        return ScriptState.COMPLETED;
    } 
}
