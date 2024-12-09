package ScriptActions;

import java.util.ArrayList;

import Level.Map;
import Level.MapEntity;
import Level.Player;
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
            if (currentScriptActionIndex == 0) {
                if (currentIteration < numberOfIterations) {
                    currentIteration++;
                    scriptActions.get(currentScriptActionIndex).setup();
                    ScriptAction currentScriptAction = scriptActions.get(currentScriptActionIndex);
                    ScriptState scriptState = currentScriptAction.execute();
                    if (scriptState == ScriptState.COMPLETED) {
                        currentScriptAction.cleanup();
                        currentScriptActionIndex++;
                        if (currentScriptActionIndex < scriptActions.size()) {
                            scriptActions.get(currentScriptActionIndex).setup();
                        }
                        else {
                            currentScriptActionIndex = 0;
                            previousScriptActionIndex = -1;
                        }
                    }
                }
                else {
                    return ScriptState.COMPLETED;
                }
            }
            else {
                ScriptAction currentScriptAction = scriptActions.get(currentScriptActionIndex);
                ScriptState scriptState = currentScriptAction.execute();
                if (scriptState == ScriptState.COMPLETED) {
                    currentScriptAction.cleanup();
                    currentScriptActionIndex++;
                    if (currentScriptActionIndex < scriptActions.size()) {
                        scriptActions.get(currentScriptActionIndex).setup();
                    }
                    else {
                        currentScriptActionIndex = 0;
                        previousScriptActionIndex = -1;
                    }
                }
            }
        }
        else {
            ScriptAction currentScriptAction = scriptActions.get(currentScriptActionIndex);
            ScriptState scriptState = currentScriptAction.execute();
            if (scriptState == ScriptState.COMPLETED) {
                currentScriptAction.cleanup();
                currentScriptActionIndex++;
                if (currentScriptActionIndex < scriptActions.size()) {
                    scriptActions.get(currentScriptActionIndex).setup();
                }
                else {
                    currentScriptActionIndex = 0;
                    previousScriptActionIndex = -1;
                }
            }
        }
        return ScriptState.RUNNING;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setEntity(MapEntity entity) {
        this.entity = entity;
    }
}
