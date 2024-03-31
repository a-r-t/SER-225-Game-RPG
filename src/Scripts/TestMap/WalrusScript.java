package Scripts.TestMap;

import java.util.ArrayList;

import Level.NPC;
import Level.Script;
import Level.ScriptState;
import Scripting.ChangeFlagScriptAction;
import Scripting.LockPlayerScriptAction;
import Scripting.NPCFacePlayerScriptAction;
import Scripting.ScriptAction;
import Scripting.TextboxScriptAction;
import Scripting.UnlockPlayerScriptAction;

// script for talking to walrus npc
public class WalrusScript extends Script<NPC> {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());

        scriptActions.add(new NPCFacePlayerScriptAction());

        

        TextboxScriptAction textboxAction = new TextboxScriptAction();
        if (!isFlagSet("hasTalkedToWalrus")) {
            textboxAction.addText("Hi Cat!");
            textboxAction.addText("...oh, you lost your ball?");
            textboxAction.addText("Hmmm...my walrus brain remembers seeing Dino with\nit last. Maybe you can check with him?");
        }
        else {
            textboxAction.addText("I sure love doing walrus things!");
        }
        scriptActions.add(textboxAction);
        scriptActions.add(new UnlockPlayerScriptAction());

        scriptActions.add(new ChangeFlagScriptAction("hasTalkedToWalrus", true));
        return scriptActions;
    }
}
