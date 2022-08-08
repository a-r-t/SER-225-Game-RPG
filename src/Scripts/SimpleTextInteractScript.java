package Scripts;

import Level.*;

public class SimpleTextInteractScript extends Script {
    private String[] textItems;

    public SimpleTextInteractScript(String text) {
        this.textItems = new String[] { text };
    }

    public SimpleTextInteractScript(String[] text) {
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
        start();
        if (!isTextboxQueueEmpty()) {
            return ScriptState.RUNNING;
        }
        end();
        return ScriptState.COMPLETED;
    }
}
