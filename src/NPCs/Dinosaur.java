package NPCs;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Event.Event;
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
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
