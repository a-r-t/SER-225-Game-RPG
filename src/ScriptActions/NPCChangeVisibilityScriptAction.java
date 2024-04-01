package ScriptActions;

import Level.NPC;
import Level.ScriptState;
import Utils.Visibility;

public class NPCChangeVisibilityScriptAction extends ScriptAction {
    protected NPC npc;
    protected Visibility visibility;

    public NPCChangeVisibilityScriptAction(Visibility visibility) {
        this.visibility = visibility;
    }

    public NPCChangeVisibilityScriptAction(int npcId, Visibility visibility) {
        this.npc = map.getNPCById(npcId);
        this.visibility = visibility;
    }
    
    @Override
    public void setup() {
        if (this.npc == null) {
            this.npc = (NPC)entity;
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
