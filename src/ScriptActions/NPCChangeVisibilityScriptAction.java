package ScriptActions;

import Level.NPC;
import Level.ScriptState;
import Utils.Visibility;

public class NPCChangeVisibilityScriptAction extends ScriptAction {
    protected Integer npcId = null;
    protected NPC npc;
    protected Visibility visibility;

    public NPCChangeVisibilityScriptAction(Visibility visibility) {
        this.visibility = visibility;
    }

    public NPCChangeVisibilityScriptAction(int npcId, Visibility visibility) {
        this.npcId = npcId;
        this.visibility = visibility;
    }
    
    @Override
    public void setup() {
        if (this.npcId == null) {
            if (this.entity != null) {
                this.npc = (NPC)entity;
            }
            else {
                throw new RuntimeException("No NPC entity specified!");
            }
        }
        else {
            this.npc = map.getNPCById(npcId);
            if (this.npc == null) {
                throw new RuntimeException("NPC with id " + npcId + " not found!");
            }
        }
    }

    @Override
    public ScriptState execute() {
        if (visibility == Visibility.VISIBLE) {
            npc.setIsHidden(false);
        }
        else {
            npc.setIsHidden(true);
        }
        return ScriptState.COMPLETED;
    }
}
