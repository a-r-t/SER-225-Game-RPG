---
layout: default
title: Script Actions
nav_order: 2
parent: Scripting Engine
grand_parent: Game Code Details
permalink: /GameCodeDetails/ScriptingEngine/ScriptActions
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Script Actions

## What is a script action?

A script action is one specific instruction for the game to perform during a script.
A script is a collection of script actions (instruction set).
You can think of each script action as a building block or a lego piece, while the script itself is the sequential combination of them.

When the script has reached a specific script action instruction, the script action will first run its "setup" logic.
This "setup" logic is only run one time.
Next, the script will continually run its "execution" logic every update frame until it has completed.
Finally, the script will run its "cleanup" logic, which is only run one time.

Each `ScriptAction` base class defines the following three methods to allow any subclass to include their own setup, execute, and cleanup logic:
- `setup` -- logic that gets run before event execution, this method is only called one time.
- `execute` -- logic that gets run during event execution; this is the actual "event" being carried out.
- `cleanup` -- logic that gets run after event execution, this method is only called one time.

The below sections of this page strictly cover script actions.
To see how script actions come together to create a fully functioning script, check out the scripts page [here](./script-details.md).

## What script actions are already included in the game engine?

This game engine provides a ton of pre-made script actions that can be used in any script.
The sections below will go into detail on what they do and how they work.

### Lock Player and Unlock Player

When the player is "locked", it means they cannot move or interact with anything, essentially freezing them in place.
The player is often locked at the beginning of every script in order to allow for the script to play out without the player being able to just walk away or mess up a cutscene.

The `LockPlayerScriptAction` class can be used to lock the player.

Something very important is to ensure that you remember to unlock the player at the end of the script, or else the game will become unplayable as the player will be stuck in place unable to do anything forever.

The `UnlockPlayerScriptAction` class can be used to unlock the player.

These two script actions are very simple to use and do not require any arguments:

```java
public ArrayList<ScriptAction> loadScriptActions() {
    ArrayList<ScriptAction> scriptActions = new ArrayList<>();

    scriptActions.add(new LockPlayerScriptAction()); // lock player in place

    // all other script actions go in between

    scriptActions.add(new UnlockPlayerScriptAction()); // unlock player

    return scriptActions;
}
```

Examples of these script actions in use can be found in every script in the game, such as `SimpleTextScript`, `WalrusScript`, etc.

### Show Text in Textbox

