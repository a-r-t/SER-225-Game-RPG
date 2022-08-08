package NPCs;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Event.Event;
import Event.EventType;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.*;
import Utils.Direction;
import Utils.Point;

import java.util.HashMap;

// This class is for the walrus NPC
public class Dinosaur extends NPC {

    public Dinosaur(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("Dinosaur.png"), 14, 17), "STAND_LEFT");
    }

    public void update(Player player) {
        if (map.getFlagManager().isFlagSet("hasTalkedToDinosaur")) {
            this.isHidden = true;
        }
        super.update(player);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build()
            });
            put("STAND_RIGHT", new Frame[] {
                   new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                           .withScale(3)
                           .withBounds(4, 2, 5, 13)
                           .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                           .build()
           });

            put("WALK_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 200)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 200)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build()
            });

            put("WALK_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build()
            });
        }};
    }

    @Override
    protected Script loadScript() {
        return new Script(new Event(EventType.INTERACT) {
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
                        facePlayer(player);
                        showTextbox(map);
                        addTextToTextboxQueue(map, "Oh, you're still here...");
                        addTextToTextboxQueue(map, "...You heard from Walrus that he saw me with your\nball?");
                        addTextToTextboxQueue(map, "Well, I saw him playing with it and was worried it would\nroll into my garden.");
                        addTextToTextboxQueue(map, "So I kicked it as far as I could into the forest to the left.");
                        addTextToTextboxQueue(map, "Now, if you'll excuse me, I have to go.");
                    }
                    else if (sequence == 3) {
                        currentAnimationName = "STAND_RIGHT";
                        amountMoved = 0;
                    }
                    else if (sequence == 4) {
                        amountMoved = 0;
                    }
                    else if (sequence == 5) {
                        currentAnimationName = "STAND_LEFT";

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
                        setIsHidden(true);

                        setFlag(map, "hasTalkedToDinosaur");
                        unlockPlayer(player);
                    }
                }
            }

            @Override
            public ScriptState execute(Player player, Map map) {
                if (!isFlagSet(map,"hasTalkedToWalrus")) {
                    start(player, map);
                    if (isTextboxQueueEmpty(map)) {
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
                        walk(Direction.DOWN, 2);
                        amountMoved += 2;
                        if (amountMoved == 36) {
                            end(player, map);
                        }
                    }
                    // walk right
                    else if (sequence == 4) {
                        start(player, map);
                        walk(Direction.RIGHT, 2);
                        amountMoved += 2;
                        if (amountMoved == 196) {
                            end(player, map);
                        }
                    }
                    // walk up
                    else if (sequence == 5) {
                        start(player, map);
                        walk(Direction.UP, 2);
                        amountMoved += 2;
                        if (amountMoved == 50) {
                            end(player, map);
                        }
                    }
                    return ScriptState.RUNNING;
                }
                return ScriptState.COMPLETED;
            }
        });
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
