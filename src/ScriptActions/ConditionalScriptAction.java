package ScriptActions;

import java.util.ArrayList;
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
        boolean groupRequirementMet = false;
        for (int i = 0; i < conditionalScriptActionGroups.size(); i++) {
            ConditionalScriptActionGroup conditionalScriptActionGroup = conditionalScriptActionGroups.get(i);
            
            if (areRequirementsMet(conditionalScriptActionGroup)) {
                currentScriptActionGroupIndex = i;
                currentScriptActionIndex = 0;
                conditionalScriptActionGroups.get(currentScriptActionGroupIndex).getScriptActions().get(currentScriptActionIndex).setup();
                groupRequirementMet = true;
                break;
            }
        }
        if (!groupRequirementMet) {
            // this prevents a crash from occurring if no group requirements have been met
            // it just adds a fake group with a fake script action that does nothing
            // while there are other ways of fixing this, the other ways result in the script execution code being less efficient, which is not ideal for a game that needs to run as fast as possible
            conditionalScriptActionGroups.add(doNothingActionGroup);
            currentScriptActionGroupIndex = conditionalScriptActionGroups.size() - 1;
            currentScriptActionIndex = 0;
        }
    }

    private static ConditionalScriptActionGroup doNothingActionGroup = new ConditionalScriptActionGroup() {{
        addScriptAction(new DoNothingScriptAction());
    }};

    protected boolean areRequirementsMet(ConditionalScriptActionGroup conditionalScriptActionGroup) {
        ArrayList<Boolean> metRequirementStatuses = new ArrayList<>();
        for (Requirement requirement : conditionalScriptActionGroup.getRequirements()) {
            boolean requirementStatus = false;
            if (requirement instanceof FlagRequirement) {
                requirementStatus = isFlagRequirementMet((FlagRequirement)requirement);
            }
            else if (requirement instanceof CustomRequirement) {
                requirementStatus = ((CustomRequirement)requirement).isRequirementMet();
            }
            if (!requirementStatus && conditionalScriptActionGroup.flagStrategy == FlagStrategy.AND) {
                return false;
            }
            else if (requirementStatus && conditionalScriptActionGroup.flagStrategy == FlagStrategy.OR) {
                return true;
            }
            else {
                metRequirementStatuses.add(requirementStatus);
            }
        }
        // if strategy is AND, all requirements had to have been met up to this point to avoid the short circuit, so we know its true
        if (conditionalScriptActionGroup.getFlagStrategy() == FlagStrategy.AND) {
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
}
