package Level;

import java.util.HashMap;
import java.util.Map.Entry;

public class FlagManager {
    protected HashMap<String, Boolean> flags = new HashMap<>();

    public FlagManager() {
        loadFlags();
    }

    private void loadFlags() {
        flags.put("hasTalkedToWalrus", false);
        flags.put("hasTalkedToDinosaur", false);
    }

    public void setFlag(String flagName) {
        if (flags.containsKey(flagName)) {
            flags.put(flagName, true);
        }
    }

    public void unsetFlag(String flagName) {
        if (flags.containsKey(flagName)) {
            flags.put(flagName, false);
        }
    }

    public void reset() {
        for (Entry<String, Boolean> entry : flags.entrySet()) {
            entry.setValue(false);
        }
    }

    public boolean isFlagSet(String flagName) {
        if (flags.containsKey(flagName)) {
            return flags.get(flagName);
        }
        return false;
    }
}
