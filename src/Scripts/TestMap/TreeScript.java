package Scripts.TestMap;

import Event.*;
import GameObject.Rectangle;
import Level.*;

public class TreeScript extends Script {

    @Override
    public Event loadEvent() {
        return new Event() {
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
                if (!isFlagSet("hasFoundBall") && isFlagSet("hasTalkedToDinosaur") && isPlayerAtBottomOfTile()) {
                    start();
                    if (!isTextboxQueueEmpty()) {
                        return ScriptState.RUNNING;
                    }
                    end();
                }
                return ScriptState.COMPLETED;
            }

            private boolean isPlayerAtBottomOfTile() {
                Rectangle mapTileBounds = getMapTile(2, 6).getScaledBounds();
                return player.getCalibratedScaledBounds().getY1() >= getMapTile(2, 6).getCalibratedScaledBounds().getY2() &&
                        (player.getCalibratedScaledBounds().getX1() < getMapTile(2, 6).getCalibratedScaledBounds().getX2() && player.getCalibratedScaledBounds().getX2() > getMapTile(2, 6).getCalibratedScaledBounds().getX2()) ||
                        (player.getCalibratedScaledBounds().getX2() > getMapTile(2, 6).getCalibratedScaledBounds().getX() && player.getCalibratedScaledBounds().getX1() < getMapTile(2, 6).getCalibratedScaledBounds().getX1()) ||
                        (player.getCalibratedScaledBounds().getX1() > getMapTile(2, 6).getCalibratedScaledBounds().getX1() && player.getCalibratedScaledBounds().getX2() < getMapTile(2, 6).getCalibratedScaledBounds().getX2());

            }
        };
    }
}
