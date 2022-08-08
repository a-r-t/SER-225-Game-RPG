package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.PlayerState;
import Level.TileType;
import Utils.Direction;
import Utils.Point;

import java.util.HashMap;

// This class is for the end level gold box tile
// when the player touches it, it will tell the player that the level has been completed
public class Rock extends EnhancedMapTile {
    public Rock(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Rock.png"), 16, 16), TileType.NOT_PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);
        if (player.overlaps(this) && player.getPlayerState() == PlayerState.WALKING) {
            if (player.getCurrentWalkingXDirection() == Direction.LEFT) {
                if (canMoveLeft(player)) {
                    moveXHandleCollision(-1);
                }
            }
            else if (player.getCurrentWalkingXDirection() == Direction.RIGHT) {
                if (canMoveRight(player)) {
                    moveXHandleCollision(1);
                }
            }
             if (player.getCurrentWalkingYDirection() == Direction.UP) {
                if (canMoveUp(player)) {
                    moveYHandleCollision(-1);
                }
            }
            else if (player.getCurrentWalkingYDirection() == Direction.DOWN) {
                if (canMoveDown(player)) {
                    moveYHandleCollision(1);
                }
            }
        }
    }

    private boolean canMoveLeft(Player player) {
        return player.getScaledBoundsX1() <= getScaledBoundsX2() && player.getScaledBoundsX2() > getScaledBoundsX2() && canMoveX(player);
    }

    private boolean canMoveRight(Player player) {
        return player.getScaledBoundsX2() >= getScaledBoundsX1() && player.getScaledBoundsX1() < getScaledBoundsX1() && canMoveX(player);
    }

    private boolean canMoveX(Player player) {
        return (player.getScaledBoundsY1() < getScaledBoundsY2() && player.getScaledBoundsY2() >= getScaledBoundsY2()) ||
                (player.getScaledBoundsY2() > getScaledBoundsY1() && player.getScaledBoundsY1() <= getScaledBoundsY1()) ||
                (player.getScaledBoundsY2() < getScaledBoundsY2() && player.getScaledBoundsY1() > getScaledBoundsY1());
    }

    private boolean canMoveUp(Player player) {
        return player.getScaledBoundsY1() <= getScaledBoundsY2() && player.getScaledBoundsY2() > getScaledBoundsY2() && canMoveY(player);
    }

    private boolean canMoveDown(Player player) {
        return player.getScaledBoundsY2() >= getScaledBoundsY1() && player.getScaledBoundsY1() < getScaledBoundsY1() && canMoveY(player);
    }

    private boolean canMoveY(Player player) {
        return (player.getScaledBoundsX1() < getScaledBoundsX2() && player.getScaledBoundsX2() >= getScaledBoundsX2()) ||
                (player.getScaledBoundsX2() > getScaledBoundsX1() && player.getScaledBoundsX1() <= getScaledBoundsX1()) ||
                (player.getScaledBoundsX2() < getScaledBoundsX2() && player.getScaledBoundsX1() > getScaledBoundsX1());
    }

    @Override
    protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
        return new GameObject(spriteSheet.getSubImage(0, 0), x, y, 3);
    }
}
