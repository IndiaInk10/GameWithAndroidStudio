package com.example.a2dtopviewsurvival.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.a2dtopviewsurvival.graphics.Sprite;
import com.example.a2dtopviewsurvival.graphics.SpriteSheet;

public class PlantTile extends Tile {
    private final Sprite groundSprite;
    private final Sprite plantSprite;

    public PlantTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        groundSprite = spriteSheet.getGroundSprite();
        plantSprite = spriteSheet.getPlantSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        groundSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
        plantSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
