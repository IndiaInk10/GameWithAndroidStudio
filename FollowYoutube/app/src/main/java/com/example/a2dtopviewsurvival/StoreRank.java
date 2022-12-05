package com.example.a2dtopviewsurvival;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StoreRank extends Activity {
    SQLiteDatabase sqlDB;
    myDBHelper myHelper;
    EditText nameTxt;
    TextView scoreTxt;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_rank);

        Intent intent = getIntent();

        int score = intent.getIntExtra("score", 0);
        scoreTxt = (TextView) findViewById(R.id.scoreTxt);
        scoreTxt.setText("SCORE : " + Integer.toString(score));

        myHelper = new myDBHelper(this);
        nameTxt = (EditText) findViewById(R.id.nameTxt);
        Button storeBtn = (Button) findViewById(R.id.storeBtn) ;
        storeBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameTxt.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "please enter your name",
                            Toast.LENGTH_SHORT).show();
                } else {
                    sqlDB = myHelper.getReadableDatabase();
                    Cursor cursor;
                    cursor = sqlDB.rawQuery("SELECT * FROM rankTBL WHERE name = '"
                            + nameTxt.getText().toString() + "';", null);
                    if (cursor.getCount() == 0) {
                        sqlDB = myHelper.getWritableDatabase();
                        sqlDB.execSQL("INSERT INTO rankTBL VALUES ( '"
                                + nameTxt.getText().toString() + "' , "
                                + Integer.toString(score) + ");");
                        Toast.makeText(getApplicationContext(), "your score has been saved",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "already exist that name..",
                                Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                    sqlDB.close();
                }
            }
        });

        Button retryBtn = (Button) findViewById(R.id.retryBtn) ;
        retryBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreRank.this, StartGame.class) ;

                startActivity(intent) ;
            }
        });

        Button menuBtn = (Button) findViewById(R.id.menuBtn) ;
        menuBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreRank.this, MainActivity.class) ;

                startActivity(intent);
            }
        });
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "rankDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE  rankTBL ( name CHAR(20) PRIMARY KEY, score INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS rankTBL");
            onCreate(db);
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("StoreRank.java", "onBackPressed()");
        // Comment out super to prevent any back press action
        // super.onBackPressed();
    }
}
