package Level;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import SpriteFont.SpriteFont;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// Represents the game's textbox
// will display the text it is given to its textQueue
// each String in the textQueue will be displayed in the textbox, and hitting the interact key will cycle between additional Strings in the queue
// use the newline character in a String in the textQueue to break the text up into a second line if needed
public class Textbox {
    protected boolean isActive;
    protected final int x = 22;
    protected final int bottomY = 460;
    protected final int topY = 22;
    protected final int fontX = 35;
    protected final int fontBottomY = 472;
    protected final int fontTopY = 34;
    protected final int width = 750;
    protected final int height = 100;

    protected final int fontOptionX = 706;
    protected final int fontOptionYStart = 365;
    protected final int fontOptionSpacing = 35;
    protected final int optionPointerX = 690;
    protected final int optionPointerYStart = 378;
    protected int selectedOptionIndex = 0;


    private Queue<TextboxItem> textQueue;
    private TextboxItem currentTextItem;
    private SpriteFont text = null;
    private ArrayList<SpriteFont> options = null;
    private KeyLocker keyLocker = new KeyLocker();
    private Map map;
    private Key interactKey = Key.SPACE;

    public Textbox(Map map) {
        this.map = map;
        this.textQueue = new LinkedList<>();
    }

    public void addText(String text) {
        if (textQueue.isEmpty()) {
            keyLocker.lockKey(interactKey);
        }
        textQueue.add(new TextboxItem(text));
    }

    public void addText(String[] text) {
        if (textQueue.isEmpty()) {
            keyLocker.lockKey(interactKey);
        }
        for (String textItem : text) {
            textQueue.add(new TextboxItem(textItem));
        }
    }

    public void addText(TextboxItem text) {
        if (textQueue.isEmpty()) {
            keyLocker.lockKey(interactKey);
        }
        textQueue.add(text);
    }

    public void addText(TextboxItem[] text) {
        if (textQueue.isEmpty()) {
            keyLocker.lockKey(interactKey);
        }
        for (TextboxItem textItem : text) {
            textQueue.add(textItem);
        }
    }

    // returns whether the textQueue is out of items to display or not
    // useful for scripts to know when to complete
    public boolean isTextQueueEmpty() {
        return textQueue.isEmpty();
    }

    public void update() {
        // if textQueue has more text to display and the interact key button was pressed previously, display new text
        if (!textQueue.isEmpty() && keyLocker.isKeyLocked(interactKey)) {
            currentTextItem = textQueue.peek();
            options = null;

            // if camera is at bottom of screen, text is drawn at top of screen instead of the bottom like usual
            // to prevent it from covering the player
            int fontY;
            if (!map.getCamera().isAtBottomOfMap()) {
                fontY = fontBottomY;
            }
            else {
                fontY = fontTopY;
            }
            text = new SpriteFont(currentTextItem.getText(), fontX, fontY, "Arial", 30, Color.black);
            if (currentTextItem.getOptions() != null && currentTextItem.getOptions().size() > 0) {
                options = new ArrayList<>();
                for (int i = 0; i < currentTextItem.getOptions().size(); i++) {
                    options.add(new SpriteFont(currentTextItem.options.get(i), fontOptionX, fontOptionYStart + (i *  fontOptionSpacing), "Arial", 30, Color.black));
                }
                selectedOptionIndex = 0;
            }

        }
        // if interact key is pressed, remove the current text from the queue to prepare for the next text item to be displayed
        if (Keyboard.isKeyDown(interactKey) && !keyLocker.isKeyLocked(interactKey)) {
            keyLocker.lockKey(interactKey);
            textQueue.poll();
            if (options != null) {
                map.getActiveScript().getScriptActionOutputManager().addFlag("TEXTBOX_SELECTION", currentTextItem.getOptions().get(selectedOptionIndex));
            }
        }
        else if (Keyboard.isKeyUp(interactKey)) {
            keyLocker.unlockKey(interactKey);
        }

        if (options != null) {
            if (Keyboard.isKeyDown(Key.DOWN)) {
                if (selectedOptionIndex < options.size() - 1) {
                    selectedOptionIndex++;
                }
            }
            if (Keyboard.isKeyDown(Key.UP)) {
                if (selectedOptionIndex > 0) {
                    selectedOptionIndex--;
                }
            }
        }

    }

    public void draw(GraphicsHandler graphicsHandler) {
        // if camera is at bottom of screen, textbox is drawn at top of screen instead of the bottom like usual
        // to prevent it from covering the player
        if (!map.getCamera().isAtBottomOfMap()) {
            graphicsHandler.drawFilledRectangleWithBorder(x, bottomY, width, height, Color.white, Color.black, 2);
        }
        else {
            graphicsHandler.drawFilledRectangleWithBorder(x, topY, width, height, Color.white, Color.black, 2);
        }
        if (text != null) {
            text.drawWithParsedNewLines(graphicsHandler, 10);
            if (options != null) {
                graphicsHandler.drawFilledRectangleWithBorder(680, 350, 92, 100, Color.white, Color.black, 2);
                for (SpriteFont option : options) {
                    option.draw(graphicsHandler);
                }
                graphicsHandler.drawFilledRectangle(optionPointerX, optionPointerYStart + (selectedOptionIndex * fontOptionSpacing), 10, 10, Color.black);
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setInteractKey(Key interactKey) {
        this.interactKey = interactKey;
    }

}
