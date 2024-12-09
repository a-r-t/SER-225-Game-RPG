package ScriptActions;

import java.util.ArrayList;

import Level.Map;
import Level.MapEntity;
import Level.Player;
import Level.ScriptState;

public class LoopIndefiniteScriptAction extends ScriptAction {
    protected ArrayList<ScriptAction> scriptActions;
    protected int currentScriptActionIndex;
    protected int previousScriptActionIndex;
    protected ArrayList<Requirement> requirements;
    protected FlagStrategy flagStrategy;

    public LoopIndefiniteScriptAction() {
        this.scriptActions = new ArrayList<>();
        requirements = new ArrayList<Requirement>();
        this.flagStrategy = FlagStrategy.AND;
    }

    public LoopIndefiniteScriptAction(FlagStrategy flagStrategy) {
        this.scriptActions = new ArrayList<>();
        requirements = new ArrayList<Requirement>();
        this.flagStrategy = flagStrategy;
    }

    public ArrayList<ScriptAction> getScriptActions() {
        return scriptActions;
    }

    public void addScriptAction(ScriptAction scriptAction) {
        scriptActions.add(scriptAction);
    }

    public void addRequirement(Requirement requirement) {
        requirements.add(requirement);
    }

    public ArrayList<Requirement> getRequirements() {
        return requirements;
    }

    public FlagStrategy getFlagStrategy() {
        return flagStrategy;
    }

    public void setFlagStrategy(FlagStrategy flagStrategy) {
        this.flagStrategy = flagStrategy;
    }

    @Override
    public void setup() {
        currentScriptActionIndex = 0;
        previousScriptActionIndex = -1;
    }

    protected boolean areRequirementsMet() {
        ArrayList<Boolean> metRequirementStatuses = new ArrayList<>();
        for (Requirement requirement : requirements) {
            boolean requirementStatus = false;
            if (requirement instanceof FlagRequirement) {
                requirementStatus = isFlagRequirementMet((FlagRequirement)requirement);
            }
            else if (requirement instanceof CustomRequirement) {
                requirementStatus = ((CustomRequirement)requirement).isRequirementMet();
            }
            if (!requirementStatus && flagStrategy == FlagStrategy.AND) {
                return false;
            }
            else if (requirementStatus && flagStrategy == FlagStrategy.OR) {
                return true;
            }
            else {
                metRequirementStatuses.add(requirementStatus);
            }
        }
        // if strategy is AND, all requirements had to have been met up to this point to avoid the short circuit, so we know its true
        if (flagStrategy == FlagStrategy.AND) {
            return true;
        }
        // if strategy is OR, no requirements had to have been met up to this point to avoid the short circuit, so we know its false
        else {
            return false;
        }
    }

    protected boolean isFlagRequirementMet(FlagRequirement flagRequirement) {
        String flagName = flagRequirement.getFlagName();
        boolean currentFlagStatus = this.map.getFlagManager().isFlagSet(flagName);
        return flagRequirement.flagValue == currentFlagStatus;
    }

    @Override
    public ScriptState execute() {
        if (previousScriptActionIndex != currentScriptActionIndex) {
            previousScriptActionIndex = currentScriptActionIndex;
        
            // handle determining whether loop should stop or not
            if (currentScriptActionIndex == 0 && areRequirementsMet()) {
                return ScriptState.COMPLETED;
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
