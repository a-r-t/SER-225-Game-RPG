package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.*;
import SpriteFont.SpriteFont;
import Utils.Point;
import Event.*;

import java.util.HashMap;

// This class is for the walrus NPC
public class Walrus extends NPC {

    public Walrus(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("Walrus.png"), 24, 24), "STAND_LEFT");
    }

    public void update(Player player) {
        super.update(player);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                            .withScale(3)
                            .withBounds(7, 13, 11, 7)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build()
            });
            put("STAND_RIGHT", new Frame[] {
                   new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                           .withScale(3)
                           .withBounds(7, 13, 11, 7)
                           .build()
           });
        }};
    }

    @Override
    protected Script loadScript() {
        return new Script(new Event(EventType.INTERACT) {
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
                facePlayer(player);
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
        });
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
