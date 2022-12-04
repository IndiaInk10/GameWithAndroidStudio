package com.example.a2dtopviewsurvival.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.a2dtopviewsurvival.graphics.Sprite;
import com.example.a2dtopviewsurvival.graphics.SpriteSheet;

public class GroundTile extends Tile {
    private final Sprite sprite;

    public GroundTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getGroundSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
