package ScriptActions;

import java.util.ArrayList;

import Engine.Key;
import Engine.Keyboard;
import Level.ScriptState;

public class KeyPressAction extends ScriptAction {

    protected ArrayList<Key> keyOptions;
    protected String keyPressedFlagName;

    public KeyPressAction(String keyPressedFlagName) {
        this.keyOptions = new ArrayList<>();
        this.keyPressedFlagName = keyPressedFlagName;
    }

    public KeyPressAction(ArrayList<Key> keyOptions, String keyPressedFlagName) {
        this.keyOptions = keyOptions;
        this.keyPressedFlagName = keyPressedFlagName;
    }

    public void addKeyOption(Key key) {
        keyOptions.add(key);
    }

    @Override
    public void setup() {

    }

    @Override
    public ScriptState execute() {
        for (Key key: this.keyOptions) {
            if (Keyboard.isKeyDown(key)) {
                outputManager.addFlag(keyPressedFlagName, key);
                return ScriptState.COMPLETED;
            }
        }
        return ScriptState.RUNNING;
    }

    @Override
    public void cleanup() {
        
    }
    
}
