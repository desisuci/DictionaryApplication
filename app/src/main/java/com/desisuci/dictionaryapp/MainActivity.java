package com.desisuci.dictionaryapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db = null;
    private Cursor kamusCursor = null;
    private EditText InputInggris;
    private EditText InputIndonesia;
    private DataKamus datakamus = null;
    public static final String INGGRIS = "inggris";
    public static final String INDONESIA = "indonesia";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datakamus = new DataKamus(this);
        db = datakamus.getWritableDatabase();
        datakamus.createTable(db);
        datakamus.generateData(db);
        setContentView(R.layout.activity_main);
        InputInggris = (EditText) findViewById(R.id.in_terjemahkan);
        InputIndonesia = (EditText) findViewById(R.id.in_terjemahan);

    }
    public void getTerjemahan(View view) {
        String result = "";
        String englishword = InputInggris.getText().toString();
        kamusCursor = db.rawQuery("SELECT ID, INGGRIS, INDONESIA "
                + "FROM kamus where INGGRIS='" + englishword
                + "' ORDER BY INGGRIS", null);

        if (kamusCursor.moveToFirst()) {
            result = kamusCursor.getString(2);
            for (; !kamusCursor.isAfterLast(); kamusCursor.moveToNext()) {
                result = kamusCursor.getString(2);
            }
        }
        if (result.equals("")) {
            result = "Terjemahan Not Found";
        }
        InputIndonesia.setText(result);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        kamusCursor.close();
        db.close();
    }

}
