package Scripts.TestMap;

import java.util.ArrayList;

import Level.Script;
import Level.ScriptState;
import ScriptActions.CustomRequirement;
import ScriptActions.ChangeFlagScriptAction;
import ScriptActions.ConditionalScriptAction;
import ScriptActions.ConditionalScriptActionGroup;
import ScriptActions.FlagRequirement;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;

// script for talking to tree with hole in it
public class TreeScript extends Script {


    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        
        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToDinosaur", true));
                addRequirement(new FlagRequirement("hasFoundBall", false));
                addRequirement(new CustomRequirement() {

                    @Override
                    public boolean isRequirementMet() {
                        return isPlayerBelowEntity();
                    }
                });

                addScriptAction(new TextboxScriptAction() {{
                    addText("...");
                    addText("I found my ball inside of the tree!\nYippee!");
                }});

                addScriptAction(new ChangeFlagScriptAction("hasFoundBall", true));
            }});


        }});
       
        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}

