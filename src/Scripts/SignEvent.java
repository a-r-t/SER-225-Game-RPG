package Scripts;

import Level.*;

public class SignEvent implements InteractEvent {
    private String[] textItems;
    boolean start = true;

    public SignEvent(String text) {
        this.textItems = new String[] { text };
    }

    public SignEvent(String[] text) {
        this.textItems = text;
    }

    @Override
    public ScriptState onInteract(Player player, Map map) {
        if (start) {
            start = false;
            map.getTextbox().setIsActive(true);
            player.setPlayerState(PlayerState.INTERACTING);
            for (String text : textItems) {
                map.getTextbox().addText(text);
            }
        }
        if (!map.getTextbox().isTextQueueEmpty()) {
            return ScriptState.RUNNING;
        }
        start = true;
        player.setPlayerState(PlayerState.STANDING);
        map.getTextbox().setIsActive(false);
        return ScriptState.COMPLETED;
    }
}
