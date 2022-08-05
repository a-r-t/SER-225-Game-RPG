package Maps;

import Level.*;
import Scripts.SignEvent;
import Tilesets.CommonTileset;
import Utils.Point;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset(), new Point(21, 20));
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        return npcs;
    }

    @Override
    public void loadScripts() {
        getMapTile(21, 19).setScript(new Script(new SignEvent("Cat's house")));

        getMapTile(7, 26).setScript(new Script(new SignEvent("Walrus's house")));

        getMapTile(20, 4).setScript(new Script(new SignEvent("Dino's house")));

    }
}

/*
                   boolean start = true;
                    Stopwatch sw = new Stopwatch();
                    @Override
                new InteractEvent() {
                    boolean start = true;
                    Stopwatch sw = new Stopwatch();
                    @Override
                    public ScriptState onInteract(Player player, Map map) {
                        if (start) {
                            sw.setWaitTime(2000);
                            start = false;
                            map.getTextbox().setIsActive(true);
                            player.setPlayerState(PlayerState.INTERACTING);
                        }
                        if (!sw.isTimeUp()) {
                            return ScriptState.RUNNING;
                        }
                        BufferedImage newTileImage = map.getTileset().getSubImage(0, 0);
                        MapTile newMapTile = new MapTile(newTileImage, signMapTile.getX(), signMapTile.getY(), map.getTileset().getTileScale(), TileType.PASSABLE);
                        newMapTile.setMap(map);
                        //map.setMapTile(21, 19, newMapTile);
                        //map.getTextbox().setIsActive(false);
                        start = true;
                        player.setPlayerState(PlayerState.STANDING);
                        return ScriptState.COMPLETED;
                    }
                }));

 */