package ScriptActions;

import Level.NPC;
import Level.ScriptState;

public class NPCFacePlayerScriptAction extends ScriptAction {

    protected NPC npc;

    public NPCFacePlayerScriptAction() {}

    public NPCFacePlayerScriptAction(int npcId) {
        this.npc = map.getNPCById(npcId);
    }

    @Override
    public void setup() {
        if (this.npc == null) {
            this.npc = (NPC)entity;
        }
    }

    @Override
    public ScriptState execute() {
        npc.facePlayer(player);
        return ScriptState.COMPLETED;
    }
}
