package Level;

import java.util.ArrayList;

import ScriptActions.ScriptActionOutputManager;

public class TextboxItem {
    protected String text;
    protected ArrayList<String> options;
    protected ScriptActionOutputManager outputManager;

    public TextboxItem(String text) {
        this.text = text;
    }

    public TextboxItem(String text, ArrayList<String> options) {
        this.text = text;
        this.options = options;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public void addOption(String option) {
        if (options == null) {
            options = new ArrayList<>();
        }
        options.add(option);
    }
}
