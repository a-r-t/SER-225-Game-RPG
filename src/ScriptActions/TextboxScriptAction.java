package ScriptActions;

import java.util.ArrayList;
import java.util.Arrays;

import Level.ScriptState;

public class TextboxScriptAction extends ScriptAction {
    private ArrayList<String> textItems;

    public TextboxScriptAction() {
        this.textItems = new ArrayList<String>();
    }

    public TextboxScriptAction(String[] textItems) {
        this.textItems = new ArrayList<String>(Arrays.asList(textItems));
    }

    public TextboxScriptAction(ArrayList<String> textItems) {
        this.textItems = textItems;
    }

    public void addText(String text) {
        this.textItems.add(text);
    }

    @Override
    public void setup() {
        String[] textItemsArray = textItems.toArray(new String[0]);
        this.map.getTextbox().addText(textItemsArray);
        this.map.getTextbox().setIsActive(true);
    }

    @Override
    public ScriptState execute() {
        if (!this.map.getTextbox().isTextQueueEmpty()) {
            return ScriptState.RUNNING;
        }
        return ScriptState.COMPLETED;
    }

    @Override
    public void cleanup() {
        this.map.getTextbox().setIsActive(false);
    }
    
}
