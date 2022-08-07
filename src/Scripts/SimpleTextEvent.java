package Scripts;

import Level.*;

public class SimpleTextEvent extends BaseEvent {
    private String[] textItems;

    public SimpleTextEvent(String text) {
        this.textItems = new String[] { text };
    }

    public SimpleTextEvent(String[] text) {
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
    public ScriptState onInteract(Player player, Map map) {
        start(player, map);
        if (!isTextboxDone(map)) {
            return ScriptState.RUNNING;
        }
        end(player, map);
        return ScriptState.COMPLETED;
    }
}
