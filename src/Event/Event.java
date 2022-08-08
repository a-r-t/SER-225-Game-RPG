package Event;

import Level.*;
import Utils.Stopwatch;

public abstract class Event {
    protected boolean start = true;
    protected Stopwatch stopwatch = new Stopwatch();
    protected Map map;
    protected Player player;

    public Event() { }

    public Map getMap() { return map; }
    public void setMap(Map map) { this.map = map; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }

    public abstract ScriptState execute();

    protected void setup() {
        lockPlayer();
    }

    protected void cleanup() {
        unlockPlayer();
    }

    protected void start() {
        if (start) {
            setup();
            start = false;
        }
    }

    protected void end() {
        cleanup();
        start = true;
    }

    protected void lockPlayer() {
        player.setPlayerState(PlayerState.INTERACTING);
    }

    protected void unlockPlayer() {
        player.setPlayerState(PlayerState.STANDING);
    }

    protected void showTextbox() {
        map.getTextbox().setIsActive(true);
    }

    protected void addTextToTextboxQueue(String text) {
        map.getTextbox().addText(text);
    }

    protected void addTextToTextboxQueue(String[] text) {
        map.getTextbox().addText(text);
    }

    protected boolean isTextboxQueueEmpty() {
        return map.getTextbox().isTextQueueEmpty();
    }

    protected void hideTextbox() {
        map.getTextbox().setIsActive(false);
    }

    protected NPC getNPC(int npcId) {
        for (NPC npc : map.getNPCs()) {
            if (npc.getId() == npcId) {
                return npc;
            }
        }
        return null;
    }

    protected boolean isFlagSet(String flagName) {
        return map.getFlagManager().isFlagSet(flagName);
    }

    protected void setFlag(String flagName) {
        map.getFlagManager().setFlag(flagName);
    }

    protected void unsetFlag(String flagName) {
        map.getFlagManager().unsetFlag(flagName);
    }

    protected void setWaitTime(int milliseconds) {
        stopwatch.setWaitTime(milliseconds);
    }

    protected boolean isWaitTimeUp() {
        return stopwatch.isTimeUp();
    }

    protected MapTile getMapTile(int x, int y) {
        return map.getMapTile(x, y);
    }

    protected void setMapTile(int x, int y, MapTile mapTile) {
        mapTile.setMap(map);
        map.setMapTile(x, y, mapTile);
    }
}
