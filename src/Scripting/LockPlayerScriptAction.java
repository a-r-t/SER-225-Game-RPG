package Scripting;

import Level.PlayerState;
import Level.ScriptState;
import Utils.Direction;

public class LockPlayerScriptAction extends ScriptAction {

    @Override
    public void setup() {

    }

    @Override
    public ScriptState execute() {
        player.setPlayerState(PlayerState.INTERACTING);
        player.setCurrentAnimationName(player.getFacingDirection() == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT");
        return ScriptState.COMPLETED;
    }

    @Override
    public void cleanup() {

    }
    
}