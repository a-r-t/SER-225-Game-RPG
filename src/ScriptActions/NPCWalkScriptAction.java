package ScriptActions;

import Level.NPC;
import Level.ScriptState;
import Utils.Direction;

public class NPCWalkScriptAction extends ScriptAction {
    protected int npcId;
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
        this.npc = map.getNPCById(npcId);
        this.direction = direction;
        this.distance = distance;
        this.speed = speed;
    }

    @Override
    public void setup() {
        if (entity == null) {
            this.npc = map.getNPCById(npcId);
        }
        else {
            this.npc = (NPC)entity;
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
