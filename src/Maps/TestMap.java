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

        npcs.get(0).setScript(new Script(new Event(EventType.INTERACT) {
            private boolean start = true;

            @Override
            public ScriptState execute(Player player, Map map) {
                if (start) {
                    start = false;
                    map.getTextbox().setIsActive(true);
                    player.setPlayerState(PlayerState.INTERACTING);
                    if (!map.getFlagManager().isFlagSet("hasTalkedToWalrus")) {
                        map.getTextbox().addText("Hi Cat!");
                        map.getTextbox().addText("...oh, you lost your ball?");
                        map.getTextbox().addText("Hmmm...my walrus brain remembers seeing Dino with\nit last. Maybe you can check with him?");
                    }
                    else {
                        map.getTextbox().addText("I sure love doing walrus things!");
                    }
                    getNPCs().stream().filter(npc -> npc.getId() == 1).findFirst().get().facePlayer(player);
                }
                if (!map.getTextbox().isTextQueueEmpty()) {
                    return ScriptState.RUNNING;
                }
                map.getFlagManager().setFlag("hasTalkedToWalrus");
                start = true;
                player.setPlayerState(PlayerState.STANDING);
                map.getTextbox().setIsActive(false);
                return ScriptState.COMPLETED;
            }
        }));
        /*
        npcs.get(0).setScript(new Script(new Event() {
            private boolean start = true;

            @Override
            public ScriptState execute(Player player, Map map) {
                if (start) {
                    start = false;
                    map.getTextbox().setIsActive(true);
                    player.setPlayerState(PlayerState.INTERACTING);
                    if (!map.getFlagManager().isFlagSet("hasTalkedToWalrus")) {
                        map.getTextbox().addText("Hi Cat!");
                        map.getTextbox().addText("...oh, you lost your ball?");
                        map.getTextbox().addText("Hmmm...my walrus brain remembers seeing Dino with\nit last. Maybe you can check with him?");
                    }
                    else {
                        map.getTextbox().addText("I sure love doing walrus things!");
                    }
                    getNPCs().stream().filter(npc -> npc.getId() == 1).findFirst().get().facePlayer(player);
                }
                if (!map.getTextbox().isTextQueueEmpty()) {
                    return ScriptState.RUNNING;
                }
                map.getFlagManager().setFlag("hasTalkedToWalrus");
                start = true;
                player.setPlayerState(PlayerState.STANDING);
                map.getTextbox().setIsActive(false);
                return ScriptState.COMPLETED;
            }
        }));
*/
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