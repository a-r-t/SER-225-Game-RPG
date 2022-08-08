package Scripts.TestMap;

import Event.*;
import GameObject.Rectangle;
import Level.*;

public class TreeScript extends Script {

    @Override
    public Event loadEvent() {
        return new Event() {
            @Override
            protected void setup(Player player, Map map) {
                lockPlayer(player);
                showTextbox(map);
                addTextToTextboxQueue(map, "...");
                addTextToTextboxQueue(map, "I found my ball inside of the tree!\nYippee!");
            }

            @Override
            protected void cleanup(Player player, Map map) {
                setFlag(map, "hasFoundBall");
                hideTextbox(map);
                unlockPlayer(player);
            }

            @Override
            public ScriptState execute(Player player, Map map) {
                if (!isFlagSet(map, "hasFoundBall") && isFlagSet(map, "hasTalkedToDinosaur") && isPlayerAtBottomOfTile(map, player)) {
                    start(player, map);
                    if (!isTextboxQueueEmpty(map)) {
                        return ScriptState.RUNNING;
                    }
                    end(player, map);
                }
                return ScriptState.COMPLETED;
            }

            private boolean isPlayerAtBottomOfTile(Map map, Player player) {
                Rectangle mapTileBounds = getMapTile(map, 2, 6).getScaledBounds();
                return player.getCalibratedScaledBounds().getY1() >= getMapTile(map, 2, 6).getCalibratedScaledBounds().getY2() &&
                        (player.getCalibratedScaledBounds().getX1() < getMapTile(map, 2, 6).getCalibratedScaledBounds().getX2() && player.getCalibratedScaledBounds().getX2() > getMapTile(map, 2, 6).getCalibratedScaledBounds().getX2()) ||
                        (player.getCalibratedScaledBounds().getX2() > getMapTile(map, 2, 6).getCalibratedScaledBounds().getX() && player.getCalibratedScaledBounds().getX1() < getMapTile(map, 2, 6).getCalibratedScaledBounds().getX1()) ||
                        (player.getCalibratedScaledBounds().getX1() > getMapTile(map, 2, 6).getCalibratedScaledBounds().getX1() && player.getCalibratedScaledBounds().getX2() < getMapTile(map, 2, 6).getCalibratedScaledBounds().getX2());

            }
        };
    }
}
