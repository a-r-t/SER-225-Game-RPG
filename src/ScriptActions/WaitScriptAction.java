package ScriptActions;

import Level.ScriptState;

public class WaitScriptAction extends ScriptAction {
    protected int framesToWait;
    protected int counter;

    public WaitScriptAction(int framesToWait) {
        this.framesToWait = framesToWait;
    }

    @Override
    public void setup() {
        this.counter = framesToWait;
    }
    
    @Override
    public ScriptState execute() {
        counter--;
        if (counter > 0) {
            return ScriptState.RUNNING;
        }
        return ScriptState.COMPLETED;
    } 
}
