package Scripts.TestMap;

import GameObject.Rectangle;
import Level.*;

public class TreeScript extends Script {

    @Override
    protected void setup() {
        lockPlayer();
        showTextbox();
        addTextToTextboxQueue("...");
        addTextToTextboxQueue("I found my ball inside of the tree!\nYippee!");
    }

    @Override
    protected void cleanup() {
        setFlag("hasFoundBall");
        hideTextbox();
        unlockPlayer();
    }

    @Override
    public ScriptState execute() {
        if (!isFlagSet("hasFoundBall") && isFlagSet("hasTalkedToDinosaur") && isPlayerBelowEntity()) {
            start();
            if (!isTextboxQueueEmpty()) {
                return ScriptState.RUNNING;
            }
            end();
        }
        return ScriptState.COMPLETED;
    }
}

