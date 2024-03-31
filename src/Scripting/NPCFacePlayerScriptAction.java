package Scripting;

import Level.NPC;
import Level.ScriptState;

public class NPCFacePlayerScriptAction extends ScriptAction {


    public NPCFacePlayerScriptAction() {
    }

    @Override
    public void setup() {

    }

    @Override
    public ScriptState execute() {
        if (entity instanceof NPC) {
            NPC npc = (NPC)entity;
            npc.facePlayer(player);
        }
        return ScriptState.COMPLETED;
    }

    @Override
    public void cleanup() {

    }
    
}
