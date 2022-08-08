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
    protected void setup(Player player, Map map) {
        lockPlayer(player);
        showTextbox(map);
        addTextToTextboxQueue(map, textItems);
    }

    @Override
    protected void cleanup(Player player, Map map) {
        unlockPlayer(player);
        hideTextbox(map);
    }

    @Override
    public ScriptState execute(Player player, Map map) {
        start(player, map);
        if (!isTextboxQueueEmpty(map)) {
            return ScriptState.RUNNING;
        }
        end(player, map);
        return ScriptState.COMPLETED;
    }
}
