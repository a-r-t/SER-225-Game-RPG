package Scripting;

import java.util.ArrayList;

public class ConditionalScriptActionGroup {
    protected ArrayList<ScriptAction> scriptActions;
    protected ArrayList<FlagRequirement> flagRequirements;
    protected FlagStrategy flagStrategy;

    public ConditionalScriptActionGroup() {
        scriptActions = new ArrayList<ScriptAction>();
        flagRequirements = new ArrayList<FlagRequirement>();
        this.flagStrategy = FlagStrategy.AND;
    }
    
    public ArrayList<ScriptAction> getScriptActions() {
        return scriptActions;
    }

    public void addScriptAction(ScriptAction scriptAction) {
        scriptActions.add(scriptAction);
    }

    public void addFlagRequirement(FlagRequirement flagRequirement) {
        flagRequirements.add(flagRequirement);
    }

    public ArrayList<FlagRequirement> getFlagRequirements() {
        return flagRequirements;
    }

    public FlagStrategy getFlagStrategy() {
        return flagStrategy;
    }

    public void setFlagStrategy(FlagStrategy flagStrategy) {
        this.flagStrategy = flagStrategy;
    }
}
