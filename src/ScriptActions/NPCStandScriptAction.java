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
    public void setup() {
        if (this.npc == null) {
            this.npc = (NPC)entity;
        }
    }

    @Override
    public ScriptState execute() {
        npc.stand(facingDirection);
        return ScriptState.COMPLETED;
    }
}
