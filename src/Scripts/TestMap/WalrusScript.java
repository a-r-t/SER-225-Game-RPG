package Scripts.TestMap;

import java.util.ArrayList;

import Level.FlagManager;
import Level.NPC;
import Level.Script;
import ScriptActions.ChangeFlagScriptAction;
import ScriptActions.ConditionalScriptAction;
import ScriptActions.ConditionalScriptActionGroup;
import ScriptActions.FlagRequirement;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.NPCFacePlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;

// script for talking to walrus npc
public class WalrusScript extends Script<NPC> {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());

        scriptActions.add(new NPCFacePlayerScriptAction());


        ConditionalScriptAction hasTalkedToWalrusConditional = new ConditionalScriptAction();

        ConditionalScriptActionGroup hasNotTalkedToWalrus = new ConditionalScriptActionGroup();
        hasNotTalkedToWalrus.addFlagRequirement(new FlagRequirement("hasTalkedToWalrus", false));
        TextboxScriptAction hasNotTalkedToWalrusTextbox = new TextboxScriptAction();
        hasNotTalkedToWalrusTextbox.addText("Hi Cat!");
        hasNotTalkedToWalrusTextbox.addText("...oh, you lost your ball?");
        hasNotTalkedToWalrusTextbox.addText("Hmmm...my walrus brain remembers seeing Dino with\nit last. Maybe you can check with him?");
        hasNotTalkedToWalrus.addScriptAction(hasNotTalkedToWalrusTextbox);
        hasTalkedToWalrusConditional.addConditionalScriptActionGroup(hasNotTalkedToWalrus);

        ConditionalScriptActionGroup hasTalkedToWalrus = new ConditionalScriptActionGroup();
        hasTalkedToWalrus.addFlagRequirement(new FlagRequirement("hasTalkedToWalrus", true));
        TextboxScriptAction hasTalkedToWalrusTextbox = new TextboxScriptAction();
        hasTalkedToWalrusTextbox.addText("I sure love doing walrus things!");
        hasTalkedToWalrus.addScriptAction(hasTalkedToWalrusTextbox);
        hasTalkedToWalrusConditional.addConditionalScriptActionGroup(hasTalkedToWalrus);

        scriptActions.add(hasTalkedToWalrusConditional);

        scriptActions.add(new UnlockPlayerScriptAction());

        scriptActions.add(new ChangeFlagScriptAction("hasTalkedToWalrus", true));
        return scriptActions;
    }
}
