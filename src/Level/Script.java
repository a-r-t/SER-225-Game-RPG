package Level;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import GameObject.Rectangle;
import ScriptActions.ConditionalScriptAction;
import ScriptActions.ConditionalScriptActionGroup;
import ScriptActions.ScriptAction;
import ScriptActions.ScriptActionOutputManager;

// This class is a base class for all scripts in the game -- all scripts should extend from it
// Scripts can be used to interact with map entities
// Each script defines a set of instructions that will be carried out by the game when it is set to active
// Some examples include interact scripts (such as talking to an NPC) and trigger scripts (scripts that activate when the player walks on them)
public abstract class Script {
    protected ArrayList<ScriptAction> scriptActions;

    // this is set to true if script is currently being executed
    protected boolean isActive = false;

    // if true, script should perform "setup" logic
    protected boolean start = true;

    // references to the map entity the script is attached to
    protected MapEntity entity;

    // reference to the map instance which can be used in any script
    protected Map map;

    // reference to the player instance which can be used in any script
    protected Player player;

    protected ScriptActionOutputManager scriptActionOutputManager;

    public Script() {
        scriptActionOutputManager = new ScriptActionOutputManager();
    }

    public Map getMap() { return map; }
    public void setMap(Map map) { this.map = map; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public MapEntity getEntity() { return entity; }
    public void setMapEntity(MapEntity entity) {
        this.entity = entity;
    }
    public ArrayList<ScriptAction> getScriptActions() {
        return scriptActions;
    }
    public ScriptActionOutputManager getScriptActionOutputManager() {
        return scriptActionOutputManager;
    }

    public void initialize() {
        // load script actions from subclass
        scriptActions = loadScriptActions();

        // recursively iterate through all script actiohns and set the necessary properties on them
        // the recursive part is needed due to conditionals having nested script actions, and those conditionals can have nested conditionals, etc.
        Queue<ScriptAction> scriptActionsToInitialize = new LinkedList<>();
        for (ScriptAction scriptAction : scriptActions) {
            scriptActionsToInitialize.add(scriptAction);
        }
        while (!scriptActionsToInitialize.isEmpty()) {
            ScriptAction scriptAction = scriptActionsToInitialize.poll();
            scriptAction.setMap(map);
            scriptAction.setPlayer(player);
            scriptAction.setEntity(entity);
            scriptAction.setOutputManager(scriptActionOutputManager);
            if (scriptAction instanceof ConditionalScriptAction) {
                ConditionalScriptAction conditionalScriptAction = (ConditionalScriptAction)scriptAction;
                for (ConditionalScriptActionGroup conditionalScriptActionGroup : conditionalScriptAction.getConditionalScriptActionGroups()) {
                    for (ScriptAction conditionalScriptActionGroupScriptAction : conditionalScriptActionGroup.getScriptActions()) {
                        scriptActionsToInitialize.add(conditionalScriptActionGroupScriptAction);
                    }
                }
            }
        }
    }

    public abstract ArrayList<ScriptAction> loadScriptActions();

    private int currentScriptActionIndex;

    private boolean hasScriptActions() {
        return scriptActions.size() > 0;
    }

    public void update() {
        // Runs an execute cycle of the Script
        ScriptAction currentScriptAction = scriptActions.get(currentScriptActionIndex);
        ScriptState scriptState = currentScriptAction.execute();
        if (scriptState == ScriptState.COMPLETED) {
            currentScriptAction.cleanup();
            currentScriptActionIndex++;

            if (currentScriptActionIndex < scriptActions.size()) {
                scriptActions.get(currentScriptActionIndex).setup();
            }
            else {
                this.isActive = false;
                map.setActiveScript(null);
            }
        }
    }

    // if is active is true, game will execute script
    public boolean isActive() { return isActive; }

    public void setIsActive(boolean isActive) { 
        if (hasScriptActions()) {
            this.isActive = isActive; 
            this.currentScriptActionIndex = 0;
            scriptActions.get(currentScriptActionIndex).setup();
        }
    }

    // gets an npc instance by its id value
    protected NPC getNPC(int npcId) {
        for (NPC npc : map.getNPCs()) {
            if (npc.getId() == npcId) {
                return npc;
            }
        }
        return null;
    }

    // force an npc to enter a specified animation
    // npc chosen based on its id value
    protected void npcSetAnimation(int npcId, String animationName) {
        NPC npc = getNPC(npcId);
        if (npc != null) {
            npc.setCurrentAnimationName(animationName);
        }
    }

    // force an npc to enter a specified frame of their current animation
    // npc chosen based on its id value
    protected void npcSetAnimationFrameIndex(int npcId, int frameIndex) {
        NPC npc = getNPC(npcId);
        if (npc != null) {
            npc.setCurrentAnimationFrameIndex(frameIndex);
        }
    }

    // checks if a certain flag has been set or not
    protected boolean isFlagSet(String flagName) {
        return map.getFlagManager().isFlagSet(flagName);
    }

    // sets a flag to true
    protected void setFlag(String flagName) {
        map.getFlagManager().setFlag(flagName);
    }

    // sets a flag to falase
    protected void unsetFlag(String flagName) {
        map.getFlagManager().unsetFlag(flagName);
    }

    // checks if player is currently below the entity attached to this script
    protected boolean isPlayerBelowEntity() {
        Rectangle entityBounds = entity.getCalibratedBounds();
        return player.getBounds().getY1() > entityBounds.getY2();
    }
}
