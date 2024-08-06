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
It is also recommended that you have read through the [script overview](./script-overview.md) and [script actions](./script-actions.md) pages beforehand, as this page assumes that you already understand how scripts work, how they are created, how script actions work, and which script actions are currently included in the game and what they do.

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
The three triggers that this script is attached to also have their existence flag set to `hasLostBall`, meaning that once the flag is set, all three triggers will disappear from the map.
This ensures the trigger scripts don't get activated again when the player walks back over that spot.
Since all three triggers share the same existence flag, setting the `hasLostBall` flag removes all three.

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

    // sets WalrusScript as the walrus NPC's interact script
    walrus.setInteractScript(new WalrusScript());
    
    npcs.add(walrus);

    // ...

    return npcs;
}
```

## Dino Script

`DinoScript` is attached to the dinosaur NPC, and executes when interacting with him.

This is the longest script in the game and possibly the most complex, but the good news is that if you can understand it, you will likely be able to write any script that you want for this game.

The code looks like this:

{% raw %}
```java
public class DinoScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {

        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new TextboxScriptAction("Isn't my garden so lovely?"));

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToWalrus", true));
                addRequirement(new FlagRequirement("hasTalkedToDinosaur", false));

                addScriptAction(new WaitScriptAction(70));
                addScriptAction(new NPCFacePlayerScriptAction());
                addScriptAction(new TextboxScriptAction () {{
                    addText("Oh, you're still here...");
                    addText("...You heard from Walrus that he saw me with your\nball?");
                    addText("Well, I saw him playing with it and was worried it would\nroll into my garden.");
                    addText("So I kicked it as far as I could into the forest to the left.");
                    addText("Now, if you'll excuse me, I have to go.");
                }});
                addScriptAction(new NPCStandScriptAction(Direction.RIGHT));

                addScriptAction(new NPCWalkScriptAction(Direction.DOWN, 36, 2));
                addScriptAction(new NPCWalkScriptAction(Direction.RIGHT, 196, 2));

                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        // change door to the open door map tile
                        Frame openDoorFrame = new FrameBuilder(map.getTileset().getSubImage(4, 4), 0)
                            .withScale(map.getTileset().getTileScale())
                            .build();

                        Point location = map.getMapTile(17, 4).getLocation();

                        MapTile mapTile = new MapTileBuilder(openDoorFrame)
                            .build(location.x, location.y);

                        map.setMapTile(17, 4, mapTile);
                        return ScriptState.COMPLETED;
                    }
                });

                addScriptAction(new NPCWalkScriptAction(Direction.UP, 50, 2));
                addScriptAction(new NPCChangeVisibilityScriptAction(Visibility.HIDDEN));

                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        // change door back to the closed door map tile
                        Frame doorFrame = new FrameBuilder(map.getTileset().getSubImage(4, 3), 0)
                            .withScale(map.getTileset().getTileScale())
                            .build();
                        Point location = map.getMapTile(17, 4).getLocation();

                        MapTile mapTile = new MapTileBuilder(doorFrame)
                            .withTileType(TileType.NOT_PASSABLE)
                            .build(location.x, location.y);

                        map.setMapTile(17, 4, mapTile);
                        return ScriptState.COMPLETED;
                    }
                });

                addScriptAction(new ChangeFlagScriptAction("hasTalkedToDinosaur", true));
            }});
        }});


        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
