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
    protected int x = 22;
    protected int y = 460;
    protected final int width = 750;
    protected final int height = 100;

    private Queue<String> textQueue = new LinkedList<String>();
    private SpriteFont text = null;
    private KeyLocker keyLocker = new KeyLocker();

    public Textbox() {

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
            text = new SpriteFont(next, 35, 500, "Arial", 30, Color.black);
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
        graphicsHandler.drawFilledRectangleWithBorder(x, y, width, height, Color.white, Color.black, 2);
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
