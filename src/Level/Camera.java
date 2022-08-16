package Level;

import Engine.GraphicsHandler;
import Engine.ScreenManager;
import GameObject.GameObject;
import GameObject.Rectangle;

import java.awt.*;
import java.util.ArrayList;

// This class represents a Map's "Camera", aka a piece of the map that is currently included in a level's update/draw logic based on what should be shown on screen.
// A majority of its job is just determining which map tiles, enemies, npcs, and enhanced map tiles are "active" each frame (active = included in update/draw cycle)
public class Camera extends Rectangle {

    // the current map this camera is attached to
    private Map map;

    // width and height of each tile in the map (the map's tileset has this info)
    private int tileWidth, tileHeight;

    // if the screen is covered in full length tiles, often there will be some extra room that doesn't quite have enough space for another entire tile
    // this leftover space keeps track of that "extra" space, which is needed to calculate the camera's current "end" position on the screen (in map coordinates, not screen coordinates)
    private int leftoverSpaceX, leftoverSpaceY;

    // current map entities that are to be included in this frame's update/draw cycle
    private ArrayList<EnhancedMapTile> activeEnhancedMapTiles = new ArrayList<>();
    private ArrayList<NPC> activeNPCs = new ArrayList<>();
    private ArrayList<Trigger> activeTriggers = new ArrayList<>();

    // determines how many tiles off screen an entity can be before it will be deemed inactive and not included in the update/draw cycles until it comes back in range
    private final int UPDATE_OFF_SCREEN_RANGE = 4;

    public Camera(int startX, int startY, int tileWidth, int tileHeight, Map map) {
        super(startX, startY, ScreenManager.getScreenWidth() / tileWidth, ScreenManager.getScreenHeight() / tileHeight);
        this.map = map;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.leftoverSpaceX = ScreenManager.getScreenWidth() % tileWidth;
        this.leftoverSpaceY = ScreenManager.getScreenHeight() % tileHeight;
    }

    // gets the tile index that the camera's x and y values are currently on (top left tile)
    // this is used to determine a starting place for the rectangle of area the camera currently contains on the map
    public Point getTileIndexByCameraPosition() {
        int xIndex = Math.round(getX()) / tileWidth;
        int yIndex = Math.round(getY()) / tileHeight;
        return new Point(xIndex, yIndex);
    }

    public void update(Player player) {
        updateMapTiles(player);
        updateMapEntities(player);
    }

    // update each map tile if it is animated in order to keep animations consistent
    private void updateMapTiles(Player player) {
        for (MapTile tile : map.getMapTiles()) {
            if (tile != null) {
                if (tile.isAnimated()) {
                    tile.update();
                }
                if (tile.getInteractScript() != null && tile.getInteractScript().isActive()) {
                    tile.getInteractScript().update();
                }
            }
        }
//        Point tileIndex = getTileIndexByCameraPosition();
//        for (int i = tileIndex.y - UPDATE_OFF_SCREEN_RANGE; i <= tileIndex.y + height + UPDATE_OFF_SCREEN_RANGE; i++) {
//            for (int j = tileIndex.x - UPDATE_OFF_SCREEN_RANGE; j <= tileIndex.x + width + UPDATE_OFF_SCREEN_RANGE; j++) {
//                MapTile tile = map.getMapTile(j, i);
//                if (tile != null) {
//                    tile.update();
//                }
//            }
//        }
    }

    // update map entities currently a part of the update/draw cycle
    // active entities are calculated each frame using the loadActiveEntity methods below
    public void updateMapEntities(Player player) {
        activeEnhancedMapTiles = loadActiveEnhancedMapTiles();
        activeNPCs = loadActiveNPCs();
        activeTriggers = loadActiveTriggers();

        for (EnhancedMapTile enhancedMapTile : activeEnhancedMapTiles) {
            enhancedMapTile.update(player);
        }

        for (NPC npc : activeNPCs) {
            npc.update(player);
            if (npc.getInteractScript() != null && npc.getInteractScript().isActive()) {
                npc.getInteractScript().update();
            }
        }

        for (Trigger trigger : activeTriggers) {
            if (trigger.getTriggerScript() != null && trigger.getTriggerScript().isActive()) {
                trigger.getTriggerScript().update();
            }
        }
    }

