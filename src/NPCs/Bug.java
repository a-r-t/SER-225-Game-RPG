package NPCs;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Utils.Point;

public class Bug extends NPC {
    public Bug(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("Bug.png"), 24, 15), "WALK_RIGHT");
    }

    int amountMoved = 0;
    int mod = 1;

    @Override
    public void performAction(Player player) {
        if (amountMoved < 90) {
            float am = moveXHandleCollision(1 * mod);
            amountMoved += Math.abs(am);
        }
        else {
            amountMoved = 0;
            mod *= -1;
        }
        if (mod > 0) {
            currentAnimationName = "WALK_RIGHT";
        }
        else {
            currentAnimationName = "WALK_LEFT";
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(2)
                    .withBounds(3, 5, 18, 7)
                    .build()
            });
            put("STAND_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(2)
                    .withBounds(3, 5, 18, 7)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build()
           });
           put("WALK_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(2)
                        .withBounds(3, 5, 18, 7)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 8)
                        .withScale(2)
                        .withBounds(3, 5, 18, 7)
                        .build()
            });
            put("WALK_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(2)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(3, 5, 18, 7)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 8)
                        .withScale(2)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(3, 5, 18, 7)
                        .build()
            });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
