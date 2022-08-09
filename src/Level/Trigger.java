package Level;

import Engine.GraphicsHandler;
import GameObject.Rectangle;

public class Trigger extends MapEntity {
    protected Script triggerScript;

    public Trigger(int x, int y, int width, int height, Script triggerScript) {
        super(x, y);
        this.triggerScript = triggerScript;
        this.setBounds(new Rectangle(x, y, width, height));
    }

    public Trigger(float x, float y, int width, int height) {
        super(x, y);
        this.triggerScript = loadTriggerScript();
        this.setBounds(new Rectangle(x, y, width, height));
    }

    protected Script loadTriggerScript() { return null; }

    public Script getTriggerScript() { return triggerScript; }
    public void setTriggerScript(Script triggerScript) {
        this.triggerScript = triggerScript;
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {}
}
