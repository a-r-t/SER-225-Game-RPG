package ScriptActions;

import java.util.HashMap;

import Engine.Key;

public class ScriptActionOutputManager {
    protected HashMap<String, ScriptActionOutput> output = new HashMap<>();

    public void addFlag(String flagName, String data) {
        output.put(flagName, new ScriptActionOutput(data));
    }

    public void addFlag(String flagName, Integer data) {
        output.put(flagName, new ScriptActionOutput(data));
    }

    public void addFlag(String flagName, Boolean data) {
        output.put(flagName, new ScriptActionOutput(data));
    }

    public void addFlag(String flagName, Key data) {
        output.put(flagName, new ScriptActionOutput(data));
    }

    public void addFlag(String flagName, Object data) {
        output.put(flagName, new ScriptActionOutput(data));
    }

    public <T> T getFlagData(String flagName) {
        return (T)output.get(flagName).getData();
    }
}
