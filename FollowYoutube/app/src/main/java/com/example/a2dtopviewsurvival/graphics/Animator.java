package com.example.a2dtopviewsurvival.graphics;

import android.graphics.Canvas;

import com.example.a2dtopviewsurvival.GameDisplay;
import com.example.a2dtopviewsurvival.gameObject.Player;

public class Animator {
    private Sprite[] playerSpriteArray;
    private int idleFrameIdx = 0;
    private int walkFrameIdx = 1;
    private int updatesBeforeNextMoveFrame = 2;
    private static final int MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME = 5;

    public Animator(Sprite[] playerSpriteArray) {
        this.playerSpriteArray = playerSpriteArray;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, Player player) {
        switch (player.getPlayerState().getState()) {
            case IDLE:
                if(walkFrameIdx != 1) walkFrameIdx = 1;
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[idleFrameIdx]);
                break;
            case WALK:
                updatesBeforeNextMoveFrame--;
                if(updatesBeforeNextMoveFrame == 0) {
                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
                    changeWalkFrameIdx();
                }
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[walkFrameIdx]);
                break;
            default:
                break;
        }
    }

    private void changeWalkFrameIdx() {
        walkFrameIdx++;
        walkFrameIdx %= 4;
    }

    public void drawFrame(Canvas canvas, GameDisplay gameDisplay, Player player, Sprite sprite) {
        sprite.draw(
                canvas,
                (int) gameDisplay.gameToDisplayCoordinatesX(player.getPositionX()) - sprite.getWidth()/2,
                (int) gameDisplay.gameToDisplayCoordinatesY(player.getPositionY()) - sprite.getHeight()/2
        );
    }
}
