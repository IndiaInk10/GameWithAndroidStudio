package com.example.a2dtopviewsurvival;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShowRank extends Activity {
    SQLiteDatabase sqlDB;
    myDBHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_rank);

        myHelper = new myDBHelper(this);

        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM rankTBL ORDER BY score DESC;", null);

        int cnt = 0;
        String name = "";
        String score = "";
        while (cursor.moveToNext()) {
            if(cnt >= 3) break;
            name += cursor.getString(0) + "\r\n";
            score += cursor.getString(1) + "\r\n";
            cnt++;
        }

        ((EditText) findViewById(R.id.nameTxt)).setText(name);
        ((EditText) findViewById(R.id.scoreTxt)).setText(score);

        cursor.close();
        sqlDB.close();

        Button menuBtn = (Button) findViewById(R.id.menuBtn) ;
        menuBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowRank.this, MainActivity.class) ;

                startActivity(intent) ;
            }
        });
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "rankDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
//            db.execSQL("CREATE TABLE  rankTBL ( name CHAR(20) PRIMARY KEY, score INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            db.execSQL("DROP TABLE IF EXISTS rankTBL");
//            onCreate(db);
        }
    }
}
