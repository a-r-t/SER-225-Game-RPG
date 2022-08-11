package Level;

import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Utils.Direction;
import GameObject.Rectangle;
import java.util.ArrayList;

public abstract class Player extends GameObject {
    // values that affect player movement
    // these should be set in a subclass
    protected float walkSpeed = 0;
    protected int interactionRange = 5;
    protected Direction currentWalkingXDirection;
    protected Direction currentWalkingYDirection;

    // values used to handle player movement
    protected float moveAmountX, moveAmountY;

    // values used to keep track of player's current state
    protected PlayerState playerState;
    protected PlayerState previousPlayerState;
    protected Direction facingDirection;
    protected LevelState levelState;

    // classes that listen to player events can be added to this list
    protected ArrayList<PlayerListener> listeners = new ArrayList<>();

    // define keys
    protected KeyLocker keyLocker = new KeyLocker();
    protected Key MOVE_LEFT_KEY = Key.LEFT;
    protected Key MOVE_RIGHT_KEY = Key.RIGHT;
    protected Key MOVE_UP_KEY = Key.UP;
    protected Key MOVE_DOWN_KEY = Key.DOWN;
    protected Key INTERACT_KEY = Key.Z;

    public Player(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
        super(spriteSheet, x, y, startingAnimationName);
        facingDirection = Direction.RIGHT;
        playerState = PlayerState.STANDING;
        previousPlayerState = playerState;
        levelState = LevelState.RUNNING;
        this.affectedByTriggers = true;
    }

    public void update() {
        moveAmountX = 0;
        moveAmountY = 0;

        // if player is currently playing through level (has not won or lost)
        if (levelState == LevelState.RUNNING) {
            // update player's state and current actions, which includes things like determining how much it should move each frame and if its walking or jumping
            do {
                previousPlayerState = playerState;
                handlePlayerState();
            } while (previousPlayerState != playerState);

            // update player's animation
            super.update();

            // move player with respect to map collisions based on how much player needs to move this frame
            if (playerState != PlayerState.INTERACTING) {
                super.moveYHandleCollision(moveAmountY);
                super.moveXHandleCollision(moveAmountX);
                applySpecialEffects();
            }
            updateLockedKeys();
        }

        // if player has beaten level
        else if (levelState == LevelState.LEVEL_COMPLETED) {
            updateLevelCompleted();
        }

        // if player has lost level
        else if (levelState == LevelState.PLAYER_DEAD) {
            updatePlayerDead();
        }
    }

    // based on player's current state, call appropriate player state handling method
    protected void handlePlayerState() {
        switch (playerState) {
            case STANDING:
                playerStanding();
                break;
            case WALKING:
                playerWalking();
                break;
            case INTERACTING:
                playerInteracting();
                break;
        }
    }

    // player STANDING state logic
    protected void playerStanding() {
        // sets animation to a STAND animation based on which way player is facing
        currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";

        if (!keyLocker.isKeyLocked(INTERACT_KEY) && Keyboard.isKeyDown(INTERACT_KEY)) {
            System.out.println("INTERACT ACTIVATED");
            keyLocker.lockKey(INTERACT_KEY);
            map.onInteract(this);
        }

        // if walk left or walk right key is pressed, player enters WALKING state
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY) || Keyboard.isKeyDown(MOVE_RIGHT_KEY) || Keyboard.isKeyDown(MOVE_UP_KEY) || Keyboard.isKeyDown(MOVE_DOWN_KEY)) {
            playerState = PlayerState.WALKING;
        }
    }

    // player WALKING state logic
    protected void playerWalking() {
        // sets animation to a WALK animation based on which way player is facing
        currentAnimationName = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";

        if (!keyLocker.isKeyLocked(INTERACT_KEY) && Keyboard.isKeyDown(INTERACT_KEY)) {
            System.out.println("INTERACT ACTIVATED");
            keyLocker.lockKey(INTERACT_KEY);
            map.onInteract(this);
        }

        // if walk left key is pressed, move player to the left
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY)) {
            moveAmountX -= walkSpeed;
            facingDirection = Direction.LEFT;
            currentWalkingXDirection = Direction.LEFT;
        }

        // if walk right key is pressed, move player to the right
        else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
            moveAmountX += walkSpeed;
            facingDirection = Direction.RIGHT;
            currentWalkingXDirection = Direction.RIGHT;
        }
        else {
            currentWalkingXDirection = Direction.NONE;
        }

        if (Keyboard.isKeyDown(MOVE_UP_KEY)) {
            moveAmountY -= walkSpeed;
            currentWalkingYDirection = Direction.UP;
        }
        else if (Keyboard.isKeyDown(MOVE_DOWN_KEY)) {
            moveAmountY += walkSpeed;
            currentWalkingYDirection = Direction.DOWN;
        }
        else {
            currentWalkingYDirection = Direction.NONE;
        }

        if (Keyboard.isKeyUp(MOVE_LEFT_KEY) && Keyboard.isKeyUp(MOVE_RIGHT_KEY) && Keyboard.isKeyUp(MOVE_UP_KEY) && Keyboard.isKeyUp(MOVE_DOWN_KEY)) {
            playerState = PlayerState.STANDING;
        }
    }

    // player INTERACTING state logic
    protected void playerInteracting() {
        // sets animation to a STAND animation based on which way player is facing

    }

    protected void updateLockedKeys() {
        if (Keyboard.isKeyUp(INTERACT_KEY) && playerState != PlayerState.INTERACTING) {
            keyLocker.unlockKey(INTERACT_KEY);
        }
    }

    // anything extra the player should do based on interactions can be handled here
    protected void applySpecialEffects() {

    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {

    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {

    }

    // other entities can call this method to hurt the player
    public void hurtPlayer(MapEntity mapEntity) {

    }

    // other entities can call this to tell the player they beat a level
    public void completeLevel() {
        levelState = LevelState.LEVEL_COMPLETED;
    }

    // if player has beaten level, this will be the update cycle
    public void updateLevelCompleted() {
        // if player is not on ground, player should fall until it touches the ground

    }

    // if player has died, this will be the update cycle
    public void updatePlayerDead() {

    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public void setLevelState(LevelState levelState) {
        this.levelState = levelState;
    }

    public void addListener(PlayerListener listener) {
        listeners.add(listener);
    }

    public Rectangle getInteractionRange() {
        return new Rectangle(
                getScaledBounds().getX1() - interactionRange,
                getScaledBounds().getY1() - interactionRange,
                getScaledBounds().getWidth() + (interactionRange * 2),
                getScaledBounds().getHeight() + (interactionRange * 2));
    }

    public Direction getCurrentWalkingXDirection() { return currentWalkingXDirection; }
    public Direction getCurrentWalkingYDirection() { return currentWalkingYDirection; }
}
