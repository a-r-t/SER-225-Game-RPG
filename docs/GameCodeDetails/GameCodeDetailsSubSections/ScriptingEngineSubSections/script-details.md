---
layout: default
title: Script Details
nav_order: 3
parent: Scripting Engine
grand_parent: Game Code Details
permalink: /GameCodeDetails/ScriptingEngine/ScriptDetails
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Script Details

## What scripts are currently in the game?

There are six scripts in the game, each serving a different purpose.
Some are attached to NPCs as interact scripts, some are attached to map tiles as interact scripts, and some are attached to triggers as a trigger script.
The below sections will go over each script in detail.
It is recommended that you play through the game first before trying to understand this page, as it will greatly help to have context.
It is also recommended that you have read through the [script overview](./script-overview.md) and [script actions](./script-actions.md) pages beforehand, as this page assumes that you already understand how scripts work, how they are created, and what script actions are.

All script files are located in the `Scripts` package.

## Simple Text Script

`SimpleTextScript` is a simple script that shows text in a textbox.
It is used for all of the signs in the game -- e.g. the sign outside of the player's house, the sign outside of the walrus's house, etc.
It was designed with the intent that it is used often, as RPGs typically have a lot of quick interaction scripts that just need to show some text in a textbox and that is it.

```java
public class SimpleTextScript extends Script {
    private String[] textItems;

    public SimpleTextScript(String text) {
        this.textItems = new String[] { text };
    }

    public SimpleTextScript(String[] text) {
        this.textItems = text;
    }
    
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new TextboxScriptAction(textItems));
        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
```

There are two constructors for `SimpleTextScript`: one that takes in a string, and one that takes in an array of strings.
No matter which one is chosen, the text arguments are stored in the class instance variable `textItems`.

This script only uses three script actions.

First, it locks the player in place so they cannot move until the script has completed using the `LockPlayerScriptAction`.
Then, it gives the `TextboxScriptAction` the array of text items to show to the player and allow the player to cycle through.
Finally, it unlocks the player so they can move again and continue playing the game using the `UnlockPlayerScriptAction`.

This script is attached to several map tiles in the game (all of the signs outside of the houses).
You can see how this is done in the `TestMap` class in the `Maps` package:

```java
@Override
public void loadScripts() {
    // attach simple text script to the map tile at x: 21 and y: 19 in the map
    getMapTile(21, 19).setInteractScript(new SimpleTextScript("Cat's house"));

    // attach simple text script to the map tile at x: 7 and y: 26 in the map
    getMapTile(7, 26).setInteractScript(new SimpleTextScript("Walrus's house"));

    // attach simple text script to the map tile at x: 20 and y: 4 in the map
    getMapTile(20, 4).setInteractScript(new SimpleTextScript("Dino's house"));
}
```

## Lost Ball Script

`LostBallScript` is attached to all three [triggers](../MapSubSections/triggers.md) laid out around the starting house of the game, in an orientation that forces the player to walk over one of them and kick off the script.

The code looks like this:

{% raw %}
```java

public class LostBallScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());

        scriptActions.add(new TextboxScriptAction() {{
            addText("Where did my ball go!?");
            addText("I left it right here before I took my 22 hour cat nap.");
            addText("Maybe Walrus has seen it.");
        }});

        scriptActions.add(new ChangeFlagScriptAction("hasLostBall", true));

        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}
```
{% endraw %}

This script is pretty simple and straightforward, as it's very similar to the `SimpleTextScript`.
Like `SimpleTextScript`, it starts by locking the player using the `LockPlayerScriptAction`, and then displays three different segments of text in the textbox using the `TextboxScriptAction`.
Before it unlocks the player using the `UnlockPlayerScriptAction`, it first sets the flag `hasLostBall` using the `ChangeFlagScriptAction`.
It sets this flag so that the rest of the game knows where the player is currently at in the "sequence" of events.
It allows for other scripts to check what actions the player has already done/the state of the game and make choices based on that.

This script is attached to all three trigger instances that surround the starting area by the player's house.
You can see how this is done in the `TestMap` class in the `Maps` package (the script is passed into each Trigger's constructor):

```java
@Override
public ArrayList<Trigger> loadTriggers() {
    ArrayList<Trigger> triggers = new ArrayList<>();

    triggers.add(new Trigger(790, 1030, 100, 10, new LostBallScript(), "hasLostBall"));

    triggers.add(new Trigger(790, 960, 10, 80, new LostBallScript(), "hasLostBall"));

    triggers.add(new Trigger(890, 960, 10, 80, new LostBallScript(), "hasLostBall"));

    return triggers;
}
```

## Walrus Script

`WalrusScript` is attached to the walrus NPC, and executes when interacting with him.

The code looks like this:

{% raw %}
```java
// checkout the documentation website for a detailed guide on how the scripting system works
public class WalrusScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());

        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToWalrus", false));
                addScriptAction(new TextboxScriptAction() {{
                    addText("Hi Cat!");
                    addText("...oh, you lost your ball?");
                    addText("Hmmm...my walrus brain remembers seeing Dino with\nit last. Maybe you can check with him?");
                }});
                addScriptAction(new ChangeFlagScriptAction("hasTalkedToWalrus", true));
            }});

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToWalrus", true));
                addScriptAction(new TextboxScriptAction("I sure love doing walrus things!"));
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}
```
{% endraw %}

Outside of the conditional block, this script is very simple and similar to `SimpleTextScript`.

It starts by locking the player using the `LockPlayerScriptAction` and then telling the walrus NPC to face the player using the `NPCFacePlayerScriptAction`.

There is then a conditional block that looks to see if the flag `hasTalkedToWalrus` is set or not.
When speaking to the walrus the first time, he tells the cat that he remembers the dinosaur had it last.
However, when speaking to him subsequent times, he just says "I sure love doing walrus things!" (ugh I love his life outlook so much).

The first `ConditionalScriptActionGroup` executes when speaking with the walrus the first time, since the flag `hasTalkedToWalrus` has not yet been set.
Inside that condtional group after it has finished, it uses the `ChangeFlagScriptAction` to set `hasTalkedToWalrus` to let the rest of the game know that the walrus has been spoken to.

The second `ConditionalScriptActionGroup` executes when speaking with the walrus after the first time, since the flag `hasTalkedToWalrus` is set.

And as usual, the script ends off by unlocking the player using the `UnlockPlayerScriptAction`.

This script is attached to the walrus NPC as an interact script in the `TestMap` class with the following code:

```java
@Override
public ArrayList<NPC> loadNPCs() {
    ArrayList<NPC> npcs = new ArrayList<>();

    Walrus walrus = new Walrus(1, getMapTile(4, 28).getLocation().subtractY(40));

    // sets WalrusScript as the walrus NPCs interact script
    walrus.setInteractScript(new WalrusScript());
    
    npcs.add(walrus);

    // ...

    return npcs;
}
```

