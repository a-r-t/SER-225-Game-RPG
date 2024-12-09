package ScriptActions;

import Level.NPC;
import Level.ScriptState;
import Utils.Direction;

public class NPCStandScriptAction extends ScriptAction {
    protected Integer npcId = null;
    protected NPC npc;
    protected Direction facingDirection;

    public NPCStandScriptAction(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public NPCStandScriptAction(int npcId, Direction facingDirection) {
        this.npcId = npcId;
        this.facingDirection = facingDirection;
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
        npc.stand(facingDirection);
        return ScriptState.COMPLETED;
    }
}
