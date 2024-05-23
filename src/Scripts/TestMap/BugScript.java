package Scripts.TestMap;

import java.util.ArrayList;

import Level.Script;
import ScriptActions.*;

// script for talking to bug npc
public class BugScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());

        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new TextboxScriptAction() {{
            addText("Hello!");
            addText("Do you like bugs?", new String[] { "Yes", "No" });
        }});

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new CustomRequirement() {
                    @Override
                    public boolean isRequirementMet() {
                        String answer = outputManager.getFlagData("TEXTBOX_SELECTION");
                        return answer.equals("Yes");
                    }
                });
                addScriptAction(new TextboxScriptAction() {{
                    addText("I knew you were a cool cat!");
                    addText("I'm going to let you in on a little secret...\nYou can push some rocks out of the way.");
                }});
            }});

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new CustomRequirement() {
                    @Override
                    public boolean isRequirementMet() {
                        String answer = outputManager.getFlagData("TEXTBOX_SELECTION");
                        return answer.equals("No");
                    }
                });
                addScriptAction(new TextboxScriptAction("Oh...uh...awkward..."));
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}