The `TextboxScriptAction` can be used to show the textbox on screen, display as little or as much text as desired, and finally close the textbox when finished.
This action acts as a "wrapper" to abstract away interacting with the `Textbox` class in the `Level` package and using its logic.
This action supports both standard text as well as utilizing the options feature (which you can read more about [here](./scripting-engine-overview.md#textbox)).

Below are some examples of using this action to simply open up a textbox, display text that the user can cycle through, and then close the textbox when there is no more text left:

{% raw %}
```java
public ArrayList<ScriptAction> loadScriptActions() {
    ArrayList<ScriptAction> scriptActions = new ArrayList<>();

    // this will just show one line of text
    addScriptAction(new TextboxScriptAction() {{
        addText("Hello world!");
    }});

    // this will show two lines of text, the new line indicator tells the textbox where drop the text on to the next line
    addScriptAction(new TextboxScriptAction() {{
        addText("Hello world!\nHow are you doing today?");
    }});

    addScriptAction(new TextboxScriptAction() {{
        // this will first show two lines of text, the new line indicator tells the textbox where drop the text on to the next line
        addText("Hello world!\nHow are you doing today?");

        // afterwards, it will show this next line of text
        addText("I love programming!")
    }});

    return scriptActions;
}
```
{% endraw %}

As you can see from the above example, any amount of text can be used.
The textbox does not automatically determine if the text length will fit inside the textbox, so it may take some trial and error to ensure the order and orientation of the text within the textbox looks exactly as desired.
Examples of these script actions in use can be found in every script in the game, such as `SimpleTextScript`, `WalrusScript`, etc.

Utilizing the textbox's options feature can be setup similarly, but with the addition of providing the two options the user is able to select from:

{% raw %}
```java
public ArrayList<ScriptAction> loadScriptActions() {
    ArrayList<ScriptAction> scriptActions = new ArrayList<>();

    // this will show "Do you like programming?" in the main textbox
    // additionally, it will bring up the options textbox and show two possible options to choose from: "Yes" and "No"
    scriptActions.add(new TextboxScriptAction() {{
        addText("Do you like programming?", new String[] { "Yes", "No" });
    }});

    return scriptActions;
}
```
{% endraw %}

While the `Textbox` class technically can support any number of options, the actual options box graphics only works with two options that are of a certain text length.
If expanded functionality is desired, the box graphics can be updated from within the `Textbox` class.

As far as what to actually do from here with the options textbox, such as getting the user's answer to use in a later script action, check out the Conditionals section of this page [here](#conditional-script-action-groups) first to gain context, and then to examples of actually using the options textbox in-game can be found in the `BugScript`, which is gone over in detail [here](./script-details.md#bug-script).

### Set Flags and Unset Flags

Flags can be set and unset in flag manager using the `ChangeFlagScriptAction` class.
You can read about what Flags and Flag Manager are [here](./scripting-engine-overview.md#flags).

The action takes in two arguments: the flag to change, and the value that the flag should be.
To set a flag, the value should be set to `true`.
To unset a flag, the value should be set to `false`.
The flag must have already been defined in flag manager for it to be able to be set/unset in a script.

```java
public ArrayList<ScriptAction> loadScriptActions() {
    ArrayList<ScriptAction> scriptActions = new ArrayList<>();

    // this will set the flag named isHappy
    addScriptAction(new ChangeFlagScriptAction("isHappy", true));


    // this will unset the flag named isSad
    addScriptAction(new ChangeFlagScriptAction("isSad", false));

    return scriptActions;
}
```

The purpose of setting and unsetting flags is to update the game state.
A flag that is set indicates to the game that something has already "happened", which can allow for more complex or sequential events.
For example, if a game had a quest that required that the player has spoken to one NPC before speaking to another, flags can be used for scripts to determine what the player has or has not already done.

Examples of these script actions in use can be found in `WalrusScript`, which sets a flag named `hasTalkedToWalrus` after the walrus has been spoken to the first time, as well as `DinoScript` and `TreeScript` to indicate to the game that certain events have taken place.
Then, if the walrus is spoken to again, the script checks if the flag is set -- if it is, the walrus says a different message to the player.

### Pause for a set amount of time

This one is easy: the `WaitScriptAction` will pause a script for a certain amount of frames.
This allows for some control over "timing".

To use it, simply pass in the number of frames that the script should pause for:

```java
public ArrayList<ScriptAction> loadScriptActions() {
    ArrayList<ScriptAction> scriptActions = new ArrayList<>();

    // this will pause the script for 60 frames
    addScriptAction(new WaitScriptAction(60));

    // this will pause the script for 80 frames
    addScriptAction(new WaitScriptAction(80));

    return scriptActions;
}
```

When the number of frames to wait have passed, the wait script action will automatically end and move on to the next script action in the script's instruction set.

An example of this script action can be found in `DinoScript`, which uses it to simulate a more "natural" interaction.

### Manipulate NPCs

There are several script actions that allow for manipulating NPCs on the map:
- `NPCStandScriptAction`: Tells an NPC to stand and face a certain direction.
- `NPCFacePlayerScriptAction`: Tells an NPC to turn towards the player (to make speaking to an NPC more immersive).
- `NPCWalkScriptAction`: Tells an NPC to walk in a given direction at a given speed until they have moved a given distance.
- `NPCChangeVisibilityScriptAction`: Used to hide/show an NPC on the map. When hidden, the NPC will disappear.
- `NPCLockScriptAction`: Similar to the player lock/unlock script actions, this script action locks the NPC in place. Not always needed, but useful in some situations (such as when speaking to the bug, this stops the bug from moving back and forth).
- `NPCUnlockScriptAction`: This unlocks a locked NPC.

If an NPC script action is being used as an NPC's `interactScript` and no [NPC Id](./npcs.md#npc-id) is supplied as an argument, the action will be applied to the currently interacted with NPC. For all other instances, a specific NPC's Id must be provided to tell the action with NPC on the map to apply the logic to.

Below is a simple example of telling an NPC that is currently being interacted with to face the player:

```java
public ArrayList<ScriptAction> loadScriptActions() {
    ArrayList<ScriptAction> scriptActions = new ArrayList<>();

    // this will force the NPC being interacted with to face the player
    addScriptAction(new NPCFacePlayerScriptAction());

    return scriptActions;
}
```

Below is an example of telling a specific NPC with an id of 1 to walk 90 pixels to the left at a speed of 2 pixels per frame:

```java
public ArrayList<ScriptAction> loadScriptActions() {
    ArrayList<ScriptAction> scriptActions = new ArrayList<>();

    // this will force an NPC of id 1 to walk to the left at a speed of 2 pixels per frame until it has reached 90 pixels of distance traveled
    addScriptAction(new NPCWalkScriptAction(1, Direction.LEFT, 90, 2));

    return scriptActions;
}
```

Examples of these actions being used can be found in all of the NPC interact scripts such as `WalrusScript`, `DinoScript`, and `BugScript`.

### Conditional Script Action Groups

Conditional script action groups allow for creating separate groups of script actions that should only be executed based on a specific condition.
**While the below example may seem like the way to achieve this by using standard if statements to check on flag states, it will NOT work**:

{% raw %}
```java
public ArrayList<ScriptAction> loadScriptActions() {
    ArrayList<ScriptAction> scriptActions = new ArrayList<>();

    // this whole thing is WRONG and will not work
    if (map.getFlagManager().isFlagSet("hasTalkedToWalrus")) {
        addScriptAction(new TextboxScriptAction() {{
            addText("It was nice to meet you");
        }});
    }
    else {
        addScriptAction(new TextboxScriptAction() {{
            addText("Hello, my name is walrus!");
        }});
    }

    return scriptActions;
}
```
{% endraw %}

While syntactically this is fine, functionality wise within the scripting engine this will not work.
Since scripts and their script actions are only instantiated one time, this script would be created with only one or the other script action possibility, since the if statement/adding script actions only happens one time on load.
The other possibility would just never happen, even if the if statement's condition were to evaulate that way, because the script action was never loaded.

**To do this the correct way, conditional action groups must be used!**
This will allow the scripting engine to know the full context of the script and its conditionals, so that it can evaluate conditions dynamically each time the script is executed.
While this is a tad more complex, remember that script actions are all building blocks, and you can think of these condtional script action groups as a "container" to organize them in a particular manner.

The above incorrect example needs to be converted to look like the below example in order to work in the game as one would expect:

{% raw %}
```java
public ArrayList<ScriptAction> loadScriptActions() {
    ArrayList<ScriptAction> scriptActions = new ArrayList<>();

    // create a conditional action block
    addScriptAction(new ConditionalScriptAction() {{

        // first conditional option (think of this as an "if" block)
        addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{

            // this conditional group must meet this requirement for its script actions to be executed
            // this reads "if the flag 'hasTalkedToWalrus' is set"
            addRequirement(new FlagRequirement("hasTalkedToWalrus", true));

            // the script actions to add in this conditional group
            addScriptAction(new TextboxScriptAction() {{
                addText("It was nice to meet you");
            }});
        }});

        // second conditional option (think of this as an "else if" block)
        addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{

            // this conditional group must meet this requirement for its script actions to be executed
            // this reads "else if the flag 'hasTalkedToWalrus' is not set"
            addRequirement(new FlagRequirement("hasTalkedToWalrus", false));

            // the script actions to add in this conditional group
            addScriptAction(new TextboxScriptAction() {{
                addText("Hello, my name is walrus!");
            }});
        }});

    }});

    return scriptActions;
}
```
{% endraw %}

Yeah, I know the syntax is a bit...interesting...but don't let it bully you around!

The initial line `addScriptAction(new ConditionalScriptAction()` defines a conditional block, which you can think of as the script action saying "hey, the code inside of this action is going to be an if statement block".

The first part of this conditional action is `addConditionalScriptActionGroup(new ConditionalScriptActionGroup()`.
You can think of a `ConditionalScriptActionGroup` as a part of an if statement.
So this is telling the script "this is the first part of the if statement".

The `addRequirement(new FlagRequirement("hasTalkedToWalrus", true));` line is then used to define what the if statement's condition is.
This `addRequirement` goes hand-in-hand with the `ConditionalScriptActionGroup` and completes the if statement signature.
In this case, it is saying "if the flag hasTalkedToWalrus is set, run these script actions".

After that, it's just a matter of adding any number of desired script actions like normal to the conditional.

The next `ConditionalActionGroup` here can essentially be through of as an "else if" statement.
It reads "else if the flag hasTalkedToWalrus is not set, run these script actions".
Just like in a normal if statement, only one `ConditionalActionGroup` inside of a `ConditionalScriptAction` will have their script actions executed during each evaluation.

You are free to add script actions before and after the `ConditionalScriptAction`, add nested conditional script actions, have only one `ConditionalScriptActionGroup` in a `ConditionalScriptAction`, etc. -- they are just a different representation of an if statement in order to match the "building block" nature of a script.

#### Conditional Requirements

As mentioned in the above section on conditionals, requirements can be added to a `ConditinalScriptActionGroup` to define what condition needs to evaulate to true in order for that script action group to execute. 
Taking just a snippet from the above code sample shows how to add a `FlagRequirement`, which checks on the state of a given flag.
The below "if statement" ends up reading "if the flag hasTalkedToWalrus is set, run these script actions":

{% raw %}
```java
// first conditional option (think of this as an "if" block)
addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{

    // this conditional group must meet this requirement for its script actions to be executed
    // this reads "if the flag 'hasTalkedToWalrus' is set"
    addRequirement(new FlagRequirement("hasTalkedToWalrus", true));

    // the script actions to add in this conditional group
    addScriptAction(new TextboxScriptAction() {{
        addText("It was nice to meet you");
    }});
}});
```
{% endraw %}

Conditional branching based on flag states are very common in RPG games, however there are times when a more "custom" requirement is needed.
There is another class called `CustomRequirement` that can be used here instead of `FlagRequirement` to define a completely custom artibrary condition.
Below is an example of how to use it:

{% raw %}
```java
addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{

    // create custom requirement that checks if the date is my birthday (lol sorry best example I could think of on the spot)
    addRequirement(new CustomRequirement() {

        @Override
        public boolean isRequirementMet() {
            LocalDate date = LocalDate.now();
            return date.getMonthValue() == 2 && date.getDayOfMonth() == 11;
        }
    });

    addScriptAction(new TextboxScriptAction() {{
        addText("Happy birthday!");
    }});
}});
```
{% endraw %}

Once again, I know the syntax is a little rough...Java was a bit limiting in what I could do here to make this work the way I wanted it to...

You can see another example of a `CustomRequirement` used in the `TreeScript` where a custom requirement is used to check that the player is below the tree tile when interacting with it (which essentially forces the player to move the rock out of the way).
You can read more about the `TreeScript` [here](./script-details.md#tree-script).

Any number of requirements can be added to a conditional script action group:

{% raw %}
```java
addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{

    // if both the flag hasTalkedToWalrus is set AND the flag hasTalkedToDinosaur is set
    addRequirement(new FlagRequirement("hasTalkedToWalrus", true));
    addRequirement(new FlagRequirement("hasTalkedToDinosaur", true));

    // ... add script actions here
}});
```
{% endraw %}

By default, all requirements will use AND logic (same as using && in an if statement).
You can change all requirements to use OR logic instead by setting the flag strategy to OR:

{% raw %}
```java
addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{

    // if either the flag hasTalkedToWalrus is set OR the flag hasTalkedToDinosaur is set
    addRequirement(new FlagRequirement("hasTalkedToWalrus", true));
    addRequirement(new FlagRequirement("hasTalkedToDinosaur", true));
    setFlagStrategy(FlagStrategy.OR);

    // ... add script actions here
}});
```
{% endraw %}

The scripting engine does not support a mixture of ANDs and ORs like you can in regular if statements.
If you need that sort of functionality, you will need to make nested conditionals.

If a `ConditionalScriptActionGroup` does NOT have a requirement associated with it, it is treated like an "else" statement.

## Creating a new script action

Script actions are designed to be as modular and reusable as possible, as well as be convenient and developer-friendly to use.
If you have a new script action idea that fits that use-case, you can create your own pretty easily.
First, start by creating a new subclass of `ScriptAction`.
Next, determine if the script action needs any additional pieces of data, which if so should be defined in its constructor.
Finally, add an `execute` method. 
You can also add `setup` and `cleanup` methods if they will be needed for your action.
Your script action subclass will end up looking something like this:

```java
public class MyScriptAction extends ScriptAction {

    // add instance vars/constructor mapping if needed...
    public MyScriptAction() {

    }

    // this method is only run once when script action is loaded up (before execute)
    // it is optional
    @Override
    public void setup() {

    }

    // this method is called once every frame while the script action is active
    // it is where the actual logic should take place for carrying out a specific event
    @Override
    public ScriptState execute() {
        // ... script action execute logic

        return ScriptState.COMPLETED;
    }

    // this method is only run once after script action has completed
    // it is optional
    @Override
    public void cleanup() {

    }

}
```

The best way to learn how to make a script action is to look at how the existing ones are implemented.
One very simple one is `LockPlayerScriptAction`, which locks the player in place. 
It looks like this:

```java
public class LockPlayerScriptAction extends ScriptAction {

    @Override
    public ScriptState execute() {
        player.lock();
        return ScriptState.COMPLETED;
    }
}
```

It has no constructor, no setup, and no cleanup.
All it does when it executes is call the method `lock` on the player, and then return that the action is completed.

More complicated script actions, however, require a bit more work.
Let's now look at the `NPCWalkScriptAction`, which tells an NPC to move in a specified direction until it reaches a distance benchmark.
I have trimmed the class's code down a bit for this example to only focus on the relevant topic at hand:

```java
public class NPCWalkScriptAction extends ScriptAction {
    protected NPC npc;
    protected Direction direction;
    protected float distance;
    protected float speed;
    protected int amountMoved;

    public NPCWalkScriptAction(int npcId, Direction direction, float distance, float speed) {
        this.npc = map.getNPCById(npcId);
        this.direction = direction;
        this.distance = distance;
        this.speed = speed;
    }

    @Override
    public void setup() {
        amountMoved = 0;
    }

    @Override
    public ScriptState execute() {
        npc.walk(direction, speed);
        amountMoved += speed;
        if (amountMoved < distance) {
            return ScriptState.RUNNING;
        }
        return ScriptState.COMPLETED;
    }
}
```

The instance variables and constructor for the most part should be pretty obvious.
The script action needs to know the direction to move the NPC in, the speed at which the NPC should move at, and the distance the NPC should move before the script action is completed.
Additionally, the Id of the target NPC to move should be passed in so the script knows which one to perform this action on.

The `setup` method, which is only run once when the script action is loaded, sets `amountMoved` to 0 to reset it before the NPC starts moving.

The `execute` method, which is called every frame, will continually tell the NPC to walk in the given direction at the given speed by calling `npc.walk(direction, speed)`.
The `amountMoved` is tracked each time `execute` is run and the NPC is moved until the point is reached where the target distance has been hit.
If the target distance has not been hit yet, the `execute` method returns `ScriptState.RUNNING`, which tells the script "hey I'm not finished yet, but I completed one cycle -- call me again next frame so I can continue".
Once the target distance has finally been reached, the `execute` method returns `ScriptState.COMPLETED`, which tells the script "I'm all set, don't call me again".

If you are curious why the `execute` method doesn't do something like use a loop and move the NPC to the target distance all in one call, it's because the scripting engine's goal is to execute scripts without "blocking" the rest of the game from running. 
If a while loop was placed in that `execute` method, the ENTIRE game would pause while the NPC was moving, which means all background animations, other NPCs/entities, timers, and anything else that may be going on would all freeze in place.
While a tad more complex, this scripting engine system allows the rest of the game to run in the background while a script is being execute, which allows for a much more immersive game and an overall better player experience.
It also allows for more potential capabilities and gameplay features to be added by future developers.
You can read more about the scripting engine's design and goals [here](./scripting-engine-overview.md).

Creating a script action at first can be a tad challening, but once you have gotten one to work and understand the scripting engine well enough, they aren't bad at all. 

### Anonymous script action class instance

There are situations where a developer may just need to execute some code in a script that isn't modular/reusable enough to make a new script action for, or it's only intended to be used in one spot since it's so specific.
In that case, an anonymous script action class instance can be created without having to go through creating a new script action subclass and what not.
It's STILL doing the same exact thing (setup, execute, cleanup, etc.) so don't try to pick this option out of laziness!

Below is an example of creating an anonymous script action:

```java
public ArrayList<ScriptAction> loadScriptActions() {
    ArrayList<ScriptAction> scriptActions = new ArrayList<>();

    // anonymous script action
    addScriptAction(new ScriptAction() {
        @Override
        public ScriptState setup() {

        }

        @Override
        public ScriptState execute() {

        }

        @Override
        public ScriptState cleanup() {

        }
    });

    return scriptActions;
}
```

An example of this can be found in `DinoScript`, which uses it when opening and closing the door it walks through during its event.
Although now that I am thinking about it...a reusable script action for changing a map tile could be a good idea...
