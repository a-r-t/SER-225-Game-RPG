---
layout: default
title: Map Tiles and Tilesets
nav_order: 2
parent: Map
grand_parent: Game Code Details
permalink: /GameCodeDetails/Map/MapTilesAndTilesets
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Map Tiles and Tilesets

## Map Tiles

In order to understand how the [map file](./map-file.md) is structured/read in, the concept of a "map tile" must be understood first.

Think of a map as a 2D grid. 
Each spot on the grid contains what is known as a "tile", which is a smaller graphic that fits into the grid at a particular spot. 
A map is made up by joining these small tile graphics together to create one large graphic representing the entire map.
For example, take a look at the below screenshot of a piece of a level:

![tile-map-example-original.png](../../../assets/images/tile-map-example-original.png)

This image is actually made up of individual tiles, such as the grass tile and the sign tile:

![grass-tile.png](../../../assets/images/grass-tile.png)
![sign-tile.png](../../../assets/images/sign-tile.png)

So the image seen above of the grass, sign, cobblestone and house is actually composed together by smaller individual tiles, which is known as a tile map:

![tile-map-example.png](../../../assets/images/tile-map-example.png)

Tiles make it very easy to build up a 2D map. 
The Map Editor makes the process of using smaller tiles to build up the entire map very obvious since the editor allows tiles to be placed down on a grid in a desired location of the map. 
Each tile will have a specified index in the map at an x and y location, e.g. the first tile in the top left corner would have an index of (0,0), the tile to the right of it would be (1,0) and the tile below it would be (0,1).

The class `MapTile` in the `Level` package represents a map tile, which is really just a standard sprite with an attribute for `TileType`. 
The `TileType` attribute determines how the tile is treated by the game -- for example, a tile type of `NOT_PASSABLE` means that the `Player` cannot walk over the tile, which is used on tiles like the pieces of the house in order for the player to not be able to walk on top of it.
The available tile types are included in the `TileType` enum in the `Level` package. 

## Map Tileset

A tileset is a collection of map tiles. Easy enough.

Graphic wise, a tileset defines each tile in one image. 
Below is the `CommonTileset.png` which is used by `TestMap` to construct its tile map. 
You will notice that the common tileset image is one big image with each individual map tile defined. 
Each map tile in a tileset must be the SAME width and height.

![common-tileset.png](../../../assets/images/common-tileset.png)

The `Tileset` class in the `Level` package represents a tileset, which contains a collection of `MapTile` objects. 
The way to define a tileset in this game is to create a class that extends from this `Tileset` class, such as the `CommonTileset` class in the `Tilesets` package.
From there, the extended `Tileset` class must call its super class with the following:
- The tileset image file to be used -- `CommonTileset` uses the `CommonTileset.png` file shown above
- The width and height for every tile in the tileset -- `CommonTileset` specifies that each tile graphic in the above image is 16x16
- The tileset scale, which is how much each tile in the tileset should be scaled by when drawn to the screen -- `CommonTileset` specifies a scale of 3, meaning each tile will be 48x48 pixels on screen

Additionally, the `Tileset` class method `defineTiles` must be overridden and have actual defined `MapTiles` added to it.

### Adding a map tile to a tileset

The setup for overriding the `defineTiles` method in a `Tileset` subclass looks like this:

```java
@Override
public ArrayList<MapTileBuilder> defineTiles() {
    ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();
    return mapTiles;
}
```

From here, `MapTiles` can be added to the `ArrayList` -- kind of. 
This method actually defines a type of `MapTileBuilder`.
The class `MapTileBuilder`, which can be found in the `Builders` package, essentially defines a `MapTile` but does not instantiate it yet. 
It's not too important to fully understand this if you are unfamiliar with the builder pattern (you can read more about it [here](../game-patterns.md#builder-pattern)). 
I recommend that you use the already existing code in the `CommonTileset` class as a reference to guide you in adding a new `MapTile` to a tileset.

The following example in the `defineTiles` method adds the grass tile to the tileset, which is the first graphic in the top left corner of the above shown `CommonTileset.png` file:

![grass-tile.png](../../../assets/images/grass-tile.png)

```java
@Override
public ArrayList<MapTileBuilder> defineTiles() {
    ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

    // grass
    Frame grassFrame = new FrameBuilder(getSubImage(0, 0))
            .withScale(tileScale)
            .build();

    MapTileBuilder grassTile = new MapTileBuilder(grassFrame)
            .withTileType(TileType.PASSABLE);

    mapTiles.add(grassTile);

    // ...

    return mapTiles;
}
```

Whew, that's a bit confusing to look at I know! 
But the formatting here can be copied for every subsequent tile, so it can be treated as a template.

