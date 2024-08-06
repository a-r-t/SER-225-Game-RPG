---
layout: default
title: Scripting Engine
nav_order: 7
parent: Game Code Details
has_children: true
permalink: /GameCodeDetails/ScriptingEngine
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Scripting Engine

## What is a scripting engine?

Nearly all games of the RPG genre have some sort of scripting engine implemented that can almost be thought of as its own "language".
Essentially, it's the game's job to execute a given set of instructions, known as a **script**, without knowing what the script is going to do ahead of time or what the script is capable of doing.
Now, for this game, I didn't need to make things too complicated -- the scripts are just typical Java code sectioned off into separate classes
that can be assigned to map entities (as interact scripts for [NPCs](./npcs.md)/[Map Tiles](./map-tiles-and-tilesets.md) and trigger scripts for [Triggers](./triggers.md)).
The same script can be assigned to any number of entities if desired to have each one execute the same event.
Nearly all RPG games have their own scripting engines defined which allow for events to take place, such as Pokemon, Earthbound, and Undertale, which often have scripted events defined for talking to NPCs, creating cutscenes, and more.

Understanding this game's scripting "engine" is the key to being able to do...well anything that you want to the game.
The game is built around interact scripts and trigger scripts, so being able to make your own scripts or edit the existing scripts gives you infinite power (well...within the confines of this Java game).
Script events can be made to technically do "anything" to the game, it's just up to the coder's skill level and determination to make it happen!