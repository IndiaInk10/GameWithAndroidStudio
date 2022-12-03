package com.example.a2dtopviewsurvival.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.a2dtopviewsurvival.GameLoop;
import com.example.a2dtopviewsurvival.R;

public class Spell extends Circle {

    private static final double SPEED_PIXELS_PER_SECOND = 800;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    public Spell(Context context, Player spellCaster) {
        super(
                context,
                ContextCompat.getColor(context, R.color.spell),
                spellCaster.getPositionX(),
                spellCaster.getPositionY(),
                25.0
        );

        velocityX = spellCaster.getDirectionX() * MAX_SPEED;
        velocityY = spellCaster.getDirectionY() * MAX_SPEED;
    }

    @Override
    public void update() {
        positionX += velocityX;
        positionY += velocityY;
    }
}
