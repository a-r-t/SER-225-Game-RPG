package Scripts.TestMap;

import java.util.ArrayList;

import Level.Script;
import ScriptActions.*;


// trigger script at beginning of game to set that heavy emotional plot
public class LostBallScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());

        scriptActions.add(new TextboxScriptAction() {{
            addText("Where did my ball go!?");
            addText("I left it right here before I took my 22 hour cat nap.");
            addText("Maybe Walrus has seen it.");
        }});

        scriptActions.add(new ChangeFlagScriptAction("hasLostBall", true));

        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}
