package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Point;

import java.util.HashMap;

// This class is for the end level gold box tile
// when the player touches it, it will tell the player that the level has been completed
public class Rock extends EnhancedMapTile {
    public Rock(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("CommonTileset.png"), 16, 16), TileType.NOT_PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);
        if (player.overlaps(this)) {
            if (player.getScaledBoundsX1() <= getScaledBoundsX2()) {
                moveLeft(1);
            }
        }
    }

    @Override
    protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
        HashMap<String, Frame[]> animations = new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(3, 1), 0)
                            .withScale(3)
                            .build()
            });
        }};
        return new GameObject(x, y, animations, "DEFAULT");
    }
}
