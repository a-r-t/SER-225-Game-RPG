package Scripts.TestMap;

import java.util.ArrayList;

import Level.GameListener;
import Level.Script;
import Level.ScriptState;
import ScriptActions.CustomRequirement;
import ScriptActions.ChangeFlagScriptAction;
import ScriptActions.ConditionalScriptAction;
import ScriptActions.ConditionalScriptActionGroup;
import ScriptActions.FlagRequirement;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;

// script for talking to tree with hole in it
// checkout the documentation website for a detailed guide on how this script works
public class TreeScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        
        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToDinosaur", true));
                addRequirement(new FlagRequirement("hasFoundBall", false));
                addRequirement(new CustomRequirement() {

                    @Override
                    public boolean isRequirementMet() {
                        // ensures player is directly underneath tree trunk tile
                        // this prevents the script from working if the player tries to interact with it from the side

                        // if player is not below tree trunk tile, player location is not valid and this conditional script will not be reached
                        if (player.getBounds().getY1() <= entity.getBounds().getY2()) {
                            return false;
                        }

                        // if code gets here, it means player is below tree trunk tile and player location is valid, so this conditional script will continue
                        return true;
                    }
                });

                addScriptAction(new TextboxScriptAction() {{
                    addText("...");
                    addText("I found my ball inside of the tree!\nYippee!");
                }});

                addScriptAction(new ChangeFlagScriptAction("hasFoundBall", true));

                // alert all listeners (which includes play level screen) that the game has been won
                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        for (GameListener listener: listeners) {
                            listener.onWin();
                        }
                        return ScriptState.COMPLETED;
                    }
                });
            }});
        }});
       
        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}