To start, a new `Frame` (details on `Frame` class [here](../game-object.md#animatedsprite-class)) must be created to represent the grass tile's graphic (`grassFrame`). 
The `FrameBuilder` class is used to build a `Frame` instance. 
In the constructor, `getSubImage(0, 0)` takes a piece of the tileset image (`CommonTileset.png`), which is the grass tile graphic.
Since `CommonTileset` defines the tile width and tile height as 16x16, the `getSubImage` method will start at location (0, 0) on the image and then take 16 pixels in both directions and return the resulting subimage, which is how the individual grass tile graphic gets returned.

Then, a `MapTileBuilder` class instance must be created to represent the actual `MapTile` (although it won't instantiate the `MapTile` at this time).
Here is where the grass tile (`grassTile`) is given the `TileType` `PASSABLE`, meaning the player can walk over it.

Finally, the tile is added to the `mapTiles` list.

Let's do one for the sign tile now:

![sign-tile.png](../../../assets/images/sign-tile.png)

In the `CommmonTileset.png` image shown earlier, the sign tile is located a couple rows down from the grass tile at index (3, 0).
With that slight difference in mind, nearly everything else will be the same for the sign tile as the grass tile:

```java
@Override
public ArrayList<MapTileBuilder> defineTiles() {
    ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

    // grass
    Frame grassFrame = new FrameBuilder(getSubImage(0, 0))
            .withScale(tileScale)
            .build();

    MapTileBuilder grassTile = new MapTileBuilder(grassFrame)
            .withTileType(TileType.NOT_PASSABLE);

    mapTiles.add(grassTile);

    return mapTiles;

    // sign
    Frame signFrame = new FrameBuilder(getSubImage(3, 0))
            .withScale(tileScale)
            .build();

    MapTileBuilder signTile = new MapTileBuilder(signFrame)
            .withTileType(TileType.NOT_PASSABLE);

    mapTiles.add(signTile);

    // ...

    return mapTiles;

}
```

Unlike with the grass tile, the sign tile is "solid", and the player shouldn't be able to walk over it.
To achieve this, its `TileType` needs to be set to `NOT_PASSABLE`.

### Adding an animated map tile to a tileset

Tiles can be animated! How fun!
Currently, the `CommonTileset` contains three animated tiles: the yellow flower, the purple flower, and the rising/falling water on the shore.
To set this up, the tileset image file must have a separate image for each frame in the tile's animation. 

For example, below are the frame tiles used for the flower (three different frame images):

![purple-flower-image-1.png](../../../assets/images/purple-flower-image-1.png)
![purple-flower-image-2.png](../../../assets/images/purple-flower-image-2.png)
![purple-flower-image-3.png](../../../assets/images/purple-flower-image-3.png)

The end goal is the following animation:

![purple-flower-animation.gif](../../../assets/images/purple-flower-animation.gif)

The code for this animated tile looks like this:

```java
// purple flower
Frame[] purpleFlowerFrames = new Frame[] {
        new FrameBuilder(getSubImage(0, 2), 65)
                .withScale(tileScale)
                .build(),
        new FrameBuilder(getSubImage(0, 3), 65)
                .withScale(tileScale)
                .build(),
        new FrameBuilder(getSubImage(0, 2), 65)
                .withScale(tileScale)
                .build(),
        new FrameBuilder(getSubImage(0, 4), 65)
                .withScale(tileScale)
                .build()
};

MapTileBuilder purpleFlowerTile = new MapTileBuilder(purpleFlowerFrames);

mapTiles.add(purpleFlowerTile);
```

Instead of just one `Frame` being created, an array of `Frame` instances is defined. 
Each `Frame` specifies its own subimage location, and its delay value.
The delay value is the number of game cycles that need to pass before the animation will transition to its next frame.
Each frame in the above animation has a delay of 65 game cycles.
This animation defines these four frames in the following order:

![purple-flower-image-1.png](../../../assets/images/purple-flower-image-1.png)
![purple-flower-image-2.png](../../../assets/images/purple-flower-image-2.png)
![purple-flower-image-3.png](../../../assets/images/purple-flower-image-1.png)
![purple-flower-image-4.png](../../../assets/images/purple-flower-image-3.png)

After each animation cycle, the animation will loop back to the beginning again. 
If the delay is set to -1, the animation will never move on from a frame without something else explicitly telling it to.

### Tile Types

The available tile types are defined in the `TileType` enum, and include:

- **NOT_PASSABLE** -- player cannot walk over it, they are "solid", such as the rock tiles
- **PASSABLE** -- player can walk over it, such as grass tiles

### Tile Layers

Each map tile supports two "layers" -- a bottom layer (required) and a top layer (optional).
Tile layering has two benefits. 
The first is that tile images can be reused, as the top layer will be "pasted" on top of the bottom layer.
The second is that the top layer of a map tile will be drawn after the player, meaning it will cover the player.
You can see in the below gif that the tree tops use a top layer, which is why the player is covered by them when it makes its way to those tiles.

![map-tile-layer-example.gif](../../../assets/images/map-tile-layer-example.gif)

This layered system gives off the illusion that the player is behind the tile and gives the game some more artistic depth, and is a common tactic video games of this style use. 

In the above examples for creating the grass and sign tiles, they only had a bottom layer. 
A top layer must be explicitly specified.
Both the bottom layer and top layer can be optionally animated as well.
Below is the code used to create the tree tops that utilize a top layer being added to a map tile:

```java
// tree top leaves
Frame treeTopLeavesFrame = new FrameBuilder(getSubImage(1, 1))
        .withScale(tileScale)
        .build();

MapTileBuilder treeTopLeavesTile = new MapTileBuilder(grassFrame)
        .withTopLayer(treeTopLeavesFrame)
        .withTileType(TileType.PASSABLE);

mapTiles.add(treeTopLeavesTile);
```

The bottom layer is just a grass tile, while the top layer is the tree top tile. 
The tile is also set to passable, so the player is able to walk on it.
The bottom layer will be covered by the player (the grass), while the top layer covers the player (the tree tops).