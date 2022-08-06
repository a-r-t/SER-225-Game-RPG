package Level;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import SpriteFont.SpriteFont;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Textbox {
    protected boolean isActive;
    protected final int x = 22;
    protected final int bottomY = 460;
    protected final int topY = 22;
    protected final int fontX = 35;
    protected final int fontBottomY = 500;
    protected final int fontTopY = 62;
    protected final int width = 750;
    protected final int height = 100;

    private Queue<String> textQueue = new LinkedList<String>();
    private SpriteFont text = null;
    private KeyLocker keyLocker = new KeyLocker();
    private Map map;

    public Textbox(Map map) {
        this.map = map;
    }

    public void addText(String text) {
        if (textQueue.isEmpty()) {
            keyLocker.lockKey(Key.Z);
        }
        textQueue.add(text);
    }

    public boolean isTextQueueEmpty() {
        return textQueue.isEmpty();
    }

    public void update() {
        if (!textQueue.isEmpty() && keyLocker.isKeyLocked(Key.Z)) {
            String next = textQueue.peek();

            int fontY;
            if (map.getCamera().getEndBoundY() < map.getEndBoundY()) {
                fontY = fontBottomY;
            }
            else {
                fontY = fontTopY;
            }
            text = new SpriteFont(next, fontX, fontY, "Arial", 30, Color.black);

        }
        if (Keyboard.isKeyDown(Key.Z) && !keyLocker.isKeyLocked(Key.Z)) {
            keyLocker.lockKey(Key.Z);
            textQueue.poll();
        }
        else if (Keyboard.isKeyUp(Key.Z)) {
            keyLocker.unlockKey(Key.Z);
        }

    }

    public void draw(GraphicsHandler graphicsHandler) {
        if (map.getCamera().getEndBoundY() < map.getEndBoundY()) {
            graphicsHandler.drawFilledRectangleWithBorder(x, bottomY, width, height, Color.white, Color.black, 2);
        }
        else {
            graphicsHandler.drawFilledRectangleWithBorder(x, topY, width, height, Color.white, Color.black, 2);
        }
        if (text != null) {
            text.drawWithParsedNewLines(graphicsHandler, 10);
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
