package ScriptActions;

import Level.NPC;
import Level.ScriptState;

public class NPCLockScriptAction extends ScriptAction {
    protected NPC npc;

    public NPCLockScriptAction() {}

    public NPCLockScriptAction(int npcId) {
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
        npc.lock();
        return ScriptState.COMPLETED;
    }
}
