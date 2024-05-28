package ScriptActions;

import Level.NPC;
import Level.ScriptState;

public class NPCUnlockScriptAction extends ScriptAction {
    protected NPC npc;

    public NPCUnlockScriptAction() {}

    public NPCUnlockScriptAction(int npcId) {
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
        npc.unlock();
        return ScriptState.COMPLETED;
    }
}
