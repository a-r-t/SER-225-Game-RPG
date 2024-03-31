package Scripts;

import java.util.ArrayList;

import Level.Script;
import Level.ScriptState;
import Scripting.LockPlayerScriptAction;
import Scripting.ScriptAction;
import Scripting.TextboxScriptAction;
import Scripting.UnlockPlayerScriptAction;

// Reusable simple interact script
// Just shows text upon interacting with the associated entity
public class SimpleTextScript extends Script {
    private String[] textItems;

    public SimpleTextScript(String text) {
        this.textItems = new String[] { text };
    }

    public SimpleTextScript(String[] text) {
        this.textItems = text;
    }

    @Override
    protected void setup() {
        lockPlayer();
        showTextbox();
        addTextToTextboxQueue(textItems);
    }

    @Override
    protected void cleanup() {
        unlockPlayer();
        hideTextbox();
    }

    @Override
    public ScriptState execute() {
        // call setup code
        start();

        // while textbox is not finished displaying all text, script keeps running
        if (!isTextboxQueueEmpty()) {
            return ScriptState.RUNNING;
        }

        // call cleanup code
        end();

        // script ends
        return ScriptState.COMPLETED;
    }

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new TextboxScriptAction(textItems, getMap().getTextbox()));
        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
