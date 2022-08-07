package Level;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import GameObject.*;

import java.awt.image.BufferedImage;
import java.util.HashMap;

// This class is a base class for all enhanced map tiles in the game -- all enhanced map tiles should extend from it
public class EnhancedMapTile extends MapTile {


    public EnhancedMapTile(float x, float y, SpriteSheet spriteSheet, TileType tileType) {
        super(x, y, spriteSheet, tileType);
    }


//
//    public EnhancedMapTile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, TileType tileType) {
//        super(x, y, animations, startingAnimation, tileType);
//    }
//
//    public EnhancedMapTile(BufferedImage image, float x, float y, String startingAnimation, TileType tileType) {
//        super(image, x, y, startingAnimation, tileType);
//    }
//
//    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType) {
//        super(image, x, y, tileType);
//    }
//
//    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType, float scale) {
//        super(image, x, y, scale, tileType);
//    }
//
//    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType, float scale, ImageEffect imageEffect) {
//        super(image, x, y, scale, imageEffect, tileType);
//    }
//
//    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType, float scale, ImageEffect imageEffect, Rectangle bounds) {
//        super(image, x, y, scale, imageEffect, bounds, tileType);
//    }


    public void update(Player player) {
        super.update();
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

}
