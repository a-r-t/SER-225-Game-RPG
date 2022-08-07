package Event;

import Level.*;

public abstract class Event {
    protected boolean start = true;
    protected EventType eventType;

    public Event(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() { return eventType; }

    public abstract ScriptState execute(Player player, Map map);

    protected void setup(Player player, Map map) {
        lockPlayer(player);
    }

    protected void cleanup(Player player, Map map) {
        unlockPlayer(player);
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

    protected void setFlag(Map map, String flagName) {
        map.getFlagManager().setFlag(flagName);
    }

    protected void unsetFlag(Map map, String flagName) {
        map.getFlagManager().unsetFlag(flagName);
    }
}
