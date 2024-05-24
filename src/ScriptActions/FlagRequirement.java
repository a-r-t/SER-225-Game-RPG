package ScriptActions;

public class FlagRequirement extends Requirement {
    protected String flagName;
    protected boolean flagValue;

    public FlagRequirement(String flagName, boolean flagValue) {
        this.flagName = flagName;
        this.flagValue = flagValue;
    }

    public String getFlagName() {
        return flagName;
    }
    
    public boolean getFlagValue() {
        return flagValue;
    }

}
