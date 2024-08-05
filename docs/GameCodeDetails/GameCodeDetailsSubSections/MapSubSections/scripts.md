---
layout: default
title: Scripts
nav_order: 11
parent: Map
grand_parent: Game Code Details
permalink: /GameCodeDetails/Map/Scripts
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Scripts

# What is a script?

A script (represented by the `Script` class in the `Level` package) is an abstract class that allows for an "event" to be constructed and later triggered and executed by the game as many times as desired.
If you are familiar with Java Swing components, you can think of a script like how you would code a `JButton's` click event.
The idea is that a script contains code that should be executed at a later time, but the code needs to be defined up front in order for the game to use it.

The game's scripting engine handles executing a script when it has been triggered, whether that is through interacting with an NPC, the player walking on a specific spot on the map, etc.
From there, it's the script's job to tell the game what to do next through a set of instructions, which can include things like displaying text in the textbox, moving an NPC, and anything else that is desired.

Each script is made up of an instruction set, with each instruction acting as a "buliding block".
A script will start at its first instruction and work its way to the last instruction before terminating.
Each instruction is known as a script action.
It is highly recommended to understand both the scripting engine over page [here](./scripting-engine-overview.md) and the script actions page [here](./script-actions.md) before continuing on with this page.
This is the most complicated area of the game engine that has a bit of a learning curve, but put some time into getting the hang of things and you will be an expert in no time!

# Creating a new script


