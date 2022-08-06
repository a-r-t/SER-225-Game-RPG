package Level;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

import java.awt.image.BufferedImage;
import java.util.HashMap;

// This class is a base class for all npcs in the game -- all npcs should extend from it
public class NPC extends MapEntity {

    public NPC(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(x, y, spriteSheet, startingAnimation);
    }

    public NPC(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
    }

    public NPC(BufferedImage image, float x, float y, String startingAnimation) {
        super(image, x, y, startingAnimation);
    }

    public NPC(BufferedImage image, float x, float y) {
        super(image, x, y);
    }

    public NPC(BufferedImage image, float x, float y, float scale) {
        super(image, x, y, scale);
    }

    public NPC(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
        super(image, x, y, scale, imageEffect);
    }

    public NPC(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds) {
        super(image, x, y, scale, imageEffect, bounds);
    }

    public void update(Player player) {
        super.update();
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
