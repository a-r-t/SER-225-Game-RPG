package Level;

import Engine.GraphicsHandler;
import GameObject.*;
import Utils.Point;

import java.awt.image.BufferedImage;
import java.util.HashMap;

// Represents a map tile in a Map's tile map
public class MapTile extends MapEntity {
    // this determines a tile's properties, like if it's passable or not
    protected TileType tileType;
    protected GameObject bottomLayer;
    protected GameObject topLayer;
    private int tileIndex;

    public MapTile(float x, float y, GameObject bottomLayer, GameObject topLayer, int tileIndex, TileType tileType) {
        super(x, y);
        this.bottomLayer = bottomLayer;
        this.topLayer = topLayer;
        this.tileType = tileType;
        this.tileIndex = tileIndex;
    }

    public MapTile(float x, float y, BufferedImage image, int tileIndex, TileType tileType) {
        super(image, x, y);
        this.tileType = tileType;
        this.tileIndex = tileIndex;
    }

    public MapTile(float x, float y, HashMap<String, Frame[]> animations, int tileIndex, TileType tileType) {
        super(x, y, animations, "DEFAULT");
        this.tileType = tileType;
        this.tileIndex = tileIndex;
    }
//
    public MapTile(float x, float y, SpriteSheet spriteSheet, String startingAnimation, TileType tileType) {
        super(x, y, spriteSheet, startingAnimation);
        this.tileType = tileType;
    }
//
//    public MapTile(float x, float y, HashMap<String, Frame[]> animations, TileType tileType) {
//        super(x, y, animations, "DEFAULT");
//        this.tileType = tileType;
//    }
//
//    public MapTile(BufferedImage image, float x, float y, TileType tileType) {
//        super(image, x, y, "DEFAULT");
//        this.tileType = tileType;
//    }
//
//    public MapTile(BufferedImage image, float x, float y, float scale, TileType tileType) {
//        super(image, x, y, scale);
//        this.tileType = tileType;
//    }
//
//    public MapTile(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, TileType tileType) {
//        super(image, x, y, scale, imageEffect);
//        this.tileType = tileType;
//    }
//
//    public MapTile(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds, TileType tileType) {
//        super(image, x, y, scale, imageEffect, bounds);
//        this.tileType = tileType;
//    }

    public TileType getTileType() {
        return tileType;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public GameObject getBottomLayer() { return bottomLayer; }
    public void setBottomLayer(GameObject bottomLayer) { this.bottomLayer = bottomLayer; }

    public GameObject getTopLayer() { return topLayer; }
    public void setTopLayer(GameObject topLayer) { this.topLayer = topLayer; }

    public boolean isAnimated() {
        return (bottomLayer.getCurrentAnimation().length > 1) ||
                (topLayer != null && topLayer.getCurrentAnimation().length > 1);
    }

    // set this game object's map to make it a "part of" the map, allowing calibrated positions and collision handling logic to work
    public void setMap(Map map) {
        this.map = map;
        this.bottomLayer.setMap(map);
        if (topLayer != null) {
            this.topLayer.setMap(map);
        }
    }

    @Override
    public void update() {
        bottomLayer.update();
        if (topLayer != null) {
            topLayer.update();
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        bottomLayer.draw(graphicsHandler);
        if (topLayer != null) {
            topLayer.draw(graphicsHandler);
        }
        //drawBounds(graphicsHandler, new Color(0, 0, 255, 100));
    }

    public void drawBottomLayer(GraphicsHandler graphicsHandler) {
        bottomLayer.draw(graphicsHandler);
    }

    public void drawTopLayer(GraphicsHandler graphicsHandler) {
        topLayer.draw(graphicsHandler);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getScaledBoundsX1() {
        return bottomLayer.getScaledBoundsX1();
    }

    @Override
    public float getScaledBoundsX2() {
        return bottomLayer.getScaledBoundsX2();
    }

    @Override
    public float getScaledBoundsY1() {
        return bottomLayer.getScaledBoundsY1();
    }

    @Override
    public float getScaledBoundsY2() {
        return bottomLayer.getScaledBoundsY2();
    }

    @Override
    public Point getLocation() {
        return new Point(x, y);
    }

    @Override
    public Rectangle getIntersectRectangle() {
        return bottomLayer.getIntersectRectangle();
    }

    @Override
    public int getScaledWidth() {
        return bottomLayer.getScaledWidth();
    }

    @Override
    public int getScaledHeight() {
        return bottomLayer.getScaledHeight();
    }

    @Override
    public Rectangle getScaledBounds() {
        return bottomLayer.getScaledBounds();
    }

    @Override
    public boolean intersects(IntersectableRectangle other) {
        return bottomLayer.intersects(other);
    }

    @Override
    public float getAreaOverlapped(IntersectableRectangle other) { return bottomLayer.getAreaOverlapped(other); }

    @Override
    public boolean overlaps(IntersectableRectangle other) { return bottomLayer.overlaps(other); }

}
