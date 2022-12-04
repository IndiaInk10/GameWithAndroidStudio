package com.example.a2dtopviewsurvival.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.a2dtopviewsurvival.R;

public class SpriteSheet {
    private static final int SPRITE_WIDTH_PIXELS = 64;
    private static final int SPRITE_HEIGHT_PIXELS = 64;
    private Bitmap bitmap;

    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet, bitmapOptions);
    }

    public Sprite[] getPlayerSpriteArray() {
        Sprite[] spriteArray = new Sprite[4];
        for(int i = 0; i < 4; i++) {
            spriteArray[i] = new Sprite(this, new Rect(64 * i, 0, 64 * (i+1), 64));
        }
        return spriteArray;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Sprite getGroundSprite() {
        return getSpriteByIndex(1, 0);
    }
    public Sprite getPlantSprite() {
        return getSpriteByIndex(1, 1);
    }
    public Sprite getBaseSprite() {
        return getSpriteByIndex(1, 2);
    }

    private Sprite getSpriteByIndex(int rowIdx, int colIdx) {
        return new Sprite(this, new Rect(
                colIdx*SPRITE_WIDTH_PIXELS,
                rowIdx*SPRITE_HEIGHT_PIXELS,
                (colIdx + 1)*SPRITE_WIDTH_PIXELS,
                (rowIdx + 1)*SPRITE_HEIGHT_PIXELS
        ));
    }

}
