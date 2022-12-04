package com.example.a2dtopviewsurvival.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.a2dtopviewsurvival.graphics.Sprite;
import com.example.a2dtopviewsurvival.graphics.SpriteSheet;

public class BaseTile extends Tile {
    private final Sprite groundSprite;
    private final Sprite baseSprite;

    public BaseTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        groundSprite = spriteSheet.getGroundSprite();
        baseSprite = spriteSheet.getBaseSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        groundSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
        baseSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
