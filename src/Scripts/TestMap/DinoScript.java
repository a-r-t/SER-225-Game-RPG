package Scripts.TestMap;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Event.*;
import GameObject.Frame;
import Level.*;
import Utils.Direction;
import Utils.Point;

public class DinoScript extends Script {

    @Override
    public Event loadEvent() {
        return new Event() {
            private int sequence = 0;
            private int amountMoved = 0;

            @Override
            protected void setup() {
                lockPlayer();

                if (!isFlagSet("hasTalkedToWalrus")) {
                    showTextbox();
                    addTextToTextboxQueue("Isn't my garden so lovely?");
                }
                else if (isFlagSet("hasTalkedToWalrus") && !isFlagSet("hasTalkedToDinosaur")) {
                    if (sequence == 0) {
                        showTextbox();
                        addTextToTextboxQueue("Isn't my garden so lovely?");
                    }
                    else if (sequence == 1) {
                        setWaitTime(1000);
                    }
                    else if (sequence == 2) {
                        NPC dino = getNPC(2);
                        dino.facePlayer(player);
                        showTextbox();
                        addTextToTextboxQueue("Oh, you're still here...");
                        addTextToTextboxQueue("...You heard from Walrus that he saw me with your\nball?");
                        addTextToTextboxQueue("Well, I saw him playing with it and was worried it would\nroll into my garden.");
                        addTextToTextboxQueue("So I kicked it as far as I could into the forest to the left.");
                        addTextToTextboxQueue("Now, if you'll excuse me, I have to go.");
                    }
                    else if (sequence == 3) {
                        NPC dino = getNPC(2);
                        dino.setCurrentAnimation("STAND_RIGHT");
                        amountMoved = 0;
                    }
                    else if (sequence == 4) {
                        amountMoved = 0;
                    }
                    else if (sequence == 5) {
                        NPC dino = getNPC(2);
                        dino.setCurrentAnimation("STAND_LEFT");

                        Frame openDoorFrame = new FrameBuilder(map.getTileset().getSubImage(4, 4), 0)
                                .withScale(map.getTileset().getTileScale())
                                .build();
                        Point location = map.getMapTile(17, 4).getLocation();

                        MapTile mapTile = new MapTileBuilder(openDoorFrame)
                                .build(location.x, location.y);

                        setMapTile(17, 4, mapTile);

                        amountMoved = 0;
                    }
                }
            }

            @Override
            protected void cleanup() {
                if (!isFlagSet("hasTalkedToWalrus")) {
                    unlockPlayer();
                    hideTextbox();
                }
                else if (isFlagSet("hasTalkedToWalrus") && !isFlagSet("hasTalkedToDinosaur")) {
                    if (sequence == 0) {
                        hideTextbox();
                        sequence++;
                    }
                    else if (sequence == 1) {
                        sequence++;
                    }
                    else if (sequence == 2) {
                        hideTextbox();
                        unlockPlayer();
                        sequence++;
                    }
                    else if (sequence == 3) {
                        sequence++;
                    }
                    else if (sequence == 4) {
                        sequence++;
                    }
                    else if (sequence == 5) {
                        sequence++;

                        Frame doorFrame = new FrameBuilder(map.getTileset().getSubImage(4, 3), 0)
                                .withScale(map.getTileset().getTileScale())
                                .build();
                        Point location = map.getMapTile(17, 4).getLocation();

                        MapTile mapTile = new MapTileBuilder(doorFrame)
                                .withTileType(TileType.NOT_PASSABLE)
                                .build(location.x, location.y);

                        setMapTile(17, 4, mapTile);
                        NPC dino = getNPC(2);
                        dino.setIsHidden(true);

                        setFlag("hasTalkedToDinosaur");
                        unlockPlayer();
                    }
                }
            }

            @Override
            public ScriptState execute() {
                if (!isFlagSet("hasTalkedToWalrus")) {
                    start();
                    if (!isTextboxQueueEmpty()) {
                        return ScriptState.RUNNING;
                    }
                    end();
                    return ScriptState.COMPLETED;
                }
                else if (!isFlagSet("hasTalkedToDinosaur")) {
                    // talks
                    if (sequence == 0) {
                        start();
                        if (isTextboxQueueEmpty()) {
                            end();
                        }
                    }
                    // pauses
                    else if (sequence == 1) {
                        start();
                        if (isWaitTimeUp()) {
                            end();
                        }
                    }
                    // talks more
                    else if (sequence == 2) {
                        start();
                        if (isTextboxQueueEmpty()) {
                            end();
                        }
                    }
                    // walk downwards
                    else if (sequence == 3) {
                        start();
                        NPC dino = getNPC(2);
                        dino.walk(Direction.DOWN, 2);
                        amountMoved += 2;
                        if (amountMoved == 36) {
                            end();
                        }
                    }
                    // walk right
                    else if (sequence == 4) {
                        start();
                        NPC dino = getNPC(2);
                        dino.walk(Direction.RIGHT, 2);
                        amountMoved += 2;
                        if (amountMoved == 196) {
                            end();
                        }
                    }
                    // walk up
                    else if (sequence == 5) {
                        start();
                        NPC dino = getNPC(2);
                        dino.walk(Direction.UP, 2);
                        amountMoved += 2;
                        if (amountMoved == 50) {
                            end();
                        }
                    }
                    return ScriptState.RUNNING;
                }
                return ScriptState.COMPLETED;
            }
        };
    }
}
