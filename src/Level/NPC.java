package Level;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import SpriteFont.SpriteFont;
import Utils.Direction;
import Utils.Stopwatch;

import java.awt.image.BufferedImage;
import java.util.HashMap;

// This class is a base class for all npcs in the game -- all npcs should extend from it
public class NPC extends MapEntity {
    protected int id = 0;

    public NPC(int id, float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(x, y, spriteSheet, startingAnimation);
        this.id = id;
    }

    public NPC(int id, float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
        this.id = id;
    }

    public NPC(int id, BufferedImage image, float x, float y, String startingAnimation) {
        super(image, x, y, startingAnimation);
        this.id = id;
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

    public int getId() { return id; }

    public void facePlayer(Player player) {
        if (Math.round(getScaledBoundsX2()) - (getScaledBounds().getWidth() / 2) < Math.round(player.getScaledBoundsX2())) {
            this.currentAnimationName = "STAND_RIGHT";
        }
        else if (Math.round(getScaledBoundsX1()) + (getScaledBounds().getWidth() / 2) > Math.round(player.getScaledBoundsX1())) {
            this.currentAnimationName = "STAND_LEFT";
        }
    }

    public void stand(Direction direction) {
        if (direction == Direction.RIGHT) {
            this.currentAnimationName = "STAND_RIGHT";
        }
        else if (direction == Direction.LEFT) {
            this.currentAnimationName = "STAND_LEFT";
        }
    }

    public void walk(Direction direction, float speed) {
        if (direction == Direction.RIGHT) {
            this.currentAnimationName = "WALK_RIGHT";
        }
        else if (direction == Direction.LEFT) {
            this.currentAnimationName = "WALK_LEFT";
        }
        else {
            if (this.currentAnimationName.contains("RIGHT")) {
                this.currentAnimationName = "WALK_RIGHT";
            }
            else {
                this.currentAnimationName = "WALK_LEFT";
            }
        }
        if (direction == Direction.UP) {
            moveY(-speed);
        }
        else if (direction == Direction.DOWN) {
            moveY(speed);
        }
        else if (direction == Direction.LEFT) {
            moveX(-speed);
        }
        else if (direction == Direction.RIGHT) {
            moveX(speed);
        }
    }

    public void update(Player player) {
        super.update();
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
