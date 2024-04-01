package ScriptActions;

import java.util.ArrayList;

import Engine.Key;
import Engine.Keyboard;
import Level.ScriptState;

public class DetectKeyPressAction extends ScriptAction {

    protected ArrayList<Key> keyOptions;
    protected String keyPressedFlagName;

    public DetectKeyPressAction(String keyPressedFlagName) {
        this.keyOptions = new ArrayList<>();
        this.keyPressedFlagName = keyPressedFlagName;
    }

    public DetectKeyPressAction(ArrayList<Key> keyOptions, String keyPressedFlagName) {
        this.keyOptions = keyOptions;
        this.keyPressedFlagName = keyPressedFlagName;
    }

    public void addKeyOption(Key key) {
        keyOptions.add(key);
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
}
