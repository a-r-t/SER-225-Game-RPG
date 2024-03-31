package Scripting;

import java.util.ArrayList;

import Level.Map;
import Level.MapEntity;
import Level.Player;
import Level.ScriptState;

public class ConditionalScriptAction extends ScriptAction {
    
    protected ArrayList<ConditionalScriptActionGroup> conditionalScriptActionGroups;
    protected int currentScriptActionGroupIndex;
    protected int currentScriptActionIndex;

    public ConditionalScriptAction() {
        this.conditionalScriptActionGroups = new ArrayList<>();
    }

    public ConditionalScriptAction(ArrayList<ConditionalScriptActionGroup> conditionalScriptActionGroups) {
        this.conditionalScriptActionGroups = conditionalScriptActionGroups;
    }

    public void addConditionalScriptActionGroup(ConditionalScriptActionGroup conditionalScriptActionGroup) {
        conditionalScriptActionGroups.add(conditionalScriptActionGroup);
    }

    public ArrayList<ConditionalScriptActionGroup> getConditionalScriptActionGroups() {
        return conditionalScriptActionGroups;
    }

    @Override
    public void setup() {
        for (int i = 0; i < conditionalScriptActionGroups.size(); i++) {
            ConditionalScriptActionGroup conditionalScriptActionGroup = conditionalScriptActionGroups.get(i);
            if (areFlagRequirementsMet(conditionalScriptActionGroup)) {
                currentScriptActionGroupIndex = i;
                currentScriptActionIndex = 0;
                conditionalScriptActionGroups.get(currentScriptActionGroupIndex).getScriptActions().get(currentScriptActionIndex).setup();
            }
        }
    }

    protected boolean areFlagRequirementsMet(ConditionalScriptActionGroup conditionalScriptActionGroup) {
        ArrayList<Boolean> metFlagRequirementStatuses = new ArrayList<>();
        for (FlagRequirement flagRequirement : conditionalScriptActionGroup.getFlagRequirements()) {
            String flagName = flagRequirement.getFlagName();
            boolean currentFlagStatus = this.map.getFlagManager().isFlagSet(flagName);
            if (flagRequirement.flagValue == currentFlagStatus) {
                metFlagRequirementStatuses.add(true);
            }
            else {
                metFlagRequirementStatuses.add(false);
            }
        }
        if (conditionalScriptActionGroup.getFlagStrategy() == FlagStrategy.AND) {
            boolean allMet = metFlagRequirementStatuses.stream().allMatch(val -> val == true);
            return allMet;
        }
        else {
            boolean someMet = metFlagRequirementStatuses.stream().anyMatch(val -> val == true);
            return someMet;
        }

    }

    @Override
    public ScriptState execute() {
        // Runs an execute cycle of the Script
        ArrayList<ScriptAction> scriptActions = conditionalScriptActionGroups.get(currentScriptActionGroupIndex).getScriptActions();
        ScriptAction currentScriptAction = scriptActions.get(currentScriptActionIndex);
        ScriptState scriptState = currentScriptAction.execute();
        if (scriptState == ScriptState.COMPLETED) {
            currentScriptAction.cleanup();
            currentScriptActionIndex++;

            if (currentScriptActionIndex < scriptActions.size()) {
                scriptActions.get(currentScriptActionIndex).setup();
                return ScriptState.RUNNING;
            }
            else {
                return ScriptState.COMPLETED;
            }
        }
        return ScriptState.RUNNING;
    }

    @Override
    public void cleanup() {
        
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
