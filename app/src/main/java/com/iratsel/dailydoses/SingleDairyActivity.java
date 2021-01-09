package com.iratsel.dailydoses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.iratsel.dailydoses.model.ListMainModel;
import com.iratsel.dailydoses.utils.DatabaseHelper;
import com.iratsel.dailydoses.utils.Tag;
import com.iratsel.dailydoses.utils.Utility;

import java.util.ArrayList;

public class SingleDairyActivity extends AppCompatActivity {

    TextView txt_headline, txt_date, txt_desc;
    String headline, date, desc;
    DatabaseHelper myDb;
    Cursor res;
    SharedPreferences sharedPreferences;

    ArrayList<ListMainModel> listMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_dairy);

        txt_headline = findViewById(R.id.txt_headline);
        txt_date = findViewById(R.id.txt_date);
        txt_desc = findViewById(R.id.txt_desc);

        sharedPreferences = getSharedPreferences(Tag.SP, Context.MODE_PRIVATE);
        myDb = new DatabaseHelper(this);

        String email = sharedPreferences.getString("email", null);
        int post_id = getIntent().getIntExtra("post_id", 99);
        res = myDb.getSingleDairy(post_id);

        while (res.moveToNext()) {
            byte[] blob = res.getBlob(2);
            date = res.getString(3);
            headline = res.getString(4);
            desc = res.getString(5);
        }

        txt_headline.setText(headline);
        txt_date.setText(date);
        txt_desc.setText(desc);

        res.close();
    }
}