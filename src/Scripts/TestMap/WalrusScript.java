package Scripts.TestMap;

import Event.*;
import Level.*;

public class WalrusScript extends Script {

    @Override
    public Event loadEvent() {
        return new Event() {
            @Override
            protected void setup() {
                lockPlayer();
                showTextbox();
                if (!isFlagSet("hasTalkedToWalrus")) {
                    addTextToTextboxQueue( "Hi Cat!");
                    addTextToTextboxQueue( "...oh, you lost your ball?");
                    addTextToTextboxQueue( "Hmmm...my walrus brain remembers seeing Dino with\nit last. Maybe you can check with him?");
                }
                else {
                    addTextToTextboxQueue( "I sure love doing walrus things!");
                }
                NPC walrus = getNPC(1);
                walrus.facePlayer(player);
            }

            @Override
            protected void cleanup() {
                unlockPlayer();
                hideTextbox();
                setFlag("hasTalkedToWalrus");
            }

            @Override
            public ScriptState execute() {
                start();
                if (!isTextboxQueueEmpty()) {
                    return ScriptState.RUNNING;
                }
                end();
                return ScriptState.COMPLETED;
            }
        };
    }
}
