package com.iratsel.dailydoses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.iratsel.dailydoses.controllers.DairyController;
import com.iratsel.dailydoses.utils.Database;
import com.iratsel.dailydoses.utils.DatabaseHelper;
import com.iratsel.dailydoses.utils.Tag;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddDairyActivity extends AppCompatActivity {

    private TextInputEditText text_date, text_headline, text_desc;
    private Button btn_add;
    private Database database;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dairy);

        /* Database */
        database = new DatabaseHelper(this);
        DairyController.setDatabase(database);

        sharedPreferences = getSharedPreferences(Tag.SP, Context.MODE_PRIVATE);

        text_date = findViewById(R.id.field_date);
        text_headline = findViewById(R.id.field_headline);
        text_desc = findViewById(R.id.field_desc);
        btn_add = findViewById(R.id.btn_add);

        text_date.setText(getCurrentDate());

        btn_add.setOnClickListener(v -> {

            Intent goMainActivity = new Intent(AddDairyActivity.this, MainActivity.class);
            goMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            add();
            startActivity(goMainActivity);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        text_date.setText(getCurrentDate());
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    private boolean add() {
        String email = sharedPreferences.getString(Tag.EMAIL, null);
        String date = text_date.getText().toString();
        String headline = text_headline.getText().toString();
        String desc = text_desc.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("date", date);
        contentValues.put("headline", headline);
        contentValues.put("description", desc);

        long result = DairyController.getInstance().add(contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
}