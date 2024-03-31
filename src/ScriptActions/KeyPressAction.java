package ScriptActions;

import java.util.ArrayList;

import Engine.Key;
import Level.ScriptState;

public class KeyPressAction extends ScriptAction {

    protected ArrayList<Key> keyOptions;
    protected KeyListener keyDetector;

    public KeyPressAction() {
        this.keyOptions = new ArrayList<>();
    }

    public KeyPressAction(ArrayList<Key> keyOptions) {
        this.keyOptions = keyOptions;
    }

    public void addKeyOption(Key key) {
        keyOptions.add(key);
    }

    @Override
    public void setup() {

    }

    @Override
    public ScriptState execute() {
        
    }

    @Override
    public void cleanup() {
        
    }
    
}
