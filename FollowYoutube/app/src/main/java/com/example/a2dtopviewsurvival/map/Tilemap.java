package com.example.a2dtopviewsurvival.map;

import static com.example.a2dtopviewsurvival.map.MapLayout.NUMBER_OF_COLUMN_TILES;
import static com.example.a2dtopviewsurvival.map.MapLayout.NUMBER_OF_ROW_TILES;
import static com.example.a2dtopviewsurvival.map.MapLayout.TILE_HEIGHT_PIXELS;
import static com.example.a2dtopviewsurvival.map.MapLayout.TILE_WIDTH_PIXELS;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.a2dtopviewsurvival.GameDisplay;
import com.example.a2dtopviewsurvival.graphics.SpriteSheet;

public class Tilemap {
    private final MapLayout mapLayout;
    private Tile[][] tilemap;
    private SpriteSheet spriteSheet;
    private Bitmap mapBitmap;

    public Tilemap(SpriteSheet spriteSheet) {
        mapLayout  = new MapLayout();
        this.spriteSheet = spriteSheet;
        initializeTilemap();
    }

    private void initializeTilemap() {
        int[][] layout = mapLayout.getLayout();
        tilemap = new Tile[NUMBER_OF_ROW_TILES][NUMBER_OF_COLUMN_TILES];
        for(int i = 0; i < NUMBER_OF_ROW_TILES; i++) {
            for(int j = 0; j < NUMBER_OF_COLUMN_TILES; j++) {
                tilemap[i][j] = Tile.getTile(
                    layout[i][j],
                    spriteSheet,
                    getRectByIndex(i, j)
                );
            }
        }

        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        mapBitmap = Bitmap.createBitmap(
                NUMBER_OF_COLUMN_TILES*TILE_WIDTH_PIXELS,
                NUMBER_OF_ROW_TILES*TILE_HEIGHT_PIXELS,
                config
        );

        Canvas mapCanvas = new Canvas(mapBitmap);

        for(int i = 0; i < NUMBER_OF_ROW_TILES; i++) {
            for(int j = 0; j < NUMBER_OF_COLUMN_TILES; j++) {
                tilemap[i][j].draw(mapCanvas);
            }
        }
    }

    private Rect getRectByIndex(int rowIdx, int colIdx) {
        return new Rect(
                colIdx*TILE_WIDTH_PIXELS,
                rowIdx*TILE_HEIGHT_PIXELS,
                (colIdx + 1)*TILE_WIDTH_PIXELS,
                (rowIdx + 1)*TILE_HEIGHT_PIXELS
        );
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawBitmap(
                mapBitmap,
                gameDisplay.getGameRect(),
                gameDisplay.DISPLAY_RECT,
                null
        );
    }
}
