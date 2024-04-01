package Scripts.TestMap;

import java.util.ArrayList;

import Level.Script;
import ScriptActions.*;

// script for talking to walrus npc
public class WalrusScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());

        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addFlagRequirement(new FlagRequirement("hasTalkedToWalrus", false));
                addScriptAction(new TextboxScriptAction() {{
                    addText("Hi Cat!");
                    addText("...oh, you lost your ball?");
                    addText("Hmmm...my walrus brain remembers seeing Dino with\nit last. Maybe you can check with him?");
                }});
            }});

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addFlagRequirement(new FlagRequirement("hasTalkedToWalrus", true));
                addScriptAction(new TextboxScriptAction("I sure love doing walrus things!"));
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());

        scriptActions.add(new ChangeFlagScriptAction("hasTalkedToWalrus", true));
        return scriptActions;
    }
}
