package ScriptActions;

import java.util.ArrayList;
import Level.ScriptState;

public class LoopFixedScriptAction extends ScriptAction {
    protected ArrayList<ScriptAction> scriptActions;
    protected int numberOfIterations;
    protected int currentIteration;
    protected int currentScriptActionIndex;
    protected int previousScriptActionIndex;
    protected ArrayList<Requirement> requirements;
    protected FlagStrategy flagStrategy;

    public LoopFixedScriptAction(int numberOfIterations) {
        this.scriptActions = new ArrayList<>();
        this.numberOfIterations = numberOfIterations;
    }

    public ArrayList<ScriptAction> getScriptActions() {
        return scriptActions;
    }

    public void addScriptAction(ScriptAction scriptAction) {
        scriptActions.add(scriptAction);
    }

    @Override
    public void setup() {
        currentIteration = 0;
        currentScriptActionIndex = 0;
        previousScriptActionIndex = -1;
    }

    @Override
    public ScriptState execute() {
        if (previousScriptActionIndex != currentScriptActionIndex) {
            previousScriptActionIndex = currentScriptActionIndex;
        
            // handle iterations
            if (currentScriptActionIndex == 0 && currentIteration >= numberOfIterations) {
                return ScriptState.COMPLETED;
            }
        
            if (currentScriptActionIndex == 0) {
               currentIteration++;
            } 
        
            scriptActions.get(currentScriptActionIndex).setup();
        }
        
        ScriptAction currentScriptAction = scriptActions.get(currentScriptActionIndex);
        ScriptState scriptState = currentScriptAction.execute();
        
        if (scriptState == ScriptState.COMPLETED) {
            currentScriptAction.cleanup();
            currentScriptActionIndex++;
        
            if (currentScriptActionIndex >= scriptActions.size()) {
                currentScriptActionIndex = 0;
                previousScriptActionIndex = -1;
            }
        }
        
        return ScriptState.RUNNING;
    }
}
