package Scripting;

import Level.ScriptState;

// This is only used to prevent a crash from happening with the ConditionalScriptAction if none of its group's requirements are met
// It is not needed otherwise
public class DoNothingScriptAction extends ScriptAction {

    @Override
    public void setup() {

    }

    @Override
    public ScriptState execute() {
        return ScriptState.COMPLETED;
    }

    @Override
    public void cleanup() {

    }
    
}
