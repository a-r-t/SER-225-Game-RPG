package ScriptActions;

import java.util.ArrayList;

public class ConditionalScriptActionGroup {
    protected ArrayList<ScriptAction> scriptActions;
    protected ArrayList<Requirement> requirements;
    protected FlagStrategy flagStrategy;

    public ConditionalScriptActionGroup() {
        scriptActions = new ArrayList<ScriptAction>();
        requirements = new ArrayList<Requirement>();
        this.flagStrategy = FlagStrategy.AND;
    }

    public ConditionalScriptActionGroup(FlagStrategy flagStrategy) {
        scriptActions = new ArrayList<ScriptAction>();
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
}
