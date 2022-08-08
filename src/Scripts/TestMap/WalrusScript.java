package Scripts.TestMap;

import Event.*;
import Level.*;

public class WalrusScript extends Script {

    @Override
    public Event loadEvent() {
        return new Event() {
            @Override
            protected void setup(Player player, Map map) {
                lockPlayer(player);
                showTextbox(map);
                if (!isFlagSet(map,"hasTalkedToWalrus")) {
                    addTextToTextboxQueue(map, "Hi Cat!");
                    addTextToTextboxQueue(map, "...oh, you lost your ball?");
                    addTextToTextboxQueue(map, "Hmmm...my walrus brain remembers seeing Dino with\nit last. Maybe you can check with him?");
                }
                else {
                    addTextToTextboxQueue(map, "I sure love doing walrus things!");
                }
                NPC walrus = getNPC(1, map);
                walrus.facePlayer(player);
            }

            @Override
            protected void cleanup(Player player, Map map) {
                unlockPlayer(player);
                hideTextbox(map);
                setFlag(map,"hasTalkedToWalrus");
            }

            @Override
            public ScriptState execute(Player player, Map map) {
                start(player, map);
                if (!isTextboxQueueEmpty(map)) {
                    return ScriptState.RUNNING;
                }
                end(player, map);
                return ScriptState.COMPLETED;
            }
        };
    }
}
