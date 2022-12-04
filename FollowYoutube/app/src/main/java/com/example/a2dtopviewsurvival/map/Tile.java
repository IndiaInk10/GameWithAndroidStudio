package com.example.a2dtopviewsurvival.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.a2dtopviewsurvival.graphics.SpriteSheet;

public abstract class Tile {

    protected final Rect mapLocationRect;

    public Tile(Rect mapLocationRect) {
        this.mapLocationRect = mapLocationRect;
    }

    public enum TileType {
        GROUND_TILE,
        PLANT_TILE,
        BASE_TILE
    }

    public static Tile getTile(int tileTypeIdx, SpriteSheet spriteSheet, Rect mapLocationRect) {
        switch (TileType.values()[tileTypeIdx]) {
            case GROUND_TILE:
                return new GroundTile(spriteSheet, mapLocationRect);
            case PLANT_TILE:
                return new PlantTile(spriteSheet, mapLocationRect);
            case BASE_TILE:
                return new BaseTile(spriteSheet, mapLocationRect);
            default:
                return null;
        }
    }

    public abstract void draw(Canvas canvas);
}
