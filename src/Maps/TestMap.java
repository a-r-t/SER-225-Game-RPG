package Maps;

import Event.*;
import Level.*;
import NPCs.Walrus;
import Scripts.SimpleTextInteractEvent;
import Tilesets.CommonTileset;
import Utils.Point;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset(), new Point(4, 26));
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        npcs.add(new Walrus(1, getMapTile(4, 28).getLocation().subtractY(40)));
        return npcs;
    }

    @Override
    public void loadScripts() {
        getMapTile(21, 19).setScript(new Script(new SimpleTextInteractEvent("Cat's house")));

        getMapTile(7, 26).setScript(new Script(new SimpleTextInteractEvent("Walrus's house")));

        getMapTile(20, 4).setScript(new Script(new SimpleTextInteractEvent("Dino's house")));


    }
}

