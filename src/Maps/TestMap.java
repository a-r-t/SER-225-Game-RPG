package Maps;

import Event.*;
import Level.*;
import NPCs.Dinosaur;
import NPCs.Walrus;
import Scripts.SimpleTextInteractEvent;
import Tilesets.CommonTileset;
import Utils.Point;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset(), new Point(10, 3));
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        NPC walrus = new Walrus(1, getMapTile(4, 28).getLocation().subtractY(40));
        npcs.add(walrus);
        NPC dinosaur = new Dinosaur(1, getMapTile(13, 4).getLocation());
        npcs.add(dinosaur);
        return npcs;
    }

    @Override
    public void loadScripts() {
        getMapTile(21, 19).setScript(new Script(new SimpleTextInteractEvent("Cat's house")));

        getMapTile(7, 26).setScript(new Script(new SimpleTextInteractEvent("Walrus's house")));

        getMapTile(20, 4).setScript(new Script(new SimpleTextInteractEvent("Dino's house")));


    }
}

