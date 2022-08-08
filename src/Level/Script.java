package Level;
import Utils.Direction;
import Utils.Stopwatch;

public abstract class Script {
    protected boolean isActive = false;

    protected boolean start = true;
    protected Stopwatch stopwatch = new Stopwatch();
    protected MapEntity mapEntity;
    protected Map map;
    protected Player player;

    public Map getMap() { return map; }
    public void setMap(Map map) { this.map = map; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public MapEntity getMapEntity() { return mapEntity; }
    public void setMapEntity(MapEntity mapEntity) { this.mapEntity = mapEntity; }

    protected abstract void setup();

    protected abstract void cleanup();

    protected abstract ScriptState execute();

    public void update() {
        ScriptState scriptState = execute();
        if (scriptState == ScriptState.COMPLETED) {
            this.isActive = false;
        }
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

    public boolean isActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }

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

    protected void npcFacePlayer(int npcId) {
        NPC npc = getNPC(npcId);
        if (npc != null) {
            npc.facePlayer(player);
        }
    }

    protected void npcWalk(int npcId, Direction direction, float speed) {
        NPC npc = getNPC(npcId);
        if (npc != null) {
            npc.walk(direction, speed);
        }
    }

    protected void npcSetAnimation(int npcId, String animationName) {
        NPC npc = getNPC(npcId);
        if (npc != null) {
            npc.setCurrentAnimation(animationName);
        }
    }

    protected void npcSetAnimationFrameIndex(int npcId, int frameIndex) {
        NPC npc = getNPC(npcId);
        if (npc != null) {
            npc.setCurrentAnimationFrameIndex(frameIndex);
        }
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