    // determine which enhanced map tiles are active (within range of the camera)
    // if enhanced map tile is currently active and was also active last frame, nothing special happens and enhanced map tile is included in active list
    // if enhanced map tile is currently active but last frame was inactive, it will have its status set to active and enhanced map tile is included in active list
    // if enhanced map tile is currently inactive but last frame was active, it will have its status set to inactive, have its initialize method called if its respawnable
    //      (which will set it back up to its default state), and not include it in the active list
    //      next time a respawnable enemy is determined active, since it was reset back to default state upon going inactive, it will essentially be "respawned" in its starting state
    // if enhanced map tile is currently set to REMOVED, it is permanently removed from the map's list of enemies and will never be able to be active again
    private ArrayList<EnhancedMapTile> loadActiveEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> activeEnhancedMapTiles = new ArrayList<>();
        for (int i = map.getEnhancedMapTiles().size() - 1; i >= 0; i--) {
            EnhancedMapTile enhancedMapTile = map.getEnhancedMapTiles().get(i);

            if (isMapEntityActive(enhancedMapTile)) {
                activeEnhancedMapTiles.add(enhancedMapTile);
                if (enhancedMapTile.mapEntityStatus == MapEntityStatus.INACTIVE) {
                    enhancedMapTile.setMapEntityStatus(MapEntityStatus.ACTIVE);
                }
            } else if (enhancedMapTile.getMapEntityStatus() == MapEntityStatus.ACTIVE) {
                enhancedMapTile.setMapEntityStatus(MapEntityStatus.INACTIVE);
            } else if (enhancedMapTile.getMapEntityStatus() == MapEntityStatus.REMOVED) {
                map.getEnhancedMapTiles().remove(i);
            }
        }
        return activeEnhancedMapTiles;
    }

    // determine which npcs are active (within range of the camera)
    // if npc is currently active and was also active last frame, nothing special happens and npc is included in active list
    // if npc is currently active but last frame was inactive, it will have its status set to active and npc is included in active list
    // if npc is currently inactive but last frame was active, it will have its status set to inactive, have its initialize method called if its respawnable
    //      (which will set it back up to its default state), and not include it in the active list
    //      next time a respawnable enemy is determined active, since it was reset back to default state upon going inactive, it will essentially be "respawned" in its starting state
    // if npc is currently set to REMOVED, it is permanently removed from the map's list of enemies and will never be able to be active again
    private ArrayList<NPC> loadActiveNPCs() {
        ArrayList<NPC> activeNPCs = new ArrayList<>();
        for (int i = map.getNPCs().size() - 1; i >= 0; i--) {
            NPC npc = map.getNPCs().get(i);

            if (isMapEntityActive(npc)) {
                activeNPCs.add(npc);
                if (npc.mapEntityStatus == MapEntityStatus.INACTIVE) {
                    npc.setMapEntityStatus(MapEntityStatus.ACTIVE);
                }
            } else if (npc.getMapEntityStatus() == MapEntityStatus.ACTIVE) {
                npc.setMapEntityStatus(MapEntityStatus.INACTIVE);
            } else if (npc.getMapEntityStatus() == MapEntityStatus.REMOVED) {
                map.getNPCs().remove(i);
            }
        }
        return activeNPCs;
    }

    private ArrayList<Trigger> loadActiveTriggers() {
        ArrayList<Trigger> activeTriggers = new ArrayList<>();
        for (int i = map.getTriggers().size() - 1; i >= 0; i--) {
            Trigger trigger = map.getTriggers().get(i);

            if (isMapEntityActive(trigger)) {
                activeTriggers.add(trigger);
                if (trigger.mapEntityStatus == MapEntityStatus.INACTIVE) {
                    trigger.setMapEntityStatus(MapEntityStatus.ACTIVE);
                }
            } else if (trigger.getMapEntityStatus() == MapEntityStatus.ACTIVE) {
                trigger.setMapEntityStatus(MapEntityStatus.INACTIVE);
            } else if (trigger.getMapEntityStatus() == MapEntityStatus.REMOVED) {
                map.getTriggers().remove(i);
            }
        }
        return activeTriggers;
    }

    /*
        determines if map entity (enemy, enhanced map tile, or npc) is active by the camera's standards
        1. if entity's status is REMOVED, it is not active, no questions asked
        2. if entity's status is not REMOVED, then there's additional checks that take place:
            1. if entity's isUpdateOffScreen attribute is true, it is active
            2. OR if the camera determines that it is in its boundary range, it is active
     */
    private boolean isMapEntityActive(MapEntity mapEntity) {
        return mapEntity.getMapEntityStatus() != MapEntityStatus.REMOVED && !mapEntity.isHidden() && mapEntity.exists() && (mapEntity.isUpdateOffScreen() || containsUpdate(mapEntity));
    }

    public void draw(GraphicsHandler graphicsHandler) {
        drawMapTilesBottomLayer(graphicsHandler);
        drawMapTilesTopLayer(graphicsHandler);
    }

    public void draw(Player player, GraphicsHandler graphicsHandler) {
        drawMapTilesBottomLayer(graphicsHandler);
        drawMapEntities(player, graphicsHandler);
        drawMapTilesTopLayer(graphicsHandler);
    }

    // draws visible map tiles to the screen
    // this is different than "active" map tiles as determined in the update method -- there is no reason to actually draw to screen anything that can't be seen
    // so this does not include the extra range granted by the UPDATE_OFF_SCREEN_RANGE value
    public void drawMapTilesBottomLayer(GraphicsHandler graphicsHandler) {
        Point tileIndex = getTileIndexByCameraPosition();
        for (int i = tileIndex.y - 1; i <= tileIndex.y + height + 1; i++) {
            for (int j = tileIndex.x - 1; j <= tileIndex.x + width + 1; j++) {
                MapTile tile = map.getMapTile(j, i);
                if (tile != null) {
                    tile.drawBottomLayer(graphicsHandler);
                }
            }
        }
    }

    public void drawMapTilesTopLayer(GraphicsHandler graphicsHandler) {
        Point tileIndex = getTileIndexByCameraPosition();
        for (int i = tileIndex.y - 1; i <= tileIndex.y + height + 1; i++) {
            for (int j = tileIndex.x - 1; j <= tileIndex.x + width + 1; j++) {
                MapTile tile = map.getMapTile(j, i);
                if (tile != null && tile.getTopLayer() != null) {
                    tile.drawTopLayer(graphicsHandler);
                }
            }
        }

        for (EnhancedMapTile enhancedMapTile : activeEnhancedMapTiles) {
            if (containsDraw(enhancedMapTile) && enhancedMapTile.getTopLayer() != null) {
                enhancedMapTile.drawTopLayer(graphicsHandler);
            }
        }
    }

    // draws active map entities to the screen
    public void drawMapEntities(Player player, GraphicsHandler graphicsHandler) {
        for (EnhancedMapTile enhancedMapTile : activeEnhancedMapTiles) {
            if (containsDraw(enhancedMapTile)) {
                enhancedMapTile.drawBottomLayer(graphicsHandler);
            }
        }
        ArrayList<NPC> drawNpcsAfterPlayer = new ArrayList<>();
        for (NPC npc : activeNPCs) {
            if (containsDraw(npc)) {
                if (npc.getCalibratedScaledBounds().getY() < player.getCalibratedScaledBounds().getY1()  + (player.getCalibratedScaledBounds().getHeight() / 2)) {
                    npc.draw(graphicsHandler);
                }
                else {
                    drawNpcsAfterPlayer.add(npc);
                }
            }
        }
        player.draw(graphicsHandler);
        for (NPC npc : drawNpcsAfterPlayer) {
            npc.draw(graphicsHandler);
        }

        // Uncomment this to see triggers drawn on screen
        // helps for placing them in the correct spot/debugging
        /*
        for (Trigger trigger : activeTriggers) {
            if (containsDraw(trigger)) {
                trigger.draw(graphicsHandler);
            }
        }
        */
    }


    // checks if a game object's position falls within the camera's current radius
    public boolean containsUpdate(GameObject gameObject) {
        return getX1() - (tileWidth * UPDATE_OFF_SCREEN_RANGE) < gameObject.getX() + gameObject.getScaledWidth() &&
                getEndBoundX() + (tileWidth * UPDATE_OFF_SCREEN_RANGE) > gameObject.getX() &&
                getY1() - (tileHeight * UPDATE_OFF_SCREEN_RANGE) <  gameObject.getY() + gameObject.getScaledHeight()
                && getEndBoundY() + (tileHeight * UPDATE_OFF_SCREEN_RANGE) > gameObject.getY();
    }

    // checks if a game object's position falls within the camera's current radius
    // this does not include the extra range granted by the UPDATE_OFF_SCREEN_RANGE value, because there is no point to drawing graphics that can't be seen
    public boolean containsDraw(GameObject gameObject) {
        return getX1() - tileWidth < gameObject.getX() + gameObject.getScaledWidth() && getEndBoundX() + tileWidth > gameObject.getX() &&
                getY1() - tileHeight <  gameObject.getY() + gameObject.getScaledHeight() && getEndBoundY() + tileHeight >  gameObject.getY();
    }

    public ArrayList<EnhancedMapTile> getActiveEnhancedMapTiles() {
        return activeEnhancedMapTiles;
    }

    public ArrayList<NPC> getActiveNPCs() {
        return activeNPCs;
    }

    public ArrayList<Trigger> getActiveTriggers() {
        return activeTriggers;
    }

    // gets end bound X position of the camera (start position is always 0)
    public float getEndBoundX() {
        return x + (width * tileWidth) + leftoverSpaceX;
    }

    // gets end bound Y position of the camera (start position is always 0)
    public float getEndBoundY() {
        return y + (height * tileHeight) + leftoverSpaceY;
    }

    public boolean isAtTopOfMap() {
        return this.getY() <= 0;
    }

    public boolean isAtBottomOfMap() {
        return this.getEndBoundY() >= map.getEndBoundY();
    }
}
