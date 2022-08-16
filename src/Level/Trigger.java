package Level;

import Engine.GraphicsHandler;
import GameObject.Rectangle;

import java.awt.*;

public class Trigger extends MapEntity {
    protected Script triggerScript;

    public Trigger(int x, int y, int width, int height, Script triggerScript) {
        super(x, y);
        this.triggerScript = triggerScript;
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setBounds(new Rectangle(0, 0, width, height));
    }

    public Trigger(int x, int y, int width, int height, Script triggerScript, String existenceFlag) {
        super(x, y);
        this.triggerScript = triggerScript;
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setBounds(new Rectangle(0, 0, width, height));
        this.existenceFlag = existenceFlag;
    }

    protected Script loadTriggerScript() { return null; }

    public Script getTriggerScript() { return triggerScript; }
    public void setTriggerScript(Script triggerScript) {
        this.triggerScript = triggerScript;
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        drawBounds(graphicsHandler, Color.red);
    }

    public void draw(GraphicsHandler graphicsHandler, Color color) {
        Rectangle scaledCalibratedBounds = getCalibratedScaledBounds();
        scaledCalibratedBounds.setColor(color);
        scaledCalibratedBounds.setBorderColor(Color.black);
        scaledCalibratedBounds.setBorderThickness(1);
        scaledCalibratedBounds.draw(graphicsHandler);
    }
}
