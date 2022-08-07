package Level;

public abstract class BaseEvent implements InteractEvent {
    protected boolean start = true;

    protected void setup(Player player, Map map) {
        lockPlayer(player);
    }

    protected void cleanup(Player player, Map map) {
        unlockPlayer(player);
    }

    @Override
    public ScriptState onInteract(Player player, Map map) {
        return ScriptState.COMPLETED;
    }

    protected void start(Player player, Map map) {
        if (start) {
            setup(player, map);
            start = false;
        }
    }

    protected void end(Player player, Map map) {
        cleanup(player, map);
        start = true;
    }

    protected void lockPlayer(Player player) {
        player.setPlayerState(PlayerState.INTERACTING);
    }

    protected void unlockPlayer(Player player) {
        player.setPlayerState(PlayerState.STANDING);
    }

    protected void showTextbox(Map map) {
        map.getTextbox().setIsActive(true);
    }

    protected void addTextToTextboxQueue(Map map, String text) {
        map.getTextbox().addText(text);
    }

    protected void addTextToTextboxQueue(Map map, String[] text) {
        map.getTextbox().addText(text);
    }

    protected boolean isTextboxDone(Map map) {
        return map.getTextbox().isTextQueueEmpty();
    }

    protected void hideTextbox(Map map) {
        map.getTextbox().setIsActive(false);
    }

    protected NPC getNPC(int npcId, Map map) {
        for (NPC npc : map.getNPCs()) {
            if (npc.getId() == npcId) {
                return npc;
            }
        }
        return null;
    }
}
