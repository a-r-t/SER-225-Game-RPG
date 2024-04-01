package ScriptActions;

import Level.NPC;
import Level.ScriptState;
import Utils.Direction;

public class NPCStandScriptAction extends ScriptAction {
    protected NPC npc;
    protected Direction facingDirection;

    public NPCStandScriptAction(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public NPCStandScriptAction(int npcId, Direction facingDirection) {
        this.npc = map.getNPCById(npcId);
        this.facingDirection = facingDirection;
    }

    @Override
    public ScriptState execute() {
        if (this.npc == null) {
            this.npc = (NPC)entity;
        }
        npc.stand(facingDirection);
        return ScriptState.COMPLETED;
    }
}
