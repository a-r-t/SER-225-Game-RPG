package Maps;

import Engine.GraphicsHandler;
import Level.Map;
import Level.PlayerState;
import Players.Cat;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;

// Represents the map that is used as a background for the main menu and credits menu screen
public class TitleScreenMap extends Map {

    private Cat cat;

    public TitleScreenMap() {
        super("title_screen_map.txt", new CommonTileset());
        Point tileLocation = getMapTile(8, 5).getLocation().subtractX(6).subtractY(7);
        cat = new Cat(tileLocation.x, tileLocation.y);
        cat.setPlayerState(PlayerState.STANDING);
        cat.stand(Direction.LEFT);
        cat.update();
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        cat.draw(graphicsHandler);
    }
}