```
{% endraw %}

Let's break this code down.

To start, the first two actions are pretty standard: first it locks the player using the `LockPlayerScriptAction`, and then it shows the message "Isn't my garden so lovely?" in the textbox.

This script is setup so that the dinosaur will ONLY run this part of the script if the walrus has not yet been talked to.
There is a conditional block next that checks if the `hasTalkedToWalrus` flag has been set, and if so, executes the rest of the script actions.

{% raw %}
```java
addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
    addRequirement(new FlagRequirement("hasTalkedToWalrus", true));
    addRequirement(new FlagRequirement("hasTalkedToDinosaur", false));

    // ...
}});
```
{% endraw %}

This conditional has only the one `ConditionalScriptActionGroup` that checks the status of two flags to ensure the game state is in the right spot before continuing.

The first few actions of the conditional are pretty straightforward:

{% raw %}
```java
addScriptAction(new WaitScriptAction(70));
addScriptAction(new NPCFacePlayerScriptAction());
addScriptAction(new TextboxScriptAction () {{
    addText("Oh, you're still here...");
    addText("...You heard from Walrus that he saw me with your\nball?");
    addText("Well, I saw him playing with it and was worried it would\nroll into my garden.");
    addText("So I kicked it as far as I could into the forest to the left.");
    addText("Now, if you'll excuse me, I have to go.");
}});
```
{% endraw %}

First, the `WaitScriptAction` is used to pause the script for seventy frames.
This is what causes the dinosaur to wait a smidge after being spoken to before eventually facing the player and showing text in the textbox (using the `NPCFacePlayerScriptAction` and `TextboxScriptAction` respectively).

After the text ends, the final instructions of this script tell the dinosaur to turn to the right, walk a bit downwards, walk a bit to the right, and walk upwarads through the door of the house.

The below script actions handle the dinosaur turning to the right, walking a bit downards, and walking a bit to the right.
The `NPCStandScriptAction` is used to tell the dinosaur to face to the right.
The first `NPCWalkScriptAction` tells the dinosaur to walk downwards 36 pixels at a walk speed of 2.
The second `NPCWalkScriptAction` tells the dinosaur to walk to the right 196 pixels at a speed of 2. 

```java
addScriptAction(new NPCStandScriptAction(Direction.RIGHT));
addScriptAction(new NPCWalkScriptAction(Direction.DOWN, 36, 2));
addScriptAction(new NPCWalkScriptAction(Direction.RIGHT, 196, 2));
```

Now, before the dinosaur actually walks upwards into the door, there is some logic that makes the door appear to open (and later close after the dinosaur has walked through it).
For this to work, the map tile of the door is actually changed from the closed door map tile to an open door map tile.
Below is the anonymous script action used to change the closed door map tile to an open door tile.
There isn't a script action that handles this, which is why it had to be done in an adhoc "custom" way here.
It isn't super pretty code but sometimes you juust have to do what you have to do to make something happen you know?

```java
addScriptAction(new ScriptAction() {
    @Override
    public ScriptState execute() {
        // create a new Frame of the door opened image
        Frame openDoorFrame = new FrameBuilder(map.getTileset().getSubImage(4, 4), 0)
            .withScale(map.getTileset().getTileScale())
            .build();

        // get the location on the map where map tile x: 17, y: 4 is (which is where the closed door tile is located)
        Point location = map.getMapTile(17, 4).getLocation();

        // create a new map tile for the open door and set its location to the same location as the closed door tile
        MapTile mapTile = new MapTileBuilder(openDoorFrame)
            .build(location.x, location.y);

        // replace closed door map tile with open door map tile
        map.setMapTile(17, 4, mapTile);

        return ScriptState.COMPLETED;
    }
});
```

After that monstrousity, the next two instructions are pretty straightforward: one moves the dinosaur upward using another `NPCScriptWalkAction`, and the next hides the dinosaur (makes it invisible) using an `NPCChangeVisibilityScriptAction`:

```java
addScriptAction(new NPCWalkScriptAction(Direction.UP, 50, 2));
addScriptAction(new NPCChangeVisibilityScriptAction(Visibility.HIDDEN));
```

Then very similar to the above anonymous script action that was used to change the map tile from a closed door to an open door map tile, the next instruction does the same exact thing but changes it back from an open door map tile to a closed door map tile.
This sequence makes it appear that the dinosaur opened the door, walked inside, and closed the door:

```java
addScriptAction(new ScriptAction() {
    @Override
    public ScriptState execute() {
        // create a new Frame of the door closed image
        Frame doorFrame = new FrameBuilder(map.getTileset().getSubImage(4, 3), 0)
            .withScale(map.getTileset().getTileScale())
            .build();

        // get the location on the map where map tile x: 17, y: 4 is (which is where the open door tile is currently located)
        Point location = map.getMapTile(17, 4).getLocation();

        // create a new map tile for the closed door and set its location to the same location as the open door tile
        MapTile mapTile = new MapTileBuilder(doorFrame)
            .withTileType(TileType.NOT_PASSABLE)
            .build(location.x, location.y);

        // replace open door map tile with closed door map tile
        map.setMapTile(17, 4, mapTile);

        return ScriptState.COMPLETED;
    }
});
```

AND FINALLY, the last instruction uses a `ChangeFlagScriptAction` to set the flag `hasTalkedToDinosaur`.
The dinosaur NPC's existence flag is `hasTalkedToDinosaur`, so once this is set the dinosaur NPC is completely gone from the map and cannot be interacted with again.

```java
addScriptAction(new ChangeFlagScriptAction("hasTalkedToDinosaur", true));
```

This script is attached to the dinosaur NPC as an interact script in the `TestMap` class with the following code:

```java
@Override
public ArrayList<NPC> loadNPCs() {
    ArrayList<NPC> npcs = new ArrayList<>();

    Dinosaur dinosaur = new Dinosaur(2, getMapTile(13, 4).getLocation());

    dinosaur.setExistenceFlag("hasTalkedToDinosaur");

    // sets DinoScript as the dinosaur NPC's interact script
    dinosaur.setInteractScript(new DinoScript());

    npcs.add(dinosaur);

    // ...

    return npcs;
}
```