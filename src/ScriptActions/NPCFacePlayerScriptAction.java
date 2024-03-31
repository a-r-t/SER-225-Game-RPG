package ScriptActions;

import Level.NPC;
import Level.ScriptState;

public class NPCFacePlayerScriptAction extends ScriptAction {

    @Override
    public ScriptState execute() {
        if (entity instanceof NPC) {
            NPC npc = (NPC)entity;
            npc.facePlayer(player);
        }
        return ScriptState.COMPLETED;
    }
}
