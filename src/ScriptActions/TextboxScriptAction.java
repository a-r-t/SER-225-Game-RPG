package ScriptActions;

import java.util.ArrayList;
import java.util.Arrays;

import Level.ScriptState;
import Level.TextboxItem;

public class TextboxScriptAction extends ScriptAction {
    private ArrayList<TextboxItem> textboxItems;

    public TextboxScriptAction() {
        this.textboxItems = new ArrayList<TextboxItem>();
    }

    public TextboxScriptAction(String text) {
        this.textboxItems = new ArrayList<TextboxItem>();
        this.textboxItems.add(new TextboxItem(text));
    }

    public TextboxScriptAction(String[] textItems) {
        this.textboxItems = new ArrayList<>();
        for (String text : textItems) {
            textboxItems.add(new TextboxItem(text));
        }
    }

    public TextboxScriptAction(ArrayList<String> textItems) {
        this.textboxItems = new ArrayList<>();
        for (String text : textItems) {
            textboxItems.add(new TextboxItem(text));
        }
    }

    public void addText(String text) {
        this.textboxItems.add(new TextboxItem(text));
    }

    public void addText(TextboxItem text) {
        this.textboxItems.add(text);
    }

    public void addText(String text, String[] options) {
        this.textboxItems.add(new TextboxItem(text, new ArrayList<>(Arrays.asList(options))));
    }

    @Override
    public void setup() {
        TextboxItem[] textboxItemsArray = textboxItems.toArray(new TextboxItem[0]);
        this.map.getTextbox().addText(textboxItemsArray);
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
