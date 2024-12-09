package ScriptActions;

import Level.NPC;
import Level.ScriptState;

public class NPCFacePlayerScriptAction extends ScriptAction {
    protected Integer npcId = null;
    protected NPC npc;

    public NPCFacePlayerScriptAction() {}

    public NPCFacePlayerScriptAction(int npcId) {
        this.npcId = npcId;
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
        npc.facePlayer(player);
        return ScriptState.COMPLETED;
    }
}
