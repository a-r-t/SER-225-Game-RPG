package ScriptActions;

import Level.NPC;
import Level.ScriptState;

public class NPCFacePlayerScriptAction extends ScriptAction {
    protected int npcId;
    protected NPC npc;

    public NPCFacePlayerScriptAction() {}

    public NPCFacePlayerScriptAction(int npcId) {
        this.npc = map.getNPCById(npcId);
    }

    @Override
    public void setup() {
        if (entity == null) {
            this.npc = map.getNPCById(npcId);
        }
        else {
            this.npc = (NPC)entity;
        }
    }

    @Override
    public ScriptState execute() {
        npc.facePlayer(player);
        return ScriptState.COMPLETED;
    }
}
