package ScriptActions;

import Level.NPC;
import Level.ScriptState;
import Utils.Direction;

public class NPCWalkScriptAction extends ScriptAction {
    protected Integer npcId = null;
    protected NPC npc;
    protected Direction direction;
    protected float distance;
    protected float speed;
    protected float amountMoved;

    public NPCWalkScriptAction(Direction direction, float distance, float speed) {
        this.direction = direction;
        this.distance = distance;
        this.speed = speed;
    }

    public NPCWalkScriptAction(int npcId, Direction direction, float distance, float speed) {
        this.npcId = npcId;
        this.direction = direction;
        this.distance = distance;
        this.speed = speed;
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
        amountMoved = 0;
    }

    @Override
    public ScriptState execute() {
        npc.walk(direction, speed);
        amountMoved += speed;
        if (amountMoved < distance) {
            return ScriptState.RUNNING;
        }
        return ScriptState.COMPLETED;
    }
}
