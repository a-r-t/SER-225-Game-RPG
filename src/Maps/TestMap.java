package Maps;

import EnhancedMapTiles.Rock;
import Event.*;
import GameObject.Rectangle;
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
        npcs.add(new Dinosaur(1, getMapTile(13, 4).getLocation()));
        return npcs;
    }

    @Override
    public void loadScripts() {
        getMapTile(21, 19).setScript(new Script(new SimpleTextInteractEvent("Cat's house")));

        getMapTile(7, 26).setScript(new Script(new SimpleTextInteractEvent("Walrus's house")));

        getMapTile(20, 4).setScript(new Script(new SimpleTextInteractEvent("Dino's house")));

        getMapTile(2, 6).setScript(new Script(new Event(EventType.INTERACT) {
            @Override
            protected void setup(Player player, Map map) {
                lockPlayer(player);
                showTextbox(map);
                addTextToTextboxQueue(map, "...");
                addTextToTextboxQueue(map, "I found my ball inside of the tree!\nYippee!");
            }

            @Override
            protected void cleanup(Player player, Map map) {
                setFlag(map, "hasFoundBall");
                hideTextbox(map);
                unlockPlayer(player);
            }

            @Override
            public ScriptState execute(Player player, Map map) {
                if (!isFlagSet(map, "hasFoundBall") && isFlagSet(map, "hasTalkedToDinosaur") && isPlayerAtBottomOfTile(player)) {
                    start(player, map);
                    if (!isTextboxQueueEmpty(map)) {
                        return ScriptState.RUNNING;
                    }
                    end(player, map);
                }
                return ScriptState.COMPLETED;
            }

            private boolean isPlayerAtBottomOfTile(Player player) {
                Rectangle mapTileBounds = getMapTile(2, 6).getScaledBounds();
                return player.getCalibratedScaledBounds().getY1() >= getMapTile(2, 6).getCalibratedScaledBounds().getY2() &&
                        (player.getCalibratedScaledBounds().getX1() < getMapTile(2, 6).getCalibratedScaledBounds().getX2() && player.getCalibratedScaledBounds().getX2() > getMapTile(2, 6).getCalibratedScaledBounds().getX2()) ||
                        (player.getCalibratedScaledBounds().getX2() > getMapTile(2, 6).getCalibratedScaledBounds().getX() && player.getCalibratedScaledBounds().getX1() < getMapTile(2, 6).getCalibratedScaledBounds().getX1()) ||
                        (player.getCalibratedScaledBounds().getX1() > getMapTile(2, 6).getCalibratedScaledBounds().getX1() && player.getCalibratedScaledBounds().getX2() < getMapTile(2, 6).getCalibratedScaledBounds().getX2());

            }
        }));

    }
}

