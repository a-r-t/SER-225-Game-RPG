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
            protected void setup(Player player, Map map) {
                lockPlayer(player);

                if (!isFlagSet(map,"hasTalkedToWalrus")) {
                    showTextbox(map);
                    addTextToTextboxQueue(map, "Isn't my garden so lovely?");
                }
                else if (isFlagSet(map,"hasTalkedToWalrus") && !isFlagSet(map, "hasTalkedToDinosaur")) {
                    if (sequence == 0) {
                        showTextbox(map);
                        addTextToTextboxQueue(map, "Isn't my garden so lovely?");
                    }
                    else if (sequence == 1) {
                        setWaitTime(1000);
                    }
                    else if (sequence == 2) {
                        NPC dino = getNPC(2, map);
                        dino.facePlayer(player);
                        showTextbox(map);
                        addTextToTextboxQueue(map, "Oh, you're still here...");
                        addTextToTextboxQueue(map, "...You heard from Walrus that he saw me with your\nball?");
                        addTextToTextboxQueue(map, "Well, I saw him playing with it and was worried it would\nroll into my garden.");
                        addTextToTextboxQueue(map, "So I kicked it as far as I could into the forest to the left.");
                        addTextToTextboxQueue(map, "Now, if you'll excuse me, I have to go.");
                    }
                    else if (sequence == 3) {
                        NPC dino = getNPC(2, map);
                        dino.setCurrentAnimation("STAND_RIGHT");
                        amountMoved = 0;
                    }
                    else if (sequence == 4) {
                        amountMoved = 0;
                    }
                    else if (sequence == 5) {
                        NPC dino = getNPC(2, map);
                        dino.setCurrentAnimation("STAND_LEFT");

                        Frame openDoorFrame = new FrameBuilder(map.getTileset().getSubImage(4, 4), 0)
                                .withScale(map.getTileset().getTileScale())
                                .build();
                        Point location = map.getMapTile(17, 4).getLocation();

                        MapTile mapTile = new MapTileBuilder(openDoorFrame)
                                .build(location.x, location.y);

                        setMapTile(map, 17, 4, mapTile);

                        amountMoved = 0;
                    }
                }
            }

            @Override
            protected void cleanup(Player player, Map map) {
                if (!isFlagSet(map,"hasTalkedToWalrus")) {
                    unlockPlayer(player);
                    hideTextbox(map);
                }
                else if (isFlagSet(map,"hasTalkedToWalrus") && !isFlagSet(map, "hasTalkedToDinosaur")) {
                    if (sequence == 0) {
                        hideTextbox(map);
                        sequence++;
                    }
                    else if (sequence == 1) {
                        sequence++;
                    }
                    else if (sequence == 2) {
                        hideTextbox(map);
                        unlockPlayer(player);
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

                        setMapTile(map, 17, 4, mapTile);
                        NPC dino = getNPC(2, map);
                        dino.setIsHidden(true);

                        setFlag(map, "hasTalkedToDinosaur");
                        unlockPlayer(player);
                    }
                }
            }

            @Override
            public ScriptState execute(Player player, Map map) {
                if (!isFlagSet(map,"hasTalkedToWalrus")) {
                    start(player, map);
                    if (!isTextboxQueueEmpty(map)) {
                        return ScriptState.RUNNING;
                    }
                    end(player, map);
                    return ScriptState.COMPLETED;
                }
                else if (!isFlagSet(map, "hasTalkedToDinosaur")) {
                    // talks
                    if (sequence == 0) {
                        start(player, map);
                        if (isTextboxQueueEmpty(map)) {
                            end(player, map);
                        }
                    }
                    // pauses
                    else if (sequence == 1) {
                        start(player, map);
                        if (isWaitTimeUp()) {
                            end(player, map);
                        }
                    }
                    // talks more
                    else if (sequence == 2) {
                        start(player, map);
                        if (isTextboxQueueEmpty(map)) {
                            end(player, map);
                        }
                    }
                    // walk downwards
                    else if (sequence == 3) {
                        start(player, map);
                        NPC dino = getNPC(2, map);
                        dino.walk(Direction.DOWN, 2);
                        amountMoved += 2;
                        if (amountMoved == 36) {
                            end(player, map);
                        }
                    }
                    // walk right
                    else if (sequence == 4) {
                        start(player, map);
                        NPC dino = getNPC(2, map);
                        dino.walk(Direction.RIGHT, 2);
                        amountMoved += 2;
                        if (amountMoved == 196) {
                            end(player, map);
                        }
                    }
                    // walk up
                    else if (sequence == 5) {
                        start(player, map);
                        NPC dino = getNPC(2, map);
                        dino.walk(Direction.UP, 2);
                        amountMoved += 2;
                        if (amountMoved == 50) {
                            end(player, map);
                        }
                    }
                    return ScriptState.RUNNING;
                }
                return ScriptState.COMPLETED;
            }
        };
    }
}
