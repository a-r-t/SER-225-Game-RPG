package Maps;

import EnhancedMapTiles.Rock;
import Level.*;
import NPCs.Dinosaur;
import NPCs.Walrus;
import Scripts.SimpleTextInteractScript;
import Scripts.TestMap.DinoScript;
import Scripts.TestMap.TreeScript;
import Scripts.TestMap.WalrusScript;
import Tilesets.CommonTileset;
import Utils.Point;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset(), new Point(17, 20));
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
        enhancedMapTiles.add(new Rock(getMapTile(2, 7).getLocation()));
        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        npcs.add(new Walrus(1, getMapTile(4, 28).getLocation().subtractY(40)));
        npcs.add(new Dinosaur(2, getMapTile(13, 4).getLocation()));
        return npcs;
    }

    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        triggers.add(new Trigger(0, 0, 100, 100, new SimpleTextInteractScript("Yo")));
        return triggers;

    }

    @Override
    public void loadScripts() {
        getMapTile(21, 19).setInteractScript(new SimpleTextInteractScript("Cat's house"));

        getMapTile(7, 26).setInteractScript(new SimpleTextInteractScript("Walrus's house"));

        getMapTile(20, 4).setInteractScript(new SimpleTextInteractScript("Dino's house"));

        getMapTile(2, 6).setInteractScript(new TreeScript());

        getNPCById(1).setInteractScript(new WalrusScript());
        getNPCById(2).setInteractScript(new DinoScript());
    }
}

