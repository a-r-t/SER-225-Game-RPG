package Scripts;

import Event.Event;
import Level.*;

public class SimpleTextInteractEvent extends Event {
    private String[] textItems;

    public SimpleTextInteractEvent(String text) {
        this.textItems = new String[] { text };
    }

    public SimpleTextInteractEvent(String[] text) {
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
