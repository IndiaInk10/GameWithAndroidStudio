package com.example.a2dtopviewsurvival.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.a2dtopviewsurvival.R;

public class SpriteSheet {
    private Bitmap bitmap;

    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.space_girl, bitmapOptions);
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
}
