package Scripting;

import Level.ScriptState;
import Level.Textbox;

public class TextboxScriptAction extends ScriptAction {
    private String[] text;
    private Textbox textbox;

    public TextboxScriptAction(String[] text, Textbox textbox) {
        this.text = text;
        this.textbox = textbox;
    }

    @Override
    public void setup() {
        textbox.addText(text);
        this.map.getTextbox().setIsActive(true);
    }

    @Override
    public ScriptState execute() {
        if (!textbox.isTextQueueEmpty()) {
            return ScriptState.RUNNING;
        }
        return ScriptState.COMPLETED;
    }

    @Override
    public void cleanup() {
        this.map.getTextbox().setIsActive(false);
    }
    
}
