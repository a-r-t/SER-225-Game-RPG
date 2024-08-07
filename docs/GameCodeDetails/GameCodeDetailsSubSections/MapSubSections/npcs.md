---
layout: default
title: NPCs
nav_order: 6
parent: Map
grand_parent: Game Code Details
permalink: /GameCodeDetails/Map/NPCs
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# NPCs

## What is an NPC?

An NPC (**n**on-**p**layer **c**haracter) (represented by the `NPC` class in the `Level` package) is a `MapEntity` subclass. 
An NPC can be given its own graphics/animations, as well as its own `update` cycle which defines its behavior.

NPCs in a game tend to act as a neutral character, meaning they usually do not harm the player.
They are typically designed to supplement the traversal through a level, as well as improve overall game immersion.

An NPC is typically given an `interactScript` which will execute when the player interacts with them.
Currently, this can be seen with both the walrus and dinosaur NPCs in the game. 
Read more about scripts [here](./script-details.md).

Creating interact scripts for NPCs is one of the main ways this game functions and carries out the story.
An NPC can be made to do any desired behavior, and is not just limited to interact scripts, but it is up to the coder's skills to achieve their desired behavior!

## NPC Subclass

In the `NPCs` package, there are currently only two subclasses of the `NPC` class -- `Walrus` and `Dinosaur`.
Both NPCs can be seen in the `TestMap` map.

## Adding a new NPC to the game

This is simple -- create a new class in the `NPCs` package, subclass the `NPC` class, and then just implement desired logic from there.
I recommend copying an existing NPC class as a "template" of sorts to help set up and design the NPC.

## Adding an NPC to a map

In a map subclass's `loadNPCs` method, NPCs can be defined and added to the map's NPC list. 
For example, in `TestMap`, a `Walrus` class instance is created added to the NPC list:

```java
@Override
public ArrayList<NPC> loadNPCs() {
    ArrayList<NPC> npcs = new ArrayList<>();

    Walrus walrus = new Walrus(1, getMapTile(4, 28).getLocation().subtractY(40));
    walrus.setInteractScript(new WalrusScript());
    npcs.add(walrus);
    
    // ...
}
```

## NPCs currently in game

### Walrus

![walrus.png](../../../assets/images/walrus.png)

This NPC is defined by the `Walrus` class. 
In addition to being the best made piece of art you have ever laid your eyes on,
the walrus is able to be interacted with. 
Its `interactScript` is defined in the `WalrusScript` class (located in the `Scripts.TestMap` package).
Read more about scripts [here](./script-details.md).

The walrus doesn't do much except wait around to be talked to.

The image file for the walrus is `Walrus.png`.

### Dinosaur

![dinosaur.png](../../../assets/images/dinosaur.png)

This NPC is defined by the `Dinosaur` class. 
The dinosaur is able to be interacted with.
Its `interactScript` is defined in the `DinoScript` class (located in the `Scripts.TestMap` package).
The `DinoScript` is the most complex script in the game. 
Understanding it is the key to mastering the scripting system and being able to create anything you want in this type of game. 
Read more about scripts [here](./script-details.md).

The dinosaur also has a walking animation, which is used during its `DinoScript` to force it to walk to a certain location.

![dinosaur-walk.gif](../../../assets/images/dinosaur-walk.gif)

### Bug

![bug.png](../../../assets/images/bug.png)

This NPC is defined by the `Bug` class. 
The bug is able to be interacted with.
Its `interactScript` is defined in the `BugScript` class (located in the `Scripts.TestMap` package).
The `BugScript` class shows an example of how to create user selectable options through the textbox (such as selecting "yes" or "no" to a question asked by an NPC).
Read more about scripts [here](./script-details.md).

Unlike the walrus and dinosaur NPCs, the bug NPC walks back and forth until it is interacted with rather than being stationary. The bug has a walking animation thatis used while it is moving back and forth.

![bug-walk.gif](../../../assets/images/bug-walk.gif)

![bug-walking-in-map.gif](../../../assets/images/bug-walking-in-map.gif)

## NPC Id

The constructor for an `NPC` class instance requires an `id` value. 
This should be a unique number for each NPC in as map.
This allows NPCs to be identifiable, which is useful in situations where you would want to move an NPC on the map in a script, even if you aren't interacting with them. 
`TestMap` simply uses an id value of 0 for the walrus and 1 for the dinosaur, and if more NPCs were to be added, it would just increment from there.

## NPC Methods

The `NPC` class defines several methods that are meant to be used in scripts to force the NPC to perform a specific behavior.
These are `facePlayer`, `stand`, and `walk`.

Calling an NPC's `facePlayer` method will cause them to set their direction to "face" the player.
This is nice to use in an NPC's `interactScript` for immersion purposes, as it looks a lot better if the NPC being interacted with turns and faces the player.

The `stand` method changes the NPC's animation to STAND_LEFT or STAND_RIGHT. The NPC must have those animations defined in order to use this method.

The `walk` method moves the NPC in a specified direction and at a specified speed. It will also change the NPC's animation to the appropriate walking animation (WALK_LEFT or WALK_RIGHT)
based on which direction they were told to walk in and which direction they are facing. 
The NPC must have those animations defined in order to use this method.

These methods are very useful in scripts, but manipulating an NPC can be done outside of these methods as well if some special logic is necessary.

NPCs have the option to override the base NPC class's a `performAction` method.
This can essentially be treated as a normal entity's `update` method cycle, 
however it is implemented in this way to allow a script to "lock" the NPC while it is executing.
For example, the bug NPC's `performAction` method tells the bug to continuously walk back and forth.
This method should be used for having NPCs behave in a certain way without the need for something to trigger it (e.g. having the NPC walk around), while an `interactScript` should be used to have an NPC behave in a certain way after they are spoken to (e.g. showing text in the textbox).
