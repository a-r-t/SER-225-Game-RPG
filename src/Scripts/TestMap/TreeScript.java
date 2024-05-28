package Scripts.TestMap;

import java.util.ArrayList;

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
                        if (player.getBounds().getY1() <= entity.getBounds().getY2()) {
                            return false;
                        }

                        // if player is coming in from the right, ensure at least 20 pixels of the player is past the right edge of the tree trunk
                        // this forces the player to move the boulder out of the way more in order to successfully interact with the tree trunk
                        // without this, the boulder could be pushed out of the way only one pixel and the tree trunk could be talked to, which just doesn't look right
                        if (player.getBounds().getX() < entity.getBounds().getX2() && entity.getBounds().getX2() - player.getBounds().getX() < Math.min(player.getBounds().getWidth(), 20)) {
                            return false;
                        }

                        // if player is coming in from the left, ensure at least 20 pixels of the player is past the left edge of the tree trunk
                        // this forces the player to move the boulder out of the way more in order to successfully interact with the tree trunk
                        // without this, the boulder could be pushed out of the way only one pixel and the tree trunk could be talked to, which just doesn't look right
                        if (player.getBounds().getX2() > entity.getBounds().getX() && player.getBounds().getX2() - entity.getBounds().getX() < Math.min(player.getBounds().getWidth(), 20)) {
                            return false;
                        }

                        // if all the above guard clauses pass, the player is in the right spot location wise to interact with the tree trunk tile
                        return true;
                    }
                });

                addScriptAction(new TextboxScriptAction() {{
                    addText("...");
                    addText("I found my ball inside of the tree!\nYippee!");
                }});

                addScriptAction(new ChangeFlagScriptAction("hasFoundBall", true));
            }});


        }});
       
        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}

